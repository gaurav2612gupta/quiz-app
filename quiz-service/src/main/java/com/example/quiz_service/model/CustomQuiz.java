package com.example.quiz_service.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class CustomQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String quizTitle;

    @ElementCollection
    private List<QuestionWrapper> questionList;

    @ElementCollection
    private List<CustomResponse> correctResponseList;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
