package com.example.battleship;


import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Toast;


public class CustomDialog extends Dialog implements View.OnClickListener {

    public Activity dmActivity;

    public CustomDialog() {
        super(null);
    }

    public CustomDialog(Activity activity) {
        super(activity);
        this.dmActivity = activity;
    }

    @Override
    public void onClick(View view) {

    }

    public Activity getActivity() {
        return dmActivity;
    }


    // do not press the back button inside a dialog!
    @Override
    public void onBackPressed() {

    }
}

