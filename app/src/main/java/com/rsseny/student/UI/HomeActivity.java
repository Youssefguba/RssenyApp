package com.rsseny.student.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ornach.nobobutton.NoboButton;
import com.rsseny.student.R;


/*
* This activity is like a "Link or Bridge" between
 * SignIn Activity(FirstPage) and Faculties or Consultation Activities..
 *
* */
public class HomeActivity extends AppCompatActivity {

    NoboButton knowMorebtn, consultationbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        knowMorebtn = findViewById(R.id.faculties_button);
        consultationbtn = findViewById(R.id.consultation_button);


        knowMorebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent knowMoreIntent = new Intent(HomeActivity.this, FacultiesActivity.class);
                startActivity(knowMoreIntent);
            }
        });

        consultationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent consultationIntent = new Intent(HomeActivity.this, askingActivity.class);
                startActivity(consultationIntent);
            }
        });

    }
}
