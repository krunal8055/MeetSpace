package com.example.meetspace.MyBookingTab;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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

public class Upcoming_Booking_Tab extends Fragment {
    MyBooking myBooking = new MyBooking();
    RecyclerView UpcomingBooking_recycler;
    ArrayList<MyBooking> myBookingArrayList = new ArrayList<>();
    MyBookingAdapter myBookingAdapter;
    Context context;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    String uid;
    DatabaseReference databaseReference,dbref;
    Boolean aBoolean = true;
    TextView textView;


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
    Date d1 = null;
    Date t1 = null;
    //Current Date and Time Get End

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
        UpcomingBooking_recycler.setLayoutManager(new LinearLayoutManager(context));
        myBookingAdapter = new MyBookingAdapter(myBookingArrayList,context, aBoolean);
        UpcomingBooking_recycler.setAdapter(myBookingAdapter);
        textView = view.findViewById(R.id.nil_upcoming_text);
        if(myBookingArrayList.size() == 0)
        {
            UpcomingBooking_recycler.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
            myBookingAdapter.notifyDataSetChanged();
        }
        getcurrentDateAndTimeObj();
        getAcceptedBookingFromDB();
        getBookingsFromDB();

    }

    public void getcurrentDateAndTimeObj()
    {
        try {
            d1 = date_format.parse(current_date);
            Log.i("D1", d1.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        try {
            t1 = time_format.parse(current_time);
            Log.i("T1", t1.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    private void getAcceptedBookingFromDB()
    {
        final Date finalD = d1;
        final Date finalT = t1;
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

                    for(DataSnapshot main: dataSnapshot.getChildren())
                    {

                        if (!main.getKey().equals(uid))
                        {

                            for (DataSnapshot ds1 : main.child("MyBooking").getChildren())
                            {

                                for (DataSnapshot ds2 : ds1.child("MyInviteList").getChildren())
                                {
                                        if(ds2.child("Invitation_Status").getValue().toString().equals("Accepted"))
                                        {
                                            if(ds2.child("Reciever_UID").getValue().toString().equals(uid)) {
                                                String bookingID = ds1.getKey();
                                                String room = ds1.child("Roomno").getValue().toString();
                                                String date = ds1.child("Bookingdate").getValue().toString();
                                                String start = ds1.child("Start_time").getValue().toString();
                                                String end = ds1.child("End_time").getValue().toString();
                                                String reason = ds1.child("Booking_reason").getValue().toString();
                                                String no_person = ds1.child("No_of_person").getValue().toString();


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
                                                        UpcomingBooking_recycler.setVisibility(View.VISIBLE);
                                                        textView.setVisibility(View.GONE);
                                                        myBookingArrayList.add(new MyBooking(bookingID, reason, no_person, room, date, start, end));
                                                    } else if (t22.compareTo(finalD) < 0) {
                                                        textView.setVisibility(View.GONE);
                                                        UpcomingBooking_recycler.setVisibility(View.VISIBLE);
                                                        myBookingArrayList.add(new MyBooking(bookingID, reason, no_person, room, date, start, end));
                                                    }
                                                } else if (d2.compareTo(finalD) > 0) {
                                                    textView.setVisibility(View.GONE);
                                                    UpcomingBooking_recycler.setVisibility(View.VISIBLE);
                                                    myBookingArrayList.add(new MyBooking(bookingID, reason, no_person, room, date, start, end));
                                                }


                                                textView.setVisibility(View.GONE);
                                                UpcomingBooking_recycler.setVisibility(View.VISIBLE);
                                                myBookingArrayList.add(new MyBooking(bookingID, reason, no_person, room, date, start, end));
                                            }
                                        }
                                }
                                myBookingAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context,"Database Fetching Error"+databaseError.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void getBookingsFromDB() {
        final Date finalD = d1;
        final Date finalT = t1;
        firebaseAuth = FirebaseAuth.getInstance();
        String UID = firebaseAuth.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("User").child(UID).child("MyBooking");

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
                                UpcomingBooking_recycler.setVisibility(View.VISIBLE);
                                textView.setVisibility(View.GONE);
                                myBookingArrayList.add(new MyBooking(bookingID, reason, no_person, room, date, start, end));
                            } else if (t22.compareTo(finalD) < 0) {
                                textView.setVisibility(View.GONE);
                                UpcomingBooking_recycler.setVisibility(View.VISIBLE);
                                myBookingArrayList.add(new MyBooking(bookingID, reason, no_person, room, date, start, end));
                            }
                        } else if (d2.compareTo(finalD) > 0) {
                            textView.setVisibility(View.GONE);
                            UpcomingBooking_recycler.setVisibility(View.VISIBLE);
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
                Toast.makeText(context,"Database Fetching Error"+databaseError.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }
       public View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                //int position = viewHolder.getAdapterPosition();
            }
        };


}
