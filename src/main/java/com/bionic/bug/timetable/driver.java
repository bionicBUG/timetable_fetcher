package com.bionic.bug.timetable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class driver {

    public String webdriver(String username, String password) throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");

        WebDriver driver = new ChromeDriver(options);
        driver.get("https://ecampus.srmist.edu.in/login");

        // Fill the username
        WebElement usernameElement = driver.findElement(By.cssSelector("body > app-root > div.wrapper > app-login > div > div > div > div > div > div > div > div.card.mb-0 > div > form > div:nth-child(1) > input"));
        usernameElement.sendKeys(username);

        // Fill the password
        WebElement passwordElement = driver.findElement(By.cssSelector("body > app-root > div.wrapper > app-login > div > div > div > div > div > div > div > div.card.mb-0 > div > form > div:nth-child(2) > input"));
        passwordElement.sendKeys(password);

        // Copy and fill the captcha
        String captcha = driver.findElement(By.id("mainCaptcha")).getAttribute("value");
        WebElement captchaInput = driver.findElement(By.cssSelector("#mainCaptcha1"));
        captchaInput.sendKeys(captcha.replaceAll("\\s+", ""));

        // Sign in
        driver.findElement(By.cssSelector("body > app-root > div.wrapper > app-login > div > div > div > div > div > div > div > div.card.mb-0 > div > form > div:nth-child(4) > div:nth-child(1) > button")).click();
        Thread.sleep(15000);

        // Get timetable
        int cols = driver.findElements(By.xpath("//*[@id=\"bookingHistoryTable\"]/tbody/tr")).size();
        String xPathStart = "//*[@id=\"bookingHistoryTable\"]/tbody/tr[";
        String xPathEndc2 = "]/td[2]";
        String xPathEndc3 = "]/td[3]";
        String xPathFinalTime;
        String xPathFinalClass;

        StringBuilder tt = new StringBuilder();
        tt.append("<html>");
        for (int i = 1; i <= cols; i++) {
            xPathFinalTime = xPathStart + i + xPathEndc2;
            xPathFinalClass = xPathStart + i + xPathEndc3;
            tt.append(driver.findElement(By.xpath(xPathFinalTime)).getText() + " --> " + driver.findElement(By.xpath(xPathFinalClass)).getText() + "<br>");
        }
        tt.append("</html>");
        String timetable = tt.toString();

        driver.close();
        return timetable;
    }
}