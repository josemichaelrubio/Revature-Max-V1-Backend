package com.revaturemax.models;

import java.util.Objects;

public class QuizAverage {

    private String quizName;
    private double average;

    public QuizAverage() {}

    public QuizAverage(String topicName, double average) {
        this.quizName = topicName;
        this.average = average;
    }

    public String getTopicName() {
        return quizName;
    }

    public void setTopicName(String topicName) {
        this.quizName = topicName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuizAverage that = (QuizAverage) o;
        return Double.compare(that.average, average) == 0 && Objects.equals(quizName, that.quizName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quizName, average);
    }

    @Override
    public String toString() {
        return "QuizAverage{" +
                "quizName='" + quizName + '\'' +
                ", average=" + average +
                '}';
    }
}
