package com.deepseadevs.fisheatfish;

import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {
    private static DatabaseManager instance;
    private Map<String, String> userData;

    private DatabaseManager() {
        userData = new HashMap<>();
        userData.put("test", "test");
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public boolean isCorrectPassword(String username, String password) {
        return userData.containsKey(username) && userData.get(username).equals(password);
    }

    // Additional methods for user management could be added here
}
