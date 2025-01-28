package com.barmie.ServerManager.controller;

// TODO - Class only for testing. To be removed.

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/test/user-data")
    public ResponseEntity<HashMap<String, String>> getTestData() {
        HashMap<String, String> testData = new HashMap<>();
        testData.put("email", "bar.mie.71204@som.com");
        testData.put("first_name", "Bartek");
        testData.put("last_name", "Kowalski");
        return ResponseEntity.ok(testData);
    }

    @GetMapping("/super-admin/test")
    public ResponseEntity<String> testSuperAdmin() {
        return ResponseEntity.ok("You have permission: superAdmin, admin, user");
    }

    @GetMapping("/admin/test")
    public ResponseEntity<String> testAdmin() {
        return ResponseEntity.ok("You have permission: admin, user");
    }

    @GetMapping("/user/test")
    public ResponseEntity<String> testUser() {
        return ResponseEntity.ok("You have permission: user");
    }
}
