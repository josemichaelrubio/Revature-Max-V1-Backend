package com.revaturemax.dto;


import com.revaturemax.model.TopicTag;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * This DTO class will handle all incoming topic - related creation requests
 * Designed to cover all associated functionality of creating a topic
 * Used predominately by the instructor
 */

@Component
@Scope("prototype")
public class TopicRequest {

    private String topicName;

    @Override
    public String toString() {
        return "TopicRequest{" +
                "topicName='" + topicName + '\'' +
                ", tag=" + tag +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopicRequest that = (TopicRequest) o;
        return Objects.equals(topicName, that.topicName) && Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topicName, tag);
    }

    private TopicTag tag;


    public TopicRequest() {
        super();
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public TopicTag getTag() {
        return tag;
    }

    public void setTag(TopicTag tag) {
        this.tag = tag;
    }

    public TopicRequest(String topicName, TopicTag tag) {
        this.topicName = topicName;
        this.tag = tag;
    }
}
