package com.revaturemax.repository;

import com.revaturemax.model.EmployeeTopic;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeTopicRepository {

    public EmployeeTopic getEmployeeTopicById(long employeeId, long topicId);

}
