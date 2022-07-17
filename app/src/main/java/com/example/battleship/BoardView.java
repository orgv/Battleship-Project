package com.example.battleship;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.battleship.Logic.Board;
import com.example.battleship.Logic.Place;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A special view class to display a battleship board as a 2D grid.
 *
 * @see Board
 */
public class BoardView extends View implements Serializable {

    int[][] coordinatesOfPlayer1Ships = new int[10][10];
    int[][] coordinatesOfPlayer2Ships = new int[10][10];
    int[][] gameCoordinates = new int[10][10];
    private Board board;
    private final List<BoardTouchListener> listeners = new ArrayList<>();
    private boolean displayShips = false;
    //private int boardSize = 10;
    boolean readyToDraw = false;

    private final int redColor = Color.rgb(255, 69, 0);
    private final int blackColor = Color.rgb(0, 0, 0);
    private final int whiteColor = Color.rgb(255, 255, 255);
    private final Paint redPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint blackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint whitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final int boardColor = Color.rgb(102, 163, 255);
    private final Paint boardPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final int boardLineColor = Color.WHITE;
    private final Paint boardLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    {
        boardPaint.setColor(boardColor);
        boardLinePaint.setColor(boardLineColor);
        boardLinePaint.setStrokeWidth(2);
        boardPaint.setColor(boardColor);
        redPaint.setColor(redColor);
        blackPaint.setColor(blackColor);
        whitePaint.setColor(whiteColor);

        int boardLineColor = Color.WHITE;
        boardLinePaint.setColor(boardLineColor);
        boardLinePaint.setStrokeWidth(3);
    }


    public BoardView(Context context) {
        super(context);
    }


    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * Set the board to to be displayed by this view.
     */
    public void setBoard(Board board) {
        this.board = board;
    }


    public Board getBoard() {
        return board;
    }

    /**
     * Overridden here to detect a board touch. When the board is
     * touched, the corresponding place is identified,
     * and registered listeners are notified.
     *
     * @see BoardTouchListener
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                int xy = locatePlace(event.getX(), event.getY());
                invalidate();
                if (xy >= 0) {
                    notifyBoardTouch(xy / 100, xy % 100);
                }
                break;
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    public void setReadyToDraw(boolean readyToDraw) {
        this.readyToDraw = readyToDraw;
    }

    /**
     * Overridden here to draw a 2-D representation of the board.
     */
    /**
     * Overridden here to draw a 2-D representation of the board.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawGrid(canvas);
//        drawShotPlaces(canvas);
//        if (displayShips) {
//            drawShips(canvas);
//        }
//        drawShipHitPlaces(canvas);

    }

    /**
     * Draw horizontal and vertical lines.
     */
    private void drawGrid(Canvas canvas) {
        final float maxCoord = maxCoord();
        final float placeSize = lineGap();
        boardPaint.setColor(Color.TRANSPARENT);
        canvas.drawRect(0, 0, maxCoord, maxCoord, boardPaint);
        for (int i = 0; i < numOfLines(); i++) {
            float xy = i * placeSize;
            canvas.drawLine(0, xy, maxCoord, xy, boardLinePaint); // horizontal line
            canvas.drawLine(xy, 0, xy, maxCoord, boardLinePaint); // vertical line
        }
    }

    /**
     * Draw all the places of the board.
     */
    private void drawShotPlaces(Canvas canvas) {
        // check the state of each place of the board and draw it.
        if (board == null) {
            return;
        }
        for (int x = 0; x < Board.BOARD_SIZE; x++) {
            for (int y = 0; y < Board.BOARD_SIZE; y++) {
                if (board.placeAt(x, y).isHit()) {
                    drawSquare(canvas, Color.RED, x, y);
                }
            }
        }
    }

    /**
     * Draws ships that are on the board if displayShips variable is true
     */
    private void drawShips(Canvas canvas) {

        for (int x = 0; x < Board.BOARD_SIZE; x++) {
            for (int y = 0; y < Board.BOARD_SIZE; y++) {
                if (board.placeAt(x, y).hasShip()) {
                    drawSquare(canvas, Color.argb(215, 255, 255, 255), x, y);
                }
            }
        }
    }


    /**
     * Draws a square over all the places that been shot
     */
    public void drawShipHitPlaces(Canvas canvas) {
        if (board == null) {
            return;
        }
        List<Place> shipHitPlaces = board.getShipHitPlaces();
        for (Place places : shipHitPlaces) {
            drawSquare(canvas, Color.GREEN, places.getX(), places.getY());
        }

    }

    public void displayBoardsShips(boolean display) {
        displayShips = display;
    }


    /**
     * Draws a square in the given coordinates
     *
     * @param color you want the square to be
     * @param x     coordinate of board you want square to be in
     * @param y     coordinate of board you want square to be in
     */
    public void drawSquare(Canvas canvas, int color, int x, int y) {
        boardPaint.setColor(color);
        int length = 98;
        float viewSize = maxCoord();
        float tileSize = viewSize / 10;  //10 Is how many tiles there are
        float offSet = 8;
        canvas.drawRect((tileSize * x) + offSet, (tileSize * y) + offSet, ((tileSize * x) + tileSize) - offSet, (((viewSize / 10) * y) + tileSize) - offSet, boardPaint);
    }


    /**
     * Calculate the gap between two horizontal/vertical lines.
     */
    private float lineGap() {
        return Math.min(getMeasuredWidth(), getMeasuredHeight()) / (float) Board.BOARD_SIZE;
    }

    /**
     * Calculate the number of horizontal/vertical lines.
     */
    private int numOfLines() {
        return Board.BOARD_SIZE + 1;
    }

    /**
     * Calculate the maximum screen coordinate.
     */
    private float maxCoord() {
        return lineGap() * (numOfLines() - 1);
    }

    /**
     * Given screen coordinates, locate the corresponding place in the board
     * and return its coordinates; return -1 if the screen coordinates
     * don't correspond to any place in the board.
     * The returned coordinates are encoded as <code>x*100 + y</code>.
     */
    public int locatePlace(float x, float y) {
        if (x <= maxCoord() && y <= maxCoord()) {
            final float placeSize = lineGap();
            int ix = (int) (x / placeSize);
            int iy = (int) (y / placeSize);
            Log.w("(ix, iy)", String.valueOf(ix) + "," + String.valueOf(iy));
            return ix * 100 + iy;
        }
        return -1;
    }

    int locateX(float x) {
        if (x <= maxCoord()) {
            final float placeSize = lineGap();
            return (int) (x / placeSize);
        }
        return -1;
    }

    int locateY(float y) {
        return locateX(y);
    }

    /**
     * Register the given listener.
     */
    void addBoardTouchListener(BoardTouchListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**
     * Unregister the given listener.
     */
    public void removeBoardTouchListener(BoardTouchListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notify all registered listeners.
     */
    private void notifyBoardTouch(int x, int y) {
        for (BoardTouchListener listener : listeners) {
            listener.onTouch(x, y);
        }
    }

    /**
     * Callback interface to listen for board touches.
     */
    public interface BoardTouchListener {

        /**
         * Called when a place of the board is touched.
         * The coordinate of the touched place is provided.
         *
         * @param x 0-based column index of the touched place
         * @param y 0-based row index of the touched place
         */
        void onTouch(int x, int y);

    }


}
