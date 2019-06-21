package com.rsseny.student;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ornach.nobobutton.NoboButton;
import com.rsseny.student.Model.User;

public class SignUp extends AppCompatActivity {

    EditText nameField, emailField, passwordField, phoneNumberField;
    NoboButton submitSignUpbtn;

    ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    DatabaseReference userRef;
    FirebaseDatabase database;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    String uid = currentUser != null ? currentUser.getUid() : null;


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
                signUp();
            }
        });

    }

    private void signUp() {

        final String email_Field = emailField.getText().toString().trim();
        final String password_Field = passwordField.getText().toString().trim();
        final String phone_Field = phoneNumberField.getText().toString().trim();
        final String name_Field = nameField.getText().toString().trim();

        if (name_Field.isEmpty()) {
            nameField.setError("معقولة انت نسيت اسمك ولا ايه ☺");
            nameField.requestFocus();
            return;
        }

        if (email_Field.isEmpty()) {
            emailField.setError("انت شكلك كده نسيت تكتب الايميل ☺");
            emailField.requestFocus();
            return;
        }


        if (!Patterns.EMAIL_ADDRESS.matcher(email_Field).matches()) {
            emailField.setError("اكتب ايميل صح من فضلك لو مفهاش ازعاج يعني ☺");
            emailField.requestFocus();
            return;
        }




        if (phone_Field.isEmpty()) {
            phoneNumberField.setError("انت مش فاكر رقم تلفونك ولا ايه ☺");
            phoneNumberField.requestFocus();
            return;
        }

        if (password_Field.isEmpty()) {
            passwordField.setError("معقولة يعني فيه ايميل من غير باسوورد ☺");
            passwordField.requestFocus();
            return;
        }


        if (password_Field.length() < 8) {
            passwordField.setError("اقل حاجة 8 حروف او ارقام مش كل شوية هفهمك انا ☺");
            passwordField.requestFocus();
            return;
        }


        progressDialog = new ProgressDialog(SignUp.this);
        progressDialog.show();
        progressDialog.setMessage("استنى شوية لحد ما نعملك اكونت ☺");

       final String email = emailField.getText().toString().trim();
       final String password = passwordField.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();

                if (task.isSuccessful()) {
                    writeNewUser(uid);
                    finish();
                    Toast.makeText(getApplicationContext(), "مبروك عليك الإيميل يا كبير ☺", Toast.LENGTH_SHORT).show();

                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "انت مسجل قبل كده يا معلم ☺", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    // [START basic_write]
        private void writeNewUser(String userId) {

            String email_Field = emailField.getText().toString().trim();
            String password_Field = passwordField.getText().toString().trim();
            String phone_Field = phoneNumberField.getText().toString().trim();
            String name_Field = nameField.getText().toString().trim();

        User newUser = new User(userId ,name_Field, email_Field, phone_Field, password_Field);

        userRef.child(uid).setValue(newUser);
    }
    // [END basic_write]


}
