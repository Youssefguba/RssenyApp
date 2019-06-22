package com.rsseny.student.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rsseny.student.Interface.ItemClickListener;
import com.rsseny.student.R;

public class ChoosingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView choosingFacultyButton;
    public ImageView  choosingImage;
    private ItemClickListener itemClickListener;


    public ChoosingViewHolder(@NonNull View itemView) {
        super(itemView);

        choosingFacultyButton = itemView.findViewById(R.id.choosing_faculty_button);
        choosingImage = itemView.findViewById(R.id.choosing_faculty_image);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(),false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

}
