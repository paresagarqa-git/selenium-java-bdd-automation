package com.sagar.automation.hooks;

import com.sagar.automation.config.ConfigReader;
import com.sagar.automation.driver.DriverFactory;
import com.sagar.automation.utils.ScreenshotUtil;
import com.sagar.automation.utils.VideoRecorder;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.nio.file.Files;
import java.nio.file.Path;

public class Hooks {

    private static String videoMode() {
        return ConfigReader.get("video.mode").toLowerCase();
    }

    @Before(order = 0)
    public void startBrowser(Scenario scenario) {
        DriverFactory.initDriver();
        System.out.printf("[%s] %s | %s%n", Thread.currentThread().getName(),
                DriverFactory.browserName(), scenario.getName());
        if (!videoMode().equals("off")) {
            VideoRecorder.start();
        }
    }

    /** One video frame per step. */
    @AfterStep
    public void recordStep(Scenario scenario) {
        if (!videoMode().equals("off")) {
            VideoRecorder.captureFrame(DriverFactory.getDriver());
        }
    }

    @After(order = 0)
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                attachScreenshot(scenario);
            }
            if ((scenario.isFailed() && videoMode().equals("on-failure")) || videoMode().equals("always")) {
                attachVideo(scenario);
            }
        } finally {
            VideoRecorder.stop();
            DriverFactory.quitDriver();
        }
    }

    private void attachScreenshot(Scenario scenario) {
        try {
            byte[] png = ScreenshotUtil.capture(DriverFactory.getDriver());
            // Embedded in the Cucumber HTML report...
            scenario.attach(png, "image/png", scenario.getName());
            // ...and saved as a file for the CI artifact.
            Path file = ScreenshotUtil.save(png, scenario.getName());
            scenario.log("Screenshot saved to " + file);
        } catch (Exception e) {
            scenario.log("Could not capture screenshot: " + e.getMessage());
        }
    }

    private void attachVideo(Scenario scenario) {
        try {
            Path file = VideoRecorder.save(scenario.getName());
            if (file != null) {
                scenario.attach(Files.readAllBytes(file), "video/mp4", scenario.getName() + ".mp4");
                scenario.log("Video saved to " + file);
            }
        } catch (Exception e) {
            scenario.log("Could not save video: " + e.getMessage());
        }
    }
}
