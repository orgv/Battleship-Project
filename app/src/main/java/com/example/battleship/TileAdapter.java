package com.example.battleship;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.battleship.Logic.Board;
import com.example.battleship.Logic.Tile;

public class TileAdapter extends BaseAdapter {
    private Context mContext;
    private Board mBoard;
    private int mSize;
    private AnimationDrawable anim;

    private Integer[] mThumbIds = {
            R.drawable.small_ship, R.drawable.medium_ship, R.drawable.big_ship, R.drawable.large_ship
    };

    public TileAdapter(Context applicationContext, Board board, int size) {
        mContext = applicationContext;
        mBoard = board;
        this.mSize = size;
    }

    @Override
    public int getCount() {
        return mBoard.getTotalBoardSize();
    }

    @Override
    public Object getItem(int position) {
        int row = position / mBoard.getBoardSize();
        int col = position % mBoard.getBoardSize();
        return mBoard.getTile(row, col);
    }

    @Override
    public long getItemId(int position) {
        return position / mBoard.getBoardSize();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int row = position / mBoard.getBoardSize();
        int col = position % mBoard.getBoardSize();

        final ImageView imageView;
        anim = null;

        if (convertView == null || position ==0) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(mSize, mSize));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setPadding(0, 0, 0, 0);

        } else {
            imageView = (ImageView) convertView;
        }

        Tile.Status status = mBoard.getTile(row, col).getTileStatus();

        switch (status) {
            case NONE:
                imageView.setImageResource(mThumbIds[0]);
                break;

            case HIT:

                if(!mBoard.getTile(row,col).isFired())
                {
                    // TODO: fire animation..
                    imageView.setImageResource(R.drawable.medium_ship);
                    anim = (AnimationDrawable) imageView.getDrawable();
                    imageView.setBackgroundResource(mThumbIds[2]);
                    mBoard.getTile(row,col).setFired(true);
                }
                break;

            case MISS:

                if(!mBoard.getTile(row,col).isFired())
                {
                    // TODO: miss animation..
                    imageView.setImageResource(R.drawable.large_ship);
                    anim = (AnimationDrawable) imageView.getDrawable();
                    imageView.setBackgroundColor(Color.TRANSPARENT);
                    mBoard.getTile(row,col).setFired(true);
                }
                break;

            case SHIP:
                if (parent.getId() == R.id.player_grid)
                    imageView.setImageResource(mThumbIds[1]);
                if (parent.getId() == R.id.enemy_grid)
                    imageView.setImageResource(mThumbIds[0]);
                break;

            case SUNK:
                // TODO: sunk animation..

                if(!mBoard.getTile(row,col).isSunk()) {
                    imageView.setImageResource(R.drawable.small_ship);
                    anim = (AnimationDrawable) imageView.getDrawable();
                    imageView.setBackgroundResource(mThumbIds[3]);
                    mBoard.getTile(row,col).setmIsSunk(true);
                }
                break;

        }
        if (anim != null)
            anim.start();
        return imageView;
    }

}

