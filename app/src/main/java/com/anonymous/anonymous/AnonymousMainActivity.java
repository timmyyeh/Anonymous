package com.anonymous.anonymous;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class AnonymousMainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anonymous_main);

        findViewById(R.id.chat_imgbtn).setOnClickListener(this);
        findViewById(R.id.logout_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(getApplicationContext(), "Did", Toast.LENGTH_SHORT).show();
        int id = view.getId();
        if(id == R.id.logout_btn){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(AnonymousMainActivity.this, LoginMainActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.chat_imgbtn){
            Intent intent = new Intent(AnonymousMainActivity.this, ChatMainActivity.class);
            startActivity(intent);
        }
    }
}
