package com.revaturemax.repositories;

import com.revaturemax.models.Employee;
import com.revaturemax.models.EmployeeTopic;
import com.revaturemax.models.EmployeeTopicId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeTopicRepository extends JpaRepository<EmployeeTopic, EmployeeTopicId> {

    List<EmployeeTopic> findByEmployeeEquals(Employee emp);

    @Query("SELECT et FROM EmployeeTopic et LEFT JOIN FETCH et.employee WHERE et.id = :id")
    public EmployeeTopic getEmployeeTopicById(@Param("id") EmployeeTopicId id);

    //"SELECT et FROM EmployeeTopic et LEFT JOIN FETCH
    // //@Query("SELECT eq FROM EmployeeTopic eq, Batch b LEFT JOIN FETCH eq.topic AS t LEFT JOIN FETCH t.tag WHERE b.id = :batchId AND eq.employee MEMBER OF b.associates ORDER BY t.tag.id")"

}
