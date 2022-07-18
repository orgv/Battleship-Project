package com.example.battleship;

import static android.widget.RelativeLayout.CENTER_IN_PARENT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.example.battleship.Logic.Board;
import com.example.battleship.Logic.Ship;
import com.example.battleship.databinding.ActivityPlaceShipsBinding;

import java.util.LinkedList;
import java.util.List;

// Place Ships Activity
public class GameScreenActivity extends AppCompatActivity {
    private GridLayout mainGrid;
    ConstraintLayout.LayoutParams lParams;
    private ImageView largeShip, bigShip, mediumShip, mediumShip2, smallShip;
    //private ChoiceTouchListener choiceTouchListener;
    ImageView imageView;
    private List<ShipView> fleetView = new LinkedList<>();
    boolean isOnGrid = false;
    private ShipView shipBeingDragged = null;
    private BoardView boardView;
    private Button continueBtn;

    private Board playerBoard;


    private ViewGroup rootLayout;


    private View.OnDragListener boardDragListener;

    private android.widget.FrameLayout.LayoutParams layoutParams;
    private ActivityPlaceShipsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_place_ships);


        //rootLayout = (ViewGroup) findViewById(R.id.view_root);

        //largeShip = (ImageView) rootLayout.findViewById(R.id.large_ship);

        continueBtn = findViewById(R.id.button);
        //continueBtn.setEnabled(false);
        continueBtn.setEnabled(true);

        boardView = (BoardView) findViewById(R.id.place_ships_grid);
        playerBoard = new Board("My Fleet");
        boardView.setBoard(playerBoard);
        boardView.displayBoardsShips(true);


        largeShip = (ImageView) findViewById(R.id.large_ship);
        bigShip = (ImageView) findViewById(R.id.big_ship);
        mediumShip = (ImageView) findViewById(R.id.medium_ship_1);
        mediumShip2 = (ImageView) findViewById(R.id.medium_ship_2);
        smallShip = (ImageView) findViewById(R.id.small_ship);


        largeShip.setOnLongClickListener(new MyOnLongClickListener());
        bigShip.setOnLongClickListener(new MyOnLongClickListener());
        mediumShip.setOnLongClickListener(new MyOnLongClickListener());
        mediumShip2.setOnLongClickListener(new MyOnLongClickListener());
        smallShip.setOnLongClickListener(new MyOnLongClickListener());

        boardView.setOnDragListener(new MyOnDragListener(1));


        fleetView.add(new ShipView(largeShip, new Ship(Ship.ShipType.BATTLESHIP)));
        fleetView.add(new ShipView(bigShip, new Ship(Ship.ShipType.CRUISER)));
        fleetView.add(new ShipView(mediumShip, new Ship(Ship.ShipType.CARRIER1)));
        fleetView.add(new ShipView(mediumShip2, new Ship(Ship.ShipType.CARRIER2)));
        fleetView.add(new ShipView(smallShip, new Ship(Ship.ShipType.DESTROYER)));


        //largeShip.setOnTouchListener();
        //setTouchListener(fleetView.get(0));
        largeShip.setOnTouchListener(new MyTouchListener());


        for (ShipView shipView : fleetView) {
            setShipImage(shipView);
        }

        //setBoardDragListener(boardView, playerBoard);


        //boardDragListener = View.OnDragListener(boardView, DragEvent dragEvent)

//        View.OnDragListener boardDragListener = new View.OnDragListener() {
//            @Override
//            public boolean onDrag(View v, DragEvent event) {
//                switch (event.getAction()) {
//                    case DragEvent.ACTION_DRAG_STARTED:
//                        break;
//
//                    case DragEvent.ACTION_DRAG_ENTERED:
//                        break;
//
//                    case DragEvent.ACTION_DRAG_EXITED:
//                        break;
//
//                    case DragEvent.ACTION_DRAG_LOCATION:
//                        break;
//
//                    case DragEvent.ACTION_DRAG_ENDED:
//                        break;
//
//                    case DragEvent.ACTION_DROP:
//                        float x = event.getX();
//                        float y = event.getY();
//                        float widthShip, heightShip;
//
//                        if (!shipBeingDragged.getShip().getShipDirection()) {
//                            widthShip = shipBeingDragged.getShipImage().getHeight();
//                            heightShip = shipBeingDragged.getShipImage().getWidth();
//
//                        } else {
//                            widthShip = shipBeingDragged.getShipImage().getWidth();
//                            heightShip = shipBeingDragged.getShipImage().getHeight();
//                        }
//
//                        //x and y coordinates of top-left of image, relative to the board
//                        float boardX = x - (widthShip / 2);
//                        float boardY = y - (heightShip / 2);
//
//                        int xy = boardView.locatePlace(boardX, boardY);
//                        if (xy == -1) {
//                            return true;
//                        }
//                        int xGrid = xy / 100;
//                        int yGrid = xy % 100;
//
////                        boardView.invalidate();
////                        if (board.isFleetEmpty()) {
////                            continueBtn.setEnabled(true);
////                        }
//                        break;
//
//                    default:
//                        break;
//                }
//                return true;
//            }
//        };

        //boardView.setOnDragListener(boardDragListener);
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
                        v.setVisibility(View.VISIBLE);
                        v.invalidate();
                        break;

                    case DragEvent.ACTION_DROP:
