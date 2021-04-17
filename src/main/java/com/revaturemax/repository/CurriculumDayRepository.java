package com.revaturemax.repository;

import com.revaturemax.model.CurriculumDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CurriculumDayRepository extends JpaRepository<CurriculumDay, Long> {

    @Query("SELECT cd FROM CurriculumDay cd WHERE cd.batch.id = :batchId")
    List<CurriculumDay> findCurriculumByBatchId(long batchId);

    @Query("SELECT cd FROM CurriculumDay cd LEFT JOIN FETCH cd.batch WHERE cd.batch.id = :batchId AND cd.date = :date")
    Optional<CurriculumDay> findCurriculumDayByBatchIdAndDate(@Param("batchId") long batchId, @Param("date") Date date);

}
