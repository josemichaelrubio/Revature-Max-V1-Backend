package com.revaturemax.dto;

import java.util.Objects;

public class NewEmployee {

    private String email;
    private String role;
    private String password;
    private String name;

    public NewEmployee(String email, String role, String password, String name) {
        this.email = email;
        this.role = role;
        this.password = password;
        this.name = name;
    }

    public NewEmployee() {
    }

    @Override
    public String toString() {
        return "NewEmployee{" +
                "email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", password='" + "*******" + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewEmployee that = (NewEmployee) o;
        return Objects.equals(email, that.email) && Objects.equals(role, that.role) && Objects.equals(password, that.password) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, role, password, name);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
