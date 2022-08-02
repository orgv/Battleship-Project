package com.example.battleship;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuScreenActivity extends AppCompatActivity {
    MusicSoundHandlerApp myApp;

    public static boolean justFinishedGame = false; // indicates if came to this activity via the main menu button, at the end of the game

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);

        myApp = (MusicSoundHandlerApp) getApplication();

        if (Player.MIN_TURNS_TO_WIN != -1) {
            TextView highscoreTextView = findViewById(R.id.highscore_text_view);
            highscoreTextView.setText("Highscore: " + Player.MIN_TURNS_TO_WIN + " turns to win");
        }

        SwitchCompat swMusic = findViewById(R.id.sw_music);
        SwitchCompat swSound = findViewById(R.id.sw_sound);
        findViewById(R.id.highscore_text_view).setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        swMusic.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        swSound.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        Button btnPlayVsPlayer = findViewById(R.id.btn_play_vs_player);
        Button btnPlayVsComputer = findViewById(R.id.btn_play_vs_computer);

        swMusic.setOnCheckedChangeListener((buttonView, isChecked) -> {
            myApp.setMusicOn(isChecked);
            if (isChecked) {
                myApp.playMusic(MusicSoundHandlerApp.MUSIC_ID_START);
            } else {
                myApp.pauseMusic();
            }
        });
        swSound.setOnCheckedChangeListener((buttonView, isChecked) -> {
            myApp.setSoundOn(isChecked);
        });
        swMusic.setChecked(myApp.isMusicOn());
        swSound.setChecked(myApp.isSoundOn());

        btnPlayVsPlayer.setOnClickListener(new PlayListener(GameSystem.MODE_VS_PLAYER));
        btnPlayVsComputer.setOnClickListener(new PlayListener(GameSystem.MODE_VS_COMPUTER));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myApp.saveSettings();
        myApp.releaseMediaPlayer();
        myApp.releaseSoundPool();
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

    class PlayListener implements View.OnClickListener {
        int mode;

        PlayListener(int mode) {
            this.mode = mode;
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MenuScreenActivity.this, PlaceShipsActivity.class);
            intent.putExtra("mode", mode);
            startActivity(intent);
        }
    }

    // do not press the back button if came from a game!

    @Override
    public void onBackPressed() {
        if (!justFinishedGame) {
            super.onBackPressed();
        } else {
            // do nothing
        }
    }
}