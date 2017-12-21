package com.anonymous.anonymous.Chat;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.anonymous.anonymous.AnonymousBaseActivity;
import com.anonymous.anonymous.Chat.Model.ChatMessage;
import com.anonymous.anonymous.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ChatMainActivity extends AnonymousBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);


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

        //write the object to Database after clicked
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText) findViewById(R.id.input);

                //Write message
                String user = FirebaseAuth.getInstance().getUid();

                FirebaseDatabase.getInstance()
                        .getReference()
                        .push()
                        .setValue(new ChatMessage(input.getText().toString(), user));
                //End of writing

                input.setText("");
            }
        });

        displayMessage();

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

    private void displayMessage(){
        ListView listOfMessage = (ListView) findViewById(R.id.list_of_messages);


        ArrayList<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatMessage("Test", "Anonymous"));
        ChatAdapter chatAdapter = new ChatAdapter(this, chatMessages);


        listOfMessage.setAdapter(chatAdapter);

    }


}

