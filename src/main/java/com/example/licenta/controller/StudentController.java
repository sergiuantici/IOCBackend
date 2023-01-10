package com.example.licenta.controller;

import com.example.licenta.exceptions.RequestsLimitReachedException;
import com.example.licenta.exceptions.StudentNotFoundException;
import com.example.licenta.model.dto.TaskDocumentDto;
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

    @GetMapping("/{studentId}/stage-details")
    public ResponseEntity<?> getLatestStageDetails(@PathVariable Long studentId){
        try{
            return new ResponseEntity<>(studentService.getLatestDetails(studentId),HttpStatus.OK);
        }catch (StudentNotFoundException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/task/{taskId}")
    public ResponseEntity<?> turnInTask(@RequestBody TaskDocumentDto taskDocumentDto, @PathVariable Long taskId){
        studentService.turnInTask(taskId,taskDocumentDto.getDocumentUrl());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{studentId}/criteria")
    public ResponseEntity<?> getEvaluationCriteria(@PathVariable Long studentId){
        return new ResponseEntity<>(studentService.getEvaluation(studentId),HttpStatus.OK);
    }
}
