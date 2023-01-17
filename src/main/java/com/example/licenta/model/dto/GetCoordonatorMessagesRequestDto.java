package com.example.licenta.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCoordonatorMessagesRequestDto {
    Long teacherId;
    Long studentId;
}
