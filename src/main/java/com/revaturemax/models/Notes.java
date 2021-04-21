package com.revaturemax.models;

import com.fasterxml.jackson.annotation.JsonFilter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@JsonFilter("Notes")
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @Lob
    private String notes;

    public Notes() {}

    public Notes(Employee employee, Topic topic, String notes) {
        this.employee = employee;
        this.topic = topic;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notes notes1 = (Notes) o;
        return Objects.equals(employee, notes1.employee) && Objects.equals(topic, notes1.topic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, topic);
    }

}
