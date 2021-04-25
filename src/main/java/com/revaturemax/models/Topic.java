package com.revaturemax.models;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Component
@Scope("prototype")
@JsonFilter("Topic")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "topic_tag_id")
    private TopicTag tag;

    private String name;

    public Topic() {}

    public Topic(Long id) {
        this.id = id;
    }

    public Topic(Long id, String name, TopicTag tag) {
        this.id = id;
        this.name = name;
        this.tag = tag;
    }

    public Topic(String name) {
        this.name=name;
    }

    public Topic(TopicTag tag, String name) {
        this.tag = tag;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TopicTag getTag() {
        return tag;
    }

    public void setTag(TopicTag tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topic topic = (Topic) o;
        return name.equals(topic.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
