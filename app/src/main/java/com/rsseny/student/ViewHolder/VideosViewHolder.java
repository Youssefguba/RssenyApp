package com.rsseny.student.ViewHolder;

import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rsseny.student.Interface.ItemClickListener;
import com.rsseny.student.R;

public class VideosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public WebView videoItem;
    public TextView nameOfVideo;
    public ItemClickListener itemClickListener;


    public VideosViewHolder(@NonNull View itemView) {
        super(itemView);

        videoItem = itemView.findViewById(R.id.video_item);
        nameOfVideo = itemView.findViewById(R.id.name_of_video);

    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
