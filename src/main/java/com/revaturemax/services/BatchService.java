package com.revaturemax.services;

import com.revaturemax.dto.BatchResponse;
import com.revaturemax.models.Batch;
import com.revaturemax.projections.BatchSummary;
import com.revaturemax.repositories.BatchRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchService {

    Logger logger = LogManager.getLogger(BatchService.class);

    @Autowired
    private BatchRepository batchRepository;

    public BatchResponse getBatchInfoAndAveragesById(int id) {
        return null;
    }

    public BatchSummary getBasicBatchInfo(Long id) {
        logger.info("Hello");
        return batchRepository.getById(id);
    }




}
