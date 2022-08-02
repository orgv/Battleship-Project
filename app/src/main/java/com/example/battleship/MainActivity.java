package com.example.battleship;

import static java.lang.Thread.sleep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private CustomDialog dmDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dmDialog = new CustomDialog(this);

        findViewById(R.id.app_name_text_view).setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        findViewById(R.id.start_game_btn).setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        findViewById(R.id.log_out_btn).setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        findViewById(R.id.welcome_text).setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        findViewById(R.id.match_history_btn).setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        findViewById(R.id.settingsImageBtn).setLayoutDirection(View.LAYOUT_DIRECTION_LTR);


        Player.fetchDatafromDB(); // after login/create account phase, update the data.

        System.out.println(Player.EMAIL);
        TextView welcomePlayer = findViewById(R.id.welcome_text);

        welcomePlayer.setText("Welcome Back, " + Player.EMAIL.split("@")[0]);


        // Storing data into SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        // Creating an Editor object to edit(write to the file)
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        // Storing the key and its value as the data fetched from edittext
        myEdit.putString("email", Player.EMAIL);
        myEdit.apply();

        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_MULTI_PROCESS);
        String extractedEmail = sh.getString("email", "");

        if (extractedEmail != Player.EMAIL) {
            Log.e("IEmail", "Invalid Email");
        }
    }

    public void onStartGameBtn(View view) {
        Intent menuScreenIntent = new Intent(this, MenuScreenActivity.class);
        menuScreenIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(menuScreenIntent);
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, SignInUpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void showMatchHistory(View view) {
        Intent intent = new Intent(this, MatchHistoryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onClickSettingsBtn(View view) {

        dmDialog.setContentView(R.layout.settings_dialog);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dmDialog.getWindow().setLayout((6 * width) / 7, (4 * height) / 5);
        dmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dmDialog.show();
    }


    // might be deleted..
    public static void setLocale(@NonNull Activity activity, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        config.setLocale(locale);
    }

    public void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration config = res.getConfiguration();
        config.setLocale(locale);
        res.updateConfiguration(config, dm);

        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);

    }


    public void onClickUsBtn(View view) {
        setLocale("us");
        dmDialog.dismiss();
    }

    public void onClickIsraelBtn(View view) {
        setLocale("he");
        dmDialog.dismiss();
    }


    // do not press the back button in the main activity!
    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Log out to go back!", Toast.LENGTH_SHORT).show();
    }
}