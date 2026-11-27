package com.sagar.automation.data;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

/**
 * Loads test data from JSON files under src/test/resources/testdata.
 * Files are read once and cached; the data is read-only, so this is safe for parallel threads.
 */
public final class TestDataReader {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private static final Map<String, User> USERS = loadUsers();

    private TestDataReader() {
    }

    /** @param key a key from users.json, e.g. "valid" or "invalid_password" */
    public static User user(String key) {
        User user = USERS.get(key);
        if (user == null) {
            throw new IllegalArgumentException(
                    "No user '" + key + "' in testdata/users.json. Known users: " + USERS.keySet());
        }
        return user;
    }

    private static Map<String, User> loadUsers() {
        JsonNode users = readJson("testdata/users.json").get("users");
        Map<String, User> result = new TreeMap<>();
        users.fields().forEachRemaining(entry ->
                result.put(entry.getKey(), MAPPER.convertValue(entry.getValue(), User.class)));
        return result;
    }

    private static JsonNode readJson(String resource) {
        try (InputStream in = TestDataReader.class.getClassLoader().getResourceAsStream(resource)) {
            if (in == null) {
                throw new IllegalStateException("Missing " + resource + " on the test classpath");
            }
            return MAPPER.readTree(in);
        } catch (IOException e) {
            throw new IllegalStateException("Could not read " + resource, e);
        }
    }
}
