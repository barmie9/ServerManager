package com.barmie.ServerManager.controller;

import com.barmie.ServerManager.service.NoIpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class NoIpController {

    private final NoIpService noIpService;

    // TODO Only for test - remove
    @GetMapping("/test-selenium")
    public ResponseEntity<String> testSelenium(){
        noIpService.seleniumStart();
        return ResponseEntity.ok("OK Selenium");
    }

    // TODO Only for test - remove
    @GetMapping("/test-quit-selenium")
    public ResponseEntity<String> testHtmlUnit(){
        noIpService.seleniumStop();
        return ResponseEntity.ok("OK quit-selenium");
    }
}
