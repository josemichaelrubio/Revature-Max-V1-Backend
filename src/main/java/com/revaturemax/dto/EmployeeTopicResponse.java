package com.revaturemax.dto;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
@Scope("prototype")
public class EmployeeTopicResponse {
    private String topic;
    private String tag;
    private Float competency;

    public EmployeeTopicResponse(String topic, String tag, Float competency) {
        this.topic = topic;
        this.tag = tag;
        this.competency = competency;
    }

    public EmployeeTopicResponse() {
    }

    @Override
    public String toString() {
        return "EmployeeTopicResponse{" +
                "topic='" + topic + '\'' +
                ", tag='" + tag + '\'' +
                ", competency=" + competency +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeTopicResponse that = (EmployeeTopicResponse) o;
        return Objects.equals(topic, that.topic) && Objects.equals(tag, that.tag) && Objects.equals(competency, that.competency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topic, tag, competency);
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Float getCompetency() {
        return competency;
    }

    public void setCompetency(Float competency) {
        this.competency = competency;
    }
}
