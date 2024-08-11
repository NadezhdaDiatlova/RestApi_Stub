package com.example.springdemo.controller;

import com.example.springdemo.model.UserRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api")
public class StubController {

    @GetMapping(value = "/static-json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> getStaticJson() {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2001));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Map<String, String> response = new HashMap<>();
        response.put("message", "Welcome to the best IT-company Bellintegrator!");
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> postUserData(@RequestBody UserRequest userRequest) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2001));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String login = userRequest.getLogin();
        String password = userRequest.getPassword();
        LocalDate date = LocalDate.now();

        Map<String, Object> response = new HashMap<>();
        response.put("login", login);
        response.put("password", password);
        response.put("date", date);

        return ResponseEntity.ok(response);
    }

}
