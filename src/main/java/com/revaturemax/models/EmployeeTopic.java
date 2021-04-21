package com.revaturemax.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "employee_topic")
public class EmployeeTopic {

    @EmbeddedId
    private EmployeeTopicId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("employeeId")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("topicId")
    private Topic topic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fav_notes_id")
    private Notes favNotes;

    private Float competency;

    public EmployeeTopic() {
        id = new EmployeeTopicId();
    }

    public EmployeeTopic(Employee employee, Topic topic, float competency) {
        id = new EmployeeTopicId(employee.getId(), topic.getId());
        this.employee = employee;
        this.topic = topic;
        this.competency = competency;
    }

    public EmployeeTopicId getId() {
        return id;
    }

    public void setId(EmployeeTopicId id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        id.setEmployeeId(employee.getId());
        this.employee = employee;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        id.setTopicId(topic.getId());
        this.topic = topic;
    }

    public Notes getFavNotes() {
        return favNotes;
    }

    public void setFavNotes(Notes favNotes) {
        this.favNotes = favNotes;
    }

    public float getCompetency() {
        return competency;
    }

    public void setCompetency(float competency) {
        this.competency = competency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeTopic that = (EmployeeTopic) o;
        return Float.compare(that.competency, competency) == 0 &&
                Objects.equals(employee, that.employee) &&
                Objects.equals(topic, that.topic) &&
                Objects.equals(favNotes, that.favNotes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, topic, favNotes, competency);
    }

}
