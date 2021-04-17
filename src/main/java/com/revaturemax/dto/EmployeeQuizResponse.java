package com.revaturemax.dto;

import java.util.Objects;

public class EmployeeQuizResponse {
    private long quizId;
    private float quizScore;

    @Override
    public String toString() {
        return "QuizResponse{" +
                "quizId=" + quizId +
                ", quizScore=" + quizScore +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeQuizResponse that = (EmployeeQuizResponse) o;
        return quizId == that.quizId && Float.compare(that.quizScore, quizScore) == 0;
    }

    public EmployeeQuizResponse(long quizId, float quizScore) {
        this.quizId = quizId;
        this.quizScore = quizScore;
    }

    public EmployeeQuizResponse() {
    }

    @Override
    public int hashCode() {
        return Objects.hash(quizId, quizScore);
    }

    public long getQuizId() {
        return quizId;
    }

    public void setQuizId(long quizId) {
        this.quizId = quizId;
    }

    public float getQuizScore() {
        return quizScore;
    }

    public void setQuizScore(float quizScore) {
        this.quizScore = quizScore;
    }

}
