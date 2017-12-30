package com.anonymous.anonymous.Discussion;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.anonymous.anonymous.AnonymousBaseActivity;
import com.anonymous.anonymous.Discussion.DiscussionCreateActivity;
import com.anonymous.anonymous.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DiscussionMainActivity extends AnonymousBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.top_toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Discussion Board");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return super.onNavigationItemSelected(item);
    }

    //When add button is invoked by the user
    public void createNewDiscussion(View view) {
        Intent intent = new Intent(this, DiscussionCreateActivity.class);
        startActivity(intent);
    }

    public void refresh() {

        final ListView listView = findViewById(R.id.discussion_list);
        //final String[] LIST_MENU = {"LIST1", "LIST2", "LIST3"} ;

        DBHelper dbHelper = new DBHelper(getApplicationContext(), "Anonymous.db", null, 1);
        String[] LIST_MENU = dbHelper.getTitles();

        Toast.makeText(getApplicationContext(),
                LIST_MENU[0], Toast.LENGTH_SHORT).show();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, LIST_MENU);

        listView.setAdapter(adapter);

    }

}
