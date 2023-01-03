package com.example.licenta.controller;

import com.example.licenta.model.Acord;
import com.example.licenta.model.StudentTeacherId;
import com.example.licenta.model.User;
import com.example.licenta.service.CoordonatorService;
import com.example.licenta.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
    @GetMapping("/acord")
    public ResponseEntity<?> findAllAcords() {
        return new ResponseEntity<>(coordonatorService.findAllAcords(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLocuriLibere(@PathVariable Long id){
        return new ResponseEntity<>(coordonatorService.getLocuriLibere(id),HttpStatus.OK);
    }

}