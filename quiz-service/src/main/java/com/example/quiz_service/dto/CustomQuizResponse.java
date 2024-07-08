package com.example.quiz_service.dto;

import com.example.quiz_service.model.QuestionWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CustomQuizResponse {
    private String quizTitle;
    private List<QuestionWrapper> questions;
}
