package com.deepseadevs.fisheatfish;

import com.deepseadevs.fisheatfish.game.GameData;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

// Only handles read write to data collection
// does not handle application logic

/*
data -> Database folder
data/accounts.csv -> Login info
data/history/*.csv -> Game history (info about every game played by an account)

Formatting
accounts.csv: userID,displayName,password,highScore
history.csv: score,level,fishEaten,size,ended,startTime,endTime,gameDuration
 */
class DataBase {
    private Map<String, UserData> dataMap;
    private String dataPath;

    public DataBase(String dataPath) {
        this.dataPath = dataPath;
        this.dataMap = new HashMap<>();
        try {
            Files.createDirectories(Paths.get(dataPath+"/history"));
        } catch (IOException e) {
            System.err.println("Error creating directories: " + e.getMessage());
        }
        loadFromCSV();
    }

    // Loads the data from the CSV
    private void loadFromCSV() {
        try (BufferedReader accountsReader = new BufferedReader(
                new FileReader(dataPath+"/accounts.csv"))) {
            String accountsRow;
            while ((accountsRow = accountsReader.readLine()) != null) {
                String[] accountsCol = accountsRow.split(",");

                ArrayList<GameData> history = new ArrayList<>();
                try (BufferedReader historyReader = new BufferedReader(
                        new FileReader(dataPath+"/history/"+accountsCol[0]+".csv"))) {
                    String historyRow;
                    while ((historyRow = historyReader.readLine()) != null) {
                        String[] historyCol = historyRow.split(",");
                        history.add(new GameData(
                                Long.parseLong(historyCol[0]),
                                Integer.parseInt(historyCol[1]),
                                Integer.parseInt(historyCol[2]),
                                Integer.parseInt(historyCol[3]),
                                Boolean.parseBoolean(historyCol[4]),
                                Instant.parse(historyCol[5]),
                                Instant.parse(historyCol[6]),
                                Duration.parse(historyCol[7])));
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("CSV file not found. Starting with empty database.");
                } catch (IOException e) {
                    System.err.println("Error reading CSV file: " + e.getMessage());
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing number from CSV: " + e.getMessage());
                }

                dataMap.put(accountsCol[0], new UserData(
                        accountsCol[0],
                        accountsCol[1],
                        accountsCol[2],
                        Long.parseLong(accountsCol[3]),
                        history));
            }
        } catch (FileNotFoundException e) {
            System.out.println("CSV file not found. Starting with empty database.");
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number from CSV: " + e.getMessage());
        }
    }

    // Save current dataMap to CSV
    private void saveToCSV() {
        try (BufferedWriter accountsWriter = new BufferedWriter(
                new FileWriter(dataPath+"/accounts.csv"))) {
            for (Map.Entry<String, UserData> entry : dataMap.entrySet()) {
                UserData userData = entry.getValue();
                accountsWriter.write(userData.getUserID()+","+
                        userData.getDisplayName()+","+
                        userData.getPassword()+","+
                        userData.getHighScore());
                accountsWriter.newLine();

                try (BufferedWriter historyWriter = new BufferedWriter(
                        new FileWriter(dataPath+"/history/"+userData.getUserID()+".csv"))) {
                    List<GameData> history = userData.getHistory();
                    for (GameData gameData : history) {
                        historyWriter.write(gameData.getScore() + "," +
                                gameData.getLevel() + "," +
                                gameData.getFishEaten() + "," +
                                gameData.getSize() + "," +
                                gameData.isEnded() + "," +
                                gameData.getStartTime() + "," +
                                gameData.getEndTime() + "," +
                                gameData.getGameDuration());
                        historyWriter.newLine();
                    }
                } catch (IOException e) {
                    System.err.println("Error writing to CSV file: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
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