package com.anonymous.anonymous;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class AnonymousMainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anonymous_main);

        //top toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.top_toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Anonymous");

        //bottom nav
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    // toolbar's overflow menu item
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.item_logout:
                FirebaseAuth.getInstance().signOut();
                switchActivity(LoginMainActivity.class);
                return true;
            case R.id.item_2:
                Toast.makeText(getApplicationContext(), "You click item 2", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //init overflow menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.ic_chat:
                Toast.makeText(getApplicationContext(), "chat Selected", Toast.LENGTH_LONG).show();
                switchActivity(ChatMainActivity.class);
                return true;
            case R.id.ic_discuss:
                Toast.makeText(getApplicationContext(), "discussion selected", Toast.LENGTH_SHORT).show();
                switchActivity(DiscussionMainActivity.class);
                return true;
            case R.id.ic_news:
                Toast.makeText(getApplicationContext(), "news selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void switchActivity(Class<?> ActivityClass) {
        Intent intent = new Intent(AnonymousMainActivity.this, ActivityClass);
        startActivity(intent);
    }
}
