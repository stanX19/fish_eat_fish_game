package com.deepseadevs.fisheatfish;

import java.util.HashMap;
import java.util.Map;

public class DataHandler {
    private final Map<String, String> userDatabase = new HashMap<>();

    public DataHandler() {
        readData();
    }

    public void readData() {
        userDatabase.put("user1", "pass1");
        userDatabase.put("user2", "pass2");
    }

    public void saveData() {

    }

    public boolean verifyCredentials(String username, String password) {
        return userDatabase.containsKey(username)
                && userDatabase.get(username).equals(password);
    }
}
