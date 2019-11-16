package com.example.meetspace;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.meetspace.ListAdapters.EventView_Adapter;
import com.example.meetspace.ModelClass.MyBooking;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Event_view extends Fragment {
    Context context;
TextView textView;
RecyclerView recyclerView;
EventView_Adapter eventView_adapter;
ArrayList<MyBooking> evenArrayList;
FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;
ProgressBar progressBar;
String RoomNO;
    public Event_view() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity().getApplicationContext();
        textView = view.findViewById(R.id.text_event);
        recyclerView = view.findViewById(R.id.event_list_recycler);
        evenArrayList = new ArrayList<>();
        evenArrayList.clear();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        eventView_adapter = new EventView_Adapter(evenArrayList,context);
        recyclerView.setAdapter(eventView_adapter);
        progressBar = view.findViewById(R.id.event_list_progress);
        if(evenArrayList.size() ==0)
        {
            textView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    public void getRoomno(String roomno)
    {
        RoomNO = roomno;
    }

    public void getdataFromDB(final String selectedDate) {
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        evenArrayList.clear();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("User");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    for(DataSnapshot ds:dataSnapshot.getChildren())
                    {
                        if(!ds.child("MyBooking").getChildren().toString().isEmpty())
                        {
                            for (DataSnapshot dataSnapshot1 : ds.child("MyBooking").getChildren())
                            {
                                progressBar.setVisibility(View.GONE);
                                String date = dataSnapshot1.child("Bookingdate").getValue().toString();
                                String room = dataSnapshot1.child("Roomno").getValue().toString();
                                //check available booking of Selected Date
                                if(date.equals(selectedDate) && room.equals(RoomNO))
                                {
                                    //Check for Selected Room
                                    textView.setVisibility(View.GONE);
                                        String Start = dataSnapshot1.child("Start_time").getValue().toString();
                                        String End = dataSnapshot1.child("End_time").getValue().toString();
                                        evenArrayList.add(new MyBooking(Start, End));
                                }

                                if(evenArrayList.size() == 0)
                                {
                                    textView.setVisibility(View.VISIBLE);
                                }

                            }
                        }
                    }
                    eventView_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
