package com.rsseny.student.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rsseny.student.R;

public class SpinnerAdapter extends BaseAdapter {
    Context context;
    int img[];
    String gender[];
    LayoutInflater layoutInflater;

    public SpinnerAdapter(Context context, int[] img, String[] gender) {
        this.context = context;
        this.img = img;
        this.gender = gender;
        layoutInflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return img.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.custom_spinner_items, null);
        ImageView icon =  view.findViewById(R.id.imageView);
        TextView names =  view.findViewById(R.id.textView);
        icon.setImageResource(img[i]);
        names.setText(gender[i]);
        return view;
    }
}
