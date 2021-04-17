package com.revaturemax.dto;


import com.revaturemax.projections.BatchSummary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@Scope("prototype")
public class BatchResponse implements Serializable {

    Logger logger = LogManager.getLogger(BatchResponse.class);
    private BatchSummary batch;
    private List<QuizAverage> quizAverage = new ArrayList<>();
    private List<CompetencyAverage> competencyAverage = new ArrayList<>();

    public BatchResponse() {}

    public BatchResponse(BatchSummary batch) {
        this.batch = batch;
    }

    public void addQuizAverage(String quizName, float average) {
        logger.info("quizName: " + quizName);
        logger.info("average: " + average);
        this.quizAverage.add(new QuizAverage(quizName, average));
    }

    public void addCompetencyAverage(String topicName, float average) {
        this.competencyAverage.add(new CompetencyAverage(topicName, average));
    }

    public BatchSummary getBatch() {
        return batch;
    }

    public void setBatch(BatchSummary batch) {
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
        private float average;

        public QuizAverage() {}

        public QuizAverage(String topicName, float average) {
            this.quizName = topicName;
            this.average = average;
        }

        public String getQuizName() {
            return quizName;
        }

        public void setQuizName(String quizName) {
            this.quizName = quizName;
        }

        public float getAverage() {
            return average;
        }

        public void setAverage(float average) {
            this.average = average;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            QuizAverage that = (QuizAverage) o;
            return Float.compare(that.average, average) == 0 && Objects.equals(quizName, that.quizName);
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
        private float average;

        public CompetencyAverage() {}

        public CompetencyAverage(String topicName, float average) {
            this.topicName = topicName;
            this.average = average;
        }

        public String getTopicName() {
            return topicName;
        }

        public void setTopicName(String topicName) {
            this.topicName = topicName;
        }

        public float getAverage() {
            return average;
        }

        public void setAverage(float average) {
            this.average = average;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CompetencyAverage that = (CompetencyAverage) o;
            return Float.compare(that.average, average) == 0 && Objects.equals(topicName, that.topicName);
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
