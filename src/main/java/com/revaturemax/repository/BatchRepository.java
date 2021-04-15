package com.revaturemax.repository;

import com.revaturemax.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {

    @Query("SELECT b FROM Batch b LEFT JOIN FETCH b.associates LEFT JOIN FETCH b.instructor WHERE b.id = :batchId")
    public Batch getBatchById(@Param("batchId") long id);

}
