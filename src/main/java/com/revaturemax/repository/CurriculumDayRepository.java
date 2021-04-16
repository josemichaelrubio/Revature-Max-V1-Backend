package com.revaturemax.repository;

import com.revaturemax.model.CurriculumDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CurriculumDayRepository extends JpaRepository<CurriculumDay, Long> {

    List<CurriculumDay> findCurriculumDayByBatchId(long batchId);

    Optional<CurriculumDay> findCurriculumDayByBatchIdAndDate(long batchId, Date date);

}
