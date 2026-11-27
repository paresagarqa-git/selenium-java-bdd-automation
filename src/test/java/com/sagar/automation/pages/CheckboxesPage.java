package com.sagar.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CheckboxesPage extends BasePage {

    private final By checkboxes = By.cssSelector("#checkboxes input[type='checkbox']");

    public CheckboxesPage(WebDriver driver) {
        super(driver);
    }

    public CheckboxesPage open() {
        open("/checkboxes");
        waitForVisible(checkboxes);
        return this;
    }

    /** @param position 1-based, as written in the feature file */
    private WebElement checkbox(int position) {
        List<WebElement> all = driver.findElements(checkboxes);
        return all.get(position - 1);
    }

    public boolean isChecked(int position) {
        return checkbox(position).isSelected();
    }

    public void setChecked(int position, boolean checked) {
        WebElement box = checkbox(position);
        if (box.isSelected() != checked) {
            box.click();
        }
    }
}
