package com.example.battleship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private FirebaseDatabase firebaseDatabase;
    FirebaseFirestore db;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null) {
//            startActivity(new Intent(CreateAccountActivity.this, MainActivity.class));
//        }
//    }

    private void Register() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            db = FirebaseFirestore.getInstance();

                            // Create a new user with a first and last name
                            Map<String, Object> user = new HashMap<>();

                            user.put("email", email);
                            user.put("score", 0);
                            user.put("wins", 0);
                            user.put("losses", 0);
                            user.put("min_turns_to_win", -1); // min turns number vs cpu score.
                            user.put("matchesResultHistory", Collections.emptyList()); // string type
                            user.put("numberOfTurnsHistory", Collections.emptyList()); // int type


                            String userDocumentId = "[" + email + "]_UserData";

                            // Add a new document with a generated ID
                            db.collection("Users").document(userDocumentId).set(user)
                                    .addOnSuccessListener(documentReference -> Log.d("Success", "DocumentSnapshot added with ID: " + email + "UserData"))
                                    .addOnFailureListener(e -> Log.w("Failure", "Error adding document", e));

                            startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class));
                        } else {
                            Toast.makeText(CreateAccountActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // if the data is not added or it is cancelled then
//                // we are displaying a failure toast message.
//                Toast.makeText(MainActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    public void onClickCreateAccount(View view) {
        if (checkDataEntered()) {
            Register(); // TODO: has to be in a different thread than the main thread
        }
    }
//
//    }
//


    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    boolean checkDataEntered() {
        boolean isValid = true;
        if (isEmpty(emailEditText)) {
            emailEditText.setError("Enter a valid email!");
            isValid = false;
        } else {
            if (!isEmail(emailEditText)) {
                emailEditText.setError("Enter a valid email!");
                isValid = false;
            }
        }

        if (isEmpty(passwordEditText)) {
            passwordEditText.setError("Enter a valid password!");
            isValid = false;
        } else {
            if (passwordEditText.getText().toString().length() < 6) {
                passwordEditText.setError("Password must contain at least 6 chars!");
                isValid = false;
            }
        }
        return isValid;
    }

}
