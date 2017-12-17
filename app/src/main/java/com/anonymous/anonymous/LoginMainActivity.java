package com.anonymous.anonymous;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoginMainActivity extends AppCompatActivity {

    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);


        EditText _username = (EditText) findViewById(R.id.username_input);
        EditText _password = (EditText) findViewById(R.id.password_input);
        email = _username.getText().toString();
        password = _password.getText().toString();
        Button login_btn = (Button) findViewById(R.id.login_btn);

        login_btn.setOnClickListener(searchEvent);


    }

    /**
     * checking user's information with out database. If users does not exist, then register pages will pop up
     */
    private View.OnClickListener searchEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(isValid()){
                initMainActivity();
            }
        }

    };
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
}
