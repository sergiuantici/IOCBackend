package com.example.licenta.controller;

import com.example.licenta.exceptions.NoCoordinatorException;
import com.example.licenta.exceptions.RequestsLimitReachedException;
import com.example.licenta.exceptions.StudentNotFoundException;
import com.example.licenta.model.Acord;
import com.example.licenta.model.dto.SendMessageRequestDto;
import com.example.licenta.model.dto.TaskDocumentDto;
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

    @GetMapping("/get-all")
    public ResponseEntity<?> getStudents() {
        return new ResponseEntity<>(studentService.getStudents(), HttpStatus.OK);
    }

    @GetMapping("/without-coordinator")
    public ResponseEntity<?> getStudentsWithoutCoordinator() {
        return new ResponseEntity<>(studentService.getStudentsWithoutCoordinator(), HttpStatus.OK);
    }

    @PostMapping("/request")
    public ResponseEntity<?> sendRequest(@RequestBody Acord solicitareAcordRequest) {
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

    @GetMapping("/{studentId}/messages")
    public ResponseEntity<?> getMessages(@PathVariable Long studentId) {
        try {
            return new ResponseEntity<>(studentService.getMessagesForStudentAndTeacher(studentId), HttpStatus.OK);
        } catch (NoCoordinatorException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/messages/sendMessage")
    public ResponseEntity<?> sendMessage(@RequestBody SendMessageRequestDto sendMessageRequest) {
        return new ResponseEntity<>(studentService.sendMessage(sendMessageRequest.getFromId(), sendMessageRequest.getToId(), sendMessageRequest.getMessage()), HttpStatus.OK);
    }

    @GetMapping("/{studentId}/stage-details")
    public ResponseEntity<?> getLatestStageDetails(@PathVariable Long studentId) {
        try {
            return new ResponseEntity<>(studentService.getLatestDetails(studentId), HttpStatus.OK);
        } catch (StudentNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/task/{taskId}")
    public ResponseEntity<?> turnInTask(@RequestBody TaskDocumentDto taskDocumentDto, @PathVariable Long taskId) {
        studentService.turnInTask(taskId, taskDocumentDto.getDocumentUrl());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{studentId}/criteria")
    public ResponseEntity<?> getEvaluationCriteria(@PathVariable Long studentId) {
        return new ResponseEntity<>(studentService.getEvaluation(studentId), HttpStatus.OK);
    }

    @GetMapping("/announcements-admin")
    public ResponseEntity<?> getEvaluationCriteria() {
        return new ResponseEntity<>(studentService.getAnnouncements(), HttpStatus.OK);
    }

    @GetMapping("/status-cereri/{studentId}")
    public ResponseEntity<?> getStatusCereri(@PathVariable Long studentId) {
        return new ResponseEntity<>(studentService.getStatusCereri(studentId), HttpStatus.OK);
    }
}
