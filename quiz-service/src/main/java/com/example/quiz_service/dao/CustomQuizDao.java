package com.example.quiz_service.dao;

import com.example.quiz_service.model.CustomQuiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomQuizDao extends JpaRepository<CustomQuiz, String> {
}
