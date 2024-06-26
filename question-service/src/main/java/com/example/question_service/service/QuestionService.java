package com.example.question_service.service;

import com.example.question_service.dao.QuestionDao;
import com.example.question_service.model.Question;
import com.example.question_service.model.QuestionWrapper;
import com.example.question_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionByTopic(String topic) {
        try {
            return new ResponseEntity<>(questionDao.findByTopic(topic), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_GATEWAY);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionDao.save(question);
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("", HttpStatus.BAD_GATEWAY);

    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String topic, int noOfQuestions) {
        try{
            List<Integer> questions = questionDao.findRandomQuestionsByCategory(topic, noOfQuestions);
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_GATEWAY);
    }


    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        try {
            List<QuestionWrapper> wrappers = new ArrayList<>();
            for(Integer questionId: questionIds) {
                Question q = questionDao.findById(questionId).get();
                QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(),q.getOption3(), q.getOption4());
                wrappers.add(qw);
            }

            return new ResponseEntity<>(wrappers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_GATEWAY);

    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        try {
            int score = 0;
            for(Response response : responses) {
                Question question = questionDao.findById(response.getId()).get();
                if(response.getResponse().equals(question.getRightAnswer())) {
                    score++;
                }
            }
            return new ResponseEntity<>(score, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(-1, HttpStatus.BAD_REQUEST);
    }
}
