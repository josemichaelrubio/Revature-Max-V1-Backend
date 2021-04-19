package com.revaturemax.repositories;

import com.revaturemax.models.EmployeeQuiz;
import com.revaturemax.models.EmployeeQuizId;
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



    List<EmployeeQuiz> findByEmployeeEquals(Employee emp);

}