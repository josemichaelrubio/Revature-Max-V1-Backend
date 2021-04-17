package com.revaturemax.repository;

import com.revaturemax.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    @Query("SELECT q FROM Quiz q LEFT JOIN FETCH q.topics AS t LEFT JOIN FETCH t.tag WHERE q.day.batch.id = :batchId")
    List<Quiz> findQuizzesByBatchId(@Param("batchId") long batchId);

}
