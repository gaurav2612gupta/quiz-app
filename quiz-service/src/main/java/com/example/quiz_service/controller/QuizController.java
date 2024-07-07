package com.example.quiz_service.controller;

import com.example.quiz_service.model.CustomQuiz;
import com.example.quiz_service.model.QuestionWrapper;
import com.example.quiz_service.model.QuizDto;
import com.example.quiz_service.model.Response;
import com.example.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
@CrossOrigin(origins = "http://localhost:3000")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<Integer> createQuiz(@RequestBody QuizDto quizDto) {
        return quizService.createQuiz(quizDto.getTopic(), quizDto.getNoOfQuestions(), quizDto.getQuizTitle());
    }

    @PostMapping("createcustom")
    public ResponseEntity<String> createCustomQuiz(@RequestBody CustomQuiz customQuiz) {
        return quizService.createCustomQuiz(customQuiz.getId(), customQuiz.getQuizTitle(), customQuiz.getQuestionList());
    }

    @GetMapping("getcustom/{id}")
    public ResponseEntity<List<QuestionWrapper>> getCustomQuiz(@PathVariable String id) {
        return quizService.getCustomQuiz(id);
    }

    @GetMapping("submitcustom")
    public ResponseEntity<Integer> submitCustomQuiz(@RequestBody CustomQuiz customQuiz) {
        return quizService.submitCustomQuiz(customQuiz.getCorrectResponseList(), customQuiz.getResponseList());
    }

    @PostMapping("getQuiz/{quizId}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer quizId) {
        return quizService.getQuizQuestions(quizId);
    }

    @PostMapping("submit/{quizId}")
        public  ResponseEntity<Integer> submitQuiz(@PathVariable Integer quizId, @RequestBody List<Response> responses) {
        return quizService.calculateResult(quizId, responses);
    }

}
