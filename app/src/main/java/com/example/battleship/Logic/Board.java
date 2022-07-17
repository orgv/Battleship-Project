package com.example.battleship.Logic;

import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

//
public class Board {
    public final static int BOARD_SIZE = 10;
    public final static String HORIZONTAL = "horizontal";//רוחב
    public final static String VERTICAL = "vertical";//אורך
    private int numberOfTilesLeft;
    private int numberOfShipsLeft;
    private String nameOfPlayer;
    private int numberOfShots;
    private final Random random = new Random();
    //int[][] grid = new int[BOARD_SIZE][BOARD_SIZE]; // matrix-representation reference, raw one. Logic will be based on it.
    ArrayList<Ship> fleet = new ArrayList<>(5);
    private final Tile[][] mTiles;

    private Place[][] board = null;


    GridLayout gridLayout; // Graphical reference to the grid. Provides visualization.


    /**
     * Create a new board of the given size.
     */
    public Board(String playerName) {
        nameOfPlayer = playerName;
        numberOfShipsLeft = 5;
        fleet.add(new Ship(Ship.ShipType.BATTLESHIP));
        fleet.add(new Ship(Ship.ShipType.CRUISER));
        fleet.add(new Ship(Ship.ShipType.CARRIER1));
        fleet.add(new Ship(Ship.ShipType.CARRIER2));
        fleet.add(new Ship(Ship.ShipType.DESTROYER));

        mTiles = new Tile[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                mTiles[i][j] = new Tile();
            }
        }
    }
    public boolean isFleetEmpty(){
       return fleet.isEmpty();
    }
    public int getNumberOfTilesLeft() {
        return numberOfTilesLeft;
    }

    public void setNumberOfTilesLeft(int numberOfTilesLeft) {
        this.numberOfTilesLeft = numberOfTilesLeft;
    }

    public int getNumberOfShipsLeft() {
        return numberOfShipsLeft;
    }

    public void setNumberOfShipsLeft(int numberOfShipsLeft) {
        this.numberOfShipsLeft = numberOfShipsLeft;
    }

    boolean isOutOfBounds(int x, int y) {
        return x >= BOARD_SIZE || y >= BOARD_SIZE || x < 0 || y < 0;
    }

    public void place(int row, int col, Ship ship) {
        int size = ship.getSize();
        Point[] shipPoints = new Point[ship.getSize()];

        if (ship.getDirection().compareTo(HORIZONTAL) == 0) {
            for (int i = 0; i < size; i++) {
                mTiles[row][col + i].setShip(ship);
                shipPoints[i] = new Point(row, col + i);
            }
        }
        //vertical
        else {
            for (int i = 0; i < size; i++) {
                mTiles[row + i][col].setShip(ship);
                shipPoints[i] = new Point(row + i, col);
            }
            ship.setPointsOnBoard(shipPoints);
        }
    }

    public boolean checkValidLocation(int row, int col, String direction, int shipSize) {
        if (direction.compareTo(HORIZONTAL) == 0) {
            //check if the ship in the bounds of the board
            if (col + shipSize - 1 > BOARD_SIZE - 1)
                return false;

            for (int i = 0; i < shipSize; i++) {
                //check for collision
                if (mTiles[row][col + i].getTileStatus() != Tile.Status.NONE)
                    return false;
            }
        }

        //vertical case
        else {
            if (row + shipSize - 1 > BOARD_SIZE - 1)
                return false;

            for (int i = 0; i < shipSize; i++) {
                //check for collision
                if (mTiles[row + i][col].getTileStatus() != Tile.Status.NONE)
                    return false;
            }
        }
        return true;
    }

    public void placeShipRandomOnBoard(Ship ship) {
        boolean flag = true;
        int randomRow, randomCol, randomDirectionInt;
        String randomDirection;
        do {
            randomRow = random.nextInt(BOARD_SIZE);   //[0-9]
            randomCol = random.nextInt(BOARD_SIZE);
            randomDirectionInt = random.nextInt(2);        //[0-1]
            if (randomDirectionInt == 1)
                randomDirection = VERTICAL;
            else
                randomDirection = HORIZONTAL;
            flag = checkValidLocation(randomRow, randomCol, randomDirection, ship.getSize());
        } while (!flag);
        ship.setDirection(randomDirection);
        ship.setHeadPoint(randomRow, randomCol);
        place(randomRow, randomCol, ship);
    }

    public Tile getTile(int row, int col) {
        return mTiles[row][col];
    }

    public int getBoardSize() {
        return BOARD_SIZE;
    }

    public int getTotalBoardSize() {
        return BOARD_SIZE * BOARD_SIZE;
    }

    public String getNameOfPlayer() {
        return nameOfPlayer;
    }

    public void setNameOfPlayer(String nameOfPlayer) {
        this.nameOfPlayer = nameOfPlayer;
    }

    public int getNumberOfShots() {
        return numberOfShots;
    }

    public void setNumberOfShots(int numberOfShots) {
        this.numberOfShots = numberOfShots;
    }

    public ArrayList<Ship> getFleet() {
        return fleet;
    }

    public void setFleet(ArrayList<Ship> fleet) {
        this.fleet = fleet;
    }

    public void countDownOneTileFromBoard() {
        if (numberOfTilesLeft > 0)
            numberOfTilesLeft--;
    }


    /**
     * Returns all of the board's places in a LinkedList
     */
    private List<Place> getPlaces() {
        List<Place> boardPlaces = new LinkedList<Place>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                boardPlaces.add(board[i][j]);
            }
        }
        return boardPlaces;
    }

    /**
     * Returns all of the places that have a ship and have been hit
     */
    public List<Place> getShipHitPlaces() {

        List<Place> boardPlaces = getPlaces();
        List<Place> shipHitPlaces = new ArrayList<Place>();

        for (Place place : boardPlaces) {
            if (place.isHit() && place.hasShip()) {
                shipHitPlaces.add(place);
            }
        }
        return shipHitPlaces;
    }

    public Place placeAt(int x, int y){
        if(board == null || isOutOfBounds(x,y) || board[y][x] == null){
            return null;
        }

        return board[y][x];
    }

}


//Game - 2 Boards, 2 players(Human&CPU)
//        for each: Board which includes its own fleet.
//
//
//Board - 10X10 grid, 100 tiles. int numOfShips. 10X10 temp array which tracks the square state.(X,0,..) fleet, Ship[].
//        We create the ships in the beginning.
//
//        Once a ship is being destroyed, it will be removed from the array.
//        Subsequent Tiles(Sequence) which ships are deployed on, will be marked. <-> Ship ImageView respectively.
//
//
//
//
//Ship - shipSize.

