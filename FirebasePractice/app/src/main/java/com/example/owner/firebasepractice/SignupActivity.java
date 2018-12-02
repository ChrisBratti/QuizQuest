package com.example.owner.firebasepractice;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignupActivity extends AppCompatActivity {

    //in order to use FirebaseAuth, put implementation 'com.firebaseui:firebase-ui-auth:4.1.0'
    //in the Module:app
    private FirebaseAuth auth;

    EditText newFirstName;
    EditText newLastName;
    EditText newEmail;
    EditText newPassword;
    EditText newConfirm;
    Button newSignup;
    Button newCancel;

    String fName;
    String lName;
    String email;
    String password;
    String confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        setTitle("Sign Up");

        newFirstName = findViewById(R.id.et_firstName);
        newLastName = findViewById(R.id.et_lastName);
        newEmail = findViewById(R.id.et_email);
        newPassword = findViewById(R.id.et_password);
        newConfirm = findViewById(R.id.et_confirm);
        newSignup = findViewById(R.id.btn_signup);
        newCancel = findViewById(R.id.btn_cancel);
        auth = FirebaseAuth.getInstance();



        newSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if all the fields are filled in and if the passwords match, set these values
                if(newFirstName != null && newLastName != null && newEmail != null && newPassword != null && newConfirm != null) {

                    fName = newFirstName.getText().toString();
                    lName = newLastName.getText().toString();
                    email = newEmail.getText().toString();
                    password = newPassword.getText().toString();
                    confirm = newConfirm.getText().toString();

                    if(password.equals(confirm)){
                        //create a new user
                        auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            FirebaseUser user = auth.getCurrentUser();
                                            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                                    .setDisplayName(fName + " " + lName)
                                                    .build();
                                            user.updateProfile(profileChangeRequest);
                                            Intent i = new Intent(SignupActivity.this, MainActivity.class);
                                            startActivity(i);
                                            finish();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignupActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }
}
