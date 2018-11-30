package com.example.owner.quizquest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText firstname;
    EditText lastname;
    EditText email;
    EditText password;
    EditText confirmPassword;
    TextView passMatchDisplay;
    boolean passMatch;
    Button cancel;
    Button createAccount;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firstname = findViewById(R.id.etFirstname);
        lastname = findViewById(R.id.etLastname);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        confirmPassword = findViewById(R.id.etConfirmPassword);
        passMatchDisplay = findViewById(R.id.tvPassMatch);
        cancel = findViewById(R.id.btnCancel);
        createAccount = findViewById(R.id.btnCreateAccount);
        mAuth = FirebaseAuth.getInstance();


        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstname != null || lastname != null || email != null || password != null || confirmPassword != null){
                    final String fname = firstname.getText().toString();
                    final String lname = lastname.getText().toString();
                    String uEmail = email.getText().toString();
                    String pass = password.getText().toString();
                    mAuth.createUserWithEmailAndPassword(uEmail, pass)
                            .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(fname + " " + lname)
                                                .build();
                                        user.updateProfile(profileUpdates);
                                        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignupActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(SignupActivity.this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
                }


            }
        });

        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passMatchDisplay.setVisibility(View.VISIBLE);
                if(isPassMatch()){
                    passMatchDisplay.setText("Passwords Match!");
                    passMatchDisplay.setTextColor(Color.GREEN);

                }else{
                    passMatchDisplay.setText("Passwords do not match!");
                    passMatchDisplay.setTextColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private boolean isPassMatch(){
        if(confirmPassword.getText().toString().equals(password.getText().toString())){
            return true;
        }else{
            return false;
        }
    }
}
