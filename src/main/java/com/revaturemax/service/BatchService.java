package com.revaturemax.service;

import com.revaturemax.model.CurriculumDay;
import com.revaturemax.repository.BatchRepository;
import com.revaturemax.repository.CurriculumDayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class BatchService {

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private CurriculumDayRepository curriculumDayRepository;

    public List<CurriculumDay> getCurriculum(long batchId) {
        long employeeId = 1; //TODO: pull id from JWT or passed as param
        List<CurriculumDay> curriculum = curriculumDayRepository.findCurriculumByBatchId(batchId);
        curriculum.sort(Comparator.comparing(CurriculumDay::getDate));
        return curriculum;
    }

}
