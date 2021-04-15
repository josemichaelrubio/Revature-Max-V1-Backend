package com.revaturemax.services;

import com.revaturemax.repositories.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchService {

    @Autowired
    BatchRepository batchRepository;
}
