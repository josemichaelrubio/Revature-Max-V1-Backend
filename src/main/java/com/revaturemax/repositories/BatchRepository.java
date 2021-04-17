package com.revaturemax.repositories;

import com.revaturemax.models.Batch;
import com.revaturemax.projections.BatchSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {

        BatchSummary getById(long id);

        @Query(value="select eq.quiz_id, eq.score from employee_batch eb inner join employee_quiz eq on eb.employee_id=eq.employee_id where eb.batch_id = ?1 order by eq.quiz_id", nativeQuery = true)
        List<Object> findAllById(long id);

}
