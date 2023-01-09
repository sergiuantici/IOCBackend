package com.example.licenta.controller;

import com.example.licenta.exceptions.GeneralAdminException;
import com.example.licenta.model.User;
import com.example.licenta.model.dto.StudentStatusDto;
import com.example.licenta.service.AdminService;
import com.example.licenta.service.UserService;
import com.example.licenta.utils.ExcelHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;

    public AdminController(AdminService adminService, UserService userService) {
        this.adminService = adminService;
        this.userService = userService;
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

                message = "Uploaded the file successfully: " + file.getOriginalFilename() + ". Processed " + users.size() + " entries.";
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
}
