package com.rsseny.student;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ornach.nobobutton.NoboButton;
import com.rsseny.student.Model.User;

import org.json.JSONObject;

import java.util.Arrays;


public class FirstPage extends AppCompatActivity {

    private static final String EMAIL = "email";
    private static final String NAME = "name";
    private static final String BIRTHDAY = "user_birthday";
    private static final String GENDER = "user_gender";
    private static final String USER_LINK = "user_link";


    CallbackManager callbackManager;
    ProgressDialog progressDialog;

    LoginButton fbutton;
    NoboButton signUpButton;

    FirebaseDatabase database;
    DatabaseReference userRef;

    String email, birthday, gender, name, userLink;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);


        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("Users");

        signUpButton = findViewById(R.id.signup_btn_intent);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpIntent = new Intent(FirstPage.this, SignUp.class);
                startActivity(signUpIntent);

            }
        });




        callbackManager = CallbackManager.Factory.create();
        fbutton = findViewById(R.id.btn_facebook);
        fbutton.setReadPermissions(Arrays.asList(EMAIL, BIRTHDAY, GENDER));

        fbutton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                progressDialog = new ProgressDialog(FirstPage.this);
                progressDialog.setMessage("Logging with facebook...");
                progressDialog.show();

                String token = loginResult.getAccessToken().getToken();

                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        progressDialog.dismiss();

                        email = object.optString("email");
                        birthday = object.optString("birthday");
                        gender = object.optString("gender");
                    }
                });

                userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = new User (email, birthday, gender);
                        userRef.child(AccessToken.getCurrentAccessToken().getUserId()).setValue(user);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //Request Graph API
                Bundle parameters = new Bundle();
                parameters.putString("fields","id, name, email, birthday, gender");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }



    }
