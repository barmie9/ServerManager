package com.barmie.ServerManager.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.io.IOException;

@Service
@Slf4j
public class SystemCommandService {

    public void powerOffSystem() {
        try {
            Runtime.getRuntime().exec("poweroff");
        } catch (IOException e) {
            log.debug("Power off ERROR: " + e.getMessage());
        }
    }

    public void rebootSystem() {
        try {
            Runtime.getRuntime().exec("reboot");
        } catch (IOException e) {
            log.debug("Reboot ERROR: " + e.getMessage());
        }
    }

    // TODO change return value (create new class)
    public String getRunDockerContainers() {
        StringBuilder result = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec("docker ps");

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }

            process.waitFor();

        } catch (IOException | InterruptedException e) {
            log.debug("Power off ERROR: " + e.getMessage());
        }
        return result.toString();
    }
}
