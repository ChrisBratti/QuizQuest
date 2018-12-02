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

public class LoginActivity extends AppCompatActivity {

    EditText userEmail;
    EditText userPassword;
    Button login;
    Button signup;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Login");

        userEmail = findViewById(R.id.et_userEmail);
        userPassword = findViewById(R.id.et_userPassword);
        login = findViewById(R.id.btn_userLogin);
        signup = findViewById(R.id.btn_userSignup);
        auth = FirebaseAuth.getInstance();

//        Part 1: Login (10 points)
//        This is the launcher screen of you app. The wireframe is shown in Figure 1(a). The
//        requirements are as follows:
//        1. The user should provide their email and password. The provided credentials should
//        be used to authenticate the user using firebase login. Clicking the “Login” button
//        should submit the login information to firebase to verify the user’s credentials.
//        a) If the user is successfully logged in then start the Contacts List Screen, and
//        finish the Login Screen.
//        b) If the user is not successfully logged in, then show a toast message indicating
//        that the login was not successful.
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(userEmail == null || userPassword == null) {
                        Toast.makeText(LoginActivity.this, "Fields cannot be empty!!", Toast.LENGTH_SHORT).show();
                    } else {
                        auth.signInWithEmailAndPassword(userEmail.getText().toString(), userPassword.getText().toString())
                                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            FirebaseUser user = auth.getCurrentUser();
                                            if (user == null) {
                                                return;
                                            } else {
                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });

//        2. Clicking the “Sign Up” button should start the Signup Screen Figure 1(b), and finish
//        the login Screen.
            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Explicit Intent to open sign up activity when signup is clicked
                    Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                    startActivity(i);
                    finish();
                }
            });

    }
}