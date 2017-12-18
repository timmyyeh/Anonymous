package com.anonymous.anonymous;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;


public class ChatMainActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);

        findViewById(R.id.chat_imgbtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.chat_imgbtn){
            Toast.makeText(getApplicationContext(), "WOW", Toast.LENGTH_SHORT).show();
        }
    }
}

