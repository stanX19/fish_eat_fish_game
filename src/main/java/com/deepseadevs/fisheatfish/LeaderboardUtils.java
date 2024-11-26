package com.deepseadevs.fisheatfish;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collection;
import java.util.Collections;

public class LeaderboardUtils {
    public static Collection<UserData> getTopUsers(int n) {
        Collection<UserData> allData = DatabaseManager.getInstance().getAllUserData();


        if (allData == null || allData.isEmpty() || n <= 0) {
            // Return an empty collection if no data is available or invalid input
            return Collections.emptyList();
        }

        // Create a list to sort the data (since Collection doesn't support direct sorting)
        ArrayList<UserData> userList = new ArrayList<>(allData);

        // Sort the list in descending order of high scores
        userList.sort(Comparator.comparingLong(UserData::getHighScore).reversed());

        // Return the top n users (or all users if n exceeds the available data)
        return userList.subList(0, Math.min(n, userList.size()));
    }
}
