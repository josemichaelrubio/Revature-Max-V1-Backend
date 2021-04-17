package com.revaturemax.repositories;

import com.revaturemax.models.Employee;
import com.revaturemax.models.EmployeeTopic;
import com.revaturemax.models.EmployeeTopicId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeTopicRepository extends JpaRepository<EmployeeTopic, EmployeeTopicId> {

    List<EmployeeTopic> findByEmployeeEquals(Employee emp);

}
