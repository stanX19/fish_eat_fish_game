package com.deepseadevs.fisheatfish;

import java.util.Collection;

public class DatabaseManager {
    private static DatabaseManager instance;
    private final DataBase dataBase;

    private DatabaseManager() {
        this.dataBase = new DataBase("data.csv");
        createNewUser("test", "test", 100);
        createNewUser("test1", "test", 200);
        createNewUser("test2", "test", 300);
        createNewUser("test3", "test", 400);
        createNewUser("test4", "test", -500);
        createNewUser("test5", "test", 600);
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
        createNewUser(name, password, 0);
    }

    public void createNewUser(String name, String password, long highScore) {
        if (userExists(name))
            throw new IllegalArgumentException("User already exists: " + name);
        addNewUser(new UserData(name, password, highScore));
    }

    public void addNewUser(UserData user) {
        dataBase.put(user.username, user);
    }

    public Collection<UserData> getAllUserData() {
        return dataBase.getAll();
    }
}
