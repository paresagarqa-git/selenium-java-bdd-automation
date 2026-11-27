package com.sagar.automation.utils;

import org.jcodec.api.awt.AWTSequenceEncoder;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Selenium has no built-in video recording (unlike Playwright). This recorder takes a screenshot
 * after every Cucumber step and, when the scenario ends, encodes them into an MP4 with JCodec.
 * It works the same in Chrome, Firefox and Edge, headless or not, and in parallel runs
 * (frames are kept per thread).
 */
public final class VideoRecorder {

    private static final Path VIDEO_DIR = Paths.get("target", "videos");
    private static final int WIDTH = 1280;   // H.264 needs even dimensions; scale every frame to 720p
    private static final int HEIGHT = 720;
    private static final int FRAMES_PER_SECOND = 1;

    private static final ThreadLocal<List<byte[]>> FRAMES = ThreadLocal.withInitial(ArrayList::new);

    private VideoRecorder() {
    }

    public static void start() {
        FRAMES.get().clear();
    }

    /**
     * Adds a frame. Skipped while a JS alert is open: taking a screenshot then would make the
     * browser dismiss the alert and break the next step.
     */
    public static void captureFrame(WebDriver driver) {
        if (isAlertOpen(driver)) {
            return;
        }
        try {
            FRAMES.get().add(ScreenshotUtil.capture(driver));
        } catch (RuntimeException e) {
            // A missing frame should never fail the test.
        }
    }

    /** Encodes the recorded frames to an MP4. Returns null when there is nothing to save. */
    public static Path save(String scenarioName) throws IOException {
        List<byte[]> frames = FRAMES.get();
        if (frames.isEmpty()) {
            return null;
        }
        Files.createDirectories(VIDEO_DIR);
        Path file = VIDEO_DIR.resolve(ScreenshotUtil.uniqueFileName(scenarioName, "mp4"));
        AWTSequenceEncoder encoder = AWTSequenceEncoder.createSequenceEncoder(file.toFile(), FRAMES_PER_SECOND);
        try {
            for (byte[] png : frames) {
                BufferedImage image = ImageIO.read(new ByteArrayInputStream(png));
                if (image != null) {
                    encoder.encodeImage(scale(image));
                }
            }
        } finally {
            encoder.finish();
        }
        return file;
    }

    public static void stop() {
        FRAMES.remove();
    }

    private static boolean isAlertOpen(WebDriver driver) {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        } catch (RuntimeException e) {
            return false;
        }
    }

    private static BufferedImage scale(BufferedImage source) {
        BufferedImage scaled = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = scaled.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(source, 0, 0, WIDTH, HEIGHT, null);
        g.dispose();
        return scaled;
    }
}
