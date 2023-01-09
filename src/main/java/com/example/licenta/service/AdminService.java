package com.example.licenta.service;

import com.example.licenta.exceptions.GeneralAdminException;
import com.example.licenta.model.User;
import com.example.licenta.model.dto.StudentStatusDto;
import com.example.licenta.repository.CoordonationRepository;
import com.example.licenta.repository.SolicitareAcordRepository;
import com.example.licenta.repository.UserRepository;
import com.example.licenta.utils.ExcelHelper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final SolicitareAcordRepository solicitareAcordRepository;
    private final CoordonationRepository coordonationRepository;

    public AdminService(UserRepository userRepository, SolicitareAcordRepository solicitareAcordRepository, CoordonationRepository coordonationRepository) {
        this.userRepository = userRepository;
        this.solicitareAcordRepository = solicitareAcordRepository;
        this.coordonationRepository = coordonationRepository;
    }

    public List<User> processExcel(MultipartFile file) throws IOException {
        return ExcelHelper.excelToUsers(file.getInputStream());
    }

    public StudentStatusDto getStudentStatus(Long studentId) throws GeneralAdminException {
        var student = userRepository.findById(studentId).orElseThrow(() -> new GeneralAdminException("Student not found", HttpStatus.NOT_FOUND));

        var agreementRequests = solicitareAcordRepository.findAllById_StudentId(student.getId());

        // In case the student do not have a coordinator, this field will be null
        var coordinatorId = coordonationRepository.findFirstById_StudentId(student.getId());
        User coordinator = null;
        if (coordinatorId != null) {
            coordinator = userRepository.findById(coordinatorId.getId().getTeacherId()).orElse(null);
        }

        return new StudentStatusDto(student, agreementRequests, coordinator);
    }
}
