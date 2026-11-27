package com.sagar.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class DropdownPage extends BasePage {

    private final By dropdown = By.id("dropdown");

    public DropdownPage(WebDriver driver) {
        super(driver);
    }

    public DropdownPage open() {
        open("/dropdown");
        return this;
    }

    private Select select() {
        return new Select(waitForVisible(dropdown));
    }

    public void selectByVisibleText(String option) {
        select().selectByVisibleText(option);
    }

    public String getSelectedOption() {
        return select().getFirstSelectedOption().getText().trim();
    }
}
