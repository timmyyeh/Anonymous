package com.anonymous.anonymous.Chat;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.anonymous.anonymous.AnonymousBaseActivity;
import com.anonymous.anonymous.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ChatActivity extends AnonymousBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbox);

        Toolbar toolbar = (Toolbar) findViewById(R.id.top_toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Anonymous");

        //bottom nav
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        menuItem.setEnabled(false);

        // notify database that user is online
        FirebaseDatabase.getInstance()
                .getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getUid())
                .setValue(true);

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

}
