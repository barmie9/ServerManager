package com.barmie.ServerManager.service.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeleniumService {
    private WebDriver noIpDriver;
    private Thread seleniumThread;
    private final String EMAIL_URL = "https://account.proton.me/mail";
    private final String NO_IP_URL = "https://www.noip.com/login";

    // TODO remove passwords from code, implement some encryption
    private final String EMAIL_USERNAME = "EMAIL_USERNAME";
    private final String NO_IP_USERNAME = "NO_IP_USERNAME";
    private final String EMAIL_PASSWORD = "EMAIL_PASSWORD";
    private final String NO_IP_PASSWORD = "NO_IP_PASSWORD";


    public void runSeleniumThread() {
        seleniumThread = new Thread(() -> {
            WebDriver noIpDriver = getSeleniumDriver(true);
            log.info("SELENIUM THREAD STARTED.");
            logInNoIp(noIpDriver);
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    log.info("DOMAIN STATUS: " + checkNoIpDomainStatus(noIpDriver));
                    Thread.sleep(5000);
                }
            } catch (InterruptedException e) {
                log.info("InterruptedException: SELENIUM THREAD INTERRUPTED.");
            } finally {
                if (noIpDriver != null) { // TODO probably if to be removed
                    noIpDriver.quit();
                    log.info("SELENIUM DRIVER QUIT.");
                }
            }
        });
        seleniumThread.start();
    }

    public void stopSeleniumThread() {
        seleniumThread.interrupt();
        seleniumThread.interrupt(); // TODO Sometimes problem with interrupt:
        // TODO Failed to shutdown Driver Command Executor
    }

    public WebDriver getSeleniumDriver(boolean visibleInterface) {
        WebDriverManager.chromedriver().setup(); // Automatic download and setup of ChromeDriver
        WebDriver driver;
        if (!visibleInterface) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless"); // without interface
            driver = new ChromeDriver(options);
        } else {
            driver = new ChromeDriver();
        }
        return driver;
    }

    // Not necessary, probably this method will be remove
    public void testEmailLogin(WebDriver driver) {
        driver.get(EMAIL_URL);

        // Set WebDriverWait max 15 sec
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement usernameInput = findById(wait, "username");
        WebElement passwordInput = findById(wait, "password");
        usernameInput.sendKeys(EMAIL_USERNAME);
        passwordInput.sendKeys(EMAIL_PASSWORD);
        WebElement LogInButton = findByXpath(wait,
                "/html/body/div[1]/div[4]/div[1]/main/div[1]/div[2]/form/button");
        LogInButton.click();

        WebElement lastEmailName = findByXpath(wait,
                "/html/body/div[1]/div[3]/div/div[2]/div/div[2]/div/div/div/main/div/div/div/div/div/div[2]/div[1]/div/div/div/div/div[2]/span[2]/span/span/span[1]");
        System.out.println(lastEmailName.getText());
    }

    public void logInNoIp(WebDriver driver) {
        driver.get(NO_IP_URL);
        // Set WebDriverWait max 15 sec
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement usernameInput = findById(wait, "username");
        WebElement passwordInput = findById(wait, "password");
        usernameInput.sendKeys(NO_IP_USERNAME);
        passwordInput.sendKeys(NO_IP_PASSWORD);
        WebElement LogInButton = findById(wait, "clogs-captcha-button");
        LogInButton.click();

        // wait and get URL:
        wait.until(ExpectedConditions.urlContains("https://my.noip.com/")); // Wait until URL will change
        driver.get("https://my.noip.com/dynamic-dns");
        wait.until(ExpectedConditions.urlContains("https://my.noip.com/dynamic-dns")); // Wait until URL will change

    }

    // TODO check if the account has been logged out
    public String checkNoIpDomainStatus(WebDriver driver) {
        driver.get("https://my.noip.com/dynamic-dns");

        if (!isLoggedIn(driver, "user-email-container")) {
            logInNoIp(driver);
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement domainStatus = findByXpath(wait,
                "/html/body/div[1]/div[1]/div[2]/div[1]/div[1]/div[2]/div/div/div[2]/div[1]/table/tbody/tr/td[1]/div[2]/span/span/div/a");
//        log.info(domainStatus.getText());
        return domainStatus.getText();
    }

    // The method checks whether the user is logged in based on the element id,
    // which is displayed only to the logged in user.
    // TODO It works, but needs more tests
    public boolean isLoggedIn(WebDriver driver, String elementId) {
        // Set WebDriverWait max 10 sec
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        try {
            WebElement element = findById(wait, elementId);
            return true;
        } catch (TimeoutException e) {
            log.info("USER IS LOGGED OUT!");
            return false;
        }
    }

    // The method finds an element by id, waiting for it for max time: wait
    public WebElement findById(WebDriverWait wait, String id) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
    }

    // The method finds an element by xpath, waiting for it for max time: wait
    public WebElement findByXpath(WebDriverWait wait, String xpath) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(xpath)));
    }

}