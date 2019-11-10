package com.example.meetspace.MyBookingTab;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetspace.ListAdapters.MyBookingAdapter;
import com.example.meetspace.ModelClass.MyBooking;
import com.example.meetspace.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Completed_Booking_Tab extends Fragment {
    Context context;
    RecyclerView completedBooking_recycler;
    ArrayList<MyBooking> booking_complete_list = new ArrayList<>();
    MyBookingAdapter myBookingAdapter;
    Boolean aBoolean;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public Completed_Booking_Tab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_completed__booking__tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        aBoolean = false;
        context = getActivity().getApplicationContext();
        completedBooking_recycler = view.findViewById(R.id.completedBooking_recycler);
        completedBooking_recycler.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        myBookingAdapter = new MyBookingAdapter(booking_complete_list,context,aBoolean);
        completedBooking_recycler.setAdapter(myBookingAdapter);
        getCompletedBooking();
    }

    private void getCompletedBooking() {
        //Current Date and Time Get Start
        Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");
        final SimpleDateFormat time_format = new SimpleDateFormat("hh:mm");
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        int mm = calendar.get(Calendar.MONTH)+1;
        int yy = calendar.get(Calendar.YEAR);
        int hh = calendar.get(Calendar.HOUR_OF_DAY);
        int MM = calendar.get(Calendar.MINUTE);
        final String current_time = hh+":"+MM;
        final String current_date = dd+"/"+mm+"/"+yy;

        Date d1 = null;
        try {
            d1 = date_format.parse(current_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date t1 = null;
        try {
            t1 = time_format.parse(current_time);
            Log.i("T1",t1.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Current Date and Time Get End

        firebaseAuth =FirebaseAuth.getInstance();
        String UID = firebaseAuth.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final Date finalD = d1;
        final Date finalT = t1;
        databaseReference = firebaseDatabase.getReference().child("User").child(UID).child("MyBooking");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    for(DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        String booking_id = ds.getKey();
                        String room = ds.child("Roomno").getValue().toString();
                        String date = ds.child("Bookingdate").getValue().toString();
                        String start = ds.child("Start_time").getValue().toString();
                        String end = ds.child("End_time").getValue().toString();
                        String reason = ds.child("Booking_reason").getValue().toString();
                        String no_person = ds.child("No_of_person").getValue().toString();
                        // Log.i("Data",room+"/n"+date+"/n"+start+"/n"+end);

                        Date d2 = null;
                        try {
                            d2 = date_format.parse(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        Date t2 = null;
                        try {
                            t2 = time_format.parse(end);
                            Log.i("T2",t2.toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if(d2.compareTo(finalD) == 0) {
                            if(t2.compareTo(finalT)<0)
                            {
                                booking_complete_list.add(new MyBooking(booking_id,reason,no_person,room,  date, start, end));
                            }
                        }
                        else if(d2.compareTo(finalD) < 0)
                        {
                            booking_complete_list.add(new MyBooking(booking_id,reason,no_person,room,  date,  start,  end));
                        }


                        //booking_complete_list.add(new MyBooking(room, date, start, end));
                    }
                    myBookingAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context,databaseError.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
