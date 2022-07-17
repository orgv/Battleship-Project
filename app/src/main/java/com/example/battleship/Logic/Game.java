package com.example.battleship.Logic;

import com.example.battleship.Logic.Player;
import com.example.battleship.Logic.Board;
import com.example.battleship.Logic.Point;
import com.example.battleship.Logic.ScoreBoard;
import com.example.battleship.Logic.Tile;

import java.util.Random;


public class Game {
    private boolean playerTurn;// true = humanPlayer turn, false = computerPlayer turn
    private boolean huntMode=false;
    private Random random = new Random();
    private Board playerBoard;
    private Board computerBoard;
    private Point computerCurrentPoint;
    private Point randomPoint;
    private Point calculatedPoint;
    private int computerDirection;
    private Point computerLastHitPoint;

    public Game() {
        // Context.getString(R.string.myStringResource);
        playerBoard = new Board("My Board");
        computerBoard = new Board("Opponent's Board");

        setPlayerTurn(true);
        //computerCurrentPoint = getRandomPoint();

    }
    public Point getCalculatedPoint(Point computerLastHitPoint) {
        int lastRow = computerLastHitPoint.getRow();
        int lastCol = computerLastHitPoint.getCol();
        int boardSize = getPlayerBoard().getBoardSize();

        while (true) {
            //right
            if (computerDirection == 1) {
                if (lastCol < boardSize - 1) {
                    if ((getPlayerBoard().getTile(lastRow, lastCol + 1).getTileStatus() == Tile.Status.NONE)
                            || (getPlayerBoard().getTile(lastRow, lastCol + 1).getTileStatus() == Tile.Status.SHIP)) {
                        calculatedPoint.setRow(lastRow);
                        calculatedPoint.setCol(lastCol + 1);
                        return calculatedPoint;
                    }
                }
                nextDirection();
            }
            //up
            if (computerDirection == 2) {

                if (lastRow > 0) {
                    if ((getPlayerBoard().getTile(lastRow - 1, lastCol).getTileStatus() == Tile.Status.NONE)
                            || (getPlayerBoard().getTile(lastRow - 1, lastCol).getTileStatus() == Tile.Status.SHIP)) {
                        calculatedPoint.setRow(lastRow - 1);
                        calculatedPoint.setCol(lastCol);
                        return calculatedPoint;
                    }
                }
                nextDirection();

            }
            //left
            if (computerDirection == 3) {

                if (lastCol > 0) {
                    if ((getPlayerBoard().getTile(lastRow, lastCol - 1).getTileStatus() == Tile.Status.NONE)
                            || (getPlayerBoard().getTile(lastRow, lastCol - 1).getTileStatus() == Tile.Status.SHIP)) {
                        calculatedPoint.setRow(lastRow);
                        calculatedPoint.setCol(lastCol - 1);
                        return calculatedPoint;
                    }
                }
                nextDirection();
            }

            //down
            if (computerDirection == 4) {

                if (lastRow < boardSize - 1) {
                    if ((getPlayerBoard().getTile(lastRow + 1, lastCol).getTileStatus() == Tile.Status.NONE)
                            || (getPlayerBoard().getTile(lastRow + 1, lastCol).getTileStatus() == Tile.Status.SHIP)) {
                        calculatedPoint.setRow(lastRow + 1);
                        calculatedPoint.setCol(lastCol);
                        return calculatedPoint;
                    }
                }
                nextDirection();
            }
        }
    }
    public void nextDirection() {
        if (computerDirection >= 4)
            computerDirection = 1;
        else
            computerDirection++;
    }
    public int computerPlay() {
        int theState;
        theState = playTile(computerCurrentPoint, playerBoard);
        //hit a ship - now trying to sink it
        if (huntMode) {
            //case HIT
            if (theState == 1) {
                computerCurrentPoint = getCalculatedPoint(computerCurrentPoint);
            }

            //case MISS
            else if (theState == 0) {
                nextDirection();
                computerCurrentPoint = getCalculatedPoint(computerLastHitPoint);
            }

            //case SUNK
            else {
                computerCurrentPoint = getRandomPoint();
                huntMode = false;
            }
        }

        //the ship is sunk- now searching for new random point
        else {
            //case HIT
            if (theState == 1) {
                computerLastHitPoint = computerCurrentPoint;
                huntMode = true;
                computerCurrentPoint = getCalculatedPoint(computerLastHitPoint);
            }
            //case MISS or SUNK
            else {
                computerCurrentPoint = getRandomPoint();
            }
        }
        return theState;
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
