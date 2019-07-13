package com.rsseny.student.UI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ornach.nobobutton.NoboButton;
import com.rsseny.student.Model.Mentor;
import com.rsseny.student.R;
import com.rsseny.student.ViewHolder.MentorViewHolder;
import com.squareup.picasso.Picasso;

public class askingActivity extends AppCompatActivity {

    public TextView titleOfMentor, descriptionOfMentor, priceOfMentor;
    public NoboButton chooseButtonOfDescription;
    public View show_details_of_mentor;
    public AlertDialog.Builder builder;
    public LayoutInflater inflater;

    FirebaseDatabase database;
    DatabaseReference mentorsRef;
    FirebaseRecyclerAdapter<Mentor, MentorViewHolder> adapter;

    FloatingActionButton askBtn;
    FloatingActionButton knowMoreBtn;

    RecyclerView recyclerView;
    ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asking);

        database = FirebaseDatabase.getInstance();
        mentorsRef = database.getReference("Mentors");
        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_forward_black_24dp);
            actionBar.setDisplayShowHomeEnabled(true);
        }


        askBtn = findViewById(R.id.askingButton);
        knowMoreBtn = findViewById(R.id.know_more_button);

        askBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
        knowMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(askingActivity.this, MainActivity.class));
            }
        });
        fetchDataOfMentors();
    }

    // This method to call data from Firebase Database
    private void fetchDataOfMentors() {
        FirebaseRecyclerOptions<Mentor> options = new FirebaseRecyclerOptions.Builder<Mentor>()
                .setQuery(mentorsRef, Mentor.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Mentor, MentorViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MentorViewHolder mentorViewHolder, int i, @NonNull final Mentor mentor) {
                mentorViewHolder.nameOfMentor.setText(mentor.getName());
                mentorViewHolder.priceOfMentor.setText(mentor.getCost());
                mentorViewHolder.descriptionOfMentor.setText(mentor.getDescription());
                mentorViewHolder.detailsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDetailsOfMentor();
                        titleOfMentor.setText(mentor.getDescription());
                        descriptionOfMentor.setText(mentor.getDetails());
                        priceOfMentor.setText(mentor.getCost());
                    }
                });
                Picasso.with(getBaseContext())
                        .load(mentor.getPhoto())
                        .into(mentorViewHolder.photoOfMentor);

                mentorViewHolder.chooseButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String url = "https://www.facebook.com/Rsseny/";
                        Intent websiteIntent = new Intent(Intent.ACTION_VIEW);
                        websiteIntent.setData(Uri.parse(url));
                        startActivity(websiteIntent);
                    }
                });
            }

            @NonNull
            @Override
            public MentorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.mentor_item_ui, parent, false);
                return new MentorViewHolder(view);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }


    // This called when pressed on المزيد Button to show details of Mentor in Dialog.
    private void showDetailsOfMentor() {
        builder = new AlertDialog.Builder(this);
        inflater = this.getLayoutInflater();
        show_details_of_mentor = inflater.inflate(R.layout.description_of_metor, null);
        titleOfMentor = show_details_of_mentor.findViewById(R.id.title_of_mentor);
        descriptionOfMentor = show_details_of_mentor.findViewById(R.id.description_of_mentor);
        priceOfMentor = show_details_of_mentor.findViewById(R.id.price_of_mentor);
        chooseButtonOfDescription = show_details_of_mentor.findViewById(R.id.chooseButtonOfMentor);

        builder.setView(show_details_of_mentor);
        builder.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(askingActivity.this, HomeActivity.class));
    }
}
