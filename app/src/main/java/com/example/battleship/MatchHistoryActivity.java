package com.example.battleship;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Pair;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MatchHistoryActivity extends AppCompatActivity {

    private ListView matchesListView;
    private static ArrayList<RowItem> myRowItems;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_history);

        myRowItems = new ArrayList<>();

        matchesListView = (ListView) findViewById(R.id.match_history_list);


        CustomAdapter adapter = new CustomAdapter(getApplicationContext(), myRowItems);

        matchesListView.setAdapter(adapter);

        for (Pair<String, Integer> match : Player.matchesInfoList) {
            addRowToList("VS AI",match.first, "CPU", Player.EMAIL);
        }


        adapter.notifyDataSetChanged();

        // TODO: add an array to store the last 10 games with the according results. save history for each user.

    }

    public static void addRowToList(String matchModeParam, String matchResultParam, String opponentNameParam, String myNameParam) {
        RowItem row = new RowItem();
        row.setMatchMode(matchModeParam);
        row.setMatchResult(matchResultParam);
        row.setOpponentName(opponentNameParam);
        row.setPlayerName(myNameParam);
        myRowItems.add(row);
    }

}