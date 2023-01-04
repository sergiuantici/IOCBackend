package com.example.licenta.controller;

import com.example.licenta.service.CoordonatorService;
import com.example.licenta.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("student")
@CrossOrigin(origins = "*")
public class StudentController {
    @Resource
    private StudentService studentService;
}
