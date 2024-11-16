package com.deepseadevs.fisheatfish;

import java.util.Collection;

public class DatabaseManager {
    private static DatabaseManager instance;
    private final DataBase dataBase;

    private DatabaseManager() {
        this.dataBase = new DataBase("data.csv");

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
        return dataBase.get(username);
    }

    public boolean userExists(String username) {
        return dataBase.containsKey(username);
    }

    public boolean isCorrectPassword(String username, String password) {
        return userExists(username) && dataBase.get(username).password.equals(password);
    }

    // TODO:
    //  complete the following classes
    //  any change in data must be reflected on csv too
    public void updateUserData(UserData data) {
        // TODO:
        //  write to database, database will handle save to csv
        //  else raise
    }

    public void deleteUser(String name) {
        // TODO:
        //  Used to remove user on "delete account" button
        //  remove user from database
    }

    public void createNewUser(String name, String password) {
        if (userExists(name))
            throw new IllegalArgumentException("User already exists: " + name);
        addNewUser(new UserData(name, password, 0));
    }

    public void addNewUser(UserData user) {
        dataBase.put(user.username, user);
    }

    public Collection<UserData> getAllUserData() {
        return dataBase.getAll();
    }
}
