package com.revaturemax.repositories;

import com.revaturemax.models.EmployeeQuiz;
import com.revaturemax.models.EmployeeQuizId;
import com.revaturemax.projections.QuizAverage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.revaturemax.models.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeQuizRepository extends JpaRepository<EmployeeQuiz, EmployeeQuizId> {

    @Query("SELECT eq FROM EmployeeQuiz eq, Batch b LEFT JOIN FETCH eq.employee LEFT JOIN FETCH eq.quiz WHERE b.id = :batchId AND eq.employee MEMBER OF b.associates ")
    List<EmployeeQuiz> findEmployeeQuizzesByBatchId(@Param("batchId") long batchId);

    @Query("SELECT eq FROM EmployeeQuiz eq, Batch b LEFT JOIN FETCH eq.employee LEFT JOIN FETCH eq.quiz WHERE b.id = :batchId AND eq.employee MEMBER OF b.associates ORDER BY eq.quiz.id asc")
    List<EmployeeQuiz> findEmployeeQuizzesByBatchIdAndSort(@Param("batchId") long batchId);

    @Query("SELECT eq FROM EmployeeQuiz eq LEFT JOIN FETCH eq.quiz WHERE eq.employee = :employee")
    List<EmployeeQuiz> findByEmployeeEquals(@Param("employee") Employee employee);

    @Query("SELECT new com.revaturemax.projections.QuizAverage(eq.quiz.id, AVG(eq.score), COUNT(eq.quiz.id))" +
            " FROM EmployeeQuiz eq, Batch b" +
            " WHERE b.id = :batchId AND eq.employee MEMBER OF b.associates" +
            " GROUP BY eq.quiz")
    List<QuizAverage> findQuizAveragesByBatch(@Param("batchId") long batchId);

}
