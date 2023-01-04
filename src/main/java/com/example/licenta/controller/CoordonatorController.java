package com.example.licenta.controller;

import com.example.licenta.model.Acord;
import com.example.licenta.model.StudentTeacherId;
import com.example.licenta.model.User;
import com.example.licenta.service.CoordonatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("coordonator")
@CrossOrigin(origins = "*")
public class CoordonatorController {
    @Resource
    private CoordonatorService coordonatorService;

    @PostMapping("/accept")
    public ResponseEntity<?> acceptRequest(@RequestBody StudentTeacherId studentTeacherId) {
        coordonatorService.acceptRequest(studentTeacherId);
        return new ResponseEntity<>("bine", HttpStatus.ACCEPTED);
    }

    @PostMapping("/acord")
    public ResponseEntity<?> getAcord(@RequestBody StudentTeacherId studentTeacherId){
        Acord acord = coordonatorService.getAcord(studentTeacherId);
        return new ResponseEntity<>(acord, HttpStatus.ACCEPTED);
    }
    @GetMapping("/acord/{teacherId}")
    public ResponseEntity<?> findAllAcords(@PathVariable Long teacherId) {
        return new ResponseEntity<>(coordonatorService.findAllAcords(teacherId), HttpStatus.OK);
    }

    @GetMapping("/students/{teacherId}")
    public ResponseEntity<?> findAllStudents(@PathVariable Long teacherId) {

        return new ResponseEntity<>(coordonatorService.getStudents(teacherId), HttpStatus.OK);
    }

//    @GetMapping("/acord")
//    public ResponseEntity<?> findAllAcords() {
//        List<User> users=coordonatorService.getStudents();
//        return new ResponseEntity<>(coordonatorService.findAllAcords(teacherId), HttpStatus.OK);
//    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getLocuriLibere(@PathVariable Long id){
        return new ResponseEntity<>(coordonatorService.getLocuriLibere(id),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getTeachers(){
        return new ResponseEntity<>(coordonatorService.getTeachers(), HttpStatus.OK);
    }
}