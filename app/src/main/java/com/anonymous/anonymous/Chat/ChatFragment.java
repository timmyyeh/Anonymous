package com.anonymous.anonymous.Chat;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.anonymous.anonymous.Chat.Adapter.ChatAdapter;
import com.anonymous.anonymous.Chat.Model.ChatMessage;
import com.anonymous.anonymous.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ChatFragment extends Fragment {
    private DatabaseReference mDatabaseChatNode = FirebaseDatabase.getInstance().getReference("chats");
    private ArrayList<ChatMessage> chatMessages = chatMessages = new ArrayList<>();
    private ChatAdapter chatAdapter;
    private  FloatingActionButton fab;
    private EditText input;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        fab = (FloatingActionButton) getActivity().findViewById(R.id.button_search_anonymous);
        fab.hide();

        input = (EditText) view.findViewById(R.id.input);

        //write the object to Database after clicked
        view.findViewById(R.id.fab_chat_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

        ListView listOfMessage = (ListView) view.findViewById(R.id.list_of_messages);
        chatAdapter = new ChatAdapter(getContext(), chatMessages);
        listOfMessage.setAdapter(chatAdapter);

        return view;
    }

        @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        mDatabaseChatNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    chatMessages.add(snapshot.getValue(ChatMessage.class));
                    chatAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        fab.show();
        super.onDestroyView();
    }
}
