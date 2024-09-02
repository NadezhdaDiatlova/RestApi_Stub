package com.example.controller;

import com.example.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class RandomStringController {

    private static final String FILE_PATH = "src/users.json";



    public RandomStringController() {
        try {
            generateJsonFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //сгенерируем файл json с 10-ю User
    private void generateJsonFile() throws IOException {
        List<User> users = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            String login = "user" + i;
            String pwd = "password" + i;
            String email = login + "@example.com";
            users.add(new User(login, pwd, email));
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.writeValue(new File(FILE_PATH), users);
    }



    @GetMapping(value = "/random-json-string", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getRandomJsonString() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            List<User> users = mapper.readValue(new File(FILE_PATH), mapper.getTypeFactory().constructCollectionType(List.class, User.class));

            Random random = new Random();
            int randomIndex = random.nextInt(users.size());
            return ResponseEntity.ok(users.get(randomIndex));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

/*
    @GetMapping(value = "/random-string", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getRandomString() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH2));
            int randomIndex = random.nextInt(lines.size());
            return ResponseEntity.ok(lines.get(randomIndex));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    @GetMapping("/random-string2")
    public ResponseEntity<String> getRandomString2() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH2))) {
            List<String> lines2 = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    lines2.add(line);
                }
            }
            int randomIndex = random.nextInt(lines2.size());
            return ResponseEntity.ok(lines2.get(randomIndex));

        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Файл не найден: " + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при чтении файла: " + e.getMessage());
        }
    }


}

 */
