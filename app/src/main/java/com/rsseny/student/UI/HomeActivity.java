package com.rsseny.student.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ornach.nobobutton.NoboButton;
import com.rsseny.student.Model.Common;
import com.rsseny.student.Model.User;
import com.rsseny.student.R;

import es.dmoral.toasty.Toasty;


/*
* This activity is like a "Link or Bridge" between
 * SignIn Activity(FirstPage) and Faculties or Consultation Activities..
 *
* */
public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    NoboButton knowMorebtn, consultationbtn;
    TextView nameOfUser;

    FirebaseDatabase database;
    DatabaseReference userRef;

    FirebaseAuth mAuth;
    String userID = "";
    String idUser = "";
    String userFBID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

            //Firebase Init
            database = FirebaseDatabase.getInstance();
            userRef = database.getReference("Users");
            mAuth = FirebaseAuth.getInstance();

            //Views Init
            knowMorebtn = findViewById(R.id.faculties_button);
            consultationbtn = findViewById(R.id.consultation_button);
            nameOfUser = findViewById(R.id.nameOfUser);

            // Get Name of the User..
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nameOfUser = findViewById(R.id.nameOfUser);

                // We equaled userID variable with Common.mCurrent.getUid to get the name of user
                // with the uid from the Table of user.

                //When Sign up
                if (Common.mCurrentUser != null) {
                    userID = Common.mCurrentUser.getUid();
                    //We get a snapshot of the data to read the name of user.
                    User userInfo = dataSnapshot.child(userID).getValue(User.class);
                    nameOfUser.setText(userInfo.getName());

                    Log.d(TAG, "There is the UID " + userInfo.getUid());
                    Log.d(TAG, "There is the Name " + userInfo.getName());

                    //When Login with Facebook
                } else if (AccessToken.getCurrentAccessToken().getUserId() != null){
                    userFBID = AccessToken.getCurrentAccessToken().getUserId();
                    //We get a snapshot of the data to read the name of user.
                    User userInfo = dataSnapshot.child(userFBID).getValue(User.class);
                    nameOfUser.setText(userInfo.getName());

                    Log.d(TAG, "There is the UID " + userInfo.getUid());
                    Log.d(TAG, "There is the Name " + userInfo.getName());
                }
                    //When Login with mail
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