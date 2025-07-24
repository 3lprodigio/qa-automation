package com.nicolas.qa.tests;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class SeleniumDemoTest {
    
    @Test
    public void abrirPagina(){
        System.setProperty("webdriver.edge.driver","C:\\WebDriver\\msedgedriver.exe");
        WebDriver driver = new EdgeDriver();

        driver.get("https://www.bing.com");
        String Titulo = driver.getTitle();

        assertEquals("Search - Microsoft Bing", Titulo);

        driver.quit();
    }

}
