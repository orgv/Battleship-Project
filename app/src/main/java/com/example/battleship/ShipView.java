package com.example.battleship;

import android.widget.ImageView;

import com.example.battleship.Logic.Ship;

public class ShipView {

    private ImageView shipImage;
    private Ship ship;

    ShipView(ImageView image, Ship newShip){
        shipImage = image;
        ship = newShip;
    }
    public Ship getShip(){
        return ship;
    }
    ImageView getShipImage(){
        return shipImage;
    }
}
