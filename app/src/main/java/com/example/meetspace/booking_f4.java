package com.example.meetspace;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class booking_f4 extends Fragment implements View.OnClickListener {
    NavController navController;
    ProgressBar progressBar;
    Button BookRoomButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    Context context;
    SharedPreferences sharedPreferences;
    TextView Roomno,Bookingdate,Bookingstart,Bookingend,Bookingreason,NoOfPerson;

    public booking_f4() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking_f4, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        context = getActivity().getApplicationContext();

        //Init TextField
        Roomno = view.findViewById(R.id.room_no_cf_booking);
        Bookingdate = view.findViewById(R.id.booking_date_cf_booking);
        Bookingstart = view.findViewById(R.id.start_time_cf_booking);
        Bookingend = view.findViewById(R.id.end_time_cf_booking);
        Bookingreason = view.findViewById(R.id.reason_cf_booking);
        NoOfPerson = view.findViewById(R.id.no_person_cf_booking);
        progressBar = view.findViewById(R.id.progress_bar_done);
        BookRoomButton = view.findViewById(R.id.cf_button_booking_4);




        BookRoomButton.setOnClickListener(this);
        getDataFromSharedPref();

    }

    private void getDataFromSharedPref() {
        progressBar.setVisibility(View.VISIBLE);
        sharedPreferences = getActivity().getSharedPreferences("BookingData",context.MODE_PRIVATE);
        String roomno = sharedPreferences.getString("Roomno","");
        String date = sharedPreferences.getString("BookingDate","");
        String start = sharedPreferences.getString("StartTime","");
        String end = sharedPreferences.getString("EndTime","");
        String reason = sharedPreferences.getString("Reason","");
        String no_of_person = sharedPreferences.getString("NoOfPerson","");
        Roomno.setText(roomno);
        Bookingdate.setText(date);
        Bookingstart.setText(start);
        Bookingend.setText(end);
        Bookingreason.setText(reason);
        NoOfPerson.setText(no_of_person);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        if(view == BookRoomButton)
        {
            progressBar.setVisibility(View.VISIBLE);
            bookRoom();
            progressBar.setVisibility(View.GONE);
            navController.navigate(R.id.action_booking_f4_to_booking_done);
        }
    }

    private void bookRoom()
    {
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        String UID;
        UID = user.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("User").child(UID);
        databaseReference.child("MyBooking").child(UID+Roomno.getText().toString()).child("Roomno").setValue(Roomno.getText().toString());
        databaseReference.child("MyBooking").child(UID+Roomno.getText().toString()).child("Bookingdate").setValue(Bookingdate.getText().toString());
        databaseReference.child("MyBooking").child(UID+Roomno.getText().toString()).child("Start_time").setValue(Bookingstart.getText().toString());
        databaseReference.child("MyBooking").child(UID+Roomno.getText().toString()).child("End_time").setValue(Bookingend.getText().toString());
        databaseReference.child("MyBooking").child(UID+Roomno.getText().toString()).child("Booking_reason").setValue(Bookingreason.getText().toString());
        databaseReference.child("MyBooking").child(UID+Roomno.getText().toString()).child("No_of_person").setValue(NoOfPerson.getText().toString());
    }
}
