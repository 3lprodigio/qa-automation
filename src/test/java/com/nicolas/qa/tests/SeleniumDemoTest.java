package com.nicolas.qa.tests;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.Key;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import java.time.Duration;

public class SeleniumDemoTest {
    
    @Test
    public void abrirPagina(){
        System.setProperty("webdriver.edge.driver","C:\\WebDriver\\msedgedriver.exe");
        WebDriver driver = new EdgeDriver();

        driver.get("https://www.bing.com");
        String Titulo = driver.getTitle();

        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("selenium WebDriver");
        searchBox.sendKeys(Keys.ENTER);

        new webDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.titleContains("Selenium"));

        assertEquals(driver.getTitle().toLowerCase().contains("selenium"),true);

        driver.quit();
    }

}

