package com.example.licenta.service;

import com.example.licenta.model.Acord;
import com.example.licenta.model.Coordonare;
import com.example.licenta.model.StudentTeacherId;
import com.example.licenta.model.TeacherDetails;
import com.example.licenta.repository.AcordRepository;
import com.example.licenta.repository.CoordonationRepository;
import com.example.licenta.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class CoordonatorService {

    @Resource
    AcordRepository acordRepository;
    @Resource
    CoordonationRepository coordonationRepository;
    @Resource
    TeacherRepository teacherRepository;
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

    public List<Acord> findAllAcords() {
        return acordRepository.findAll();
    }

    public Long getLocuriLibere(Long id) {
        return teacherRepository.getReferenceById(id).getLocuriLibere();
    }
}
