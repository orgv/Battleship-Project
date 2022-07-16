package com.example.battleship;

import android.app.GameManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.battleship.Logic.Board;
import com.example.battleship.Logic.Game;
import com.example.battleship.Logic.Player;
import com.example.battleship.Logic.Ship;

public class GameActivity extends AppCompatActivity {

    public final static String THE_WINNER = "winner";
    public final static String THE_SCORE = "score";

    private Game mGame;// =new Game();
    private BoardView playerGrid, computerGrid;
    private int state, score;
    private boolean gameIsOver = false;
    private String the_winner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

//        Board myBoard = new Board("Human Player");
//
//        myBoardView = findViewById(R.id.player_grid);
//        opponentBoardView = findViewById(R.id.enemy_grid);
//
//        myBoardView.setBoard(myBoard);
//        myBoardView.setReadyToDraw(true);
//        Board opponentBoard = new Board("Computer Player");
//        for (Ship ship : opponentBoard.getFleet()) {
//            ship.setVisible(false);
//        }
//        opponentBoardView.setBoard(opponentBoard);
//        opponentBoardView.setReadyToDraw(true);
//        disableOpponentBoard();
        //mGame = new Game(new Player());

        computerGrid = findViewById(R.id.enemy_grid);
        playerGrid = findViewById(R.id.player_grid);

        computerGrid.setBoard();




        //computerGrid.setAdapter(computerAdapter);
        //playerGrid.setAdapter(playerAdapter);



    }

}