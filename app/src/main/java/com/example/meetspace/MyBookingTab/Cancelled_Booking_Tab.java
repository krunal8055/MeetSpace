package com.example.meetspace.MyBookingTab;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meetspace.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Cancelled_Booking_Tab extends Fragment {


    public Cancelled_Booking_Tab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cancelled__booking__tab, container, false);
    }

}
