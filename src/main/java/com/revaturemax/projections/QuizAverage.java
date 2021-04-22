package com.revaturemax.projections;

import com.revaturemax.models.Quiz;

public class QuizAverage {

    private String quizName;
    private Double averageScore;
    private Long scoresCounted;

    public QuizAverage(String quizName, Double averageScore, Long scoresCounted) {
        this.quizName = quizName;
        this.averageScore = averageScore;
        this.scoresCounted = scoresCounted;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }

    public Long getScoresCounted() {
        return scoresCounted;
    }

    public void setScoresCounted(Long scoresCounted) {
        this.scoresCounted = scoresCounted;
    }

}
