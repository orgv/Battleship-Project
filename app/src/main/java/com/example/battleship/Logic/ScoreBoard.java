package com.example.battleship.Logic;

import android.icu.lang.UProperty;

public class ScoreBoard {
    String playerName;
    float playerScore;
    int playerWins;
    int playerLoses;

    public ScoreBoard(String name, float score, int wins, int loses) {
        playerName = name;
        playerScore = score;
        playerWins = wins;
        playerLoses = loses;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public float getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(float playerScore) {
        this.playerScore = playerScore;
    }

    public int getPlayerWins() {
        return playerWins;
    }

    public void setPlayerWins(int playerWins) {
        this.playerWins = playerWins;
    }

    public int getPlayerLoses() {
        return playerLoses;
    }

    public void setPlayerLoses(int playerLoses) {
        this.playerLoses = playerLoses;
    }
}

