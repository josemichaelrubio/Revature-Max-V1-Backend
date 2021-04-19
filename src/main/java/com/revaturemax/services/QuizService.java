package com.revaturemax.services;

import com.revaturemax.models.*;
import com.revaturemax.repositories.BatchRepository;
import com.revaturemax.repositories.CurriculumDayRepository;
import com.revaturemax.repositories.EmployeeQuizRepository;
import com.revaturemax.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private BatchRepository batchRepository;
    @Autowired
    private CurriculumDayRepository curriculumDayRepository;
    @Autowired
    private EmployeeQuizRepository employeeQuizRepository;

    public ResponseEntity<String> setNewQuiz(long batchId, Quiz quiz) {
        //Batch batch = authorizeBatchInstructorAccess(batchId, employeeId);
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
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    public ResponseEntity<String> updateQuiz(long batchId, Quiz quiz) {
        //Batch batch = authorizeBatchInstructorAccess(batchId, employeeId);
        //validateQuizBelongingToBatch(Batch batch, long quizId);
        quizRepository.save(quiz);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    public ResponseEntity<String> removeQuiz(long batchId, long quizId) {
        //Batch batch = authorizeBatchInstructorAccess(batchId, employeeId);
        //validateQuizBelongingToBatch(Batch batch, long quizId);
        quizRepository.deleteById(quizId);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    public ResponseEntity<String> setEmployeeQuiz(long employeeId, long quizId, EmployeeQuiz employeeQuiz) {
        //assert JWT.id = employeeId
        if (!quizRepository.existsById(quizId)) {
            //404
        }
        employeeQuiz.setEmployee(new Employee(employeeId));
        employeeQuiz.setQuiz(new Quiz(quizId));
        employeeQuizRepository.save(employeeQuiz);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    private Batch authorizeBatchInstructorAccess(long batchId, long employeeId) throws Exception {
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
    }

}
