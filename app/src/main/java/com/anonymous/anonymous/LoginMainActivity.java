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
import android.widget.Toast;

import com.anonymous.anonymous.Chat.ChatMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

/**
 * Log in Screen, if users already log in, then it will go to the main activity screen.
 * Two testing account: <anonymous@test.com> <anonymous>
 *                      <anonymous1@test.com> <anonymous>
 * After users click log in or register button, the email and password will be sent to the account
 * database to verify their identities.
 *
 */
public class LoginMainActivity extends AppCompatActivity {

    EditText _username;
    EditText _password;
    String email;
    String password;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;
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
                    Log.d(TAG, "Status_Login:" + user.getUid());
                    userUID = user.getUid();
                    initMainActivity();
                }
                else{
                    Log.d(TAG, "Status_Logout");
                }
            }
        };


        _username = (EditText) findViewById(R.id.username_input);
        _password = (EditText) findViewById(R.id.password_input);

        Button login_btn = (Button) findViewById(R.id.login_btn);
        final Button createAccount_btn = (Button) findViewById(R.id.createAccount_btn);

        // log in event.
        login_btn.setOnClickListener(searchEvent);
        // create event
        createAccount_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValid()) {
                    createUser(email, password);
                }
            }
        });

        findViewById(R.id.phone_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Text Click", Toast.LENGTH_SHORT).show();
            }
        });


    }

    /**
     * checking user's information with Firebase. If users does not exist, then register pages will pop up
     */
    private View.OnClickListener searchEvent = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            // when the email and password are in valid formed, the data will be verified with Firebase
            if(isValid()){
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthInvalidUserException invalidEmail) {
                                        register(email, password);
                                    }
                                    catch (FirebaseAuthInvalidCredentialsException invalidPass){
                                        Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_LONG).show();
                                    }
                                    catch (Exception e){
                                        Log.d(TAG, "onComplete: " + e.getMessage());
                                    }
                                }
                            }
                        });
            }
        }

    };

    /**
     * When user login with a email that does not exist, a dialog will prompt users to register.
     * @param email user's email account
     * @param password password
     */
    private void register(final String email, final String password) {
        new AlertDialog.Builder(LoginMainActivity.this)
                .setTitle("Account does not exist")
                .setMessage("Would you like to register with this account and password?")
                .setPositiveButton("Register", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        createUser(email, password);
                        Toast.makeText(getApplicationContext(), "Create", Toast.LENGTH_LONG).show();
                    }
                })
                .setNeutralButton("Cancel", null)
                .show();

    }

    private void createUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String message = task.isSuccessful() ? "Welcome, New Anonymous" : "Failed: ";
                        if(!task.isSuccessful()){
                            try{
                                throw task.getException();
                            }
                            catch(FirebaseAuthUserCollisionException ex){
                                message += "Email already exists";

                            }
                            catch (FirebaseAuthWeakPasswordException e){
                                message += "Password too weak";

                            }
                            catch (Exception ex){
                                message += "Opps, something is wrong";
                            }
                        }
                        new AlertDialog.Builder(LoginMainActivity.this)
                                .setMessage(message)
                                .setPositiveButton("Ok", null)
                                .show();
                    }
                });
    }

    // move from login activity to main activity
    private void initMainActivity() {
        Intent intent = new Intent(LoginMainActivity.this, ChatMainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        finish();

    }

    /**
     * checking whether email and password is valid
     * @return true if both are valid
     */
    private boolean isValid(){
        boolean valid = true;
        email = _username.getText().toString();
        password = _password.getText().toString();

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
    // logout
    @Override
    protected void onStop() {
        super.onStop();
        auth.removeAuthStateListener(authStateListener);
    }

}
