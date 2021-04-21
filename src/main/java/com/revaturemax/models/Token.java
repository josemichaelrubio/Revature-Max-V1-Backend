package com.revaturemax.models;

public class Token {

    private final long employeeId;
    private final Role employeeRole;

    public Token(long employeeId, Role employeeRole) {
        this.employeeId = employeeId;
        this.employeeRole = employeeRole;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public Role getEmployeeRole() {
        return employeeRole;
    }

}
