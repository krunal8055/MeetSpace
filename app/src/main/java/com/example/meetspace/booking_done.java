package com.example.meetspace;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class booking_done extends Fragment implements View.OnClickListener {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    TextView Roomno,BookingRef;
    Button BackToHomeButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking_done, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        Roomno = view.findViewById(R.id.room_no_done);
        BookingRef = view.findViewById(R.id.booking_ref_done);
        BackToHomeButton = view.findViewById(R.id.back_to_home_button);
        Roomno.setText("");
        BookingRef.setText("");
        getBookingRef();
        BackToHomeButton.setOnClickListener(this);

    }

    public void getBookingRef()
    {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("User");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        String UID = firebaseUser.getUid();

        databaseReference.child(UID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    for(DataSnapshot ds:dataSnapshot.child("MyBooking").getChildren())
                    {
                        //Log.i("Booking Ref",ds.getKey());
                        //Log.i("Room no",);
                        String ref = ds.getKey();
                        BookingRef.setText(ref);
                        Roomno.setText(ds.child("Roomno").getValue().toString());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onClick(View view) {
        if(view == BackToHomeButton)
        {
            Intent i = new Intent(getContext(), Main2Activity.class);
            startActivity(i);
            getActivity().finish();
        }
    }
}
