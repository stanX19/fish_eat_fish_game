package com.deepseadevs.fisheatfish;

import java.util.HashMap;
import java.util.Map;


public class DatabaseManager {
    private static DatabaseManager instance;
    private String csvPath;
    private Map<String, UserData> loadedData;

    private DatabaseManager() {
        this.loadedData = new HashMap<>();
        this.csvPath = "data.csv";

        // TODO:
        //  load data from csv file
        //  the map data structure can to hold more data such as high score
        createNewUser("test", "test");
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public UserData getUserData(String username) {
        return loadedData.get(username);
    }

    public boolean userExists(String username) {
        return loadedData.containsKey(username);
    }

    public boolean isCorrectPassword(String username, String password) {
        return userExists(username)
                && loadedData.get(username).password.equals(password);
    }

    // TODO:
    //  complete the following classes
    //  any change in data must be reflected on csv too
    public void updateUserData(UserData data) {
        // TODO:
        //  write to database
        //  if data.username already exists in database, overwrite and update csv
        //  else raise
    }

    public void loadDataBase() {
        // TODO:
        //  load all data from csvPath
    }

    public void saveDataBase() {
        // TODO:
        //  saves all data in loadedData to csvPath
    }

    public void deleteUser(String name) {
        // TODO:
        //  Used to remove user on "delete account" button
        //  remove user from database
    }

    public void createNewUser(String name, String password) {
        loadedData.put(name, new UserData(name, password, 0));
    }

    public void addNewUser(UserData user) {
        loadedData.put(user.username, user);
    }
}
