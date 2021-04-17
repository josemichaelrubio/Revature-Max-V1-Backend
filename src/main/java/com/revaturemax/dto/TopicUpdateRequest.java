package com.revaturemax.dto;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

/**
 * This DTO class will handle all incoming topic - related update requests
 * Designed to cover all associated functionality of updating/deleting a topic
 * Used predominately by the instructor
 */

@Component
@Scope("prototype")
public class TopicUpdateRequest {
    private String topicName;
    private String topicTagName;
    private long batchId;
    private Date date;

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

    public TopicUpdateRequest(String topicName, String topicTagName, long batchId, Date date) {
        this.topicName = topicName;
        this.topicTagName = topicTagName;
        this.batchId = batchId;
        this.date = date;
    }

    public TopicUpdateRequest() {
    }

    @Override
    public String toString() {
        return "TopicUpdateRequest{" +
                "topicName='" + topicName + '\'' +
                ", topicTagName='" + topicTagName + '\'' +
                ", batchId=" + batchId +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopicUpdateRequest that = (TopicUpdateRequest) o;
        return batchId == that.batchId && Objects.equals(topicName, that.topicName) && Objects.equals(topicTagName, that.topicTagName) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topicName, topicTagName, batchId, date);
    }
}
