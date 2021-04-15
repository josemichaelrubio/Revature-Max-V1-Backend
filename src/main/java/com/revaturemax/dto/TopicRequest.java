package com.revaturemax.dto;


import java.util.Date;

/**
 * This DTO class will handle all incoming topic - related requests
 * Designed to cover all associated functionality of creating/updating/deleting a topic
 * Used predominately by the instructor
 */

public class TopicRequest {

    private String topicName;
    private String topicTagName;
    private long batchId;
    private Date date;

    public TopicRequest(String topicName, String topicTagName, long batchId, Date date) {
        super();
        this.topicName = topicName;
        this.topicTagName = topicTagName;
        this.batchId = batchId;
        this.date = date;
    }

    public TopicRequest() {
        super();
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicTagName() {
        return topicTagName;
    }

    public void setTopicTagName(String topicTagName) {
        this.topicTagName = topicTagName;
    }

    public long getBatchId() {
        return batchId;
    }

    public void setBatchId(long batchId) {
        this.batchId = batchId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
