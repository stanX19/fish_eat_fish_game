package com.deepseadevs.fisheatfish.database;

import java.util.Collection;

public class DatabaseManager {
    private static DatabaseManager instance;
    private final DataBase dataBase;

    private DatabaseManager() {
        this.dataBase = new DataBase("data");
//        createNewUser("test", "test", "test", 100);
//        createNewUser("tes1", "tes1", "test", 200);
//        createNewUser("tes2", "tes2", "test", 300);
//        createNewUser("tes3", "tes3", "test", 400);
//        createNewUser("tes4", "tes4", "test", -500);
//        createNewUser("tes5", "tes5", "test", 600);
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public UserData getUserData(String userID) {
        return dataBase.get(userID);
    }

    public boolean userExists(String userID) {
        return dataBase.containsKey(userID);
    }

    public boolean isCorrectPassword(String userID, String password) {
        return userExists(userID) && dataBase.get(userID).getPassword().equals(password);
    }

    public void updateUserData(UserData data) {
        if (!userExists(data.getUserID())) {
            throw new IllegalArgumentException("User does not exist: " + data.getUserID());
        }
        dataBase.put(data.getUserID(), data);
    }

    public void deleteUser(String userID) {
        if (!userExists(userID)) {
            throw new IllegalArgumentException("User does not exist: " + userID);
        }
        dataBase.remove(userID);
    }

    public void createNewUser(String userID, String name, String password) {
        createNewUser(userID, name, password, 0);
    }

    public void createNewUser(String userID, String name, String password, long highScore) {
        if (userExists(userID)) throw new IllegalArgumentException("User already exists: " + name);
        addNewUser(new UserData(userID, name, password, highScore));
    }

    public void addNewUser(UserData user) {
        dataBase.put(user.getUserID(), user);
    }

    public Collection<UserData> getAllUserData() {
        return dataBase.getAll();
    }
}
