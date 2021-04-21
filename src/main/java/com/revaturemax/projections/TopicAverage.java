package com.revaturemax.projections;

import com.revaturemax.models.Topic;

public class TopicAverage {

    private Topic topic;
    private Double averageCompetency;
    private Long competenciesCounted;

    public TopicAverage(long topicId, Double averageCompetency, Long competenciesCounted) {
        topic = new Topic();
        topic.setId(topicId);
        this.averageCompetency = averageCompetency;
        this.competenciesCounted = competenciesCounted;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Double getAverageCompetency() {
        return averageCompetency;
    }

    public void setAverageCompetency(Double averageCompetency) {
        this.averageCompetency = averageCompetency;
    }

    public Long getCompetenciesCounted() {
        return competenciesCounted;
    }

    public void setCompetenciesCounted(Long competenciesCounted) {
        this.competenciesCounted = competenciesCounted;
    }

}
