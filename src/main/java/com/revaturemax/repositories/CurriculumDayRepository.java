package com.revaturemax.repositories;

import com.revaturemax.models.Batch;
import com.revaturemax.models.CurriculumDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface CurriculumDayRepository  extends JpaRepository<CurriculumDay, Long> {
    CurriculumDay findByBatchAndDate(Batch batch, Date date);
}
