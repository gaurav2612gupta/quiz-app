package com.example.quiz_service.service;

import com.example.quiz_service.dao.CustomQuizDao;
import com.example.quiz_service.dao.QuizDao;
import com.example.quiz_service.feign.QuizInterface;
import com.example.quiz_service.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizService {

    private static final Logger log = LoggerFactory.getLogger(QuizService.class);
    @Autowired
    private QuizDao quizDao;

    @Autowired
    private CustomQuizDao customQuizDao;

    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<Integer> createQuiz(String topic, int noOfQuestions, String quizTitle) {
        try {
            List<Integer> questions = quizInterface.getQuestionsForQuiz(topic, noOfQuestions).getBody();
            Quiz quiz = new Quiz();
            quiz.setTitle(quizTitle);
            quiz.setQuestionIds(questions);
            quizDao.save(quiz);

            return new ResponseEntity<>(quiz.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(-1, HttpStatus.BAD_REQUEST);
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

    public ResponseEntity<String> createCustomQuiz(Integer quizId, String quizTitle, List<QuestionWrapper> questionList, List<CustomResponse> correctResponseList) {
        CustomQuiz customQuiz = new CustomQuiz();
        customQuiz.setId(quizId);
        customQuiz.setQuizTitle(quizTitle);
        customQuiz.setQuestionList(questionList);
        customQuiz.setCorrectResponseList(correctResponseList);
        customQuizDao.save(customQuiz);
        String response = customQuiz.getCreatedAt().toString();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<List<CustomQuiz>> getCustomQuiz(String id) {
        // Decode the Base64 encoded timestamp
        byte[] decodedBytes = Base64.getDecoder().decode(id);
        String decodedTimestampStr = new String(decodedBytes, StandardCharsets.UTF_8);

        // Parse the decoded timestamp to LocalDateTime
        LocalDateTime decodedTimestamp = LocalDateTime.parse(decodedTimestampStr, DateTimeFormatter.ISO_DATE_TIME);
        return new ResponseEntity<>(customQuizDao.findByCreatedAt(decodedTimestamp), HttpStatus.OK);
    }

    public ResponseEntity<Integer> submitCustomQuiz(String customQuizId, List<CustomResponse> responseList) {
        Integer score = 0;

        try {
            // Decode the Base64 encoded customQuizId
            byte[] decodedBytes = Base64.getDecoder().decode(customQuizId);
            String decodedTimestampStr = new String(decodedBytes, StandardCharsets.UTF_8);

            // Parse the decoded timestamp to LocalDateTime
            LocalDateTime decodedTimestamp = LocalDateTime.parse(decodedTimestampStr, DateTimeFormatter.ISO_DATE_TIME);

            // Fetch the CustomQuiz using the decoded timestamp
            List<CustomQuiz> customQuizzes = customQuizDao.findByCreatedAt(decodedTimestamp);
            if (customQuizzes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            CustomQuiz customQuiz = customQuizzes.get(0);
            List<CustomResponse> correctResponseList = customQuiz.getCorrectResponseList();

            // Create a map of correct responses for quick lookup
            Map<String, String> correctResponseMap = correctResponseList.stream()
                    .collect(Collectors.toMap(CustomResponse::getQuestion, CustomResponse::getResponse));

            // Compare user responses with correct responses and calculate the score
            for (CustomResponse response : responseList) {
                String correctAnswer = correctResponseMap.get(response.getQuestion());
                if (correctAnswer != null && correctAnswer.equals(response.getResponse())) {
                    score++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(score, HttpStatus.OK);
    }

}
