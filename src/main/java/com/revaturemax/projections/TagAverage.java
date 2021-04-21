package com.revaturemax.projections;

import com.revaturemax.models.TopicTag;

public class TagAverage {

    private TopicTag tag;
    private Double averageCompetency;
    private Long competenciesCounted;

    public TagAverage(long tagId, Double averageCompetency, Long competenciesCounted) {
        tag = new TopicTag();
        tag.setId(tagId);
        this.averageCompetency = averageCompetency;
        this.competenciesCounted = competenciesCounted;
    }

    public TopicTag getTag() {
        return tag;
    }

    public void setTag(TopicTag tag) {
        this.tag = tag;
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
