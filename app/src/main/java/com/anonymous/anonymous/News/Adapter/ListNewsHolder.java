package com.anonymous.anonymous.News.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.anonymous.anonymous.News.Utility.ItemClickListener;
import com.anonymous.anonymous.R;
import com.github.curioustechizen.ago.RelativeTimeTextView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by pan on 2017/12/28.
 */

class ListNewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    ItemClickListener itemClickListener;

    TextView article_title;
    RelativeTimeTextView article_time;
    CircleImageView article_image;
    TextView num_comment;

    public ListNewsHolder(View itemView) {
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


