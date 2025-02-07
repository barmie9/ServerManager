package com.barmie.ServerManager.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
@Slf4j
public class SystemCommandService {

    public void powerOffSystem() {
        try {
            Runtime.getRuntime().exec("poweroff");
        } catch (IOException e) {
            log.debug("Power off ERROR: {}", e.getMessage());
        }
    }

    public void rebootSystem() {
        try {
            Runtime.getRuntime().exec("reboot");
        } catch (IOException e) {
            log.debug("Reboot ERROR: {}", e.getMessage());
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
            log.debug("GET RUN DOCKER CONT. ERROR: {}", e.getMessage());
        }
        return result.toString();
    }

    //TODO create new method executeCommand and edit method dockerRestartContainer and getRunDockerContainers
    public String dockerRestartContainer(String name) {
        StringBuilder result = new StringBuilder();
        StringBuilder errorResult = new StringBuilder(); // Added for error trapping

        try {
            Process process = Runtime.getRuntime().exec("docker restart " + name);

            // Reading standard output (stdout)
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }

            // Reading errors (stderr)
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = errorReader.readLine()) != null) {
                errorResult.append(line).append("\n");
            }

            process.waitFor();

        } catch (IOException | InterruptedException e) {
            String errorMessage = "Docker restart ERROR: " + e.getMessage();
            log.info(errorMessage);
            return errorMessage;
        }

        // If an error occurred, return an error message
        if (!errorResult.isEmpty()) {
            String errorMessage = "Docker restart ERROR: " + errorResult.toString();
            log.info(errorMessage);
            return errorMessage;
        }

        log.info("RESTART OK: {}", result.toString());
        return result.toString();
    }
}
