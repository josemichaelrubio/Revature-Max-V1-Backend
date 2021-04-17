package com.revaturemax.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="quiz")
@Component
@JsonIgnoreProperties("hibernateLazyInitializer")
@Scope("prototype")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_day_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private CurriculumDay day;

    private String name;

    @ManyToMany
    @JoinTable(name = "quiz_topic",
            joinColumns = @JoinColumn(name = "quiz_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id")
    )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Topic> topics = new ArrayList<>();

    public Quiz() {}

    public Quiz(CurriculumDay day, String name) {
        this.day = day;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CurriculumDay getDay() {
        return day;
    }

    public void setDay(CurriculumDay day) {
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quiz quiz = (Quiz) o;
        return Objects.equals(day, quiz.day) && Objects.equals(name, quiz.name) &&
                Objects.equals(topics, quiz.topics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, name, topics);
    }
}
