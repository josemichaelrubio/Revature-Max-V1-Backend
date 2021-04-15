package com.revaturemax.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "employee_quiz")
public class EmployeeQuiz {

    @EmbeddedId
    private EmployeeQuizId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("employeeId")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("quizId")
    private Quiz quiz;

    private float score;

    public EmployeeQuiz() {
        id = new EmployeeQuizId();
    }

    public EmployeeQuiz(Employee employee, Quiz quiz, float score) {
        id = new EmployeeQuizId(employee.getId(), quiz.getId());
        this.employee = employee;
        this.quiz = quiz;
        this.score = score;
    }

    public EmployeeQuizId getId() {
        return id;
    }

    public void setId(EmployeeQuizId id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        id.setEmployeeId(employee.getId());
        this.employee = employee;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        id.setQuizId(quiz.getId());
        this.quiz = quiz;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeQuiz that = (EmployeeQuiz) o;
        return Float.compare(that.score, score) == 0 && Objects.equals(employee, that.employee) &&
                Objects.equals(quiz, that.quiz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, quiz, score);
    }

}
