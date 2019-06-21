package com.rsseny.student;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ornach.nobobutton.NoboButton;

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
                Intent knowMoreIntent = new Intent();
                startActivity(knowMoreIntent);
            }
        });

        consultationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent consultationIntent = new Intent();
                startActivity(consultationIntent);
            }
        });

    }
}
