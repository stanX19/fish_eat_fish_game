package com.deepseadevs.fisheatfish;

public class UserData {
    public String username;
    public String password;
    public long highScore;

    UserData(String username, String password, long highScore) {
        this.username = username;
        this.password = password;
        this.highScore = highScore;
    }
}
