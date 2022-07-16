package com.example.battleship;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.battleship.Logic.Board;

import pl.droidsonroids.gif.GifImageView;
// Place Ships Activity
public class GameScreenActivity extends AppCompatActivity {
    private GridLayout mainGrid;
    //RelativeLayout.LayoutParams lParams;
    private ImageView largeShip, bigShip, mediumShip, mediumShip2, smallShip;
    //private ChoiceTouchListener choiceTouchListener;
    ImageView imageView;

    boolean isOnGrid = false;

    private android.widget.FrameLayout.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_ships);
        findViewById(R.id.large_ship).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.big_ship).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.medium_ship_1).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.medium_ship_2).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.small_ship).setOnTouchListener(new MyTouchListener());

        //largeShip = (ImageView) findViewById(R.id.large_ship);
        //mainGrid = findViewById(R.id.gridLayout);
    }

    public void onClickContinueButton(View view) {
        startActivity(new Intent(this, GameActivity.class));
    }

//    private final class setOnTouch implements View.OnTouchListener {
//
//        @Override
//        public boolean onTouch(View view, MotionEvent motionEvent) {
//            if(isOnGrid) Rotate();
//            return false;
//        }
//    }
//
//    public void Rotate(ImageView){
//
//    }


    private final class MyTouchListener implements View.OnTouchListener {
        float x, y;
        float dx, dy;
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            imageView = ((ImageView) view);
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                x = motionEvent.getX();
                y = motionEvent.getY();
                return true;
            }
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                view.setVisibility(View.VISIBLE);
                return true;
            }
            if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                dx = motionEvent.getX() - x;
                dy = motionEvent.getY() - y;
                (imageView).setX((imageView).getX() + dx);
                (imageView).setY((imageView).getY() + dy);
                x = motionEvent.getX();
                y = motionEvent.getY();
                return true;
            }
            view.invalidate();
            return true;
        }
    }
}