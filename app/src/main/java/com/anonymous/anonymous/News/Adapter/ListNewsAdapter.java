package com.anonymous.anonymous.News.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anonymous.anonymous.News.Model.Article;
import com.anonymous.anonymous.News.NewsArticleActivity;
import com.anonymous.anonymous.News.Utility.ISO8601DateParser;
import com.anonymous.anonymous.News.Utility.ItemClickListener;
import com.anonymous.anonymous.R;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by pan on 2017/12/17.
 */

class ListNewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    ItemClickListener itemClickListener;

    TextView article_title;
    RelativeTimeTextView article_time;
    CircleImageView article_image;
    TextView num_comment;

    public ListNewsViewHolder(View itemView) {
        super(itemView);

        article_image = (CircleImageView) itemView.findViewById(R.id.new_image);
        article_title = (TextView) itemView.findViewById(R.id.news_title);
        article_time = (RelativeTimeTextView) itemView.findViewById(R.id.news_time);
        num_comment = (TextView) itemView.findViewById(R.id.comment_num);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view){

        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}


public class ListNewsAdapter extends RecyclerView.Adapter<ListNewsViewHolder>{

    private List<Article> articleList;
    private Context context;

    public ListNewsAdapter(List<Article> articleList, Context context) {
        this.articleList = articleList;
        this.context = context;
    }

    @Override
    public ListNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_newsitem, parent, false);
        return new ListNewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ListNewsViewHolder holder, int position) {
        Picasso.with(context)
                .load(articleList.get(position).getUrlToImage())
                .into(holder.article_image);

        if(articleList.get(position).getTitle().length() > 65)
            holder.article_title.setText(articleList.get(position).getTitle().substring(0,65) + "...");
        else
            holder.article_title.setText(articleList.get(position).getTitle());

        Date date = null;
        try{
            date = ISO8601DateParser.parse(articleList.get(position).getPublishedAt());
        }catch (ParseException ex)
        {
            ex.printStackTrace();
        }
        holder.article_time.setReferenceTime(date.getTime());


        DatabaseReference newsArticleDataRef = FirebaseDatabase.getInstance().getReference("news");
        DatabaseReference numOfComRef = newsArticleDataRef
                .child(articleList.get(position).getUrl().replace('.', ',')).child("numOfComment");


        numOfComRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int num = dataSnapshot.getValue(Integer.class);
                if(num < 0)
                    num = 0;
                holder.num_comment.setText(String.valueOf(num));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseError.toException().printStackTrace();
            }
        });

        //Set Event Click
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent articleBody = new Intent(context, NewsArticleActivity.class);
                articleBody.putExtra("webURL", articleList.get(position).getUrl());
                context.startActivity(articleBody);
            }
        });

    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }
}
