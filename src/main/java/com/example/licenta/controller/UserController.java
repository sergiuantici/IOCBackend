package com.example.licenta.controller;

import com.example.licenta.model.User;
import com.example.licenta.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("users")
@CrossOrigin(origins = "*")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody User user) {
        return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
    }

    @GetMapping("/{user}/{password}")
    public ResponseEntity<?> findUser(@PathVariable String user,@PathVariable String password){
        User user1 = userService.findUser(user, password);
        if(user1!=null) {
            return new ResponseEntity<>(user1,HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping
    public ResponseEntity<?> findAllUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }
}
