package com.anonymous.anonymous.Chat.Adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.anonymous.anonymous.Chat.Model.ChatMessage;
import com.anonymous.anonymous.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/**
 * Created by timmy on 2017/12/20.
 */

public class ChatAdapter extends ArrayAdapter<ChatMessage>{
    private final String uid = FirebaseAuth.getInstance().getUid();

    private static class ViewHolder{
        TextView message;
        TextView user;
        TextView time;
    }

    public ChatAdapter(Context context, ArrayList<ChatMessage> messages){
        super(context, R.layout.message, messages);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ChatMessage messages = getItem(position);

        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.message, parent, false);
            viewHolder.message = (TextView) convertView.findViewById(R.id.message_text);
            viewHolder.user = (TextView) convertView.findViewById(R.id.message_user);
            viewHolder.time = (TextView) convertView.findViewById(R.id.message_time);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String account = uid.equals(messages.getUser()) ? "Me" : "Anonymous";

        viewHolder.message.setText(messages.getMessage());
        viewHolder.user.setText(account);
        viewHolder.time.setText(DateFormat.format("MM/dd(E) (HH:mm)", messages.getTime()));


        return convertView;
    }
}
