package com.example.controller;

import com.example.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api")
@Validated
public class StubController {

    @GetMapping(value = "/static-json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> getStaticJson() {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2001));
        } catch (InterruptedException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Map<String, String> response = new HashMap<>();
        response.put("message", "Welcome to the best IT-company Bellintegrator!");
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> postUser(@Valid @RequestBody User user) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2001));
        } catch (InterruptedException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/post-user-map", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> postUserMapStrict(@RequestBody Map<String, Object> userMap) {
        try {
            if (userMap.size() != 2) {
                return new ResponseEntity<>("The request must only contain two fields: login and password", HttpStatus.BAD_REQUEST);
            }

            if (!userMap.containsKey("login") || !userMap.containsKey("password")) {
                return new ResponseEntity<>("The request must include the keys 'login' and 'password'", HttpStatus.BAD_REQUEST);
            }

            if (userMap.get("login") == null || userMap.get("password") == null ||
                    userMap.get("login").toString().isEmpty() || userMap.get("password").toString().isEmpty()) {
                return new ResponseEntity<>("The values for 'login' and 'password' must not be null or empty", HttpStatus.BAD_REQUEST);
            }

            User user = new User((String) userMap.get("login"), (String) userMap.get("password"));

            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2001));
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (InterruptedException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

