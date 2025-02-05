package com.barmie.ServerManager.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class SystemCommandService {

    // TODO test on linux
    public void powerOffSystem() {
        try {
            // Linux:
//            Runtime.getRuntime().exec("poweroff");
            // Windows:
            Runtime.getRuntime().exec("shutdown -s -t 0");
        } catch (IOException e) {
            log.info("Power off ERROR: " + e.getMessage());
        }
    }
}
