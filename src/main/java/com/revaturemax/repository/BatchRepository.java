package com.revaturemax.repository;

import com.revaturemax.model.Batch;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchRepository {

    public Batch getBatchById(long id);

}
