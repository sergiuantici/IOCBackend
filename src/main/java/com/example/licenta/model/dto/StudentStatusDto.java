package com.example.licenta.model.dto;


import com.example.licenta.model.Acord;
import com.example.licenta.model.User;

import java.util.List;

public record StudentStatusDto(User user, List<Acord> agreementRequests, User coordinator) {
}
