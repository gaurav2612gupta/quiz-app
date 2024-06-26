package com.example.question_service.dao;

import com.example.question_service.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QuestionDao extends JpaRepository<Question,Integer> {

    List<Question> findByTopic(String category);

    @Query(value = "SELECT q.id FROM question q where q.topic=:topic ORDER BY RANDOM() LIMIT :noOfQuestions", nativeQuery = true)
    List<Integer> findRandomQuestionsByCategory(String topic, int noOfQuestions);
}
