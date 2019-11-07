package com.example.meetspace;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    public Toolbar toolbar;
    TextView Emp_Id,Emp_Name,Designation;
    String id,first_name,last_name,designation;
    FirebaseAuth  firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    public DrawerLayout HomedrawerLayout;
    public NavigationView HomeNavigationView;
    public NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        firebaseAuth = FirebaseAuth.getInstance();
        setupNavigation();

    }

    private void setupNavigation() {
        //Toolbar SetUp
        toolbar = findViewById(R.id.main2_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Meetspace");
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Drawer Layout Setup
        HomedrawerLayout = findViewById(R.id.home_drawer);
        HomeNavigationView = findViewById(R.id.home_navigationview);
        //Home Navigation Drawer TextView
        Emp_Id = HomeNavigationView.getHeaderView(0).findViewById(R.id.user_emp_id_nav_drw);
        Emp_Name = HomeNavigationView.getHeaderView(0).findViewById(R.id.user_name_nav_drw);
        Designation = HomeNavigationView.getHeaderView(0).findViewById(R.id.user_designation_nav_drw);
        setdata_in_drawer_layout();

        navController = Navigation.findNavController(this, R.id.home_fragment);
        NavigationUI.setupActionBarWithNavController(this,navController,HomedrawerLayout);
        NavigationUI.setupWithNavController(HomeNavigationView,navController);

        HomeNavigationView.setNavigationItemSelectedListener(this);
        //EditProfile_btn.setOnClickListener(this);

    }

    private void setdata_in_drawer_layout() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        final String UID = firebaseUser.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("User");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    id = dataSnapshot.child(UID).child("Emp_id").getValue().toString();
                    first_name = dataSnapshot.child(UID).child("Firstname").getValue().toString();
                    last_name = dataSnapshot.child(UID).child("Lastname").getValue().toString();
                    designation = dataSnapshot.child(UID).child("Designation").getValue().toString();
                    Emp_Id.setText(id);
                    Emp_Name.setText(first_name+" "+last_name);
                    Designation.setText(designation);
                    System.out.println("ID="+id+"\n"+"Name="+first_name+" "+last_name+"\n"+"Designation="+designation);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.home_fragment),HomedrawerLayout);
    }

    @Override
    public void onBackPressed() {
        if(HomedrawerLayout.isDrawerOpen(GravityCompat.START))
        {
            HomedrawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
                super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setCheckable(true);
        HomedrawerLayout.closeDrawers();
        int id = item.getItemId();
        switch (id)
        {
            case R.id.home_nav_drawer:
                navController.navigate(R.id.action_homepage_self);
                break;
            case R.id.log_out_nav_draw:
                firebaseAuth.signOut();
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.my_booking_nav_draw:
                navController.navigate(R.id.action_homepage_to_myBookingPage);
                break;
            case R.id.edit_profile_nav_drawer:
                navController.navigate(R.id.action_homepage_to_edit_Profile);
                break;
        }
        return false;
    }
}
