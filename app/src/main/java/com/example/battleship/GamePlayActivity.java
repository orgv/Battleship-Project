package com.example.battleship;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.Dialog;
import android.app.GameManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class GamePlayActivity extends AppCompatActivity {
    // a view to display player's board
    private BoardView myBoardView;
    // a view to display opponent's board
    private BoardView opponentBoardView;
    // a view to display some text messages
    private TextView tvStatus;
    private TextView tvResult;
    private ProgressBar progressBar;
    private TextView turnNumber;
    // the game
    private GameSystem game;
    // custom Application class for sound and effect
    private MusicSoundHandlerApp myApp;
    // option dialog
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        Player.NUMBER_OF_TURNS = 0; // init again, in case of improbable user behavior
        myApp = (MusicSoundHandlerApp) getApplication();
        tvStatus = findViewById(R.id.tv_status);
        tvResult = findViewById(R.id.tv_result);

        //tvStatus.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        //tvResult.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        progressBar = findViewById(R.id.progresss_bar);
        turnNumber = findViewById(R.id.turn_number);
        //turnNumber.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);


        createDialog();
        Button btOption = findViewById(R.id.bt_option);
        btOption.setOnClickListener(v -> {
            dialog.show();
        });
        int mode = getIntent().getIntExtra("mode", GameSystem.MODE_VS_PLAYER);
        Board myBoard = (Board) getIntent().getSerializableExtra("board");
        setUp(mode, myBoard);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myApp.saveSettings();
        game.end();
    }

    @Override
    protected void onPause() {
        super.onPause();
        myApp.pauseMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myApp.resumeMusic();
    }

    private void setUp(int mode, Board myBoard) {
        // play background music
        myApp.playMusic(MusicSoundHandlerApp.MUSIC_ID_PLAY);
        // a view to display player's board
        myBoardView = findViewById(R.id.my_board_view);
        opponentBoardView = findViewById(R.id.opponent_board_view);

        myBoardView.setBoard(myBoard);
        myBoardView.setReadyToDraw(true);
        Board opponentBoard = new Board();
        for (Ship ship : opponentBoard.getShips()) {
            ship.setVisible(false);
        }
        opponentBoardView.setBoard(opponentBoard);
        opponentBoardView.setReadyToDraw(true);
        disableOpponentBoard();

        game = new GameSystem(mode, GamePlayActivity.this, myBoard, opponentBoard);
    }

    void enableOpponentBoard() {
        opponentBoardView.post(() -> opponentBoardView.setOnTouchListener(new BoardTouchListener()));
    }

    void disableOpponentBoard() {
        opponentBoardView.post(() -> opponentBoardView.setOnTouchListener(null));
    }

    void enableProgressBar() {
        //progressBar.setVisibility(View.VISIBLE);
        progressBar.post(() -> progressBar.setVisibility(View.VISIBLE));
    }

    void disableProgressBar() {
        //progressBar.setVisibility(View.GONE);
        progressBar.post(() -> progressBar.setVisibility(View.GONE));
    }

    void incrementTurnNumber() {
        turnNumber.post(() -> turnNumber.setText(getString(R.string.turn_text) + ++Player.NUMBER_OF_TURNS));
    }


    void displayMessage(String message) {
        tvStatus.post(() -> tvStatus.setText(message));
    }

    void displayResult(String result) {
        tvResult.post(() -> tvResult.setText(result));
    }

    void reDrawBoardViews() {
        myBoardView.invalidate();
        opponentBoardView.invalidate();
    }

    private class BoardTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Cell cell = opponentBoardView.locateCell(event.getX(), event.getY());
                    if (!cell.isHit()) {
                        myApp.playSoundEffect(MusicSoundHandlerApp.SOUND_ID_FIRE);
                        game.me.lastShootCell = cell;
                        String cellLocation = cell.getX() + "," + cell.getY();
                        game.me.sendMessage(cellLocation);
                        disableOpponentBoard();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    v.performClick();
                    break;
                case MotionEvent.ACTION_MOVE:
                case MotionEvent.ACTION_CANCEL:
                    break;
            }
            return true;
        }
    }

    public void createDialog() {
        //Create a dialog for pausing and ending game
        dialog = new Dialog(GamePlayActivity.this);
        dialog.setContentView(R.layout.option_dialog);
        dialog.setTitle("Options");

        //Buttons
        // Continue button, just close the dialog
        Button btContinue = dialog.findViewById(R.id.bt_continue);
        btContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        // Main menu button
        Button btQuit = dialog.findViewById(R.id.bt_quit);
        btQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GamePlayActivity.this, MenuScreenActivity.class);
                startActivity(intent);
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
                GamePlayActivity.this.finish();
            }
        });

        SwitchCompat swMusic = dialog.findViewById(R.id.dialog_sw_music);
        SwitchCompat swSound = dialog.findViewById(R.id.dialog_sw_sound);
        swMusic.setChecked(myApp.isMusicOn());
        swSound.setChecked(myApp.isSoundOn());

        swMusic.setOnCheckedChangeListener((buttonView, isChecked) -> {
            myApp.setMusicOn(isChecked);
            if (isChecked) {
                myApp.playMusic(MusicSoundHandlerApp.MUSIC_ID_PLAY);
            } else {
                myApp.pauseMusic();
            }
        });

        swSound.setOnCheckedChangeListener((buttonView, isChecked) -> {
            myApp.setSoundOn(isChecked);
        });
//            The dialog can only be canceled by clicking Continue Button
//        dialog.setCanceledOnTouchOutside(false);
    }


    // do not press the back button to go back while playing!
    @Override
    public void onBackPressed() {
    }

}