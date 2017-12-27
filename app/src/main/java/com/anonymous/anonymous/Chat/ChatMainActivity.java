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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;


public class ChatMainActivity extends AnonymousBaseActivity {
    // query for last 50 messages
    private static final Query query = FirebaseDatabase.getInstance().getReference("chats").limitToLast(50);
    private DatabaseReference mDatabaseChatNode = FirebaseDatabase.getInstance().getReference("chats");
    ArrayList<ChatMessage> chatMessages = chatMessages = new ArrayList<>();

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

                // push object to chats node
                FirebaseDatabase.getInstance()
                        .getReference("chats")
                        .push()
                        .setValue(new ChatMessage(input.getText().toString(), user));
                //End of writing

                input.setText("");
            }
        });

        // Listen to Database
        mDatabaseChatNode.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                chatMessages.add(chatMessage);
                displayMessage();



            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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

    /**
     * Helper method that will update the list
     */
    private void displayMessage(){
        ListView listOfMessage = (ListView) findViewById(R.id.list_of_messages);

        ChatAdapter chatAdapter = new ChatAdapter(this, chatMessages);

        listOfMessage.setAdapter(chatAdapter);

    }


}

