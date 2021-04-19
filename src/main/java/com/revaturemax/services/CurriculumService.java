package com.revaturemax.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.revaturemax.dto.CurriculumRequest;
import com.revaturemax.models.Batch;
import com.revaturemax.models.CurriculumDay;
import com.revaturemax.repositories.CurriculumDayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CurriculumService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CurriculumDayRepository curriculumDayRepository;

    @Transactional
    public ResponseEntity<String> getCurriculum(long batchId) {
        //long employeeId = 1; //TODO: pull id from JWT or passed as param
        List<CurriculumDay> curriculum = curriculumDayRepository.findCurriculumByBatchId(batchId);
        curriculum  = curriculumDayRepository.findCurriculumTopics(curriculum);
        curriculum.sort(Comparator.comparing(CurriculumDay::getDate));

        SimpleFilterProvider filter = new SimpleFilterProvider();
        filter.addFilter("Quiz", SimpleBeanPropertyFilter.serializeAllExcept("Topic"));
        try {
            return new ResponseEntity<String>(objectMapper.writer(filter).writeValueAsString(curriculum),
                    HttpStatus.OK);
        } catch (JsonProcessingException exception) {
            exception.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> setCurriculumDay(long batchId, CurriculumRequest curriculumRequest) {
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
        return new ResponseEntity<String>(HttpStatus.OK);
    }

}
