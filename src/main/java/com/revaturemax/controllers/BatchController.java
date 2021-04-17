package com.revaturemax.controllers;

import com.revaturemax.dto.BatchResponse;
import com.revaturemax.models.Batch;
import com.revaturemax.models.Quiz;
import com.revaturemax.projections.BatchSummary;
import com.revaturemax.services.BatchService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/batches")
@CrossOrigin
public class BatchController {

    private static Logger logger = LogManager.getLogger(BatchController.class);

    @Autowired
    private BatchService batchService;

     @GetMapping(value = "/{batch-id}", produces = "application/json")
    public BatchResponse handleGetBatchInfoById(@PathVariable("batch-id") long id) {
         return batchService.getBatchInfoAndAveragesById(id);
    }

    /***********************************************************************************************
     * The methods below exist for endpoint testing purposes
     *
     ***********************************************************************************************/
    /*@GetMapping(value = "/{batch-id}", produces ="application/json")
    public ResponseEntity<BatchSummary> getBatchInfoById(@PathVariable("batch-id") Long id) {
        logger.info("Getting batch with id: " + id);
        return ResponseEntity.ok(batchService.getBasicBatchInfo(id));
    }*/


}
