package com.nicolas.qa.tests;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.Key;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SeleniumDemoTest {

    @Test
    public void abrirPagina(){
        System.setProperty("webdriver.edge.driver","C:\\WebDriver\\msedgedriver.exe");
        WebDriver driver = new EdgeDriver();

        driver.get("https://www.saucedemo.com");

        WebElement username = driver.findElement(By.id("user-name"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        username.sendKeys("standard_user");
        password.sendKeys("secret_sauce");
        loginButton.click();

        new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.urlContains("inventory"));
        
        WebElement productList = driver.findElement(By.id("inventory_container"));
        List<WebElement> products = driver.findElements(By.className("inventory_item"));

        assertTrue(driver.getCurrentUrl().toLowerCase().contains("inventory"));
        assertTrue(productList.isDisplayed());
        assertEquals(6, products.size());

        driver.quit();
    }

    
    @ParameterizedTest
    @CsvSource({
        "usuario_invalido1, clave_invalida1",
        "usuario_invalido2, clave_invalida2",
        "locked_out_user, secret_sauce"
    })
    public void wrongLogin(String username, String password){
        System.setProperty("webdriver.edge.driver", "C:\\WebDriver\\msedgedriver.exe");
        WebDriver driver = new EdgeDriver();

        driver.get("https://www.saucedemo.com");
        
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();

        WebElement errorMessage = new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[data-test='error']")));
        
        assertTrue(errorMessage.getText().toLowerCase().contains("username and password"));

        driver.quit();
    }
}

