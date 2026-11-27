package com.sagar.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Example 1: the element is already in the DOM but hidden, and becomes visible after a loading bar.
 */
public class DynamicLoadingPage extends BasePage {

    private final By startButton = By.cssSelector("#start button");
    private final By loadingBar = By.id("loading");
    private final By finishText = By.cssSelector("#finish h4");

    public DynamicLoadingPage(WebDriver driver) {
        super(driver);
    }

    public DynamicLoadingPage open() {
        open("/dynamic_loading/1");
        return this;
    }

    public void clickStart() {
        click(startButton);
    }

    public boolean isFinishTextDisplayed() {
        return driver.findElement(finishText).isDisplayed();
    }

    /** The loading bar takes about 5 seconds, so this uses a longer wait than the default. */
    public String waitForFinishText() {
        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        longWait.until(ExpectedConditions.invisibilityOfElementLocated(loadingBar));
        return longWait.until(ExpectedConditions.visibilityOfElementLocated(finishText)).getText().trim();
    }
}
