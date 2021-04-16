package com.revaturemax.service;

import com.revaturemax.model.Batch;
import com.revaturemax.model.CurriculumDay;
import com.revaturemax.model.Quiz;
import com.revaturemax.repository.BatchRepository;
import com.revaturemax.repository.CurriculumDayRepository;
import com.revaturemax.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void setNewQuiz(long batchId, Quiz quiz) {
        //Batch batch = authorizeBatchInstructorAccess(batchId, employeeId);
        Date date = quiz.getDay().getDate();
        Optional<CurriculumDay> day = curriculumDayRepository.findCurriculumDayByBatchIdAndDate(batchId, date);
        if (day.isPresent()) {
            quiz.setDay(day.get());
        } else {
            quiz.getDay().setBatch(new Batch(batchId)); //set to batch obj loaded earlier
            curriculumDayRepository.save(quiz.getDay());
        }
        quizRepository.save(quiz);
    }

    public void updateQuiz(long batchId, Quiz quiz) {
        //Batch batch = authorizeBatchInstructorAccess(batchId, employeeId);
        //validateQuizBelongingToBatch(Batch batch, long quizId);
        quizRepository.save(quiz);
    }

    public void removeQuiz(long batchId, long quizId) {
        //Batch batch = authorizeBatchInstructorAccess(batchId, employeeId);
        //validateQuizBelongingToBatch(Batch batch, long quizId);
        quizRepository.deleteById(quizId);
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

}
