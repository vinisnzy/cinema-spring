package com.vinisnzy.cinema.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("admin"),
    USER("user");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }
}
