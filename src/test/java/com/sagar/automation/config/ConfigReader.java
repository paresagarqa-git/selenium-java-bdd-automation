package com.sagar.automation.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Reads settings from config/config.properties.
 * A JVM system property with the same key wins, so CI can run
 * {@code mvn test -Dheadless=true} without editing the file.
 */
public final class ConfigReader {

    private static final String CONFIG_FILE = "config/config.properties";
    private static final Properties PROPERTIES = load();

    private ConfigReader() {
    }

    private static Properties load() {
        Properties props = new Properties();
        try (InputStream in = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (in == null) {
                throw new IllegalStateException("Missing " + CONFIG_FILE + " on the test classpath");
            }
            props.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("Could not read " + CONFIG_FILE, e);
        }
        return props;
    }

    public static String get(String key) {
        String value = System.getProperty(key, PROPERTIES.getProperty(key));
        if (value == null) {
            throw new IllegalArgumentException("No config value for '" + key + "'");
        }
        return value.trim();
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static String baseUrl() {
        return get("base.url");
    }
}
