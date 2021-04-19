package com.revaturemax.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="batch")
@Component
@JsonIgnoreProperties("hibernateLazyInitializer")
@Scope("prototype")
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @OneToOne
    @JoinColumn(name = "instructor_id")
    private Employee instructor;

    @ManyToMany
    @JoinTable(name = "employee_batch",
            joinColumns = @JoinColumn(name = "batch_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Employee> associates = new ArrayList<>();

    public Batch() {}

    public Batch(long id) {
        this.id = id;
    }

    public Batch(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Batch(String name, String description, Employee instructor) {
        this.name = name;
        this.description = description;
        this.instructor = instructor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Employee getInstructor() {
        return instructor;
    }

    public void setInstructor(Employee instructor) {
        this.instructor = instructor;
    }

    public List<Employee> getAssociates() {
        return associates;
    }

    public void setAssociates(List<Employee> associates) {
        this.associates = associates;
    }

    public void addAssociate(Employee associate) {
        this.associates.add(associate);
    }

    public void removeAssociate(Employee associate) {
        this.associates.remove(associate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Batch batch = (Batch) o;
        return Objects.equals(name, batch.name) && Objects.equals(description, batch.description) &&
                Objects.equals(instructor, batch.instructor) && Objects.equals(associates, batch.associates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, instructor, associates);
    }

}
