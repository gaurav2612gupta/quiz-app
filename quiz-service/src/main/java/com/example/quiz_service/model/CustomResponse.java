package com.example.quiz_service.model;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Embeddable
public class CustomResponse {
    private String question;
    private String response;
}
