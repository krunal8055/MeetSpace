package com.example.meetspace;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;


public class MyBookingPage extends Fragment {

    public TabLayout tableLayout;
    public NavController navController;
    Context context;

    public MyBookingPage() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        //tabLayout2.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPause() {
        super.onPause();
        //tableLayout.setVisibility(View.GONE);
    }

//tableLayout.setVisibility(View.VISIBLE);
    // tableLayout.setVisibility(View.GONE);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_booking_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity().getApplicationContext();
        navController = Navigation.findNavController(getActivity(),R.id.my_booking_tabs);
        tableLayout = view.findViewById(R.id.tab_layout);
        tableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition())
                {
                    case 0:
                        navController.navigate(R.id.upcoming_Booking_Tab);
                        break;
                    case 1:
                        navController.navigate(R.id.completed_Booking_Tab);
                        break;
                    case 2:
                        navController.navigate(R.id.cancelled_Booking_Tab);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
