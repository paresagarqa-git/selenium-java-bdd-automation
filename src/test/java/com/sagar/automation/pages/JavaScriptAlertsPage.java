package com.sagar.automation.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class JavaScriptAlertsPage extends BasePage {

    private final By result = By.id("result");

    public JavaScriptAlertsPage(WebDriver driver) {
        super(driver);
    }

    public JavaScriptAlertsPage open() {
        open("/javascript_alerts");
        return this;
    }

    /** @param label the button text, e.g. "Click for JS Alert" */
    public void clickButton(String label) {
        click(By.xpath("//button[normalize-space()='" + label + "']"));
    }

    private Alert waitForAlert() {
        return wait.until(ExpectedConditions.alertIsPresent());
    }

    public String getAlertText() {
        return waitForAlert().getText();
    }

    public void acceptAlert() {
        waitForAlert().accept();
    }

    public void dismissAlert() {
        waitForAlert().dismiss();
    }

    public void typeInPromptAndAccept(String text) {
        Alert alert = waitForAlert();
        alert.sendKeys(text);
        alert.accept();
    }

    public String getResult() {
        return getText(result);
    }
}
