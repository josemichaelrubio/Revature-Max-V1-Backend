package com.revaturemax.services;

import com.revaturemax.model.Batch;
import com.revaturemax.model.Employee;
import com.revaturemax.repositories.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchService {

    @Autowired
    BatchRepository batchRepo;

    public long getByAssociate(Employee emp){
        return batchRepo.findBatchIdByEmployeeId(emp.getId());
    }

}
