package com.revaturemax.controllers;

import com.revaturemax.dto.BatchResponse;
import com.revaturemax.services.BatchService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/batches")
@CrossOrigin
public class BatchController {

    private static Logger logger = LogManager.getLogger(BatchController.class);

    @Autowired
    private static BatchService batchService;

    // Handles request to get basic batch data and averages associated with a particular batch
    @GetMapping(value = "/{batch-id}", produces = "application/json")
    public BatchResponse handleGetBatchInfoById(@PathVariable("batch-id") int id) {
        return batchService.getBatchInfoById(id);
    }


}
