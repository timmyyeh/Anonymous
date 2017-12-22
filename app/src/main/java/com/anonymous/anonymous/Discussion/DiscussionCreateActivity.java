package com.anonymous.anonymous.Discussion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.anonymous.anonymous.R;

public class DiscussionCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_create);

        Toolbar toolbar = (Toolbar) findViewById(R.id.top_toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create Discussion");
    }
}
