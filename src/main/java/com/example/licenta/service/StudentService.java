package com.example.licenta.service;

import java.util.List;

import com.example.licenta.exceptions.RequestsLimitReachedException;
import com.example.licenta.model.Acord;
import com.example.licenta.model.SoliciareAcord;
import com.example.licenta.model.StudentTeacherId;
import com.example.licenta.model.TeacherDetails;
import com.example.licenta.model.Announcement;
import com.example.licenta.repository.*;
import com.example.licenta.requests.SolicitareAcordRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

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
    SolicitareAcordRepository solicitareAcordRepository;

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
}
