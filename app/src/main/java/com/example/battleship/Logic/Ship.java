package com.example.battleship.Logic;

import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;


public class Ship {
    private ShipType shipType;
    private int hitPoints;
    public String direction;
    private boolean isSunk;
    private Point pointsOnBoard[];
    private Point headPoint;
    private boolean isVisible = true;
    public boolean shipDirection = true;


    public enum ShipType {
        BATTLESHIP("Battleship", 5),
        CRUISER("Cruiser", 4),
        CARRIER1("Carrier1", 3),
        CARRIER2("Carrier2", 3),
        DESTROYER("Destroyer", 2);

        private int e_shipSize;
        private String e_shipName;

        private ShipType(String name, int shipSize) {
            this.e_shipName = name;
            this.e_shipSize = shipSize;
        }
    }


    public Ship(ShipType shipType) {
        this.shipType = shipType;
        this.hitPoints = shipType.e_shipSize;
        this.isSunk = false;
        this.headPoint = new Point();
        pointsOnBoard = new Point[shipType.e_shipSize];
    }

    public void hitShip() {
        this.hitPoints--;
        if (this.hitPoints == 0)
            isSunk = true;
    }
    public void setShipDirection(boolean newDir) {
        shipDirection = newDir;
    }

    public boolean getShipDirection(){
        return shipDirection;
    }

    public int getSize() {
        return shipType.e_shipSize;
    }

    public void setSize(int size) {
        shipType.e_shipSize = size;
    }

    public String getShipName() {
        return shipType.e_shipName;
    }

    public void setShipName(String shipName) {
        shipType.e_shipName = shipName;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public boolean isSunk() {
        return isSunk;
    }

    public void setSunk(boolean sunk) {
        isSunk = sunk;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public Point[] getPointsOnBoard() {
        return pointsOnBoard;
    }

    public void setPointsOnBoard(Point[] pointsOnBoard) {
        this.pointsOnBoard = pointsOnBoard;
    }

    public Point getHeadPoint() {
        return headPoint;
    }

    public void setHeadPoint(Point headPoint) {
        this.headPoint = headPoint;
    }

    public void setHeadPoint(int randomRow, int randomCol) {
    }
}
