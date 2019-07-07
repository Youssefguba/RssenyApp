package com.rsseny.student.ViewHolder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.rsseny.student.Interface.ItemClickListener;
import com.rsseny.student.R;

public class VideosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public YouTubePlayerView videoItem;
    public YouTubeThumbnailView youTubeThumbnailView;
    public TextView nameOfVideo;
    public ItemClickListener itemClickListener;
    public LinearLayout linearLayout;

    public VideosViewHolder(@NonNull View itemView) {
        super(itemView);

        videoItem = itemView.findViewById(R.id.video_item);
        nameOfVideo = itemView.findViewById(R.id.name_of_video);
        youTubeThumbnailView = itemView.findViewById(R.id.video_thumbnail);
        linearLayout = itemView.findViewById(R.id.background_of_text);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
