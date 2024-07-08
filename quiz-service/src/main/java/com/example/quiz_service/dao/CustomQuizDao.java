package com.example.quiz_service.dao;

import com.example.quiz_service.model.CustomQuiz;
import com.example.quiz_service.model.QuestionWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface CustomQuizDao extends JpaRepository<CustomQuiz, String> {
    List<CustomQuiz> findByCreatedAt(LocalDateTime decodedTimestamp);
}
