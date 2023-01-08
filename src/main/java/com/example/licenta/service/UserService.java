package com.example.licenta.service;

import com.example.licenta.model.User;
import com.example.licenta.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class UserService{
    @Resource
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id){
        return userRepository.findById(id).get();
    }
}
