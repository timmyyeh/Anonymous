package com.anonymous.anonymous;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.anonymous.anonymous.Discussion.DiscussionMainActivity;
import com.anonymous.anonymous.News.NewsMainActivity;
import com.google.firebase.auth.FirebaseAuth;


public abstract class AnonymousBaseActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

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

    // bottom nav bar items
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
                switchActivity(NewsMainActivity.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //helper method to switch between activities
    private void switchActivity(Class<?> ActivityClass) {
        Intent intent = new Intent(this, ActivityClass);
        startActivity(intent);
    }
}
