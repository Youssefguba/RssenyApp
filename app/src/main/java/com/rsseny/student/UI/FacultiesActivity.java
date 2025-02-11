package com.rsseny.student.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.rsseny.student.Interface.ItemClickListener;
import com.rsseny.student.Model.ChoosingItem;
import com.rsseny.student.R;
import com.rsseny.student.ViewHolder.ChoosingViewHolder;
import com.squareup.picasso.Picasso;


/*
 * This Activity for showing the list of faculties that user can choose.
 *
 * */
public class FacultiesActivity extends AppCompatActivity {

    private static final String TAG = "FacultiesActivity";

    FirebaseDatabase database;
    DatabaseReference choosingRef;
    FirebaseRecyclerAdapter<ChoosingItem, ChoosingViewHolder> adapter;

    ProgressWheel progressWheel;
    FrameLayout progressOverLay;

    RecyclerView recycler_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosing_home);

        database = FirebaseDatabase.getInstance();
        choosingRef = database.getReference("Faculties");

        progressWheel = findViewById(R.id.progress_wheel);
        progressOverLay = findViewById(R.id.progress_overlay);

        recycler_menu = findViewById(R.id.recycler_menu);
        recycler_menu.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));

        loadChoosingMenu();

    }

    //This called when Activity started to fetch data from Firebase database
    public void loadChoosingMenu() {
        FirebaseRecyclerOptions<ChoosingItem> options = new FirebaseRecyclerOptions.Builder<ChoosingItem>()
                .setQuery(choosingRef, ChoosingItem.class)
                .build();

        progressOverLay = findViewById(R.id.progress_overlay);
        progressOverLay.setVisibility(View.VISIBLE);


        Log.d(TAG, "Tell me if the progress bar visible or not");
        adapter = new FirebaseRecyclerAdapter<ChoosingItem, ChoosingViewHolder>(options) {


            @Override
            protected void onBindViewHolder(@NonNull ChoosingViewHolder viewHolder, int position, @NonNull ChoosingItem model) {
                viewHolder.choosingFacultyButton.setText(model.getNameOfFaculty());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.choosingImage);


                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Start Food Details Activity
                        Intent foodIntent = new Intent(getApplicationContext(), MainActivity.class);
                        //ChoosingItemId is a key, so we need to get the Key of item ..
                        foodIntent.putExtra("ChoosingItemId", adapter.getRef(position).getKey());
                        startActivity(foodIntent);
                    }
                });
            }

            @NonNull
            @Override
            public ChoosingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.choosing_item_ui, parent, false);
                return new ChoosingViewHolder(itemView);
            }
        };

        progressOverLay.setVisibility(View.INVISIBLE);

        adapter.startListening();
        recycler_menu.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        recycler_menu.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.startListening();
        recycler_menu.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, HomeActivity.class));
    }
}
