package com.revaturemax.repository;

import com.revaturemax.model.EmployeeQuiz;
import com.revaturemax.model.EmployeeQuizId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeQuizRepository extends JpaRepository<EmployeeQuiz, EmployeeQuizId> {

    @Query("SELECT eq FROM EmployeeQuiz eq, Batch b LEFT JOIN FETCH eq.employee LEFT JOIN FETCH eq.quiz WHERE b.id = :batchId AND eq.employee MEMBER OF b.associates ")
    List<EmployeeQuiz> findEmployeeQuizzesByBatchId(@Param("batchId") long batchId);

}
