package com.sagar.automation.driver;

import com.sagar.automation.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Creates and holds one WebDriver per thread. Each parallel scenario runs on its own TestNG
 * thread, so ThreadLocal gives every scenario its own private browser.
 */
public final class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();
    private static final Set<String> DRIVERS_RESOLVED = ConcurrentHashMap.newKeySet();

    private DriverFactory() {
    }

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("WebDriver not started. Is the @Before hook running?");
        }
        return driver;
    }

    public static String browserName() {
        return ConfigReader.get("browser").toLowerCase();
    }

    public static void initDriver() {
        String browser = browserName();
        boolean headless = ConfigReader.getBoolean("headless");

        WebDriver driver = switch (browser) {
            case "chrome" -> createChrome(headless);
            case "firefox" -> createFirefox(headless);
            case "edge" -> createEdge(headless);
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        };

        // Explicit waits only (see BasePage). Mixing implicit and explicit waits causes slow, flaky tests.
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigReader.getInt("page.load.timeout.seconds")));
        if (!headless) {
            driver.manage().window().maximize();
        }
        DRIVER.set(driver);
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }

    private static WebDriver createChrome(boolean headless) {
        resolveDriverOnce("chrome", WebDriverManager.chromedriver());
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--window-size=1920,1080", "--no-sandbox", "--disable-dev-shm-usage",
                "--disable-gpu", "--disable-notifications", "--remote-allow-origins=*");
        return new ChromeDriver(options);
    }

    private static WebDriver createFirefox(boolean headless) {
        resolveDriverOnce("firefox", WebDriverManager.firefoxdriver());
        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
            options.addArguments("-headless");
        }
        options.addArguments("--width=1920", "--height=1080");
        return new FirefoxDriver(options);
    }

    private static WebDriver createEdge(boolean headless) {
        resolveDriverOnce("edge", WebDriverManager.edgedriver());
        EdgeOptions options = new EdgeOptions();
        if (headless) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--window-size=1920,1080", "--no-sandbox", "--disable-dev-shm-usage",
                "--disable-gpu", "--disable-notifications");
        return new EdgeDriver(options);
    }

    /**
     * WebDriverManager downloads the driver binary on first use. With parallel threads, only the
     * first thread should do that; the others wait here and then reuse the cached binary.
     */
    private static void resolveDriverOnce(String browser, WebDriverManager manager) {
        if (DRIVERS_RESOLVED.contains(browser)) {
            return;
        }
        synchronized (DriverFactory.class) {
            if (!DRIVERS_RESOLVED.contains(browser)) {
                manager.setup();
                DRIVERS_RESOLVED.add(browser);
            }
        }
    }
}
