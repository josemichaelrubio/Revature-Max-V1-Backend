package com.revaturemax.services;

import com.revaturemax.dto.CurriculumRequest;
import com.revaturemax.models.Batch;
import com.revaturemax.models.CurriculumDay;
import com.revaturemax.repositories.CurriculumDayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

@Service
public class CurriculumService {

    @Autowired
    private CurriculumDayRepository curriculumDayRepository;

    public void setCurriculumDay(long batchId, CurriculumRequest curriculumRequest) {
        //pull employeeId from JWT
        //Batch batch = authorizeBatchInstructorAccess(batchId, employeeId);
        Date date = curriculumRequest.getDate();
        Optional<CurriculumDay> day = curriculumDayRepository.findCurriculumDayByBatchIdAndDate(batchId, date);
        if (day.isPresent()) {
            day.get().setQuizzes(curriculumRequest.getQuizzes());
            day.get().setTopics(curriculumRequest.getTopics());
            curriculumDayRepository.save(day.get());
        } else {
            CurriculumDay newDay = new CurriculumDay(new Batch(batchId), date);
            newDay.setQuizzes(curriculumRequest.getQuizzes());
            newDay.setTopics(curriculumRequest.getTopics());
            curriculumDayRepository.save(newDay);
        }
    }

}
