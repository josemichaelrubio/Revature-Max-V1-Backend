package com.revaturemax.models;

import java.util.Objects;

public class CompetencyAverage {

    private String topicName;
    private double average;

    public CompetencyAverage() {}

    public CompetencyAverage(String topicName, double average) {
        this.topicName = topicName;
        this.average = average;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompetencyAverage that = (CompetencyAverage) o;
        return Double.compare(that.average, average) == 0 && Objects.equals(topicName, that.topicName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topicName, average);
    }

    @Override
    public String toString() {
        return "CompetencyAverage{" +
                "topicName='" + topicName + '\'' +
                ", average=" + average +
                '}';
    }
}
