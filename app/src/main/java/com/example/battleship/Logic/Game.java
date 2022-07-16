package com.example.battleship.Logic;

import com.example.battleship.Logic.Player;
import com.example.battleship.Logic.Board;
import com.example.battleship.Logic.Point;
import com.example.battleship.Logic.ScoreBoard;
import com.example.battleship.Logic.Tile;

import java.util.Random;


public class Game {
    private boolean playerTurn;// true = humanPlayer turn, false = computerPlayer turn
    private Point computerCurrentPoint;
    private Point randomPoint;
    private Random random = new Random();
    private Board playerBoard;
    private Board computerBoard;


    public Game() {
        playerBoard = new Board("My Board");
        computerBoard = new Board("Opponent's Board");
        setPlayerTurn(true);
        computerCurrentPoint = getRandomPoint();

    }

    public Point getRandomPoint() {
        int row, col;
        do {
            row = random.nextInt(playerBoard.getBoardSize());
            col = random.nextInt(playerBoard.getBoardSize());
        } while ((playerBoard.getTile(row, col).getTileStatus() == Tile.Status.HIT)
                || (playerBoard.getTile(row, col).getTileStatus() == Tile.Status.MISS)
                || (playerBoard.getTile(row, col).getTileStatus() == Tile.Status.SUNK));
        randomPoint.setRow(row);
        randomPoint.setCol(col);
        return randomPoint;
    }

    public int playTile(Point point, Board board) {
        boolean sunk;
        Tile tileSelected = board.getTile(point.getRow(), point.getCol());
        Tile.Status tileStatus =tileSelected .getTileStatus();
        //in case player MISS
        if (tileStatus == Tile.Status.NONE){
            tileSelected.setTileStatus(Tile.Status.MISS);
            board.countDownOneTileFromBoard();
            toggleTurn();
            return 0;
        }
        //in case player HIT
        else if (tileStatus == Tile.Status.SHIP) {
            sunk = tileSelected.hitTile();

            if (sunk) {//if all ship is sinking
                Point[] shipPoint = tileSelected.getShipPoints();
                for (int i = 0; i < shipPoint.length; i++) {
                    board.getTile(shipPoint[i].getRow(), shipPoint[i].getCol()).setTileStatus(Tile.Status.SUNK);
                }
                board.setNumberOfShipsLeft(board.getNumberOfShipsLeft() - 1);
                board.countDownOneTileFromBoard();
                toggleTurn();
                return 2;
            } else {//if just part of ship is sinking
                tileSelected.setTileStatus(Tile.Status.HIT);
                board.countDownOneTileFromBoard();
                toggleTurn();
                return 1;
            }
        } else
            return -1;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }
    public void toggleTurn() {
        playerTurn = !playerTurn;
    }

    public boolean checkIfWin(Board board) {
        return board.getNumberOfShipsLeft() == 0;
    }

    public Board getPlayerBoard() {
        return playerBoard;
    }

    public Board getComputerBoard() {
        return computerBoard;
    }

    public void setPlayerBoard(Board playerBoard) {
        this.playerBoard = playerBoard;
    }

    public void setComputerBoard(Board computerBoard) {
        this.computerBoard = computerBoard;
    }
}
