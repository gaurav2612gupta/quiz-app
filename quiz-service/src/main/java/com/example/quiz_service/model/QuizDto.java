package com.example.quiz_service.model;

import lombok.Data;

@Data
public class QuizDto {
    private String topic;
    private int noOfQuestions;
    private String quizTitle;
}
