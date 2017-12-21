package com.anonymous.anonymous.Chat;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.anonymous.anonymous.Chat.Model.ChatMessage;
import com.anonymous.anonymous.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import dmax.dialog.SpotsDialog;

/**
 * Created by timmy on 2017/12/20.
 */

public class ChatFragment extends android.support.v4.app.Fragment{
    private static final String TAG = "ChatSearchFragment";
    private Button btn;
    private SpotsDialog spotsDialog;
    private FirebaseListAdapter<ChatMessage> adapter;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        view = inflater.inflate(R.layout.fragment_chat, container, false);
        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText) view.findViewById(R.id.input);

               //Write message
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Message");

                myRef.setValue("Hello World");
            }
        });



        return view;
    }
//    private void displayChatMessage(){
//        ListView listOfMessage = (ListView) view.findViewById(R.id.list_of_messages);
//
//        FirebaseListOptions<ChatMessage> options = new FirebaseListOptions.Builder<ChatMessage>()
//                .setQuery(FirebaseDatabase.getInstance().getReference(), ChatMessage.class)
//                .build();
//
//        adapter = new FirebaseListAdapter<ChatMessage>(options) {
//            @Override
//            protected void populateView(View v, ChatMessage model, int position) {
//                TextView msgTxt = (TextView) v.findViewById(R.id.message_text);
//                TextView msgUser = (TextView) v.findViewById(R.id.message_user);
//                TextView msgTime = (TextView) v.findViewById(R.id.message_time);
//
//                msgTxt.setText(model.getMessage());
//                msgUser.setText("Anonymous");
//                msgTime.setText(DateFormat.format("dd-mm-yyyy (HH:mm:ss)", model.getTime()));
//
//            }
//        };
//        listOfMessage.setAdapter(adapter);
//    }
}
