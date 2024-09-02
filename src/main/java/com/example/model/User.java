package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class User {

    @JsonProperty("login")
    @NotBlank(message = "Login must not be empty or null")
    private String login;

    @JsonProperty("pwd")
    @NotBlank(message = "Password must not be empty or null")
    private String pwd;

    @JsonProperty("dt")
    //@JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dt;

    @JsonProperty("email")
    private String email;

    //конструктор для получения user из БД
    public User(String login, String pwd, java.sql.Date sqlDate, String email) {
        this.login = login;
        this.pwd = pwd;
        this.dt = sqlDate.toLocalDate();
        this.email = email;
    }

    // конструктор для вставки user в БД
    public User(String login, String pwd, String email) {
        this.login = login;
        this.pwd = pwd;
        this.dt = LocalDate.now();
        this.email = email;
    }

    public User() {
    }

    public String getLogin() {
        return login;
    }

    public void setDt(LocalDate dt) {
        this.dt = dt;
    }

    public String getPwd() {
        return pwd;
    }

    public LocalDate getDt() {
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
