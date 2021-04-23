package com.revaturemax.projections;


public class TagAverage {

    private Long id;
    private String tagName;
    private Double averageCompetency;
    private Long competenciesCounted;

    public TagAverage(Long id, String tagName, Double averageCompetency, Long competenciesCounted) {
        this.id = id;
        this.tagName = tagName;
        this.averageCompetency = averageCompetency;
        this.competenciesCounted = competenciesCounted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
