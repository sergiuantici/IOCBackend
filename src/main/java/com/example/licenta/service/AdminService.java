package com.example.licenta.service;

import com.example.licenta.exceptions.GeneralAdminException;
import com.example.licenta.model.Announcement;
import com.example.licenta.model.GlobalDetails;
import com.example.licenta.model.User;
import com.example.licenta.model.dto.DetailedStudentReportDto;
import com.example.licenta.model.dto.StudentStatusDto;
import com.example.licenta.repository.*;
import com.example.licenta.utils.ExcelHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class AdminService {

    private final UserRepository userRepository;
    private final SolicitareAcordRepository solicitareAcordRepository;
    private final CoordonationRepository coordonationRepository;
    private final AnnouncementRepository announcementRepository;
    private final GlobalDetailsRepository globalDetailsRepository;

    public AdminService(UserRepository userRepository, SolicitareAcordRepository solicitareAcordRepository,
            CoordonationRepository coordonationRepository, AnnouncementRepository announcementRepository,GlobalDetailsRepository globalDetailsRepository) {
        this.userRepository = userRepository;
        this.solicitareAcordRepository = solicitareAcordRepository;
        this.coordonationRepository = coordonationRepository;
        this.announcementRepository = announcementRepository;
        this.globalDetailsRepository = globalDetailsRepository;
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
        var agreementRequests = solicitareAcordRepository.findAllById_StudentId(student.getId());

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

    public void saveGlobalDetails(GlobalDetails globalDetails){
        globalDetailsRepository.save(globalDetails);
    }
}
