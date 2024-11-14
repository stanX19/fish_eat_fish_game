package com.deepseadevs.fisheatfish;

import java.util.HashMap;
import java.util.Map;


public class DatabaseManager {
    private static DatabaseManager instance;
    private Map<String, UserData> dataBase;

    private DatabaseManager() {
        dataBase = new HashMap<>();

        // TODO:
        //  load data from csv file
        //  the map data structure can to hold more data such as high score
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public boolean isCorrectPassword(String username, String password) {
        return dataBase.containsKey(username)
                && dataBase.get(username).password.equals(password);
    }

    // TODO:
    //  complete the following classes
    public void updateUserData(UserData data) {
        // write to database
        // if data.username already exists in database, overwrite
        // else raise
    }

    public void createNewUser(String name, String password) {
        dataBase.put(name, new UserData(name, password, 0));
    }

    public void addNewUser(UserData user) {
        dataBase.put(user.username, user);
    }
}
