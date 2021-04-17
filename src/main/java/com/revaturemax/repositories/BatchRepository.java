package com.revaturemax.repositories;

import com.revaturemax.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {

    @Query(value = "select * from employee_batch where employee_id = :emp", nativeQuery = true)
    long findBatchIdByEmployeeId(@Param("emp")long empId);

}
