package com.revaturemax.repositories;

import com.revaturemax.model.Employee;
import com.revaturemax.model.EmployeeTopic;
import com.revaturemax.model.EmployeeTopicId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeTopicRepository extends JpaRepository<EmployeeTopic, EmployeeTopicId> {

    List<EmployeeTopic> findByEmployeeEquals(Employee emp);

}
