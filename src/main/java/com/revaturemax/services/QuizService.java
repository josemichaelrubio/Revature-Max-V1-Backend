package com.revaturemax.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.revaturemax.models.*;
import com.revaturemax.projections.QuizAverage;
import com.revaturemax.repositories.BatchRepository;
import com.revaturemax.repositories.CurriculumDayRepository;
import com.revaturemax.repositories.EmployeeQuizRepository;
import com.revaturemax.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private BatchRepository batchRepository;
    @Autowired
    private CurriculumDayRepository curriculumDayRepository;
    @Autowired
    private EmployeeQuizRepository employeeQuizRepository;

    public ResponseEntity<String> setNewQuiz(Token token, long batchId, Quiz quiz) {
        if (!token.getEmployeeRole().equals(Role.INSTRUCTOR)) {
            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        }
        Batch batch = batchRepository.findById(batchId).orElse(null);
        if (batch == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        //validateQuiz(quiz);
        Date date = quiz.getDay().getDate();
        Optional<CurriculumDay> day = curriculumDayRepository.findCurriculumDayByBatchIdAndDate(batchId, date);
        if (day.isPresent()) {
            quiz.setDay(day.get());
        } else {
            quiz.getDay().setBatch(new Batch(batchId)); //set to batch obj loaded earlier
            curriculumDayRepository.save(quiz.getDay());
        }
        quizRepository.save(quiz);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<String> updateQuiz(Token token, long batchId, Quiz quiz) {
        //Batch batch = authorizeBatchInstructorAccess(batchId, employeeId);
        //validateQuizBelongingToBatch(Batch batch, long quizId);
        quizRepository.save(quiz);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<String> removeQuiz(Token token, long batchId, long quizId) {
        //Batch batch = authorizeBatchInstructorAccess(batchId, employeeId);
        //validateQuizBelongingToBatch(Batch batch, long quizId);
        quizRepository.deleteById(quizId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<String> setEmployeeQuiz(Token token, long employeeId, long quizId, EmployeeQuiz employeeQuiz) {
        if (token.getEmployeeId() != employeeId) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        if (!quizRepository.existsById(quizId)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        employeeQuiz.setEmployee(new Employee(employeeId));
        employeeQuiz.setQuiz(new Quiz(quizId));
        employeeQuizRepository.save(employeeQuiz);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<String> test() {
        List<QuizAverage> averages = employeeQuizRepository.findQuizAveragesByBatch(1L);
        SimpleFilterProvider filter = new SimpleFilterProvider();
        filter.addFilter("Quiz", SimpleBeanPropertyFilter.serializeAllExcept("Topic"));
        try {
            return new ResponseEntity<String>(objectMapper.writer(filter).writeValueAsString(averages),
                    HttpStatus.OK);
        } catch (JsonProcessingException exception) {
            exception.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*private Batch authorizeBatchInstructorAccess(long batchId, long employeeId) throws Exception {
        Batch batch = batchRepository.findById(batchId).orElseThrow(() -> new Exception("404"));
        if (batch.getInstructor().getId() != employeeId) {
            throw new Exception("409");
        }
        return batch;
    }

    private void validateQuizBelongingToBatch(Batch batch, long quizId) throws Exception {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new Exception("404"));
        //TODO - throw BAD_REQUEST if quiz.day.batch.id != batch.id
    }

    private void validateQuiz(Quiz quiz) throws Exception {
        if (quiz.getDay() == null || (quiz.getDay().getId() == null && quiz.getDay().getDate() == null)) {
            throw new Exception("400"); //date must be specified
        }
        if (quiz.getName() == null || quiz.getName().equals("")) {
            throw new Exception("400"); //name must be specified and non-empty
        }
    }*/

}
