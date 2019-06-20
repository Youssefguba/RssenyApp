package com.rsseny.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ornach.nobobutton.NoboButton;
import com.rsseny.student.Model.User;

public class SignUp extends AppCompatActivity {

    EditText nameField, emailField, passwordField, phoneNumberField;
    NoboButton submitSignUpbtn;

    ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    DatabaseReference userRef;
    FirebaseDatabase database;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameField = findViewById(R.id.userName_editText);
        emailField = findViewById(R.id.email_editText);
        passwordField = findViewById(R.id.password_editText);
        phoneNumberField = findViewById(R.id.phoneNumber_editText);

        //Init Firebase
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("Users");
        mAuth = FirebaseAuth.getInstance();


        submitSignUpbtn = findViewById(R.id.signup_submit);
        submitSignUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validForm();
            }
        });

    }

    private void validForm() {
        final String email_Field = emailField.getText().toString().trim();
        final String password_Field = passwordField.getText().toString().trim();
        final String phone_Field = phoneNumberField.getText().toString().trim();
        final String name_Field = nameField.getText().toString().trim();

        if (email_Field.isEmpty()) {
            emailField.setError("انت شكلك كده نسيت تكتب الايميل");
            emailField.requestFocus();
            return;
        }


        if (!Patterns.EMAIL_ADDRESS.matcher(email_Field).matches()) {
            emailField.setError("اكتب ايميل صح بعد اذنك");
            emailField.requestFocus();
            return;
        }


        if (name_Field.isEmpty()) {
            passwordField.setError("معقولة انت نسيت اسمك ولا ايه :)");
            passwordField.requestFocus();
            return;
        }

        if (phone_Field.isEmpty()) {
            passwordField.setError("انت مش فاكر رقم تلفونك ولا ايه :)");
            passwordField.requestFocus();
            return;
        }

        if (password_Field.isEmpty()) {
            passwordField.setError("انت نسيت الباسوورد ولا ايه :)");
            passwordField.requestFocus();
            return;
        }


        if (password_Field.length() < 8) {
            passwordField.setError("اقل حاجة 8 حروف او ارقام يا كبير");
            passwordField.requestFocus();
            return;
        }

        registerUser(email_Field, name_Field, password_Field, phone_Field);
    }

    private void registerUser (final String email, final String name, final String password, final String phone) {

        progressDialog = new ProgressDialog(SignUp.this);
        progressDialog.show();
        progressDialog.setMessage("استنى شوية لحد ما نعملك اكونت :)");


        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();

                if (task.isSuccessful()) {
                    userRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            User new_user = new User(name, email, phone, password);

                            userRef.child(user.getUid()).setValue(new_user);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {}

                    });

                    finish();
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "انت مسجل قبل كده يا معلم :)", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

}
