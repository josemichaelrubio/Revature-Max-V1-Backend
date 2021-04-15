package com.revaturemax.repositories;


import com.revaturemax.models.Batch;
import com.revaturemax.projections.BatchSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {

        BatchSummary getById(Long id);
}
