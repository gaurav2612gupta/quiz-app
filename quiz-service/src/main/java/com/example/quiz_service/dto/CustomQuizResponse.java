package com.example.quiz_service.dto;

import com.example.quiz_service.model.QuestionWrapper;
import jakarta.persistence.ElementCollection;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CustomQuizResponse {
    private String quizTitle;
    @ElementCollection
    private List<QuestionWrapper> questions;
}
