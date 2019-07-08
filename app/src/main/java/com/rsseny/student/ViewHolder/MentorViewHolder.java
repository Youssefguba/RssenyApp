package com.rsseny.student.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ornach.nobobutton.NoboButton;
import com.rsseny.student.R;

public class MentorViewHolder extends RecyclerView.ViewHolder {
    public TextView nameOfMentor;
    public TextView priceOfMentor;
    public TextView descriptionOfMentor;
    public ImageView photoOfMentor;
    public NoboButton detailsButton;
    public NoboButton chooseButton;

    public MentorViewHolder(@NonNull View itemView) {
        super(itemView);

        nameOfMentor = itemView.findViewById(R.id.nameOfMentor);
        priceOfMentor = itemView.findViewById(R.id.priceOfMentor);
        descriptionOfMentor = itemView.findViewById(R.id.descriptionOfMentor);
        photoOfMentor = itemView.findViewById(R.id.mentorPhoto);
        detailsButton = itemView.findViewById(R.id.detailsButtonOfMentor);
        chooseButton = itemView.findViewById(R.id.chooseButtonOfMentor);


    }
}
