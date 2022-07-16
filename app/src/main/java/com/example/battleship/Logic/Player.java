package com.example.battleship.Logic;
import com.example.battleship.Logic.ScoreBoard;

import java.util.Scanner;

public class Player {
    public static String playerName;
    public static ScoreBoard scoreBoard;


    public Player(String playerName, ScoreBoard scoreBoard) {
        this.playerName = playerName;
        this.scoreBoard = scoreBoard;

    }

    public static ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    public static void setScoreBoard(ScoreBoard scoreBoard) {
        Player.scoreBoard = scoreBoard;
    }


    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        Player.playerName = playerName;
    }
}

