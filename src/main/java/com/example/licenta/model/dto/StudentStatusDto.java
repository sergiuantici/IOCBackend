package com.example.licenta.model.dto;


import com.example.licenta.model.SoliciareAcord;
import com.example.licenta.model.User;

import java.util.List;

public record StudentStatusDto(User user, List<SoliciareAcord> agreementRequests, User coordinator) {
}
