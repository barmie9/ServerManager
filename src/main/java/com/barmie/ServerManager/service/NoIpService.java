package com.barmie.ServerManager.service;

import com.barmie.ServerManager.service.selenium.SeleniumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoIpService {

    private final SeleniumService seleniumService;

    // TODO Only for test - remove
    public void seleniumStart() {
        seleniumService.runSeleniumThread();
    }

    // TODO Only for test - remove
    public void seleniumStop() {
        seleniumService.stopSeleniumThread();
    }

}
