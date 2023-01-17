package com.example.licenta.model.dto;

import com.example.licenta.model.Message;
import com.example.licenta.model.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAdminMessagesForStudentResponseDto {
    User student;
    Long adminId;
    List<Message> messages;
}
