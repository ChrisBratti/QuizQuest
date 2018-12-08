package com.example.owner.quizquest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements VerifyUserAPI.DataInterface{
    private static final int RC_SIGN_IN = 0000;
    public static final String NAME_KEY = "name";
    EditText email;
    EditText password;
    Button login;
    Button signup;
    ProgressDialog progressDialog;

    @Override
    protected void onStart() {
        super.onStart();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login);
        signup = findViewById(R.id.btnSignup);
        login = findViewById(R.id.btnLogin);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email == null || password == null){
                    Toast.makeText(LoginActivity.this, "Fields cannot be emtpy!", Toast.LENGTH_SHORT).show();
                }else{
                    progressDialog= new ProgressDialog(LoginActivity.this);
                    progressDialog.setTitle("Logging in...");
                    progressDialog.show();
                    new VerifyUserAPI(LoginActivity.this).execute(MainActivity.VERIFY_URL, email.getText().toString(), password.getText().toString());
                }

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void sendVerified(String valid) {
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        if(Boolean.parseBoolean(valid)){
            new VerifyUserAPI(LoginActivity.this).execute(MainActivity.GET_USER_INFO_URL, email.getText().toString());
        }else{
            Log.d("Test", "user not validated");
            Toast.makeText(this, "Could not validate user", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void sendName(String name) {
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        Log.d("Test", "send name has been called");

        if(name != null){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(NAME_KEY, name);
            startActivity(intent);
            finish();
        }else{
            Log.d("Test", "name is null");
            Toast.makeText(this, "Could not validate", Toast.LENGTH_SHORT).show();
        }
    }
}
