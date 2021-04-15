package com.revaturemax.dto;


import com.revaturemax.model.TopicTag;

/**
 * This DTO class will handle all incoming topic - related creation requests
 * Designed to cover all associated functionality of creating a topic
 * Used predominately by the instructor
 */

public class TopicRequest {

    private String topicName;
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
