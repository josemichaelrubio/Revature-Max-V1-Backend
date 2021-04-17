package com.revaturemax.repositories;


import com.revaturemax.model.Employee;
import com.revaturemax.model.EmployeeQuiz;
import com.revaturemax.model.EmployeeQuizId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeQuizRepository extends JpaRepository<EmployeeQuiz, EmployeeQuizId> {

    List<EmployeeQuiz> findByEmployeeEquals(Employee emp);
}
