package com.example.battleship;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.battleship.Logic.Board;
import com.example.battleship.Logic.Ship;

import java.util.LinkedList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

// Place Ships Activity
public class GameScreenActivity extends AppCompatActivity {
    private GridLayout mainGrid;
    //RelativeLayout.LayoutParams lParams;
    private ImageView largeShip, bigShip, mediumShip, mediumShip2, smallShip;
    //private ChoiceTouchListener choiceTouchListener;
    ImageView imageView;
    private List<ShipView> fleetView = new LinkedList<>();
    boolean isOnGrid = false;
    private ShipView shipBeingDragged = null;
    private BoardView boardView;
    private Button continueBtn;

    private Board playerBoard;

    private android.widget.FrameLayout.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_ships);

        continueBtn = findViewById(R.id.button);
        //continueBtn.setEnabled(false);
        continueBtn.setEnabled(true);

        //boardView = (BoardView) findViewById(R.id.placeShipsBoardView);
        //playerBoard = new Board();
        //boardView.setBoard(playerBoard);
        //boardView.displayBoardsShips(true);


        largeShip = (ImageView) findViewById(R.id.large_ship);
        bigShip = (ImageView) findViewById(R.id.big_ship);
        mediumShip = (ImageView) findViewById(R.id.medium_ship_1);
        mediumShip2 = (ImageView) findViewById(R.id.medium_ship_2);
        smallShip = (ImageView) findViewById(R.id.small_ship);

        fleetView.add(new ShipView(largeShip, new Ship(Ship.ShipType.BATTLESHIP)));
        fleetView.add(new ShipView(bigShip, new Ship(Ship.ShipType.CRUISER)));
        fleetView.add(new ShipView(mediumShip, new Ship(Ship.ShipType.CARRIER1)));
        fleetView.add(new ShipView(mediumShip2, new Ship(Ship.ShipType.CARRIER2)));
        fleetView.add(new ShipView(smallShip, new Ship(Ship.ShipType.DESTROYER)));


        for (ShipView shipView : fleetView) {
            setShipImage(shipView);
        }

        //setBoardDragListener(boardView, playerBoard);


    }

    public void setBoardDragListener(final BoardView boardView, final Board board) {
        boardView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        break;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        break;

                    case DragEvent.ACTION_DRAG_LOCATION:
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        break;

                    case DragEvent.ACTION_DROP:
                        float x = event.getX();
                        float y = event.getY();
                        float widthShip, heightShip;

                        if (!shipBeingDragged.getShip().getShipDirection()) {
                            widthShip = shipBeingDragged.getShipImage().getHeight();
                            heightShip = shipBeingDragged.getShipImage().getWidth();

                        } else {
                            widthShip = shipBeingDragged.getShipImage().getWidth();
                            heightShip = shipBeingDragged.getShipImage().getHeight();
                        }

                        //x and y coordinates of top-left of image, relative to the board
                        float boardX = x - (widthShip / 2);
                        float boardY = y - (heightShip / 2);

                        int xy = boardView.locatePlace(boardX, boardY);
                        if (xy == -1) {
                            return true;
                        }
                        int xGrid = xy / 100;
                        int yGrid = xy % 100;

                        boardView.invalidate();
                        if (board.isFleetEmpty()) {
                            continueBtn.setEnabled(true);
                        }
                        break;

                    default:
                        break;
                }
                return true;
            }
        });
    }


//        for (ShipView shipView : fleetView) {
//            shipView.getShipImage().setOnTouchListener(new MyTouchListener());
//        }


    public void onClickContinueButton(View view) {
        startActivity(new Intent(this, GameActivity.class));
    }

    private void setShipImage(final ShipView shipView) {
        setImageScaling(shipView.getShipImage());
        setTouchListener(shipView);
    }

    private void setImageScaling(final ImageView image) {

        image.setAdjustViewBounds(true);

        ViewTreeObserver vto = image.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {

                //image.setMaxHeight(boardView.getMeasuredHeight() / 10);
            }

        });
    }


    @SuppressLint("ClickableViewAccessibility")
    private void setTouchListener(final ShipView shipView) {
        final ImageView image = shipView.getShipImage();
        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    ClipData data = ClipData.newPlainText("", "");


                    double rotationRad = Math.toRadians(image.getRotation());
                    final int w = (int) (image.getWidth() * image.getScaleX());
                    final int h = (int) (image.getHeight() * image.getScaleY());
                    double s = Math.abs(Math.sin(rotationRad));
                    double c = Math.abs(Math.cos(rotationRad));
                    final int width = (int) (w * c + h * s);
                    final int height = (int) (w * s + h * c);
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(image) {
                        @Override
                        public void onDrawShadow(Canvas canvas) {
                            canvas.scale(image.getScaleX(), image.getScaleY(), width / 2,
                                    height / 2);
                            canvas.rotate(image.getRotation(), width / 2, height / 2);
                            canvas.translate((width - image.getWidth()) / 2,
                                    (height - image.getHeight()) / 2);
                            super.onDrawShadow(canvas);
                        }

                        @Override
                        public void onProvideShadowMetrics(Point shadowSize,
                                                           Point shadowTouchPoint) {
                            shadowSize.set(width, height);
                            shadowTouchPoint.set(shadowSize.x / 2, shadowSize.y / 2);
                        }
                    };
                    image.startDragAndDrop(data, shadowBuilder, image, 0);
                    //image.setVisibility(View.INVISIBLE);
                    shipBeingDragged = shipView;
                    //deselectAllShipViews();
                    //select(shipView);

                    return true;
                } else {
                    return false;
                }
            }

        });
    }

}


