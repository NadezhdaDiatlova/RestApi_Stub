package com.example.model;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class User {
    @NotBlank(message = "Login must not be empty or null")
    private String login;

    @NotBlank(message = "Password must not be empty or null")
    private String pwd;

    private Date dt;
    private String email;

    //конструктор для получение user из БД
    public User(String login, String pwd, Date dt, String email) {
        this.login = login;
        this.pwd = pwd;
        this.dt = dt;
        this.email = email;
    }

    // конструктор для вставки user в БД
    public User(String login, String pwd, String email) {
        this.login = login;
        this.pwd = pwd;
        this.dt = new java.util.Date();
        this.email = email;
    }

    public User() {
    }

    public String getLogin() {
        return login;
    }

    public String getPwd() {
        return pwd;
    }

    public Date getDt() {
        return dt;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + pwd + '\'' +
                ", date=" + dt +
                ", email='" + email + '\'' +
                '}';
    }

}
