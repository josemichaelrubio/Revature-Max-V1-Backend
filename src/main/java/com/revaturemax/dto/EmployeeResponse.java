package com.revaturemax.dto;

import com.revaturemax.models.Employee;
import com.revaturemax.models.Quiz;
import com.revaturemax.models.Topic;

import java.util.ArrayList;
import java.util.List;

public class EmployeeResponse {

    private Employee employee;
    private List<QuizScore> quizzes = new ArrayList<>();
    private List<TopicDetails> topics = new ArrayList<>();

    public EmployeeResponse(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    public List<QuizScore> getQuizzes() {
        return quizzes;
    }

    public void addQuiz(Quiz quiz) {
        this.quizzes.add(new QuizScore(quiz));
    }

    public void addQuizScore(Quiz quiz, Float score) {
        for (QuizScore qs : quizzes) {
            if (qs.getQuiz().getId() == quiz.getId()) {
                qs.setScore(score);
            }
        }
    }

    public List<TopicDetails> getTopics() {
        return topics;
    }

    public void addTopicCompetency(Topic topic, float competency) {
        this.topics.add(new TopicDetails(topic, competency));
    }

    private class QuizScore {

        private Quiz quiz;
        private Float score;

        public QuizScore(Quiz quiz) {
            this.quiz = quiz;
        }

        public Quiz getQuiz() {
            return quiz;
        }

        public Float getScore() {
            return score;
        }

        public void setScore(Float score) {
            this.score = score;
        }

    }

    private class TopicDetails {

        private Topic topic;
        private Float competency;

        public TopicDetails(Topic topic, Float competency) {
            this.topic = topic;
            this.competency = competency;
        }

        public Topic getTopic() {
            return topic;
        }

        public Float getCompetency() {
            return competency;
        }

    }

}
