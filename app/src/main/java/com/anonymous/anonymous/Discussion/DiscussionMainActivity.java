package com.anonymous.anonymous.Discussion;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.anonymous.anonymous.AnonymousBaseActivity;
import com.anonymous.anonymous.R;

public class DiscussionMainActivity extends AnonymousBaseActivity {

    //Path to save the file.
    private String saveFullPath;
    private String savePath;
    private final String filename = "MoneyBook.db";
    private final String host = "ec2-13-57-227-82.us-west-1.compute.amazonaws.com";
    private final int port = 1998;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.top_toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Discussion Board");

        savePath = getCacheDir().toString();
        saveFullPath = savePath + "/" + filename;
        FileClient fc = new FileClient(host, port, savePath, filename);
        fc.downloadFile();
        refresh();
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

        DBHelper dbHelper = new DBHelper(getApplicationContext(), saveFullPath, null, 1);
        String[] LIST_MENU = dbHelper.getTitle();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, LIST_MENU);

        listView.setAdapter(adapter);
    }

}
