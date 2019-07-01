package com.rsseny.student.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.rsseny.student.Model.Common;
import com.rsseny.student.Model.Videos;
import com.rsseny.student.R;
import com.rsseny.student.ViewHolder.VideosViewHolder;

import es.dmoral.toasty.Toasty;

import static androidx.core.view.GravityCompat.RELATIVE_LAYOUT_DIRECTION;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public static final int END = RELATIVE_LAYOUT_DIRECTION | GravityCompat.END;


    FirebaseDatabase database;
    DatabaseReference vidRef;
    DatabaseReference facultyRef;
    FirebaseRecyclerAdapter<Videos, VideosViewHolder> adapter;

    RecyclerView recyclerView;
    Toolbar toolbar;

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private FloatingActionButton askBtn;
    private FloatingActionButton knowMoreBtn;

    String videosId = "";



    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Navigation and Toolbar func and Init..
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toolbar.setTitle("الكليات");
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);


        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        //Firebase init
        database = FirebaseDatabase.getInstance();
        vidRef = database.getReference("Videos");
        facultyRef = database.getReference("Faculties");

        //Recycler Init
        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));

        //Buttons Init
        knowMoreBtn = findViewById(R.id.know_more_button);
        askBtn = findViewById(R.id.ask_nav);

        knowMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    recreate();
            }
        });

        askBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, askingActivity.class));

            }
        });

            // Get data from firebase according to choosing of item..
        if (getIntent() != null ) {
            videosId = getIntent().getStringExtra("ChoosingItemId");
            if (!videosId.isEmpty()  && videosId != null) {
                if (Common.isConnectionToInternet(this)) {
                    loadVideosData(videosId);
                } else {
                    Toasty.error(this,
                                 "اعمل باقة الاول عشان تعرف تتفرج بطلوا بخل بقى  \uD83D\uDE21 \uD83D\uDE12 ",
                                  Toasty.LENGTH_LONG,
                                 false)
                            .show();
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
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent (this, HomeActivity.class));
        } else if (id == R.id.askNavigation) {
            startActivity(new Intent (this, askingActivity.class));

        } else if (id == R.id.knowMoreNav) {
            recreate();

        } else if (id == R.id.logOutNav) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, FirstPage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }


}
