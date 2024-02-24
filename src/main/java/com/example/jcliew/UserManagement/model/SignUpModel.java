package com.example.jcliew.UserManagement.model;

import com.example.jcliew.UserManagement.enums.UserRole;

public record SignUpModel(String username,
                          String password,
                          UserRole role) {
}
