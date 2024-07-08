package com.example.quiz_service.controller;

import com.example.quiz_service.dto.CustomQuizResponse;
import com.example.quiz_service.model.*;
import com.example.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<Integer> createQuiz(@RequestBody QuizDto quizDto) {
        return quizService.createQuiz(quizDto.getTopic(), quizDto.getNoOfQuestions(), quizDto.getQuizTitle());
    }

    @PostMapping("getQuiz/{quizId}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer quizId) {
        return quizService.getQuizQuestions(quizId);
    }

    @PostMapping("submit/{quizId}")
        public  ResponseEntity<Integer> submitQuiz(@PathVariable Integer quizId, @RequestBody List<Response> responses) {
        return quizService.calculateResult(quizId, responses);
    }

    @PostMapping("createcustom")
    public ResponseEntity<String> createCustomQuiz(@RequestBody CustomQuiz customQuiz) {
        return quizService.createCustomQuiz(customQuiz.getId(), customQuiz.getQuizTitle(), customQuiz.getQuestionList(), customQuiz.getCorrectResponseList());
    }

    @GetMapping("/getcustom/{id}")
    public ResponseEntity<CustomQuizResponse> getCustomQuiz(@PathVariable String id) {
        List<CustomQuiz> customQuizzes = quizService.getCustomQuiz(id).getBody();
        if (customQuizzes == null || customQuizzes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CustomQuiz customQuiz = customQuizzes.get(0);
        String quizTitle = customQuiz.getQuizTitle();
        List<QuestionWrapper> questions = customQuiz.getQuestionList();

        CustomQuizResponse response = new CustomQuizResponse(quizTitle, questions);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("submitcustom/{customQuizId}")
    public ResponseEntity<Integer> submitCustomQuiz(@RequestBody List<CustomResponse> responses, @PathVariable String customQuizId) {
        return quizService.submitCustomQuiz(customQuizId, responses);
    }

}
