package com.example.licenta.controller;

import com.example.licenta.exceptions.GeneralAdminException;
import com.example.licenta.model.AdminAnnouncementType;
import com.example.licenta.model.Announcement;
import com.example.licenta.model.GlobalDetails;
import com.example.licenta.model.User;
import com.example.licenta.model.dto.AdminAnnouncementRequestDto;
import com.example.licenta.service.AdminService;
import com.example.licenta.service.UserService;
import com.example.licenta.utils.ExcelGenerator;
import com.example.licenta.utils.ExcelHelper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.example.licenta.model.AdminAnnouncementType.LICENTA;
import static com.example.licenta.model.AdminAnnouncementType.STAGIU;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/admins")
@CrossOrigin(origins = "*")
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;

    public AdminController(AdminService adminService, UserService userService) {
        this.adminService = adminService;
        this.userService = userService;
    }
    @Resource
    EmailService emailService;
    @GetMapping("/email/{email}/{subject}/{message}")
    public ResponseEntity<?> sendEmail(@PathVariable String email,@PathVariable String subject,@PathVariable String message){

        emailService.sendSimpleMessage(email,subject, message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/accounts")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        String message;

        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                List<User> users = adminService.processExcel(file);
                for (var u : users) {
                    userService.save(u);
                }

                message = "Uploaded the file successfully: " + file.getOriginalFilename() + ". Processed "
                        + users.size() + " entries.";
                return ResponseEntity.status(HttpStatus.OK).body(message);
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Excel file required.");
    }

    @GetMapping("/students/{studentId}")
    public ResponseEntity<?> getStudentStatus(@PathVariable Long studentId) {
        if (studentId <= 0) {
            notFound();
        }

        try {
            return ok(adminService.getStudentStatus(studentId));
        } catch (GeneralAdminException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping("/students/all")
    public ResponseEntity<?> getDetailedReportForAllStudents(
            @RequestParam(value = "type", defaultValue = "json") String type) throws GeneralAdminException {
        var response = adminService.getDetailedReportForAllStudents();
        if ("json".equalsIgnoreCase(type)) {
            return ok(response);
        } else {

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "application/ms-excel");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());

            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=report" + currentDateTime + ".xlsx";
            headers.add(headerKey, headerValue);

            ExcelGenerator generator = new ExcelGenerator(response.students());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try {
                generator.generateExcelFile().write(outputStream);
            } catch (IOException e) {
                throw new GeneralAdminException("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
        }
    }

    @GetMapping("/announcements")
    public ResponseEntity<?> getAnnouncements(@RequestParam(value = "type", defaultValue = "ALL") String type) {
        if (STAGIU.name().equalsIgnoreCase(type)) {
            return ok(adminService.getAnnouncementsByType(STAGIU));
        } else if (LICENTA.name().equalsIgnoreCase(type)) {
            return ok(adminService.getAnnouncementsByType(LICENTA));
        } else {
            return ok(adminService.getAnnouncementsByType(AdminAnnouncementType.ALL));
        }
    }

    @PostMapping("/announcements/{type}")
    public ResponseEntity<?> addAnnouncement(@PathVariable String type, @RequestBody AdminAnnouncementRequestDto requestDto) {
        if (STAGIU.name().equalsIgnoreCase(type)) {
            return ok(adminService.addAnnouncement(STAGIU, requestDto));
        } else if (LICENTA.name().equalsIgnoreCase(type)) {
            return ok(adminService.addAnnouncement(LICENTA, requestDto));
        }
        return badRequest().build();
    }


    @PostMapping("/save-announcement")
    public ResponseEntity<?> saveEntity(@RequestBody Announcement announcement) {
        adminService.saveAnnouncement(announcement);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/save-global-details")
    public ResponseEntity<?> saveGlobalDetails(@RequestBody GlobalDetails globalDetails) {
        adminService.saveGlobalDetails(globalDetails);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/global-details")
    public ResponseEntity<?> getGlobalDetails() {
        return ok(adminService.getGlobalDetails());
    }

    @GetMapping("/grades")
    public ResponseEntity<?> getStudentsGrades() {

        return new ResponseEntity<>(adminService.getStudentsGrades(), HttpStatus.OK);
    }
}
