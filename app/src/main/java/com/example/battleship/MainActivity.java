package com.example.battleship;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onStartGameBtn(View view) {
        //Intent gameScreenIntent = new Intent(this, GameScreenActivity.class);
        Intent gameScreenIntent = new Intent(this, GameScreenActivity.class);
        startActivity(gameScreenIntent);

    }
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this,SignInUpActivity.class));
        finish();
    }
}