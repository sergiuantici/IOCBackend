package com.example.licenta.service;

import com.example.licenta.exceptions.GeneralAdminException;
import com.example.licenta.model.*;
import com.example.licenta.model.dto.*;
import com.example.licenta.repository.*;
import com.example.licenta.utils.ExcelHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminService {

    private final UserRepository userRepository;
    private final AcordRepository acordRepository;
    private final CoordonationRepository coordonationRepository;
    private final AnnouncementRepository announcementRepository;
    private final GlobalDetailsRepository globalDetailsRepository;
    private final AdminAnnouncementRepository adminAnnouncementRepository;
    private final StudentRepository studentRepository;

    public AdminService(UserRepository userRepository, AcordRepository acordRepository,
                        CoordonationRepository coordonationRepository, AnnouncementRepository announcementRepository, GlobalDetailsRepository globalDetailsRepository, AdminAnnouncementRepository adminAnnouncementRepository, StudentRepository studentRepository) {
        this.userRepository = userRepository;
        this.acordRepository = acordRepository;
        this.coordonationRepository = coordonationRepository;
        this.announcementRepository = announcementRepository;
        this.globalDetailsRepository = globalDetailsRepository;
        this.adminAnnouncementRepository = adminAnnouncementRepository;
        this.studentRepository = studentRepository;
    }

    public List<User> processExcel(MultipartFile file) throws IOException {
        return ExcelHelper.excelToUsers(file.getInputStream());
    }

    public StudentStatusDto getStudentStatus(Long studentId) throws GeneralAdminException {
        var student = userRepository.findById(studentId)
                .orElseThrow(() -> new GeneralAdminException("Student not found", HttpStatus.NOT_FOUND));

        return getStudentStatusDto(student);
    }

    private StudentStatusDto getStudentStatusDto(User student) {
        var agreementRequests = acordRepository.findAllById_StudentId(student.getId());

        // In case the student do not have a coordinator, this field will be null
        var coordinatorId = coordonationRepository.findFirstById_StudentId(student.getId());
        User coordinator = null;
        if (coordinatorId != null) {
            coordinator = userRepository.findById(coordinatorId.getId().getTeacherId()).orElse(null);
        }

        return new StudentStatusDto(student, agreementRequests, coordinator);
    }

    public DetailedStudentReportDto getDetailedReportForAllStudents() {

        List<StudentStatusDto> studentStatuses = userRepository.findAll()
                .stream()
                .filter(user -> "student".equalsIgnoreCase(user.getRole())
                        || "user".equalsIgnoreCase(user.getRole()))
                .map(this::getStudentStatusDto)
                .toList();

        return new DetailedStudentReportDto(studentStatuses);
    }

    public void saveAnnouncement(Announcement announcement) {
        announcementRepository.save(announcement);
    }

    public void saveGlobalDetails(GlobalDetails globalDetails) {
        if (!globalDetailsRepository.findAll().isEmpty()) //mai intai sterg tot ce in db
            globalDetailsRepository.deleteAll();

        //adaug din nou in db
        globalDetailsRepository.save(globalDetails);
    }

    public GlobalDetails getGlobalDetails(){
        return globalDetailsRepository.findAll().get(0); //returneaza primul element din db
    }

    public List<AdminAnnouncementResponseDto> getAnnouncementsByType(AdminAnnouncementType type) {
        if (type == AdminAnnouncementType.ALL) {
            return adminAnnouncementRepository.findAll()
                    .stream()
                    .map(adminAnnouncement ->
                            new AdminAnnouncementResponseDto(adminAnnouncement.getMessage(), adminAnnouncement.getCreated()))
                    .toList();
        } else {
            return adminAnnouncementRepository.findAllByType(type)
                    .stream()
                    .map(adminAnnouncement ->
                            new AdminAnnouncementResponseDto(adminAnnouncement.getMessage(), adminAnnouncement.getCreated()))
                    .toList();
        }
    }

    public AdminAnnouncementResponseDto addAnnouncement(AdminAnnouncementType type, AdminAnnouncementRequestDto requestDto) {
        var entity = new AdminAnnouncement(requestDto.message(), type);

        var result = adminAnnouncementRepository.save(entity);
        return new AdminAnnouncementResponseDto(result.getMessage(), result.getCreated());
    }

    public List<StudentsGradesDto> getStudentsGrades() {
        List<StudentDetails> studentsDetails = new ArrayList<>(this.studentRepository.findAll());
        return studentsDetails.stream()
                .map(studentDetails ->
                        new StudentsGradesDto(studentDetails.getNormalGrade(), studentDetails.getReTakeGrade(),
                                studentDetails.getUser().getFirstName(), studentDetails.getUser().getLastName()))
                .collect(Collectors.toList());
    }


    public List<User> getAdminContacts() {
        return userRepository.findAllUsers();
    }
}
