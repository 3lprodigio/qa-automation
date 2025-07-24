package com.nicolas.qa.tests;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.Key;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    public WebDriver setupDriver(){
        System.setProperty("webdriver.edge.driver","C:\\WebDriver\\msedgedriver.exe");
        WebDriver driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        return driver;
    }

    public void loginTest(WebDriver driver, String username, String password) {
        driver.get("https://www.saucedemo.com");

        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();
 
    }
        
    @Test
    public void inventoryPageTest(){

        loginTest(driver, "standard_user","secret_sauce");

        new WebDriverWait(driver, Duration.ofSeconds(1)).until(ExpectedConditions.urlContains("inventory"));   
    
        WebElement productList = driver.findElement(By.id("inventory_container"));
        WebElement name = driver.findElement(By.className("inventory_item_name"));
        WebElement price = driver.findElement(By.className("inventory_item_price"));
        List<WebElement> products = driver.findElements(By.className("inventory_item"));

        assertTrue(driver.getCurrentUrl().toLowerCase().contains("inventory"));
        assertTrue(productList.isDisplayed());
        assertEquals(6, products.size());
        assertEquals("Sauce Labs Backpack",name.getText());
        assertEquals("$29.99",price.getText());


    }

    @ParameterizedTest
    @CsvSource({
        "usuario_invalido1, clave_invalida1",
        "usuario_invalido2, clave_invalida2",
        "usuario_invalido3, secret_sauce"
    })
    public void wrongLogin(String username, String password){

        loginTest(driver, username, password);

        WebElement errorMessage = new WebDriverWait(driver, Duration.ofSeconds(1)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[data-test='error']")));
        
        //assertTrue(errorMessage.getText().toLowerCase().contains("username and password"));
        String errorText = errorMessage.getText().toLowerCase();

        assertTrue(errorText.contains("password")
                || errorText.contains("username"));

    }

    WebDriver driver;
    @BeforeEach
    void setup() {
        driver = setupDriver();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

