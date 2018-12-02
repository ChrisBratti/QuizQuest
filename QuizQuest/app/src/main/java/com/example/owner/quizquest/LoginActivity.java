package com.example.owner.quizquest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements VerifyUserAPI.DataInterface{
    private static final int RC_SIGN_IN = 0000;
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
                    new VerifyUserAPI(LoginActivity.this).execute(email.getText().toString(), password.getText().toString());
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

   private void updateUI(FirebaseUser user){
        if(user == null){
            return;
        }else{
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
   }

    @Override
    public void sendVerified(boolean valid) {
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        if(valid){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this, "Could not validate user", Toast.LENGTH_SHORT).show();
        }
    }
}
