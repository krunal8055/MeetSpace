package com.example.meetspace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public Toolbar toolbar;
    TextView Emp_Id, Emp_Name, Designation;
    String id, first_name, last_name, designation;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    public DrawerLayout HomedrawerLayout;
    public NavigationView HomeNavigationView;
    public NavController navController;
    String TokenID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main2);
        firebaseAuth = FirebaseAuth.getInstance();
        setupNavigation();
        GetToakenID();
    }


    private void setupNavigation() {
        //Toolbar SetUp
        setdata_in_drawer_layout();
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
        navController = Navigation.findNavController(this, R.id.home_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, HomedrawerLayout);
        NavigationUI.setupWithNavController(HomeNavigationView, navController);
        HomeNavigationView.setNavigationItemSelectedListener(this);

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
                if (dataSnapshot.exists()) {
                    id = dataSnapshot.child(UID).child("Emp_id").getValue().toString();
                    first_name = dataSnapshot.child(UID).child("Firstname").getValue().toString();
                    last_name = dataSnapshot.child(UID).child("Lastname").getValue().toString();
                    designation = dataSnapshot.child(UID).child("Designation").getValue().toString();
                    Emp_Id.setText(id);
                    Emp_Name.setText(first_name + " " + last_name);
                    Designation.setText(designation);
                    System.out.println("ID=" + id + "\n" + "Name=" + first_name + " " + last_name + "\n" + "Designation=" + designation);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void GetToakenID() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("User");

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (task.isSuccessful()) {
                    String uid = firebaseUser.getUid();
                    TokenID = task.getResult().getToken();
                    databaseReference.child(uid).child("Tokenid").setValue(TokenID);
                } else {
                    Toast.makeText(Main2Activity.this, (CharSequence) task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public boolean onSupportNavigateUp() {
        setdata_in_drawer_layout();
        //ON Fragment change hide Keyboard
        InputMethodManager keyboard = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.hideSoftInputFromWindow(HomeNavigationView.getWindowToken(), 0);
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.home_fragment), HomedrawerLayout) || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (HomedrawerLayout.isDrawerOpen(GravityCompat.START)) {
            HomedrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void DeleteToken() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        String uid = firebaseUser.getUid();
        databaseReference.child(uid).child("Tokenid").removeValue();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        item.setCheckable(true);
        HomedrawerLayout.closeDrawers();
        int id = item.getItemId();
        switch (id) {
            case R.id.home_nav_drawer:
                    navController.navigate(R.id.action_homepage_self);
                    break;
            case R.id.edit_profile_nav_drawer:
                navController.navigate(R.id.action_homepage_to_edit_Profile);
                break;
            case R.id.my_booking_nav_draw:
                navController.navigate(R.id.action_homepage_to_myBookingPage);
                break;
            case R.id.notification_nav_draw:
                //navController.navigate(R.id.action_homepage_to_noitifcationPage);
                Intent intent_notification = new Intent(this, NotificationPage.class);
                startActivity(intent_notification);
                //finish();
                break;
            case R.id.about_us_nav_draw:
                navController.navigate(R.id.action_homepage_to_about_us_page);
                break;
            case R.id.log_out_nav_draw:
                DeleteToken();
                firebaseAuth.signOut();
                Intent intent_signout = new Intent(this, MainActivity.class);
                startActivity(intent_signout);
                finish();
                break;
        }
        return false;
    }
}
