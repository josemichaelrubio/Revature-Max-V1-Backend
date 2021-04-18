package com.revaturemax.repositories;

import com.revaturemax.models.Batch;
import com.revaturemax.models.CurriculumDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CurriculumDayRepository extends JpaRepository<CurriculumDay, Long> {

    CurriculumDay findByBatchAndDate(Batch batch, Date date);

    @Query("SELECT cd FROM CurriculumDay cd WHERE cd.batch.id = :batchId")
    List<CurriculumDay> findCurriculumByBatchId(long batchId);

    @Query("SELECT cd FROM CurriculumDay cd LEFT JOIN FETCH cd.batch WHERE cd.batch.id = :batchId AND cd.date = :date")
    Optional<CurriculumDay> findCurriculumDayByBatchIdAndDate(@Param("batchId") long batchId, @Param("date") java.sql.Date date);

}
