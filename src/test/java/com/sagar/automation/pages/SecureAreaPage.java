package com.sagar.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SecureAreaPage extends BasePage {

    private final By heading = By.cssSelector("div.example h2");
    private final By logoutButton = By.cssSelector("a[href='/logout']");

    public SecureAreaPage(WebDriver driver) {
        super(driver);
    }

    public String getHeading() {
        return getText(heading);
    }

    public void logout() {
        click(logoutButton);
    }
}
