package com.example.licenta.controller;

import com.example.licenta.exceptions.RequestsLimitReachedException;
import com.example.licenta.requests.SolicitareAcordRequest;
import com.example.licenta.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("student")
@CrossOrigin(origins = "*")
public class StudentController {
    @Resource
    private StudentService studentService;

    @PostMapping("/request")
    public ResponseEntity<?> sendRequest(@RequestBody SolicitareAcordRequest solicitareAcordRequest) {
        try {
            studentService.sendRequest(solicitareAcordRequest);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (RequestsLimitReachedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{studentId}/request-count")
    public ResponseEntity<?> getRequestCount(@PathVariable Long studentId) {
        return new ResponseEntity<>(studentService.getRequestCount(studentId), HttpStatus.OK);
    }

    @GetMapping("/{studentId}/is-coordinated")
    public ResponseEntity<?> isCoordinated(@PathVariable Long studentId) {
        return new ResponseEntity<>(studentService.isCoordinated(studentId), HttpStatus.OK);
    }

    @GetMapping("/{studentId}/coordinator")
    public ResponseEntity<?> getCoordinator(@PathVariable Long studentId) {
        return new ResponseEntity<>(studentService.getCoordinatorForStudent(studentId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAnnouncement() {
        return new ResponseEntity<>(studentService.getAnnouncements(), HttpStatus.OK);
    }
}
