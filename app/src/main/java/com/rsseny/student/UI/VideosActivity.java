package com.rsseny.student.UI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rsseny.student.Model.Common;
import com.rsseny.student.Model.Videos;
import com.rsseny.student.R;
import com.rsseny.student.ViewHolder.VideosViewHolder;

import java.util.Vector;

public class VideosActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference vidRef;
    FirebaseRecyclerAdapter<Videos, VideosViewHolder> adapter;

    RecyclerView recyclerView;

    Vector<Videos> videosVector = new Vector<>();
    String videosId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        database = FirebaseDatabase.getInstance();
        vidRef = database.getReference("Videos");

        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));

        if (getIntent() != null ) {
            videosId = getIntent().getStringExtra("ChoosingItemId");
            if (!videosId.isEmpty()  && videosId != null) {
                if (Common.isConnectionToInternet(this)) {
                    loadVideosData();
                } else {
                    Toast.makeText(this, "ما تعمل باقة بقى عشان تعرف تتفرج على الفيديوهات ☺", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void loadVideosData(){
        FirebaseRecyclerOptions<Videos> options = new FirebaseRecyclerOptions.Builder<Videos>()
                .setQuery(vidRef, Videos.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Videos, VideosViewHolder>(options) {

            @SuppressLint("SetJavaScriptEnabled")
            @Override
            protected void onBindViewHolder(@NonNull VideosViewHolder videosViewHolder, int i, @NonNull Videos videos) {
                videosViewHolder.nameOfVideo.setText(videos.getnameOfVideo());
                videosViewHolder.videoItem.getSettings().setJavaScriptEnabled(true);
                videosViewHolder.videoItem.loadData(videos.getvideoLink(),"text/html","utf-8");
            }

            @NonNull
            @Override
            public VideosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.video_item_ui, parent, false);
                return  new VideosViewHolder(view);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }


}
