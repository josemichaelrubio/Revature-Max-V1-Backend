package com.revaturemax.projections;


public class TagAverage {

    private String tagName;
    private Double averageCompetency;
    private Long competenciesCounted;

    public TagAverage(String tagName, Double averageCompetency, Long competenciesCounted) {
        this.tagName = tagName;
        this.averageCompetency = averageCompetency;
        this.competenciesCounted = competenciesCounted;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
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
