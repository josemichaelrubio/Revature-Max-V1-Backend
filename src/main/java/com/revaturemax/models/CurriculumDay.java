package com.revaturemax.models;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "curriculum_day")
public class CurriculumDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id")
    private Batch batch;

    private Date date;

    @ManyToMany
    @JoinTable(name = "curriculum_day_topic",
            joinColumns = @JoinColumn(name = "curriculum_day_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id")
    )
    private List<Topic> topics = new ArrayList<>();

    @OneToMany(mappedBy = "day")
    private List<Quiz> quizzes = new ArrayList<>();

    public CurriculumDay() {}

    public CurriculumDay(Long id) {
        this.id = id;
    }

    public CurriculumDay(Batch batch, Date date) {
        this.batch = batch;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public void addTopic(Topic topic) {
        this.topics.add(topic);
    }

    public void removeTopic(Topic topic) {
        this.topics.remove(topic);
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    public void addQuiz(Quiz quiz) {
        this.quizzes.add(quiz);
    }

    public void removeQuiz(Quiz quiz) {
        this.quizzes.remove(quiz);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurriculumDay that = (CurriculumDay) o;
        return batch.equals(that.batch) && date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(batch, date);
    }

}
