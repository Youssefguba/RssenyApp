package com.rsseny.student.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.rsseny.student.R;

/**************************************************************
* Created by Yu-Guba (Techack)                                *
* This activity for Handle the Splash Screen                  *
* We use Thread here to handle mipmap with professional way   *
*                                                             *
**************************************************************/


public class splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        /* Create Thread that will sleep for 3 seconds **/
        Thread background = new Thread() {
            public void run() {
                try {
                    // Thread will sleep for 3 seconds
                    sleep(3*1000);

                    // After 3 seconds redirect to another intent
                    Intent i = new Intent(getBaseContext(), FirstPage.class);
                    startActivity(i);

                    //Remove activity
                    finish();
                } catch (Exception e) {}
            }
        };

        // start thread
        background.start();

    }
}
