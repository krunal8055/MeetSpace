package com.example.meetspace;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class Booking_Activity extends AppCompatActivity {
    Toolbar toolbar;
    NavController navController;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_);
        toolbar = findViewById(R.id.booking_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Booking Room");
        navController = Navigation.findNavController(this, R.id.booking_fragment);
    }

   @Override
   public boolean onSupportNavigateUp() {
       onBackPressed();
       return true;
   }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
