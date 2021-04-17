package com.revaturemax.repositories;

import com.revaturemax.models.Batch;
import com.revaturemax.projections.BatchSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {

        BatchSummary getById(long id);

        @Query(value = "select * from employee_batch where employee_id = :emp", nativeQuery = true)
        long findBatchIdByEmployeeId(@Param("emp")long empId);

        @Query("SELECT b FROM Batch b LEFT JOIN FETCH b.associates LEFT JOIN FETCH b.instructor WHERE b.id = :batchId")
        public Batch getBatchById(@Param("batchId") long id);

}
