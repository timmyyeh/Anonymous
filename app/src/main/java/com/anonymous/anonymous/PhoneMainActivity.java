package com.anonymous.anonymous;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anonymous.anonymous.Chat.ChatMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneMainActivity extends AppCompatActivity implements View.OnClickListener{
    private final static String TAG = "PhoneLogin";
    private EditText phone_input;
    private EditText phone_code;
    private Button button_send;
    private Button button_signIn;
    private TextView textView_resend;

    //Firebase Auth
    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";
    private FirebaseAuth mAuth;
    private boolean mVerificationInProgress = false;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private String mVerificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_main);

        // Restore instance state in case user go to check the code
        if(savedInstanceState != null){
            onRestoreInstanceState(savedInstanceState);
        }

        phone_input = (EditText) findViewById(R.id.phone_input);
        phone_code = (EditText) findViewById(R.id.input_code);
        button_send = (Button) findViewById(R.id.button_send);
        button_signIn = (Button) findViewById(R.id.button_signin);
        textView_resend = (TextView) findViewById(R.id.resend_textView);

        //Enable the sending button to send the code
        //Disable other button until the code is sent
        button_send.setEnabled(true);
        button_signIn.setEnabled(false);
        textView_resend.setEnabled(false);

        //Set Listener
        button_send.setOnClickListener(this);
        button_signIn.setOnClickListener(this);
        textView_resend.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        // verification callbacks
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                // success
                mVerificationInProgress = false;
                signIn(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                mVerificationInProgress = false;

                if (e instanceof FirebaseAuthInvalidCredentialsException) {

                    phone_input.setError("Invalid phone number.");

                } else if (e instanceof FirebaseTooManyRequestsException) {

                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCodeSent(String id, PhoneAuthProvider.ForceResendingToken token){
                mResendToken = token;
                mVerificationId = id;
            }
        };



    }
    // Sign in with phone and switch to main activity
    private void signIn(PhoneAuthCredential phoneAuthCredential) {
        mAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // sign in success
                        if(task.isSuccessful()){
                            Intent intent = new Intent(PhoneMainActivity.this, ChatMainActivity.class);
                            startActivity(intent);
                            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                            finish();
                        }
                        else{
                            //sign in failed with wrong code
                            if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(getApplicationContext(), "Wrong Code!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
    }


    private void phoneNumberVerification(String phoneNumber){
        // start verify phone
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks
        );
        // enable user to log in
        button_signIn.setEnabled(true);
        textView_resend.setEnabled(true);
        mVerificationInProgress = true;
    }

    private void verifyPhoneNumberCode(String id, String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(id, code);
        signIn(credential);
    }


    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks,
                token);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_send:
                // do action
                Log.w(TAG, "send!");
                phoneNumberVerification(phone_input.getText().toString());
                return;
            case R.id.button_signin:
                //do action
                Log.w(TAG, "sign in!");
                verifyPhoneNumberCode(mVerificationId, phone_code.getText().toString());
                return;
            case R.id.resend_textView:
                Log.w(TAG, "resend");
                resendVerificationCode(phone_input.getText().toString(), mResendToken);
        }
    }
}
