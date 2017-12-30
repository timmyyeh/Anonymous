package com.anonymous.anonymous.Discussion;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.anonymous.anonymous.R;

import java.text.SimpleDateFormat;
import java.util.Date;

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

        if(isEmpty(titleBar))               //When title bar is empty
        {
            Toast.makeText(getApplicationContext(),
                    "Set title and message", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (isEmpty(messageBar))       //When message bar is empty
        {
            Toast.makeText(getApplicationContext(), "Set message", Toast.LENGTH_SHORT).show();
            return;
        }

        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "Anonymous.db", null, 1);

        //Getting current time and date
        final long now = System.currentTimeMillis();

        //Format of the date
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/ MM/ dd");
        String date = simpleDateFormat.format(new Date(now)).toString();
        String title = titleBar.getText().toString();
        String message = messageBar.getText().toString();

        dbHelper.insert(date, title, message);

        Toast.makeText(getApplicationContext(),
                message, Toast.LENGTH_SHORT).show();
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
}
