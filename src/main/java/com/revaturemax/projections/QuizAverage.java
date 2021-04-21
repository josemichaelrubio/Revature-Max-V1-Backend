package com.revaturemax.projections;

import com.revaturemax.models.Quiz;

public class QuizAverage {

    private Quiz quiz;
    private Double averageScore;
    private Long scoresCounted;

    public QuizAverage(long quizId, Double averageScore, Long scoresCounted) {
        quiz = new Quiz();
        quiz.setId(quizId);
        this.averageScore = averageScore;
        this.scoresCounted = scoresCounted;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
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
