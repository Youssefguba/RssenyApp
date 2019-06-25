package com.rsseny.student.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.rsseny.student.Model.Common;
import com.rsseny.student.Model.Videos;
import com.rsseny.student.R;
import com.rsseny.student.ViewHolder.VideosViewHolder;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    //Todo Don't forget the problem of navigation drawer it's not clickable
    //Todo Don't forget the title and logo of faculty in the @Toolbar


    FirebaseDatabase database;
    DatabaseReference vidRef;
    DatabaseReference facultyRef;
    FirebaseRecyclerAdapter<Videos, VideosViewHolder> adapter;

    RecyclerView recyclerView;
    Toolbar toolbar;

    private FloatingActionButton askBtn;
    private FloatingActionButton knowMoreBtn;

    String videosId = "";



    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("الكليات");
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();
        vidRef = database.getReference("Videos");
        facultyRef = database.getReference("Faculties");

        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));

        knowMoreBtn = findViewById(R.id.know_more_button);
        askBtn = findViewById(R.id.ask_nav);


        knowMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "ما هي دي نفس الصفحة اللى انت فيها يا ذكي ☺", Toast.LENGTH_LONG).show();
            }
        });

        askBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, askingActivity.class));

            }
        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        setSupportActionBar(toolbar);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        if (getIntent() != null ) {
            videosId = getIntent().getStringExtra("ChoosingItemId");
            if (!videosId.isEmpty()  && videosId != null) {
                if (Common.isConnectionToInternet(this)) {
                    loadVideosData(videosId);
                } else {
                    Toast.makeText(this, "ما تعمل باقة بقى عشان تعرف تتفرج على الفيديوهات ☺", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    private void loadVideosData(String videosId){

        // Load List of videos based on video category id
        Query loadData = vidRef.orderByChild("MenuId").equalTo(videosId);

        FirebaseRecyclerOptions<Videos> options = new FirebaseRecyclerOptions.Builder<Videos>()
                .setQuery(loadData, Videos.class)
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


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            drawer.openDrawer(GravityCompat.END);
        }

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.askNavigation) {

        } else if (id == R.id.knowMoreNav) {

        } else if (id == R.id.logOutNav) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }
}
