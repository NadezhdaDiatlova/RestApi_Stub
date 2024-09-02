package com.example.controller;

import com.example.exception.UserNotFoundException;
import com.example.model.User;
import com.example.service.DatabaseManager;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api")
@Validated
public class StubController {

    @GetMapping(value = "/user/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> getUserByLogin(@PathVariable String login) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2001));
        } catch (InterruptedException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        try {
            User userFromDb = DatabaseManager.getUserByLogin(login);
            // Запись в CSV-файл
            try (FileWriter fileWriter = new FileWriter("user_" + login + ".csv")) {
                StringBuilder csvRow = new StringBuilder();
                csvRow.append(login).append(",");
                csvRow.append(userFromDb.getPwd()).append(",");
                csvRow.append(userFromDb.getDt()).append(",");
                csvRow.append(userFromDb.getEmail()).append("\n");
                fileWriter.write(csvRow.toString());
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return ResponseEntity.ok(userFromDb);
        } catch (SQLException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при получении пользователя: " + e.getMessage());
        }
    }

    @PostMapping(value = "/post-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> postUser(@Valid @RequestBody User user)  {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2001));
        } catch (InterruptedException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String login =  user.getLogin();
        String pwd = user.getPwd();
        String email  = user.getEmail();

        User userForInsert = new User(login, pwd, email);
        System.out.println(userForInsert);

        try {
            DatabaseManager.insertUser(userForInsert);
            return new ResponseEntity<>(userForInsert, HttpStatus.OK);
    } catch (SQLException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


    @PostMapping(value = "/post-user-map", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> postUserMapStrict(@RequestBody Map<String, Object> userMap) {
        try {
            if (userMap.size() != 3) {
                throw new IllegalArgumentException("The request must only contain three fields: login, pwd, email");
            }
            if (!userMap.containsKey("login") || !userMap.containsKey("pwd") || !userMap.containsKey("email")) {
                throw new IllegalArgumentException("The request must include the keys: login, pwd, email");
            }
            if (userMap.get("login") == null || userMap.get("pwd") == null ||
                    userMap.get("login").toString().isEmpty() || userMap.get("pwd").toString().isEmpty()) {
                throw new IllegalArgumentException("The values for 'login' and 'pwd' must not be null or empty");
            }
            User user = new User((String) userMap.get("login"), (String) userMap.get("pwd"), (String) userMap.get("email"));

            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2001));

            DatabaseManager.insertUser(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InterruptedException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (SQLException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

