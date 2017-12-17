package com.anonymous.anonymous;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginMainActivity extends AppCompatActivity {

    EditText _username;
    EditText _password;
    String email;
    String password;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;
    ProgressBar progressBar;
    private String userUID;
    private static final String TAG = "Authentication";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
        auth = FirebaseAuth.getInstance();

        // go to mainActivity if already login in
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Log.d(TAG, "Log in:" + user.getUid());
                    userUID = user.getUid();
                    initMainActivity();
                }
                else{
                    Log.d(TAG, "Log Out");
                }
            }
        };


        _username = (EditText) findViewById(R.id.username_input);
        _password = (EditText) findViewById(R.id.password_input);
        progressBar = (ProgressBar) findViewById(R.id.mProgessBar);

        Button login_btn = (Button) findViewById(R.id.login_btn);
        Button createAccount_btn = (Button) findViewById(R.id.createAccount_btn);

        login_btn.setOnClickListener(searchEvent);


    }

    /**
     * checking user's information with out database. If users does not exist, then register pages will pop up
     */
    private View.OnClickListener searchEvent = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            progressBar.setVisibility(ProgressBar.VISIBLE);
            email = _username.getText().toString();
            password = _password.getText().toString();

            if(isValid()){
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    Log.d(TAG, "Failed");
                                    register(email, password);
                                }
                            }
                        });
            }
            progressBar.setVisibility(ProgressBar.INVISIBLE);
        }

    };

    private void register(final String email, final String password) {
        new AlertDialog.Builder(LoginMainActivity.this)
                .setTitle("Login Failure")
                .setMessage("Account Does not Exist. Would you like to register?")
                .setPositiveButton("Register", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        createUser(email, password);
                        Toast.makeText(getApplicationContext(), "Create", Toast.LENGTH_LONG).show();
                    }
                })
                .setNeutralButton("Cancel", null)
                .show();

    }

    private void initMainActivity() {
        Intent intent = new Intent(LoginMainActivity.this, AnonymousMainActivity.class);
        startActivity(intent);
        finish();

    }

    private boolean isValid(){
        boolean valid = true;

        if(TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(getApplicationContext(), "Please Enter Valid Email", Toast.LENGTH_LONG).show();
            valid = false;
        }

        if(TextUtils.isEmpty(password) || password.length() < 6){
            Toast.makeText(getApplicationContext(), "Password cannot be shorter than 6 chars", Toast.LENGTH_LONG).show();
            valid = false;
        }

        return valid;
    }

    //check if user is currently signed in
    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        auth.removeAuthStateListener(authStateListener);
    }

}
