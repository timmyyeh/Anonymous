package com.anonymous.anonymous.News.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anonymous.anonymous.News.Model.Comment;
import com.anonymous.anonymous.News.Utility.ISO8601DateParser;
import com.anonymous.anonymous.News.Utility.ItemClickListener;
import com.anonymous.anonymous.R;
import com.github.curioustechizen.ago.RelativeTimeTextView;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by pan on 2017/12/24.
 */

class ListCommentsViewHolder extends RecyclerView.ViewHolder {
    TextView comment_user;
    TextView comment_content;
    RelativeTimeTextView comment_time;

    public ListCommentsViewHolder(View itemView) {
        super(itemView);

        comment_user = (TextView) itemView.findViewById(R.id.comment_user);
        comment_content = (TextView) itemView.findViewById(R.id.comment);
        comment_time = (RelativeTimeTextView) itemView.findViewById(R.id.comment_time);
    }
}
public class ListCommentsAdapter extends RecyclerView.Adapter<ListCommentsViewHolder>{
    private List<Comment> commentList;
    private Context context;

    public ListCommentsAdapter(List<Comment> commentList, Context context) {
        this.commentList = commentList;
        this.context = context;
    }

    @Override
    public ListCommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_comment, parent, false);
        return new ListCommentsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListCommentsViewHolder holder, int position) {
        holder.comment_user.setText(commentList.get(position).getUser());
        holder.comment_content.setText(commentList.get(position).getComment());

        Date date = new Date(commentList.get(position).getTimeCreated());
        holder.comment_time.setReferenceTime(date.getTime());

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }
}
