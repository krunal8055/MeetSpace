package com.example.meetspace;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class room_booking_f1 extends Fragment implements View.OnClickListener {
    Button next_button_1;
    public NavController navController;
    public room_booking_f1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_room_booking_f1, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        next_button_1 = view.findViewById(R.id.next_button_f1);
        navController = Navigation.findNavController(view);
        next_button_1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == next_button_1)
        {
            navController.navigate(R.id.action_room_booking_f1_to_room_booking_f2);
        }
    }
}
