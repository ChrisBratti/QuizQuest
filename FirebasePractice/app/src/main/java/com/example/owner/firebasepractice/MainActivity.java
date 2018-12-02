package com.example.owner.firebasepractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button createNew;
    Button contactsLogout;
    ListView listView;

    FirebaseUser currentUser;
    //implementation in gradle implementation 'com.google.firebase:firebase-database:16.0.1" (firebase needs to match on the numbers)
    private DatabaseReference mDatabase;
    ValueEventListener postListener;
    //ContactAdapter adapter;
    //private ArrayList<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Contacts");

        createNew = findViewById(R.id.btn_createContact);
        contactsLogout = findViewById(R.id.btn_logout);
        listView = findViewById(R.id.lv_contacts);
    }
}
