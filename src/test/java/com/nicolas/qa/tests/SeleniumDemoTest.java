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

import com.nicolas.qa.pages.InventoryPage;
import com.nicolas.qa.pages.LoginPage;

import org.openqa.selenium.support.ui.ExpectedConditions;

public class SeleniumDemoTest {

    public WebDriver setupDriver(){
        System.setProperty("webdriver.edge.driver","C:\\WebDriver\\msedgedriver.exe");
        WebDriver driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        return driver;
    }

    @Test
    public void inventoryPageTest(){

        LoginPage loginP = new LoginPage(driver);
        InventoryPage inventoryP = new InventoryPage(driver)

        loginP.open();
        loginP.login("standard_user","secret_sauce");

        new WebDriverWait(driver, Duration.ofSeconds(1)).until(ExpectedConditions.urlContains("inventory"));   

        assertTrue(driver.getCurrentUrl().toLowerCase().contains("inventory"));
        assertTrue(inventoryP.isProductListDisplayed());
        assertEquals(6, inventoryP.webProducts());
        assertEquals("Sauce Labs Backpack",inventoryP.getFirstProductName());
        assertEquals("$29.99",inventoryP.getFirstProductPrice());


    }

    @ParameterizedTest
    @CsvSource({
        "usuario_invalido1, clave_invalida1",
        "usuario_invalido2, clave_invalida2",
        "usuario_invalido3, secret_sauce"
    })
    public void wrongLogin(String username, String password){

        LoginPage loginP = new LoginPage(driver);

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

