package com.revaturemax.controllers;

import com.revaturemax.services.BatchService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batches")
@CrossOrigin
public class BatchController {

    private static Logger logger = LogManager.getLogger(BatchController.class);

    @Autowired
    private static BatchService batchService;
    


}
