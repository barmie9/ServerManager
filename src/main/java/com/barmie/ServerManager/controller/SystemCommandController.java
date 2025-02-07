package com.barmie.ServerManager.controller;

import com.barmie.ServerManager.dto.request.NameRequest;
import com.barmie.ServerManager.service.SystemCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/reboot")
    public ResponseEntity<String> rebootSystem() {
        systemCommandService.rebootSystem();
        return ResponseEntity.ok("OK");
    }

    // TODO change response (create new class)
    @GetMapping("/running-docker-containers")
    public ResponseEntity<String> getRunningDockerContainers() {
        String response = systemCommandService.getRunDockerContainers();
        return ResponseEntity.ok(response);
    }

    // TODO change response (create new class)
    @PostMapping("/restart-container")
    public ResponseEntity<String> restartContainer(@RequestBody NameRequest nameRequest) {
        String response = systemCommandService.dockerRestartContainer(nameRequest.getName());
        return ResponseEntity.ok(response);
    }
}
