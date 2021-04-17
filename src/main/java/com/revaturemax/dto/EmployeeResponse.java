package com.revaturemax.dto;


import com.revaturemax.models.Role;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Objects;

@Component
@Scope("prototype")
public class EmployeeResponse {
    private String name;
    private List<EmployeeQuizResponse> quizzes;
    private List<EmployeeTopicResponse> topics;
    private Role role;
    private long batchId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EmployeeQuizResponse> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<EmployeeQuizResponse> quizzes) {
        this.quizzes = quizzes;
    }

    public List<EmployeeTopicResponse> getTopics() {
        return topics;
    }

    public void setTopics(List<EmployeeTopicResponse> topics) {
        this.topics = topics;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public long getBatchId() {
        return batchId;
    }

    public void setBatchId(long batchId) {
        this.batchId = batchId;
    }

    public EmployeeResponse(String name, Role role) {
        this.name = name;
        this.role = role;
    }

    public EmployeeResponse(String name, Role role, long batchId) {
        this.name = name;
        this.role = role;
        this.batchId = batchId;
    }

    public EmployeeResponse(String name, List<EmployeeQuizResponse> quizzes, List<EmployeeTopicResponse> topics, Role role, long batchId) {
        this.name = name;
        this.quizzes = quizzes;
        this.topics = topics;
        this.role = role;
        this.batchId = batchId;
    }

    public EmployeeResponse() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeResponse that = (EmployeeResponse) o;
        return batchId == that.batchId && Objects.equals(name, that.name) && Objects.equals(quizzes, that.quizzes) && Objects.equals(topics, that.topics) && role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, quizzes, topics, role, batchId);
    }

    @Override
    public String toString() {
        return "EmployeeResponse{" +
                "name='" + name + '\'' +
                ", quizzes=" + quizzes +
                ", topics=" + topics +
                ", role=" + role +
                ", batchId=" + batchId +
                '}';
    }
}
