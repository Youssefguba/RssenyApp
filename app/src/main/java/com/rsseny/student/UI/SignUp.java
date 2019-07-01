package com.rsseny.student.UI;

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
import com.rsseny.student.R;

import es.dmoral.toasty.Toasty;

public class SignUp extends AppCompatActivity {

    EditText nameField, emailField, passwordField, phoneNumberField;
    NoboButton submitSignUpbtn;

    ProgressDialog progressDialog;
    DatabaseReference userRef;
    FirebaseDatabase database;
    private FirebaseAuth mAuth;

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
            nameField.requestFocus();
            Toasty.error(this, "معقولة انت نسيت اسمك ولا ايه \uD83D\uDE01", Toast.LENGTH_LONG, false).show();

            return;
        }

        if (email_Field.isEmpty()) {
            emailField.requestFocus();
            Toasty.error(this, "انت شكلك كده نسيت تكتب الايميل \uD83D\uDE01", Toast.LENGTH_LONG, false).show();
            return;
        }


        if (!Patterns.EMAIL_ADDRESS.matcher(email_Field).matches()) {
            emailField.requestFocus();
            Toasty.error(this, "اكتب ايميل صح من فضلك لو مفهاش ازعاج يعني \uD83D\uDE01", Toast.LENGTH_LONG, false).show();
            return;
        }


        if (phone_Field.isEmpty()) {
            phoneNumberField.requestFocus();
            Toasty.error(this, "انت مش فاكر رقم تلفونك ولا ايه \uD83D\uDE01", Toast.LENGTH_LONG, false).show();
            return;
        }

        if (password_Field.isEmpty()) {
            passwordField.requestFocus();
            Toasty.error(this, "معقولة يعني فيه ايميل من غير باسوورد \uD83D\uDE01", Toast.LENGTH_LONG, false).show();

            return;
        }


        if (password_Field.length() < 8) {
            passwordField.requestFocus();
            Toasty.error(this, "اقل حاجة 8 حروف او ارقام مش كل شوية هفهمك انا \uD83D\uDE01", Toast.LENGTH_LONG, false).show();
            return;
        }


        progressDialog = new ProgressDialog(SignUp.this);
        progressDialog.show();
        progressDialog.setMessage("استنى شوية لحد ما نعملك اكونت \uD83D\uDE01");

        final String email = emailField.getText().toString().trim();
        final String password = passwordField.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();

                if (task.isSuccessful()) {
                    writeNewUser(task.getResult().getUser());
                    finish();
                    Toasty.success(getApplicationContext(), "مبروك عليك الإيميل يا كبير \uD83D\uDE0D \uD83D\uDE01", Toast.LENGTH_LONG,false).show();

                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toasty.error(getApplicationContext(), "انت مسجل قبل كده يا معلم \uD83D\uDE12", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }

    // [START basic_write]
    private void writeNewUser(FirebaseUser user) {

        String email_Field = emailField.getText().toString().trim();
        String password_Field = passwordField.getText().toString().trim();
        String phone_Field = phoneNumberField.getText().toString().trim();
        String name_Field = nameField.getText().toString().trim();

        User newUser = new User(name_Field, email_Field, phone_Field, password_Field);

        userRef.child(user.getUid()).setValue(newUser);
    }
    // [END basic_write]


}
