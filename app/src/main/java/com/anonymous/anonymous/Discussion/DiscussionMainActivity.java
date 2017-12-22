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
import android.widget.Toast;

import com.anonymous.anonymous.AnonymousBaseActivity;
import com.anonymous.anonymous.Discussion.DiscussionCreateActivity;
import com.anonymous.anonymous.R;

public class DiscussionMainActivity extends AnonymousBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.top_toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Discussion Board");

        //bottom nav
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        menuItem.setEnabled(false);

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

        FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(getApplicationContext());

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                FeedReaderContract.FeedEntry._ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_MESSAGE
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = { "My Title" };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                FeedReaderContract.FeedEntry.COLUMN_NAME_MESSAGE + " DESC";

        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if(cursor.moveToFirst()) {
            String itemId = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._COUNT)
            );

            Toast.makeText(getApplicationContext(), itemId, Toast.LENGTH_SHORT).show();
        }
    }
}
