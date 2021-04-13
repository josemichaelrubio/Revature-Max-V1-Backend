package com.revaturemax.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class TopicTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    public TopicTag() {}

    public TopicTag(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        TopicTag topicTag = (TopicTag) o;
        return Objects.equals(name, topicTag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
