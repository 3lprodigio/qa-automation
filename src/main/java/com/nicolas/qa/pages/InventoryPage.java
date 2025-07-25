package com.nicolas.qa.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class InventoryPage {

    private WebDriver driver;

    private By productList = By.id("inventory_container");
    private By name = By.className("inventory_item_name");
    private By price = By.className("inventory_item_price");
    private By product = By.className("inventory_item");
    
    public InventoryPage(WebDriver driver){
        this.driver = driver;
    }

    public List<WebElement> webProducts(){
        return driver.findElements(product);
    }

    public String getFirstProductName(){
        return driver.findElements(name).get(0).getText();
    }

    public String getFirstProductPrice(){
        return driver.findElements(price).get(0).getText();
    }

    public boolean isProductListDisplayed(){
        return driver.findElement(productList).isDisplayed();
    }

}
