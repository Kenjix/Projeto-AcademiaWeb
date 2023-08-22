package com.example.academia.enuns;

public enum UserRole {
    ADMIN("admin"),
    INSTRUTOR("instrutor"),
    USER("user");


    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}