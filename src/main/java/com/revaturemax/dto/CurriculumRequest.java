package com.revaturemax.dto;

import com.revaturemax.models.Quiz;
import com.revaturemax.models.Topic;

import java.sql.Date;
import java.util.List;

public class CurriculumRequest {

    Date date;
    List<Quiz> quizzes;
    List<Topic> topics;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

}
