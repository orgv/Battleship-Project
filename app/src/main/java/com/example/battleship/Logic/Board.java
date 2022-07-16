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
    ArrayList<Ship> fleet = new ArrayList<Ship>(5);
    private final Tile[][] mTiles;

    private Place[][] board = null;
    private int size;


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
    /**Returns the place in the board with coordinates (x, y)
     * @param x x coordinate 0-based index
     * @param y y coordinate 0-based index
     * @return place on the board*/
    public Place placeAt(int x, int y){
        if(board == null || isOutOfBounds(x,y) || board[y][x] == null){
            return null;
        }

        return board[y][x];
    }


    /**Returns true if the (x,y) coordinates given are outside the board*/
    boolean isOutOfBounds(int x, int y){
        return x >= size() || y >= size() || x < 0 || y < 0;
    }

    public void place(int row, int col, Ship ship) {
        int size = ship.getSize();
        Point[] shipPoints = new Point[ship.getSize()];

        if (ship.getDirection().compareTo(HORIZONTAL) == 0) {
            for (int i = 0; i < size; i++) {
                mTiles[row][col + i].setShip(ship);
                shipPoints[i] = new Point(row, col + i);
//                if (row < BOARD_SIZE - 1)
//                    mTiles[row + 1][col + i].setTileStatus(Tile.Status.NONE_X);
//                if (row > 0)
//                    mTiles[row - 1][col + i].setTileStatus(Tile.Status.NONE_X);
            }

//            if (col > 0)
//                mTiles[row][col - 1].setTileStatus(Tile.Status.NONE_X);
//            if (col + size - 1 < BOARD_SIZE - 1)
//                mTiles[row][col + size].setTileStatus(Tile.Status.NONE_X);
        }
        //vertical
        else {
            for (int i = 0; i < size; i++) {
                mTiles[row + i][col].setShip(ship);
                shipPoints[i] = new Point(row + i, col);

//                if (col < BOARD_SIZE - 1)
//                    mTiles[row + i][col + 1].setTileStatus(Tile.Status.NONE_X);
//
//                if (col > 0)
//                    mTiles[row + i][col - 1].setTileStatus(Tile.Status.NONE_X);

            }
//            if (row > 0)
//                mTiles[row - 1][col].setTileStatus(Tile.Status.NONE_X);
//            if (row + size - 1 < BOARD_SIZE - 1)
//                mTiles[row + size][col].setTileStatus(Tile.Status.NONE_X);

        }
        ship.setPointsOnBoard(shipPoints);
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


    /** Return the size of this board. */
    public int size() {
        return size;
    }

    /**Returns all of the board's places in a LinkedList*/
    private List<Place> getPlaces(){
        List<Place> boardPlaces = new LinkedList<Place>();
        for(int i = 0; i < size(); i++){
            for(int j = 0; j < size(); j++){
                boardPlaces.add(board[i][j]);
            }
        }
        return boardPlaces;
    }

    /**Returns all of the places that have a ship and have been hit*/
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

