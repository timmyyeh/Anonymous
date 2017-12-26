package com.anonymous.anonymous.Discussion;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

        //Assinging the data
        final EditText etTitle = (EditText) findViewById(R.id.discussion_title);
        final EditText etMessage = (EditText) findViewById(R.id.discussion_message);

        //Getting current time and date
        final long now = System.currentTimeMillis();

        //Format of the date
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/ MM/ dd");

        ImageButton insert = (ImageButton) findViewById(R.id.apply_button);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = simpleDateFormat.format(new Date(now)).toString();
                String title = etTitle.toString();
                String message = etMessage.toString();

                dbHelper.insert(date, title, message);
            }
        });
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
}
