package com.sagar.automation.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ScreenshotUtil {

    private static final Path SCREENSHOT_DIR = Paths.get("target", "screenshots");

    private ScreenshotUtil() {
    }

    public static byte[] capture(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    /** Saves the screenshot as a PNG so CI can upload it as an artifact. Returns the file path. */
    public static Path save(byte[] png, String scenarioName) throws IOException {
        Files.createDirectories(SCREENSHOT_DIR);
        Path file = SCREENSHOT_DIR.resolve(uniqueFileName(scenarioName, "png"));
        Files.write(file, png);
        return file;
    }

    /**
     * Scenario name + timestamp + thread id. The thread id keeps names unique when the same
     * scenario runs on two threads at once (e.g. two Scenario Outline examples).
     */
    public static String uniqueFileName(String scenarioName, String extension) {
        String safeName = scenarioName.replaceAll("[^a-zA-Z0-9-_]", "_");
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));
        return safeName + "_" + timestamp + "_t" + Thread.currentThread().getId() + "." + extension;
    }
}
