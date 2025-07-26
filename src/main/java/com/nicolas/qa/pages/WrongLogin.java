package com.nicolas.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WrongLogin {
    
    private WebDriver driver;

    private By errorMessage = By.cssSelector("h3[data-test='error']");

    public WrongLogin(WebDriver driver){
        this.driver = driver;
    }

    public String getErrorMessage(){
        return driver.findElement(errorMessage).getText();
    }

    public boolean isErrorDisplayed(){
        return driver.findElement(errorMessage).isDisplayed();
    }
}
