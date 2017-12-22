package com.anonymous.anonymous.Discussion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

    public void saveDiscussion(View view)
    {
        EditText titleBar = (EditText) findViewById(R.id.discussion_title);
        EditText messageBar = (EditText) findViewById(R.id.discussion_message);

        if(isEmpty(titleBar))
        {
            Toast.makeText(getApplicationContext(), "Set title and message", Toast.LENGTH_SHORT).show();
        }
        else if (isEmpty(messageBar))
        {
            Toast.makeText(getApplicationContext(), "Set message", Toast.LENGTH_SHORT).show();
        }

        String title = titleBar.toString();
        String message = messageBar.getText().toString();

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
}
