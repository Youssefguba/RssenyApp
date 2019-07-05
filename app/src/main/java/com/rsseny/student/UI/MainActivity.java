package com.rsseny.student.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.AccessToken;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rsseny.student.Model.Common;
import com.rsseny.student.Model.User;
import com.rsseny.student.Model.Videos;
import com.rsseny.student.R;
import com.rsseny.student.ViewHolder.VideosViewHolder;


import es.dmoral.toasty.Toasty;

import static androidx.core.view.GravityCompat.END;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    //Firebase Def
    FirebaseDatabase database;
    DatabaseReference vidRef;
    DatabaseReference facultyRef;
    DatabaseReference userRef;
    FirebaseRecyclerAdapter<Videos, VideosViewHolder> adapter;
    FirebaseAuth mAuth;

    RecyclerView recyclerView;
    Toolbar toolbar;

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private FloatingActionButton askBtn;
    private FloatingActionButton knowMoreBtn;

    String videosId = "";
    String userID = "";
    String idUser = "";
    String userFBID = "";

    TextView nameOfUser;

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
        navigationView.setNavigationItemSelectedListener(this);
        toggle.syncState();


        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        //Firebase init
        database = FirebaseDatabase.getInstance();
        vidRef = database.getReference("Videos");
        facultyRef = database.getReference("Faculties");
        userRef = database.getReference("Users");
        mAuth = FirebaseAuth.getInstance();



        //Recycler Init
        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));

        //Views Init
        knowMoreBtn = findViewById(R.id.know_more_button);
        askBtn = findViewById(R.id.ask_nav);

        View headerView = navigationView.getHeaderView(0);
        nameOfUser = headerView.findViewById(R.id.nameOfUser);


        //Buttons onClickListener Method
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

        // Get Name of the User..
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                View headerView = navigationView.getHeaderView(0);
                nameOfUser = headerView.findViewById(R.id.nameOfUser);


                    //This implement when the first time used
                if (Common.mCurrentUser != null) {
                    userID = Common.mCurrentUser.getUid();

                    // We equaled userID variable with Common.mCurrent.getUid to get the name of user
                    // with the uid from the Table of user.

                    //We get a snapshot of the data to read the name of user.
                    User userInfo = dataSnapshot.child(userID).getValue(User.class);
                    nameOfUser.setText(userInfo.getName());

                    Log.d(TAG, "There is the UID " + userInfo.getUid());
                    Log.d(TAG, "There is the Name " + userInfo.getName());

                    // This implement when login or Sign up with Facebook
                } else if (AccessToken.getCurrentAccessToken().getUserId() != null){
                    userFBID = AccessToken.getCurrentAccessToken().getUserId();
                    //We get a snapshot of the data to read the name of user.
                    User userInfo = dataSnapshot.child(userFBID).getValue(User.class);
                    nameOfUser.setText(userInfo.getName());

                    Log.d(TAG, "There is the UID " + userInfo.getUid());
                    Log.d(TAG, "There is the Name " + userInfo.getName());
                }
                    //This implement when the user login with normal way of mail .. second time using and forth
                else {
                    idUser = mAuth.getCurrentUser().getUid();
                    //We get a snapshot of the data to read the name of user.
                    User userInfo = dataSnapshot.child(idUser).getValue(User.class);
                    nameOfUser.setText(userInfo.getName());

                    Log.d(TAG, "There is the UID " + userInfo.getUid());
                    Log.d(TAG, "There is the Name " + userInfo.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        // Get data from firebase according to choosing of item..
        if (getIntent() != null) {
            videosId = getIntent().getStringExtra("ChoosingItemId");
            if (!videosId.isEmpty() && videosId != null) {
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

    private void loadVideosData(String videosId) {

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
                videosViewHolder.videoItem.loadData(videos.getvideoLink(), "text/html", "utf-8");

            }

            @NonNull
            @Override
            public VideosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.video_item_ui, parent, false);
                return new VideosViewHolder(view);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(END)) {
            drawer.closeDrawer(END);
        } else {
            drawer.openDrawer(END);

            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent(this, HomeActivity.class));
        } else if (id == R.id.askNavigation) {
            startActivity(new Intent(this, askingActivity.class));

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
        drawer.closeDrawer(END);


        return true;
    }


}
