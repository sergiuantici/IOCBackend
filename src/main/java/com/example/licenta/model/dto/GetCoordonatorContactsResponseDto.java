package com.example.licenta.model.dto;

import com.example.licenta.model.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCoordonatorContactsResponseDto {
    User teacher;
    List<User> students;
}
