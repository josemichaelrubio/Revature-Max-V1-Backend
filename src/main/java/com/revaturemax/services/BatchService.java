package com.revaturemax.services;

import com.revaturemax.dto.BatchResponse;
import com.revaturemax.projections.BatchSummary;
import com.revaturemax.repositories.BatchRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BatchService {

    Logger logger = LogManager.getLogger(BatchService.class);

    @Autowired
    private BatchRepository batchRepository;

    public BatchResponse getBatchInfoAndAveragesById(long id) {
        // Get name, description and instructor of batch -- will later be passed to BatchResponse object
        BatchSummary batchSummary = getBasicBatchInfo(id);

        //Get mapping of average quiz score to quiz name for batch
        Map<String, Double> averageQuizScores = getQuizAveragesInfo(id);

        // Get mapping of average competancies by topic for batch
        Map<String, Double> averageCompetenciesByTopic = getTopicCompetencyAveragesInfo(id);

        BatchResponse batchResponse = new BatchResponse(batchSummary);

        for (String key : averageQuizScores.keySet()) {
            batchResponse.addQuizAverage(key, averageQuizScores.get(key));
        }

        for (String key : averageCompetenciesByTopic.keySet()) {
            batchResponse.addCompetencyAverage(key, averageCompetenciesByTopic.get(key));
        }

        return batchResponse;
    }

    public BatchSummary getBasicBatchInfo(Long id) {
        logger.info("Hello");
        return batchRepository.getById(id);
    }

    // NOT FINISHED
    public Map<String, Double> getQuizAveragesInfo(Long id) {

        // Need to define comparator
        Map<String, Double> quizAverages = new TreeMap<String, Double>();

        // More logic needs to go here
        List<Object> batchQuizScores = batchRepository.findAllById(id);
        for (int i=0; i<batchQuizScores.size(); i++) {
            Object[] row = (Object[]) batchQuizScores.get(i);
            logger.info("Element " + i + Arrays.toString(row));
        }

        return quizAverages;
    }

    public Map<String, Double> getTopicCompetencyAveragesInfo(Long id) {
        return null;
    }


}
