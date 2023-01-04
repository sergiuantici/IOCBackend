package com.example.licenta.service;

import com.example.licenta.repository.AcordRepository;
import com.example.licenta.repository.CoordonationRepository;
import com.example.licenta.repository.TeacherRepository;
import com.example.licenta.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StudentService {
    @Resource
    AcordRepository acordRepository;
    @Resource
    CoordonationRepository coordonationRepository;
    @Resource
    TeacherRepository teacherRepository;
    @Resource
    UserRepository userRepository;
}
