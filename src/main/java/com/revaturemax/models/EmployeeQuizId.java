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
public class EmployeeQuizId implements Serializable {

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "quiz_id")
    private Long quizId;

    public EmployeeQuizId() {}

    public EmployeeQuizId(Long employeeId, Long quizId) {
        this.employeeId = employeeId;
        this.quizId = quizId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeQuizId that = (EmployeeQuizId) o;
        return employeeId.equals(that.employeeId) && quizId.equals(that.quizId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, quizId);
    }

}
