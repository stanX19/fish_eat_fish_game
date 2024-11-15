package com.deepseadevs.fisheatfish;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// Only handles read write to data collection
// does not handle application logic
class DataBase {
    private Map<String, UserData> dataMap;
    private String csvPath;

    public DataBase(String csvPath) {
        this.csvPath = csvPath;
        this.dataMap = new HashMap<>();
        loadFromCSV();
    }

    private void loadFromCSV() {
        // TODO:
        //  Loads the data from the CSV
    }

    private void saveToCSV() {
        // TODO
        //  Save current dataMap to CSV
    }

    // Add or update a user
    public void put(String username, UserData userData) {
        dataMap.put(username, userData);
        saveToCSV();
    }

    // Remove a user
    public void remove(String username) {
        dataMap.remove(username);
        saveToCSV();
    }

    // Retrieve a user
    public UserData get(String username) {
        return dataMap.get(username);
    }

    public boolean containsKey(String username) {
        return dataMap.containsKey(username);
    }

    public Collection<UserData> getAll() {
        return new ArrayList<>(dataMap.values());
    }
}
