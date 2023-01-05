package com.example.licenta.service;

import com.example.licenta.model.*;
import com.example.licenta.repository.AcordRepository;
import com.example.licenta.repository.CoordonationRepository;
import com.example.licenta.repository.TeacherRepository;
import com.example.licenta.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CoordonatorService {

    @Resource
    AcordRepository acordRepository;
    @Resource
    CoordonationRepository coordonationRepository;
    @Resource
    TeacherRepository teacherRepository;
    @Resource
    UserRepository userRepository;
    public void acceptRequest(StudentTeacherId studentTeacherId) {
        if (acordRepository.existsById(studentTeacherId)) {
            coordonationRepository.save(new Coordonare(studentTeacherId));
            acordRepository.delete(new Acord(studentTeacherId));
            TeacherDetails referenceById = teacherRepository.getReferenceById(studentTeacherId.getTeacherId());
            referenceById.setLocuriLibere(referenceById.getLocuriLibere()-1);
            teacherRepository.save(referenceById);
        }

    }

    public Acord getAcord(StudentTeacherId studentTeacherId) {
        Optional<Acord> byId = acordRepository.findById(studentTeacherId);
        if(byId.isEmpty())
            return null;
        else return byId.get();
    }

    public List<Acord> findAllAcords(Long teacherId) {
        return acordRepository.findAllByTeacherId(teacherId);
    }

    public Long getLocuriLibere(Long id) {
        return teacherRepository.getReferenceById(id).getLocuriLibere();
    }

    public List<User> getStudents(Long teacherId) {
        Stream<Long> longStream = acordRepository.findAllByTeacherId(teacherId).stream().map(e -> e.getId().getStudentId());
        return userRepository.findAllById(longStream.collect(Collectors.toList()));
    }

    public List<TeacherDetails> getTeachers(){
        return teacherRepository.findAll();
    }

    public String getThemesInteres(Long id){
        TeacherDetails referenceById =  teacherRepository.getReferenceById(id);
        return referenceById.getTemeInteres();
    }

    public void updateThemesInteres(Long id, String themesInteres){
        TeacherDetails referenceById =  teacherRepository.getReferenceById(id);
        referenceById.setTemeInteres(themesInteres);
        teacherRepository.save(referenceById);
    }
}
