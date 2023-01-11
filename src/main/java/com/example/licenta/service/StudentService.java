package com.example.licenta.service;

import com.example.licenta.exceptions.RequestsLimitReachedException;
import com.example.licenta.exceptions.StudentNotFoundException;
import com.example.licenta.model.*;
import com.example.licenta.model.dto.EvaluationDto;
import com.example.licenta.model.dto.PracticeDetailsDto;
import com.example.licenta.repository.*;
import com.example.licenta.requests.SolicitareAcordRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Resource
    AcordRepository acordRepository;
    @Resource
    CoordonationRepository coordonationRepository;
    @Resource
    TeacherRepository teacherRepository;
    @Resource
    UserRepository userRepository;
    @Resource
    AnnouncementRepository announcementRepository;
    @Resource
    GlobalDetailsRepository globalDetailsRepository;
    @Resource
    SolicitareAcordRepository solicitareAcordRepository;
    @Resource
    StudentRepository studentRepository;
    @Resource
    TaskRepository taskRepository;

    private Long getNumberOfSentRequests(Long studentId) {
        return solicitareAcordRepository
                .findAll()
                .stream()
                .filter(x -> x.getTime().isAfter(LocalDateTime.now().minusWeeks(1))
                        &&
                        x.getId().getStudentId() == studentId)
                .count();
    }

    public void sendRequest(SolicitareAcordRequest solicitareAcordRequest) throws RequestsLimitReachedException {
        Long countOfRequestsMade = getNumberOfSentRequests(solicitareAcordRequest.getStudentId());

        if (countOfRequestsMade >= 3) {
            throw new RequestsLimitReachedException("This student has already made 3 requests this week.");
        }

        StudentTeacherId studentTeacherId = new StudentTeacherId(solicitareAcordRequest.getStudentId(),
                solicitareAcordRequest.getTeacherId());
        String fileURL = solicitareAcordRequest.getFileURL();
        solicitareAcordRepository.save(new SoliciareAcord(studentTeacherId, fileURL, LocalDateTime.now()));
    }

    public Long getRequestCount(Long studentId) {
        return getNumberOfSentRequests(studentId);
    }

    public Boolean isCoordinated(Long studentId) {
        return coordonationRepository.existsByStudentId(studentId);
    }

    public TeacherDetails getCoordinatorForStudent(Long studentId) {
        Long teacherId = coordonationRepository.findTeacherIdByStudentId(studentId);
        return teacherRepository.findByUserId(teacherId);
    }

    public List<Announcement> getAnnouncements() {
        return announcementRepository.findAll();
    }

    public PracticeDetailsDto getLatestDetails(Long studentId) throws StudentNotFoundException {
        PracticeDetailsDto practiceDetailsDto = new PracticeDetailsDto();
        GlobalDetails globalDetails = globalDetailsRepository.findFirstByOrderByIdDesc();
        practiceDetailsDto.setGlobalDetails(globalDetails);

        StudentDetails studentDetails = studentRepository.findByUserId(studentId);

        practiceDetailsDto.setExecutedHours(studentDetails.getExecutedHours());
        practiceDetailsDto.setRemainingHours(globalDetails.getPracticeHoursTotal()-studentDetails.getExecutedHours());

        TeacherDetails teacherDetails = getCoordinatorForStudent(studentId);
        practiceDetailsDto.setCoordonator(teacherDetails.getUser().getFirstName()+" "+teacherDetails.getUser().getLastName());
        practiceDetailsDto.setTasks(taskRepository.findAllByStudentTeacherid(new StudentTeacherId(studentId,teacherDetails.getUser().getId())));

        return practiceDetailsDto;
    }

    public void turnInTask(Long taskId,String documentUrl){
        Task task = taskRepository.findById(taskId).get();
        task.getDocumentUrls().add(documentUrl);
        taskRepository.save(task);
    }

    public EvaluationDto getEvaluation(Long studentId){
        EvaluationDto evaluationDto = new EvaluationDto();
        StudentDetails studentDetails = studentRepository.findByUserId(studentId);
        evaluationDto.setNormalGrade(studentDetails.getNormalGrade());
        evaluationDto.setReTakeGrade(studentDetails.getReTakeGrade());

        TeacherDetails teacherDetails = getCoordinatorForStudent(studentId);
        evaluationDto.setEvaluationCriteria(teacherDetails.getPracticeCriteria());
        return evaluationDto;
    }


}
