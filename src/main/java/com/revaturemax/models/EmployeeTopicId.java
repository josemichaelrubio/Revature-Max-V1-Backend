package com.revaturemax.models;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Component
@Scope("prototype")
public class EmployeeTopicId implements Serializable {

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "topic_id")
    private Long topicId;

    public EmployeeTopicId() {}

    public EmployeeTopicId(Long employeeId, Long topicId) {
        this.employeeId = employeeId;
        this.topicId = topicId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeTopicId that = (EmployeeTopicId) o;
        return employeeId.equals(that.employeeId) && topicId.equals(that.topicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, topicId);
    }

}
