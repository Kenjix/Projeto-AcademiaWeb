package com.example.academia.model.DTO;

public record AuthDTO(String login, String password) {
    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }


}
