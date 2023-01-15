package com.example.licenta.service;

import com.example.licenta.model.*;
import com.example.licenta.repository.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
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
    @Resource
    PracticeDocumentRepository practiceDocumentRepository;
    @Resource
    AnnouncementRepository announcementRepository;

    private final TaskRepository taskRepository;

    public CoordonatorService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void acceptRequest(StudentTeacherId studentTeacherId) {
        if (acordRepository.existsById(studentTeacherId)) {
            coordonationRepository.save(new Coordonare(studentTeacherId));
            acordRepository.delete(new Acord(studentTeacherId));
            //TeacherDetails referenceById = teacherRepository.getReferenceById(studentTeacherId.getTeacherId());
            TeacherDetails referenceById = teacherRepository.findByUserId(studentTeacherId.getTeacherId());
            referenceById.setLocuriLibere(referenceById.getLocuriLibere() - 1);
            teacherRepository.save(referenceById);
        }

    }

    public void receiveStudent(Long studentId, Long teacherId) {
        StudentTeacherId studentTeacherId = new StudentTeacherId(studentId, teacherId);
        coordonationRepository.save(new Coordonare(studentTeacherId));
        TeacherDetails referenceById = teacherRepository.findByUserId(studentTeacherId.getTeacherId());
        referenceById.setLocuriLibere(referenceById.getLocuriLibere() - 1);
        teacherRepository.save(referenceById);
    }

    public Acord getAcord(StudentTeacherId studentTeacherId) {
        Optional<Acord> byId = acordRepository.findById(studentTeacherId);
        if (byId.isEmpty())
            return null;
        else
            return byId.get();
    }

    public List<Acord> findAllAcords(Long teacherId) {
        return acordRepository.findAllByTeacherId(teacherId);
    }

    public Long getLocuriLibere(Long id) {
        return teacherRepository.getReferenceById(id).getLocuriLibere();
    }

    public List<User> getStudents(Long teacherId) {
        Stream<Long> longStream = acordRepository.findAllByTeacherId(teacherId).stream()
                .map(e -> e.getId().getStudentId());
        return userRepository.findAllById(longStream.collect(Collectors.toList()));
    }
    public List<User> getStudentsAccepted(Long teacherId) {
        Stream<Long> longStream = coordonationRepository.findByTeacherId(teacherId).stream()
                .map(e -> e.getId().getStudentId());
        return userRepository.findAllById(longStream.collect(Collectors.toList()));
    }

    public List<TeacherDetails> getTeachers() {
        return teacherRepository.findAll();
    }

    public String getThemesInteres(Long id) {
        TeacherDetails referenceById = teacherRepository.findByUserId(id);
        return referenceById.getTemeInteres();
    }

    public void updateThemesInteres(Long id, String themesInteres) {
        TeacherDetails referenceById = teacherRepository.findByUserId(id);
        referenceById.setTemeInteres(themesInteres);
        teacherRepository.save(referenceById);
    }

    public List<User> getAcceptedStudents(Long teacherId) {
        List<Coordonare> studentsTeachers = coordonationRepository.findAll();
        Stream<Long> studentsForTeacherStream = studentsTeachers.stream()
                .filter((coordonare -> Objects.equals(coordonare.getId().getTeacherId(), teacherId)))
                .map(coordonare -> coordonare.getId().getStudentId());
        return userRepository.findAllById(studentsForTeacherStream.collect(Collectors.toList()));
    }

    public PracticeDocument savePracticeDocument(PracticeDocument practiceDocument) {
        return practiceDocumentRepository.save(practiceDocument);
    }

    public Task saveAssignment(Task task) {
        return taskRepository.save(task);
    }

    public List<Announcement> getAnnouncements() {
        return announcementRepository.findAll();
    }

    public List<Task> getTasks(Long teacherId) {
        return taskRepository.findAllByTeacherIdWithDocument(teacherId);
    }
}
