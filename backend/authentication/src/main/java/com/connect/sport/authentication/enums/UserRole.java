package com.connect.sport.authentication.enums;

public enum UserRole {

    ANONYMOUS_USER("ANONYMOUS_USER"),
    ADMIN_ROLE("ADMIN_ROLE")
    ;

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
