package com.revaturemax.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Component
@Scope("prototype")
@JsonIgnoreProperties("hibernateLazyInitializer")
public class Password {

    @Id
    private Long employeeId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private byte[] salt, hash;

    public Password() {}

    public Password(Employee employee, byte[] salt, byte[] hash) {
        this.employee = employee;
        this.salt = salt;
        this.hash = hash;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password = (Password) o;
        return employee.equals(password.employee) && Arrays.equals(salt, password.salt) &&
                Arrays.equals(hash, password.hash);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(employee);
        result = 31 * result + Arrays.hashCode(salt);
        result = 31 * result + Arrays.hashCode(hash);
        return result;
    }

}
