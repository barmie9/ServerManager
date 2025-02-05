package com.barmie.ServerManager.controller;

import com.barmie.ServerManager.service.SystemCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class SystemCommandController {

    private final SystemCommandService systemCommandService;

    @GetMapping("/power-off")
    public ResponseEntity<String> powerOffSystem() {
        systemCommandService.powerOffSystem();
        return ResponseEntity.ok("OK");
    }
}
