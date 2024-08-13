package com.example.model;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class User {
    @NotBlank(message = "Login must not be empty or null")
    private String login;

    @NotBlank(message = "Password must not be empty or null")
    private String password;

    private LocalDate date;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.date = LocalDate.now();
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "{" +
                "\"login\":\"" + login + "\"," +
                "\"password\":\"" + password + "\"," +
                "\"date\":\"" + date + "\"" +
                "}";
    }
}
