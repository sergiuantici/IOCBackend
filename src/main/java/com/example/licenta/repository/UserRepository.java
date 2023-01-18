package com.example.licenta.repository;

import com.example.licenta.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

        public User findUserByEmailAndPassword(String email,String password);

        @Query("select u from User u where u.role = 'admin'")
        public User findAdminAccount();

        @Query("select u from User u where u.role != 'admin'")
        public List<User> findAllUsers();
}
