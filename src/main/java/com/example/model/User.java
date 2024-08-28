package com.example.model;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class User {
    @NotBlank(message = "Login must not be empty or null")
    private String login;

    @NotBlank(message = "Password must not be empty or null")
    private String password;

    private Date date;
    private String email;

    //конструктор для получение user из БД
    public User(String login, String password, Date date, String email) {
        this.login = login;
        this.password = password;
        this.date = date;
        this.email = email;
    }

    // конструктор для вставки user в БД
    public User(@NotBlank(message = "Login must not be empty or null") String login, @NotBlank(message = "Password must not be empty or null") String password, String email) {
        this.login = login;
        this.password = password;
        this.date = new java.util.Date();
        this.email = email;
    }

    //public User() {}

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Date getDate() {
        return date;
    }

    public String getEmail() {
        return email;
    }


    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", date=" + date +
                ", email='" + email + '\'' +
                '}';
    }

}
