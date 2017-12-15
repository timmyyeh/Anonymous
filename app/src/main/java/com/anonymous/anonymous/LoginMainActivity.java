package com.anonymous.anonymous;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginMainActivity extends AppCompatActivity {
    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        username = (EditText) findViewById(R.id.username_input);
        password = (EditText) findViewById(R.id.password_input);
        Button login_btn = (Button) findViewById(R.id.login_btn);

        login_btn.setOnClickListener(searchEvent);


    }

    private View.OnClickListener searchEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(!username.getText().toString().equals("root")) Toast.makeText(getApplicationContext(), "User does not exist", Toast.LENGTH_LONG).show();
            if(!password.getText().toString().equals("tiger")) Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_LONG).show();
            if(username.getText().toString().equals("root") && password.getText().toString().equals("tiger")){
                initMainActivity();
            }
        }

    };
    private void initMainActivity() {
        Intent intent = new Intent(LoginMainActivity.this, AnonymousMainActivity.class);
        startActivity(intent);
        finish();

    }
}
