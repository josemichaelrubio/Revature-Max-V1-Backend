package com.revaturemax.repositories;

import com.revaturemax.models.Batch;
import com.revaturemax.models.CurriculumDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CurriculumDayRepository extends JpaRepository<CurriculumDay, Long> {

    CurriculumDay findByBatchAndDate(Batch batch, Date date);

    @Query("SELECT cd FROM CurriculumDay cd LEFT JOIN FETCH cd.batch WHERE cd.batch.id = :batchId AND cd.date = :date")
    Optional<CurriculumDay> findCurriculumDayByBatchIdAndDate(@Param("batchId") long batchId, @Param("date") java.sql.Date date);

    /* The following two queries must be called sequentially in the same session to load both quizzes and topics
    * collections of each CurriculumDay. Trying to fetch both at once will result in Hibernate throwing
    * MultipleBagFetchException. Calling them each when not wrapped in the same session will not cache the results
    * of the first. */
    @Query("SELECT DISTINCT cd FROM CurriculumDay cd LEFT JOIN FETCH cd.quizzes WHERE cd.batch.id = :batchId")
    @QueryHints(value = {@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_PASS_DISTINCT_THROUGH, value = "false")})
    List<CurriculumDay> findCurriculumByBatchId(long batchId);

    @Query("SELECT DISTINCT cd FROM CurriculumDay cd LEFT JOIN FETCH cd.topics t LEFT JOIN FETCH t.tag WHERE cd IN :days")
    @QueryHints(value = {@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_PASS_DISTINCT_THROUGH, value = "false")})
    List<CurriculumDay> findCurriculumTopics(List<CurriculumDay> days);

}
