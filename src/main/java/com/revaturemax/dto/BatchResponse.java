package com.revaturemax.dto;

import com.revaturemax.models.Batch;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Component
@Scope("prototype")
public class BatchResponse implements Serializable {
    private Batch batch;
    private List<QuizAverage> quizAverage;
    private List<CompetencyAverage> competencyAverage;

    public BatchResponse() {}

    public BatchResponse(Batch batch, List<QuizAverage> quizAverage, List<CompetencyAverage> competencyAverage) {
        this.batch = batch;
        this.quizAverage = quizAverage;
        this.competencyAverage = competencyAverage;
    }

    public void addQuizAverage(String quizName, double average) {
        this.quizAverage.add(new QuizAverage(quizName, average));
    }

    public void addCompetencyAverage(String topicName, double average) {
        this.competencyAverage.add(new CompetencyAverage(topicName, average));
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public List<QuizAverage> getQuizAverage() {
        return quizAverage;
    }

    public void setQuizAverage(List<QuizAverage> quizAverage) {
        this.quizAverage = quizAverage;
    }

    public List<CompetencyAverage> getCompetencyAverage() {
        return competencyAverage;
    }

    public void setCompetencyAverage(List<CompetencyAverage> competencyAverage) {
        this.competencyAverage = competencyAverage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BatchResponse that = (BatchResponse) o;
        return Objects.equals(batch, that.batch) && Objects.equals(quizAverage, that.quizAverage) && Objects.equals(competencyAverage, that.competencyAverage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(batch, quizAverage, competencyAverage);
    }

    @Override
    public String toString() {
        return "BatchResponse{" +
                "batch=" + batch +
                ", quizAverage=" + quizAverage +
                ", competencyAverage=" + competencyAverage +
                '}';
    }

    @Component
    private static class QuizAverage implements Serializable {
        private String quizName;
        private double average;

        public QuizAverage() {}

        public QuizAverage(String topicName, double average) {
            this.quizName = topicName;
            this.average = average;
        }

        public String getQuizName() {
            return quizName;
        }

        public void setQuizName(String quizName) {
            this.quizName = quizName;
        }

        public double getAverage() {
            return average;
        }

        public void setAverage(double average) {
            this.average = average;
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

    @Component
    private static class CompetencyAverage implements Serializable {

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

        public double getAverage() {
            return average;
        }

        public void setAverage(double average) {
            this.average = average;
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
}
