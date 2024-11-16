package com.deepseadevs.fisheatfish;

import java.util.Collection;

public class LeaderboardUtils {
    public static void getTopUsers(int n) {
        Collection<UserData> allData = DatabaseManager.getInstance().getAllUserData();
        // TODO:
        //  Complete this function
        //  this function will be used by leaderboard
        //  return top n users based on high score
    }
}
