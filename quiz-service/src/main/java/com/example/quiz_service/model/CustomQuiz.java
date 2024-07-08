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

//    @CollectionTable(name = "question_list", joinColumns = @JoinColumn(name = "custom_quiz_id"))
    @ElementCollection
    private List<QuestionWrapper> questionList;

//    @CollectionTable(name = "correct_response_list", joinColumns = @JoinColumn(name = "custom_quiz_id"))
    @ElementCollection
    private List<CustomResponse> correctResponseList;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
