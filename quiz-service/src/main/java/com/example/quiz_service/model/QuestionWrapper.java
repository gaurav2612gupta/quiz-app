package com.example.quiz_service.model;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Embeddable
@RequiredArgsConstructor
public class QuestionWrapper {
    private Integer id;
    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;

    public QuestionWrapper(Integer id, String option4, String option3, String option2, String questionTitle, String option1) {
        this.id = id;
        this.questionTitle = questionTitle;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
    }
}