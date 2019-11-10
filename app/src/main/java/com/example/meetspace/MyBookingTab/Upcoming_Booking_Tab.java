package com.example.meetspace.MyBookingTab;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class Upcoming_Booking_Tab extends Fragment {
    RecyclerView UpcomingBooking_recycler;
    ArrayList<MyBooking> myBookingArrayList = new ArrayList<>();
    MyBookingAdapter myBookingAdapter;
    Context context;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Boolean aBoolean = true;
    boolean flag = true;
    public Upcoming_Booking_Tab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming__booking_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity().getApplicationContext();
        UpcomingBooking_recycler = view.findViewById(R.id.upcoming_Booking_recycler);
        UpcomingBooking_recycler.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        myBookingAdapter = new MyBookingAdapter(myBookingArrayList,context, aBoolean);
        UpcomingBooking_recycler.setAdapter(myBookingAdapter);
        /*((SimpleItemAnimator) UpcomingBooking_recycler.getItemAnimator()).setSupportsChangeAnimations(false);
        UpcomingBooking_recycler.setHasFixedSize(true);*/
        getBookingsFromDB();

    }




    private void getBookingsFromDB() {
        //Current Date and Time Get Start
        Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");
        final SimpleDateFormat time_format = new SimpleDateFormat("hh:mm");
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        final int mm = calendar.get(Calendar.MONTH) + 1;
        int yy = calendar.get(Calendar.YEAR);
        int hh = calendar.get(Calendar.HOUR_OF_DAY);
        int MM = calendar.get(Calendar.MINUTE);
        final String current_time = hh + ":" + MM;
        final String current_date = dd + "/" + (mm) + "/" + yy;
        //Current Date and Time Get End
        Date d1 = null;
        try {
            d1 = date_format.parse(current_date);
            Log.i("D1", d1.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date t1 = null;
        try {
            t1 = time_format.parse(current_time);
            Log.i("T1", t1.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Current Date and Time Get End

        firebaseAuth = FirebaseAuth.getInstance();
        String UID = firebaseAuth.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("User").child(UID).child("MyBooking");
        final Date finalD = d1;
        final Date finalT = t1;
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String bookingID = ds.getKey();
                        String room = ds.child("Roomno").getValue().toString();
                        String date = ds.child("Bookingdate").getValue().toString();
                        String start = ds.child("Start_time").getValue().toString();
                        String end = ds.child("End_time").getValue().toString();
                        String reason = ds.child("Booking_reason").getValue().toString();
                        String no_person = ds.child("No_of_person").getValue().toString();
                        //MyBooking myBooking = new MyBooking(reason,no_person);

                        Date d2 = null;
                        try {
                            d2 = date_format.parse(date);
                            //Log.i("D2", d2.toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        Date t2 = null;
                        try {
                            t2 = time_format.parse(start);
                            // Log.i("T2",t2.toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        Date t22 = null;
                        try {
                            t22 = time_format.parse(end);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        if (d2.compareTo(finalD) == 0) {
                            if (t2.compareTo(finalT) > 0) {
                                myBookingArrayList.add(new MyBooking(bookingID, reason, no_person, room, date, start, end));
                            } else if (t22.compareTo(finalD) < 0) {
                                myBookingArrayList.add(new MyBooking(bookingID, reason, no_person, room, date, start, end));
                            }
                        } else if (d2.compareTo(finalD) > 0) {
                            myBookingArrayList.add(new MyBooking(bookingID, reason, no_person, room, date, start, end));
                        }

                        // Log.i("Data",room+"/n"+date+"/n"+start+"/n"+end);

                    }
                    myBookingAdapter.notifyDataSetChanged();
                    myBookingAdapter.setOnClickListner(onClickListener);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
       public View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                int position = viewHolder.getAdapterPosition();
            }
        };


}
