package com.example.meetspace.MyBookingTab;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meetspace.ListAdapters.MyBookingAdapter;
import com.example.meetspace.ModelClass.MyBooking;
import com.example.meetspace.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Cancelled_Booking_Tab extends Fragment {
    RecyclerView cancelled_booking_recycler;
    ArrayList<MyBooking> Cancle_booking_list = new ArrayList<>();
    MyBookingAdapter myBookingAdapter;
    Context context;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference dbref;
    String uid;
    Boolean aBoolean = false;
    TextView textView;
    ProgressBar progressBar;

    public Cancelled_Booking_Tab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cancelled__booking__tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = view.findViewById(R.id.nil_canclled_text);
        progressBar = view.findViewById(R.id.progress_bar_cancelled_booking);
        //Recycler View
        Cancle_booking_list.clear();
        cancelled_booking_recycler = view.findViewById(R.id.cancelledBooking_recycler);
        cancelled_booking_recycler.setLayoutManager(new LinearLayoutManager(context));
        myBookingAdapter = new MyBookingAdapter(Cancle_booking_list,context,aBoolean);
        cancelled_booking_recycler.setAdapter(myBookingAdapter);
        getRejectedBookingFromDB();
    }

    private void getRejectedBookingFromDB()
    {
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        dbref = firebaseDatabase.getReference().child("User");

        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    progressBar.setVisibility(View.GONE);
                    for(DataSnapshot main: dataSnapshot.getChildren())
                    {
                        if (!main.getKey().equals(uid))
                        {
                            for (DataSnapshot ds1 : main.child("MyBooking").getChildren())
                            {
                                for (DataSnapshot ds2 : ds1.child("MyInviteList").getChildren())
                                {
                                    if(ds2.child("Invitation_Status").getValue().toString().equals("Rejected"))
                                    {
                                        if(ds2.child("Reciever_UID").getValue().toString().equals(uid)) {
                                            String bookingID = ds1.getKey();
                                            String room = ds1.child("Roomno").getValue().toString();
                                            String date = ds1.child("Bookingdate").getValue().toString();
                                            String start = ds1.child("Start_time").getValue().toString();
                                            String end = ds1.child("End_time").getValue().toString();
                                            String reason = ds1.child("Booking_reason").getValue().toString();
                                            String no_person = ds1.child("No_of_person").getValue().toString();

                                            textView.setVisibility(View.GONE);
                                            cancelled_booking_recycler.setVisibility(View.VISIBLE);
                                            Cancle_booking_list.add(new MyBooking(bookingID, reason, no_person, room, date, start, end));
                                        }
                                    }
                                }
                                myBookingAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                    if(Cancle_booking_list.size()==0)
                    {
                        progressBar.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                        cancelled_booking_recycler.setVisibility(View.GONE);
                    }
                }
                else {
                    progressBar.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);
                    cancelled_booking_recycler.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context,"Database Fetching Error"+databaseError.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
