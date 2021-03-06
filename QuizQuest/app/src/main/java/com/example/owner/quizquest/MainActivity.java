package com.example.owner.quizquest;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener, ProfileFragment.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener{
    public final static String BASE_URL = "https://shielded-meadow-34727.herokuapp.com/";
    public final static String PREF_NAME = "preferences";
    public final static String VERIFY_URL = "api/verify_login_API/";
    public final static String GET_CLASS_URL = "api/getClassesForStudent/";
    public final static String GET_USER_INFO_URL = "api/get_user_info_API/";
    public final static String GET_CLASS_QUIZZES_URL = "api/get_class_quiz_ids_API/";
    public final static String GET_STUDENT_CLASSES_URL = "api/get_student_classes_API/";

    ListView classDisplay;
    TextView nameDisplay;
    TextView navViewName;
    private DrawerLayout drawerLayout;
    private String name;
    private String email;

    @Override
    protected void onStart() {
        super.onStart();
    }

    ArrayList<Class> classes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setNavigationViewListner();

        drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navName = headerView.findViewById(R.id.tvMenuName);

        name = getIntent().getStringExtra(LoginActivity.NAME_KEY);
        email = getIntent().getStringExtra(LoginActivity.EMAIL_KEY);

        navName.setText(name);

        MainFragment f = new MainFragment();
        getFragmentManager().beginTransaction()
                .add(R.id.FragmentDisplay, f, "eval_fragment")
                .commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if(menuItem.getItemId() == R.id.nav_profile){
            ProfileFragment f = new ProfileFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.FragmentDisplay, f, "profile_fragment")
                    .commit();
        }else if(menuItem.getItemId() == R.id.nav_home){
            MainFragment f = new MainFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.FragmentDisplay, f, "main_fragment")
                    .commit();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }

    private void setNavigationViewListner(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public String getName(){
        return name;
    }

    public String getEmail() {return email;}
}
