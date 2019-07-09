package com.rsseny.student.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rsseny.student.Interface.ItemClickListener;
import com.rsseny.student.R;

public class DescriptionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView descriptionOfMentor;
    public TextView priceOfMentor;
    public TextView titleOfMentor;
    public ImageView exitOfLayout;

    private ItemClickListener itemClickListener;


    public DescriptionViewHolder(@NonNull View itemView) {
        super(itemView);


        titleOfMentor = itemView.findViewById(R.id.title_of_mentor);
        priceOfMentor = itemView.findViewById(R.id.price_of_mentor);
        descriptionOfMentor = itemView.findViewById(R.id.description_of_mentor);
    }



    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    @Override
    public void onClick(View view) {

    }
}
