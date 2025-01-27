package com.barmie.ServerManager.controller;

// TODO - Class only for testing. To be removed.

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/user-data")
    public ResponseEntity<HashMap<String,String>> getTestData(){
        HashMap<String,String> testData = new HashMap<>();
        testData.put("email", "bar.mie.71204@som.com");
        testData.put("first_name", "Bartek");
        testData.put("last_name", "Kowalski");
        return ResponseEntity.ok(testData);
    }
}
