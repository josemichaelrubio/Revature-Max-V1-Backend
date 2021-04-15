package com.revaturemax.repository;

import com.revaturemax.model.EmployeeTopic;
import com.revaturemax.model.EmployeeTopicId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeTopicRepository extends JpaRepository<EmployeeTopic, EmployeeTopicId> {

    @Query("SELECT et FROM EmployeeTopic et LEFT JOIN FETCH et.employee WHERE et.id = :id")
    public EmployeeTopic getEmployeeTopicById(@Param("id") EmployeeTopicId id);

}
