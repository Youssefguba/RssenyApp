package com.rsseny.student.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ornach.nobobutton.NoboButton;
import com.rsseny.student.Model.Common;
import com.rsseny.student.R;

import es.dmoral.toasty.Toasty;


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

            if (checkInternet()){
               Toasty.error(
                    this,
                    "اعمل باقة الاول عشان تعرف تتفرج بطلوا بخل بقى  \uD83D\uDE21 \uD83D\uDE12 ",
                    Toasty.LENGTH_LONG,
                    false)
                    .show();
            }
        }

    private boolean checkInternet() {
        if (Common.isConnectionToInternet(this)) {
            knowMorebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent knowMoreIntent = new Intent(HomeActivity.this, FacultiesActivity.class);
                    knowMoreIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(knowMoreIntent);
                    finish();
                }
            });

            consultationbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent consultationIntent = new Intent(HomeActivity.this, askingActivity.class);
                    consultationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(consultationIntent);
                    finish();

                }
            });
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkInternet()){
            Toasty.error(
                    this,
                    "اعمل باقة الاول عشان تعرف تتفرج بطلوا بخل بقى  \uD83D\uDE21 \uD83D\uDE12 ",
                    Toasty.LENGTH_LONG,
                    false)
                    .show();

        }

    }
}