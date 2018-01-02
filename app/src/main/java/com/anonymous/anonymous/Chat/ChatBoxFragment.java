package com.anonymous.anonymous.Chat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anonymous.anonymous.Chat.Adapter.ChatBoxAdapter;
import com.anonymous.anonymous.Chat.Decorator.DividerListItemDecoration;
import com.anonymous.anonymous.Chat.Model.ChatMessage;
import com.anonymous.anonymous.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by timmy on 2017/12/26.
 */

public class ChatBoxFragment extends Fragment {
    private static final String TAG = "ChatBoxFragment";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ChatBoxAdapter chatBoxAdapter;
    private DatabaseReference mDatabaseChatNode = FirebaseDatabase.getInstance().getReference("chats");
    List<ChatMessage> chatMessages = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_chat_main, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.chat_recycler_view);

        // [set the adapter && recyclerview]
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        chatBoxAdapter = new ChatBoxAdapter(getActivity(), chatMessages);
        recyclerView.setAdapter(chatBoxAdapter);
        recyclerView.addItemDecoration(new DividerListItemDecoration());
        // [End of setting]

        view.findViewById(R.id.button_search_anonymous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w(TAG, "Search a user!");
            }
        });

        mDatabaseChatNode.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

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


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Listen to Database
        mDatabaseChatNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    chatMessages.add(snapshot.getValue(ChatMessage.class));
                    chatBoxAdapter.notifyItemInserted(chatMessages.size() - 1);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
