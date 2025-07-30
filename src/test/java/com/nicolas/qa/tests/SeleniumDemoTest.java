package com.nicolas.qa.tests;

import com.nicolas.qa.UserType;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.Key;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.support.ui.WebDriverWait;

import com.nicolas.qa.pages.InventoryPage;
import com.nicolas.qa.pages.LoginPage;
import com.nicolas.qa.pages.WrongLogin;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.mockito.Mockito;

public class SeleniumDemoTest {

    WebDriver driver;

    public WebDriver setupDriver(){
        System.setProperty("webdriver.edge.driver","C:\\WebDriver\\msedgedriver.exe");
        WebDriver driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        return driver;
    }

    @Test
    public void inventoryPageTest(){

        LoginPage loginP = new LoginPage(driver);
        InventoryPage inventoryP = new InventoryPage(driver);

        loginP.open();
        loginP.login("standard_user","secret_sauce");

        new WebDriverWait(driver, Duration.ofSeconds(1)).until(ExpectedConditions.urlContains("inventory"));   

        assertTrue(driver.getCurrentUrl().toLowerCase().contains("inventory"));
        assertTrue(inventoryP.isProductListDisplayed());
        assertEquals(6, inventoryP.getProducts().size());
        assertEquals("Sauce Labs Backpack",inventoryP.getFirstProductName());
        assertEquals("$29.99",inventoryP.getFirstProductPrice());
    }

    @Test
    public void getFirstProductNameNoProduct(){
        WebDriver driver = Mockito.mock(WebDriver.class);
        Mockito.when(driver.findElements(By.className("inventory_item_name")))
        .thenReturn(Collections.emptyList());

        InventoryPage inventoryp = new InventoryPage(driver);
        assertNull(inventoryp.getFirstProductName());
    }

    @Test
    public void getFirstProductPriceNoProduct(){

        WebDriver driver = Mockito.mock(WebDriver.class);
        Mockito.when(driver.findElements(By.className("inventory_item_price")))
        .thenReturn(Collections.emptyList());

        InventoryPage inventoryP = new InventoryPage(driver);
        assertNull(inventoryP.getFirstProductPrice());
    }

    @ParameterizedTest
    @ValueSource(strings = {"","invalid_user", "invalid_user2","invalid_user3"})
    public void wrongUserLogin(String username){

        LoginPage loginP = new LoginPage(driver);
        WrongLogin wrongLogin = new WrongLogin(driver);

        loginP.open();
        loginP.login(username, "secret_sauce");

        new WebDriverWait(driver, Duration.ofSeconds(1)).until(d -> wrongLogin.isErrorDisplayed());

        String errorText = wrongLogin.getErrorMessage().toLowerCase();

        assertTrue(wrongLogin.isErrorDisplayed());
        assertTrue(errorText.contains("username")
                 ||errorText.contains("user"));

    }

    @ParameterizedTest
    @ValueSource(strings = {"","123456789","sauce_secret","Secret_Sauce"})
    public void wrongPasswordLogin(String password){
        LoginPage loginP = new LoginPage(driver);
        WrongLogin wrongL = new WrongLogin(driver);

        loginP.open();
        loginP.login("standard_user", password);

        new WebDriverWait(driver, Duration.ofSeconds(1)).until(d -> wrongL.isErrorDisplayed());

        String errorText = wrongL.getErrorMessage().toLowerCase();

        assertTrue(wrongL.isErrorDisplayed());
        assertTrue(errorText.contains("password"));
    }

    @ParameterizedTest
    @EnumSource(value = UserType.class, names = {"STANDARD_USER"})
    public void succesfulLoginEnum(UserType userType){
        LoginPage loginP = new LoginPage(driver);
        InventoryPage inventoryP = new InventoryPage(driver);

        loginP.open();
        loginP.login(userType.getUsername(),userType.getPassword());

        new WebDriverWait(driver,Duration.ofSeconds(1)).until(
            ExpectedConditions.urlContains("inventory")
        );

        assertTrue(driver.getCurrentUrl().contains("inventory"));
        assertTrue(inventoryP.isProductListDisplayed());
    }

    @ParameterizedTest
    @EnumSource(value = UserType.class, names = ("LOCKED_OUT_USER"))
    public void failedLoginEnum(UserType userType){
        LoginPage loginP = new LoginPage(driver);
        WrongLogin wrongL = new WrongLogin(driver);
        
        loginP.open();
        loginP.login(userType.getUsername(),userType.getPassword());

        new WebDriverWait(driver,Duration.ofSeconds(1)).until(d -> wrongL.isErrorDisplayed());

        String errorText = wrongL.getErrorMessage().toLowerCase();

        assertTrue(wrongL.isErrorDisplayed());
        assertTrue(errorText.contains("locked"));
    }

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

