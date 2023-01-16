package com.example.licenta.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageRequestDto {
    Long fromId;
    Long toId;
    String Message;
}
