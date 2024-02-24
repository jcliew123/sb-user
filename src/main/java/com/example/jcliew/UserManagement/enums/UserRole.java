package com.example.jcliew.UserManagement.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserRole {
    ADMIN("ADMIN"),
    USER("USER");

    private String role;

    public String getRole() {
        return role;
    }
}
