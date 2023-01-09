package com.example.licenta.controller;
import com.example.licenta.model.User;
import com.example.licenta.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("messages")
public class MessageController {
    @Resource
    private MessageService messageService;



    @GetMapping("/{idFrom}/{idTo}")
    public ResponseEntity<?> findMessagesBetweenTwoUsers(@PathVariable Long idFrom,@PathVariable Long idTo){
        return new ResponseEntity<>(messageService.findAllMessagesBetweenTwoIds(idFrom,idTo),HttpStatus.OK);

    }

}
