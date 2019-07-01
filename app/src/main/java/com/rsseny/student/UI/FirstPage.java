package com.rsseny.student.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ornach.nobobutton.NoboButton;
import com.rsseny.student.Model.User;
import com.rsseny.student.R;

import org.json.JSONObject;

import java.util.Arrays;

import es.dmoral.toasty.Toasty;


public class FirstPage extends AppCompatActivity {

    private static final String EMAIL = "email";
    private static final String TAG = "FirstPage";

    CallbackManager callbackManager;
    ProgressDialog progressDialog;

    LoginButton fbutton;
    NoboButton signUpButton, signInButton;

    FirebaseDatabase database;
    DatabaseReference userRef;
    private FirebaseAuth mAuth;


    String email, id, name;

    EditText emailEditText, passwordEditText;


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
        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.email_editText);
        passwordEditText = findViewById(R.id.password_editText);


        signUpButton = findViewById(R.id.signup_btn_intent);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpIntent = new Intent(FirstPage.this, SignUp.class);
                startActivity(signUpIntent);

            }
        });

        signInButton = findViewById(R.id.signin_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });




        callbackManager = CallbackManager.Factory.create();
        fbutton = findViewById(R.id.btn_facebook);
        fbutton.setReadPermissions(Arrays.asList(EMAIL, "public_profile"));


        fbutton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());

                progressDialog = new ProgressDialog(FirstPage.this);
                progressDialog.setMessage("Logging with facebook...");
                progressDialog.show();



                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        progressDialog.dismiss();

                        email = object.optString("email");
                        name = object.optString("name");
                        id = object.optString("id");
                    }
                });



                userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = new User (id,name, email);
                        userRef.child(AccessToken.getCurrentAccessToken().getUserId()).setValue(user);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //Request Graph API
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");

                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();

                finish();
                Intent intent = new Intent(FirstPage.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }


            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    /* This method run when press on Sign In Button (تسجيل الدخول ) then check if the user leave
    *  any field empty or not and give him error message to attract his attention.
    *  Secondly: When the user entered all the fields, then check if user existed on firebase server
    *  or not to enter..
    *
    *
    * */
    private void signIn() {
         String email_Field = emailEditText.getText().toString().trim();
         String password_Field = passwordEditText.getText().toString().trim();


        if (email_Field.isEmpty()) {
            emailEditText.setError("انت شكلك كده نسيت تكتب الايميل \uD83D\uDE01");
            emailEditText.requestFocus();
            return;
        }


        if (!Patterns.EMAIL_ADDRESS.matcher(email_Field).matches()) {
            emailEditText.setError("اكتب ايميل صح بعد اذنك \uD83D\uDE01");
            emailEditText.requestFocus();
            return;
        }


        if (password_Field.isEmpty()) {
            passwordEditText.setError("انت نسيت الباسوورد ولا ايه \uD83D\uDE01");
            passwordEditText.requestFocus();
            return;
        }


        progressDialog = new ProgressDialog(FirstPage.this);
        progressDialog.show();
        progressDialog.setMessage("اصبر شوية بنشوف الميل بتاعك راح فين \uD83D\uDE01");

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            finish();
                            Intent intent = new Intent(FirstPage.this, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                        } else {
                            Toast.makeText(FirstPage.this, " ياعم ما تتأكد من الايميل والباسوورد بقا ومتقرفناش \uD83D\uDE01", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toasty.error(FirstPage.this, "اتأكد من الايميل والباسوورد بدل ما اتعصب عليك \uD83D\uDE01",
                                    Toast.LENGTH_SHORT, true).show();
                        }

                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();


        //Check if user Signed in or not and logging in directly
        if (mAuth.getCurrentUser() != null) {
            finish();
            Intent intent = new Intent(FirstPage.this, HomeActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, FirstPage.class);
        startActivity(intent);
    }

}
