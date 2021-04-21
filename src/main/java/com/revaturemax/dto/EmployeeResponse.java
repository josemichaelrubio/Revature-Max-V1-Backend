package com.revaturemax.dto;

import com.revaturemax.models.Employee;
import com.revaturemax.models.Quiz;
import com.revaturemax.models.Topic;

import java.util.ArrayList;
import java.util.List;

public class EmployeeResponse {

    private Employee employee;
    private List<QuizDetails> quizzes = new ArrayList<>();
    private List<TopicDetails> topics = new ArrayList<>();

    public EmployeeResponse(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    public List<QuizDetails> getQuizzes() {
        return quizzes;
    }

    public void addQuizDetails(Quiz quiz, float score) {
        this.quizzes.add(new QuizDetails(quiz, score));
    }

    public List<TopicDetails> getTopics() {
        return topics;
    }

    public void addTopicDetails(Topic topic, float competency) {
        this.topics.add(new TopicDetails(topic, competency));
    }

    private class QuizDetails {

        private Quiz quiz;
        private float score;

        public QuizDetails(Quiz quiz, float score) {
            this.quiz = quiz;
            this.score = score;
        }

        public Quiz getQuiz() {
            return quiz;
        }

        public float getScore() {
            return score;
        }

    }

    private class TopicDetails {

        private Topic topic;
        private float competency;

        public TopicDetails(Topic topic, float competency) {
            this.topic = topic;
            this.competency = competency;
        }

        public Topic getTopic() {
            return topic;
        }

        public float getCompetency() {
            return competency;
        }

    }

}
