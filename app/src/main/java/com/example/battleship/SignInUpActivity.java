package com.example.battleship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;

import java.util.Locale;

public class SignInUpActivity extends AppCompatActivity {
    private CustomDialog dmDialog;
    private ImageButton settingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_up);
        dmDialog = new CustomDialog(this);
        findViewById(R.id.settingsImageBtn).setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
    }

    public void onClickLoginMainActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
    }

    public void onClickSignUpMainActivity(View view) {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
    }

    public void onClickSettingsBtn(View view) {

        dmDialog.setContentView(R.layout.settings_dialog);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dmDialog.getWindow().setLayout((6 * width) / 7, (4 * height) / 5);
        dmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dmDialog.show();

        // TODO: Implement the Settings button and the Localization. RTL, etc. if necessary
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
        // TODO: IMPLEMENT
        //setLocale(this,"us");
        setLocale("us");
        dmDialog.dismiss();
    }

    public void onClickIsraelBtn(View view) {
        //setLocale(this,"he");
        setLocale("he");
        dmDialog.dismiss();
    }
}