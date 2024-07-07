package com.example.quiz_service.service;

import com.example.quiz_service.dao.CustomQuizDao;
import com.example.quiz_service.dao.QuizDao;
import com.example.quiz_service.feign.QuizInterface;
import com.example.quiz_service.model.CustomQuiz;
import com.example.quiz_service.model.QuestionWrapper;
import com.example.quiz_service.model.Quiz;
import com.example.quiz_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

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

    public ResponseEntity<String> createCustomQuiz(Integer quizId, String quizTitle, List<QuestionWrapper> questionList) {
        CustomQuiz customQuiz = new CustomQuiz();
        customQuiz.setId(quizId);
        customQuiz.setQuizTitle(quizTitle);
        customQuiz.setQuestionList(questionList);
        customQuizDao.save(customQuiz);

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    public ResponseEntity<Integer> submitCustomQuiz(List<Response> correctResponseList, List<Response> responseList) {
        Integer score = 0;
        for(Response response : responseList) {
            for(Response correctResponse : correctResponseList) {
                if(response.getId().equals(correctResponse.getId())) {
                    if(response.getResponse().equals(correctResponse.getResponse())) score++;
                }
            }
        }
        return new ResponseEntity<>(score, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getCustomQuiz(String id) {
        return new ResponseEntity<>(customQuizDao.findById(id).get().getQuestionList(), HttpStatus.OK);
    }
}
