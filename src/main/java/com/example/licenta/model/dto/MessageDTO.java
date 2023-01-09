package com.example.licenta.model.dto;
import lombok.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class MessageDTO {
    Long messageId;
    Long fromId,toId;
    String text;
    LocalDateTime time;
    String lastnameFromId, lastnameToId, firstnameFromId, firstnameToId;


}
