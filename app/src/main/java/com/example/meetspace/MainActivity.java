package com.example.meetspace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.graphics.Color;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    public NavController navController;
    private Toolbar main_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(main_toolbar);
        main_toolbar.setTitleTextColor(Color.WHITE);
        navController = Navigation.findNavController(this, R.id.login_fragment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}