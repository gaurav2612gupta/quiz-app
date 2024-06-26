package com.example.quiz_service.service;

import com.example.quiz_service.dao.QuizDao;
import com.example.quiz_service.feign.QuizInterface;
import com.example.quiz_service.model.QuestionWrapper;
import com.example.quiz_service.model.Quiz;
import com.example.quiz_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String topic, int noOfQuestions, String quizTitle) {
        try {
            List<Integer> questions = quizInterface.getQuestionsForQuiz(topic, noOfQuestions).getBody();
            Quiz quiz = new Quiz();
            quiz.setTitle(quizTitle);
            quiz.setQuestionIds(questions);
            quizDao.save(quiz);

            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error "+  e, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer quizId) {
        Optional<Quiz> quiz = quizDao.findById(quizId);
        List<QuestionWrapper> questions = quizInterface.getQuestionsFromId(quiz.get().getQuestionIds()).getBody();

        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer quizId, List<Response> responses) {
        Integer correctResponses = quizInterface.getScore(responses).getBody();
        return new ResponseEntity<>(correctResponses, HttpStatus.OK);
    }
}
