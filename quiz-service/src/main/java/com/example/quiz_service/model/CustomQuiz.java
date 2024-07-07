package com.example.quiz_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class CustomQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String quizTitle;

    @ElementCollection
    @CollectionTable(name = "question_list", joinColumns = @JoinColumn(name = "custom_quiz_id"))
    private List<QuestionWrapper> questionList;

    @ElementCollection
    @CollectionTable(name = "response_list", joinColumns = @JoinColumn(name = "custom_quiz_id"))
    private List<Response> responseList;

    @ElementCollection
    @CollectionTable(name = "correct_response_list", joinColumns = @JoinColumn(name = "custom_quiz_id"))
    private List<Response> correctResponseList;
}
