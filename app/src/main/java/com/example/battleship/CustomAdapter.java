package com.example.battleship;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */

    private ArrayList<RowItem> singleRow;
    private LayoutInflater thisInflater;


    public CustomAdapter(Context context, ArrayList<RowItem> aRow) {

        this.singleRow = aRow;
        thisInflater = (LayoutInflater.from(context));

    }

    @Override
    public int getCount() {
        return singleRow.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    public Object getItem(int position) {
        return singleRow.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = thisInflater.inflate(R.layout.list_view_row, parent, false);
            TextView matchMode = (TextView) convertView.findViewById(R.id.tv_vs_game_mode);
            TextView matchResult = (TextView) convertView.findViewById(R.id.tv_game_result);
            TextView playerName = (TextView) convertView.findViewById(R.id.tv_player_name);
            TextView opponentName = (TextView) convertView.findViewById(R.id.tv_adversary_name);


            RowItem currentRow = (RowItem) getItem(position);

            matchMode.setText(currentRow.getMatchMode());
            matchResult.setText(currentRow.getMatchResult());
            playerName.setText(currentRow.getPlayerName());
            opponentName.setText(currentRow.getOpponentName());
        }
        return convertView;
    }
}