//                        float x = event.getX();
//                        float y = event.getY();
//                        float widthShip, heightShip;
//
//                        if (!shipBeingDragged.getShip().getShipDirection()) {
//                            widthShip = shipBeingDragged.getShipImage().getHeight();
//                            heightShip = shipBeingDragged.getShipImage().getWidth();
//
//                        } else {
//                            widthShip = shipBeingDragged.getShipImage().getWidth();
//                            heightShip = shipBeingDragged.getShipImage().getHeight();
//                        }
//
//                        //x and y coordinates of top-left of image, relative to the board
//                        float boardX = x - (widthShip / 2);
//                        float boardY = y - (heightShip / 2);
//
//                        int xy = boardView.locatePlace(boardX, boardY);
//                        if (xy == -1) {
//                            return true;
//                        }
//                        int xGrid = xy / 100;
//                        int yGrid = xy % 100;
//
//                        boardView.invalidate();
//                        if (board.isFleetEmpty()) {
//                            continueBtn.setEnabled(true);
//                        }


                        v.setX(event.getX() - v.getWidth() / 2);
                        v.setY(event.getY() - v.getHeight() / 2);


//                        View newView = (View) event.getLocalState();
//                        ViewGroup owner = (ViewGroup) newView.getParent();
//                        owner.removeView(newView);
//                        FrameLayout container = (FrameLayout) v;
//                        container.addView(newView);
//                        newView.setVisibility(View.VISIBLE);
//                        newView.setX(event.getX());
//                        newView.setY(event.getY());

//                        View view = (View) event.getLocalState();
//                        ViewGroup owner = (ViewGroup) view.getParent();
//                        owner.removeView(view);
//                        LinearLayout container = (LinearLayout) v;
//                        container.addView(view);
//                        view.setVisibility(View.VISIBLE);


//                        v.getBackground().clearColorFilter();
//                        v.invalidate();

//                        View vw = (View) event.getLocalState();
//                        ViewGroup from = (ViewGroup) vw.getParent();
//                        from.removeView(vw);
//                        ViewGroup owner = (ViewGroup) v.getParent();
//                        owner.removeView(v);
//                        LinearLayout container = (LinearLayout) v;
//                        container.addView(v);
//                        v.setVisibility(View.VISIBLE);
//                        v.invalidate();


                        //layout parent = v.getParent();

//                        //parent.
//                        binding = ActivityPlaceShipsBinding.inflate(getLayoutInflater());
//                        View view = binding.getRoot();
//                        setContentView(view);
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

    // This defines your touch listener
    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);
                view.startDragAndDrop(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }


    private class MyOnLongClickListener implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View view) {
            ClipData data = ClipData.newPlainText("simle_text", "text");

            View.DragShadowBuilder sb = new View.DragShadowBuilder(view);

            view.startDragAndDrop(data, sb, view, 0);
            view.setVisibility(View.INVISIBLE);

            return true;
        }


    }


    private class MyOnDragListener implements View.OnDragListener {

        private int num;

        public MyOnDragListener(int num) {
            super();
            this.num = num;
        }

        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {

            int action = dragEvent.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    if (dragEvent.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        return true;
                    }
                    return false;
                case DragEvent.ACTION_DRAG_ENTERED:
                    // view.setBackgroundColor(Color.YELLOW);
                    break;
                case DragEvent.ACTION_DRAG_LOCATION:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //view.setBackgroundColor(Color.BLUE);
                    break;
                case DragEvent.ACTION_DROP:

                    View myView = (View) dragEvent.getLocalState();
                    ViewGroup owner = (ViewGroup) myView.getParent();
                    owner.removeView(myView);

                    BoardView container = (BoardView) view;

                    ConstraintLayout parentContainer = (ConstraintLayout) container.getParent();


                    view.setBackgroundColor(Color.BLUE);



                    myView.setX(dragEvent.getX());
                    myView.setY(dragEvent.getY());


//                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) view.getLayoutParams();
//                    params.leftMargin = (int) view.getLeft();
//                    params.rightMargin = (int) view.getRight();
                    //mLayout.addView(button, params);


                    parentContainer.addView(myView);



                    //container.addView(view);
                    myView.setVisibility(View.VISIBLE);


                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //view.setBackgroundColor(Color.BLUE);
                    break;

            }
            return true;
        }
    }


}


