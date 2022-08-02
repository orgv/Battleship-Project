package com.example.battleship;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.util.Log;

import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Player extends AbstractPlayer {

    public static int NUMBER_OF_TURNS = 0; // for readability
    public static String EMAIL = null; // for readability
    public static int WINS = 0; // for readability
    public static int LOSSES = 0; // for readability
    public static int SCORE = 0; // for readability
    public static int MIN_TURNS_TO_WIN = -1;
    public static String CURRENT_MATCH_RESULT;
    public static ArrayList<Pair<String, Integer>> matchesInfoList = new ArrayList<>(10); // String - win/loss, Integer - int -> number of turns made

    private CustomDialog endGameDialog;

    public Player(String ip, int port, GamePlayActivity context, Board myBoard, Board opponentBoard) {
        super(ip, port, context, myBoard, opponentBoard);
    }

    @Override
    void shoot() {
        displayMessage(activity.getString(R.string.your_turn_text), "");
        activity.incrementTurnNumber();
        activity.disableProgressBar();
        activity.enableOpponentBoard();
    }

    @Override
    void _wait() {
        displayMessage(activity.getString(R.string.opponent_turn_text), "");
        activity.enableProgressBar();
        activity.disableOpponentBoard();
    }

    @Override
    void playSoundEffect(int soundId) {
        myApp.playSoundEffect(soundId);
    }

    @Override
    void endGame(String result) {

        activity.disableProgressBar();
        if (result.equals(GameSystem.WIN)) {
            displayMessage(activity.getString(R.string.win_text), activity.getString(R.string.congrats_text));
            CURRENT_MATCH_RESULT = "VICTORY";
            for (Ship ship : opponentBoard.getShips()) {
                if (!ship.isSunk()) {
                    processShootResult(ship.getName());
                    break;
                }
            }
            myApp.playSoundEffect(MusicSoundHandlerApp.SOUND_ID_WIN);
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    endGameDialog = new CustomDialog(activity);
                    endGameDialog.setContentView(R.layout.win_dialog);
                    DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
                    int width = metrics.widthPixels;
                    int height = metrics.heightPixels;
                    endGameDialog.getWindow().setLayout((6 * width) / 7, (4 * height) / 5);
                    endGameDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    endGameDialog.show();
                    TextView turnsNumber = endGameDialog.findViewById(R.id.turnsNumberTextView);
                    turnsNumber.setText(activity.getString(R.string.turns_played_text) + NUMBER_OF_TURNS);

                    Button playAgainBtn = endGameDialog.findViewById(R.id.play_again_btn);
                    playAgainBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            endGameDialog.getActivity().startActivity(new Intent(endGameDialog.getActivity(), PlaceShipsActivity.class));
                        }
                    });

                    Button mainMenuBtn = endGameDialog.findViewById(R.id.back_to_main_menu_btn);
                    mainMenuBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            endGameDialog.getActivity().startActivity(new Intent(endGameDialog.getActivity(), MenuScreenActivity.class));
                        }
                    });
                }
            });
            updateUserData(true);

        } else if (result.equals(GameSystem.LOSE)) {
            displayMessage(activity.getString(R.string.loss_text), activity.getString(R.string.cheer_up_text));
            CURRENT_MATCH_RESULT = "DEFEAT";
            myApp.playSoundEffect(MusicSoundHandlerApp.SOUND_ID_LOSE);
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    endGameDialog = new CustomDialog(activity);
                    endGameDialog.setContentView(R.layout.loss_dialog);
                    DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
                    int width = metrics.widthPixels;
                    int height = metrics.heightPixels;
                    endGameDialog.getWindow().setLayout((6 * width) / 7, (4 * height) / 5);
                    endGameDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    endGameDialog.show();
                    TextView turnsNumber = endGameDialog.findViewById(R.id.turnsNumberTextView);
                    turnsNumber.setText(activity.getString(R.string.turns_played_text) + NUMBER_OF_TURNS);

                    Button playAgainBtn = endGameDialog.findViewById(R.id.play_again_btn);
                    playAgainBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            endGameDialog.getActivity().startActivity(new Intent(endGameDialog.getActivity(), PlaceShipsActivity.class));
                        }
                    });

                    Button mainMenuBtn = endGameDialog.findViewById(R.id.back_to_main_menu_btn);
                    mainMenuBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            endGameDialog.getActivity().startActivity(new Intent(endGameDialog.getActivity(), MenuScreenActivity.class));
                        }
                    });
                }
            });
            updateUserData(false);
        }
        myApp.pauseMusic();
        isPlaying = false;
        //activity.startActivity(new Intent(activity.getApplicationContext(), MatchHistoryActivity.class));

    }

    @Override
    void displayMessage(String message, String result) {
        if (!message.isEmpty()) {
            activity.displayMessage(message);
        }
        if (!result.isEmpty()) {
            activity.displayResult(result);
        }
    }

    @Override
    void checkShoot(String cellLocation) {
        super.checkShoot(cellLocation);
        String message = activity.getString(R.string.opponent_text);
        String[] location = cellLocation.split(",");
        int x = Integer.parseInt(location[0]);
        int y = Integer.parseInt(location[1]);
        Ship hitShip = myBoard.getCell(x, y).getShip();
        if (hitShip != null) { // result is HIT or KILL
            if (hitShip.isSunk()) {
                message += activity.getString(R.string.sunk_text);
            } else {
                message += activity.getString(R.string.hit_text);
            }
            message += activity.getString(R.string.your_text) + hitShip.getName();
        } else { // result is MISS
            message += activity.getString(R.string.missed_text);
        }
        displayMessage("", message);
    }

    @Override
    void processShootResult(String shipNameOrMISS) {
        if (lastShootCell == null) {
            return;
        }
        // if result is MISS, ship = null
        Ship ship = opponentBoard.getShip(shipNameOrMISS);
        lastShootCell.hit(ship);
        String result = activity.getString(R.string.you_text);
        if (ship == null) { // MISS
            result += activity.getString(R.string.missed_text);
        } else { // HIT or KILL
            if (ship.isSunk()) { // KILL
                result += activity.getString(R.string.sunk_text);
                result += (activity.getString(R.string.opponents_text) + shipNameOrMISS); // *
            } else { // HIT
                result += activity.getString(R.string.hit_text);
                result += (activity.getString(R.string.opponents_ship_text)); // *
            }
            //result += (" opponent's " + shipNameOrMISS);
        }
        activity.displayResult(result);
        lastShootCell = null;
    }

    private void updateUserData(boolean isWin) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userDocumentId = "[" + EMAIL + "]_UserData";
        DocumentReference userDocumentReference = db.collection("Users").document(userDocumentId);

        userDocumentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("Valid Document", "This document exists");
                    } else {
                        Log.d("Invalid Document", "No such document");
                    }
                } else {
                    Log.d("TaskException", "get failed with ", task.getException());
                }
            }
        });
        if (isWin) {
            addMatchToHistory("VICTORY", NUMBER_OF_TURNS);

            userDocumentReference.update("wins", FieldValue.increment(1));
            userDocumentReference.update("score", FieldValue.increment(50));

            // check if had been already changed
            if (MIN_TURNS_TO_WIN != -1) {
                if (MIN_TURNS_TO_WIN > NUMBER_OF_TURNS) {
                    userDocumentReference.update("min_turns_to_win", NUMBER_OF_TURNS);
                }
            }

            // then, it is still initialized from the beginning. then change it to NUMBER_OF_TURNS as the new minimum.
            else {
                userDocumentReference.update("min_turns_to_win", NUMBER_OF_TURNS);
            }
        }
        // lost the game
        else {
            addMatchToHistory("DEFEAT", NUMBER_OF_TURNS);
            userDocumentReference.update("losses", FieldValue.increment(1));
            userDocumentReference.update("score", FieldValue.increment(-50));

        }

        // - -  - - - - - -  - - - -  - - -  -  - - -  - - - - - -   -  - - -  - - -  -  - - -  - - - -
        // then update the data in the db.

        ArrayList<String> matchesResultList = new ArrayList<>(10);
        ArrayList<Integer> numberOfTurnsList = new ArrayList<>(10);


        for (int i = 0; i < matchesInfoList.size(); i++) {
            matchesResultList.add(matchesInfoList.get(i).first);
            numberOfTurnsList.add(matchesInfoList.get(i).second);
        }

        userDocumentReference.update("matchesResultHistory", matchesResultList);
        userDocumentReference.update("numberOfTurnsHistory", numberOfTurnsList);


        // - -  - - - - - -  - - - -  - - -  -  - - -  - - - - - -   -  - - -  - - -  -  - - -  - - - -

        // "mini" data-fetching process , except the match history part above! because the opposite is done! (local -> DB)
        userDocumentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("Valid Document", "This document exists");
                        WINS = Integer.parseInt(document.getData().get("wins").toString());
                        LOSSES = Integer.parseInt(document.getData().get("losses").toString());
                        SCORE = Integer.parseInt(document.getData().get("score").toString());
                        MIN_TURNS_TO_WIN = Integer.parseInt(document.getData().get("min_turns_to_win").toString());

                    } else {
                        Log.d("Invalid Document", "No such document");
                    }
                } else {
                    Log.d("TaskException", "get failed with ", task.getException());
                }
            }
        });


    }

    // static because we are not allowing more than one user connected at a time!
    public static void fetchDatafromDB() {

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            // TODO: replace with init method
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            String currentUserEmail = currentUser.getEmail();
            EMAIL = currentUserEmail; // document.getData().get("email"); separate from the others
            System.out.println("The email address is: " + currentUserEmail);
            String userDocumentId = "[" + currentUserEmail + "]_UserData";
            DocumentReference userDocumentReference = db.collection("Users").document(userDocumentId);
            userDocumentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d("Valid Document", "This document exists");

                            // update data by fetching it from db

                            WINS = Integer.parseInt(document.getData().get("wins").toString());
                            LOSSES = Integer.parseInt(document.getData().get("losses").toString());
                            SCORE = Integer.parseInt(document.getData().get("score").toString());
                            MIN_TURNS_TO_WIN = Integer.parseInt(document.getData().get("min_turns_to_win").toString());


                            ArrayList<String> matchesResultTempList = (ArrayList<String>) document.getData().get("matchesResultHistory");
                            ArrayList<Object> numberOfTurnsTempList = (ArrayList<Object>) document.getData().get("numberOfTurnsHistory");

                            int size = matchesResultTempList.size(); // should be of equal size

                            for (int i = 0; i < size; i++) {
                                addMatchToHistory(matchesResultTempList.get(i), Integer.parseInt(numberOfTurnsTempList.get(i).toString()));
                            }

                            System.out.println("This is the Email " + EMAIL);

                        } else {
                            Log.d("Invalid Document", "No such document");
                        }
                    } else {
                        Log.d("TaskException", "get failed with ", task.getException());
                    }
                }
            });
        }
    }

    public static void addMatchToHistory(String status, Integer numberOfTurns) {
        // that way we retain the size of the matches list to 10!
        if (matchesInfoList.size() < 10)
            matchesInfoList.add(0, new Pair<>(status, numberOfTurns)); // unshift
        else { // size >= 10
            matchesInfoList.add(0, new Pair<>(status, numberOfTurns)); // unshift
            matchesInfoList.remove(matchesInfoList.size() - 1); // then remove last element, like queue!
        }
    }
}
