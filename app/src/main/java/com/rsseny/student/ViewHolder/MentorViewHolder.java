package com.rsseny.student.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rsseny.student.R;

public class MentorViewHolder extends RecyclerView.ViewHolder {
    public TextView nameOfMentor;
    public TextView priceOfMentor;
    public TextView phoneNumberOfMentor;
    public TextView descriptionOfMentor;
    public ImageView photoOfMentor;

    public MentorViewHolder(@NonNull View itemView) {
        super(itemView);

        nameOfMentor = itemView.findViewById(R.id.nameOfMentor);
        priceOfMentor = itemView.findViewById(R.id.priceOfMentor);
        phoneNumberOfMentor = itemView.findViewById(R.id.phoneNumberOfMentor);
        descriptionOfMentor = itemView.findViewById(R.id.descriptionOfMentor);
        photoOfMentor = itemView.findViewById(R.id.mentorPhoto);

    }
}
