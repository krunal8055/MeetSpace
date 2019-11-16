package com.example.meetspace.MyBookingTab;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meetspace.ListAdapters.MyBookingAdapter;
import com.example.meetspace.ModelClass.MyBooking;
import com.example.meetspace.ModelClass.Notification_data;
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
    ProgressBar progressBar;

    //Current Date and Time Get Start
    Calendar calendar = Calendar.getInstance();
    final SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");
    final SimpleDateFormat time_format = new SimpleDateFormat("hh:mm");
    int dd = calendar.get(Calendar.DAY_OF_MONTH);
    final int mm = calendar.get(Calendar.MONTH) + 1;
    int yy = calendar.get(Calendar.YEAR);
    int hh = calendar.get(Calendar.HOUR_OF_DAY);
    int MM = calendar.get(Calendar.MINUTE);
    final String current_time_s = hh + ":" + MM;
    final String current_date_s = dd + "/" + (mm) + "/" + yy;
    Date current_date_d = null;
    Date current_time_d = null;
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
        progressBar = view.findViewById(R.id.progress_bar_upcoming_booking);
        //RECYCLER VIEW SetUP
        UpcomingBooking_recycler = view.findViewById(R.id.upcoming_Booking_recycler);
        UpcomingBooking_recycler.setLayoutManager(new LinearLayoutManager(context));
        myBookingAdapter = new MyBookingAdapter(myBookingArrayList,context, aBoolean);
        UpcomingBooking_recycler.setAdapter(myBookingAdapter);

        textView = view.findViewById(R.id.nil_upcoming_text);
        getcurrentDateAndTimeObj();
        getAcceptedBookingFromDB();
        getBookingsFromDB();
    }

    public void getcurrentDateAndTimeObj()
    {
        try {
            current_date_d = date_format.parse(current_date_s);
            Log.i("D1", current_date_d.toString());
            current_time_d = time_format.parse(current_time_s);
            Log.i("T1", current_time_d.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    private void getAcceptedBookingFromDB()
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
                                            if(ds2.child("Reciever_UID").getValue().toString().equals(uid))
                                            {
                                                String bookingID = ds1.getKey();
                                                String room = ds1.child("Roomno").getValue().toString();
                                                String date = ds1.child("Bookingdate").getValue().toString();
                                                String start = ds1.child("Start_time").getValue().toString();
                                                String end = ds1.child("End_time").getValue().toString();
                                                String reason = ds1.child("Booking_reason").getValue().toString();
                                                String no_person = ds1.child("No_of_person").getValue().toString();

                                                Date booking_d = null;
                                                Date start_t = null;
                                                Date end_t = null;
                                                try {
                                                    booking_d = date_format.parse(date);
                                                    start_t = time_format.parse(start);
                                                    end_t = time_format.parse(end);
                                                }
                                                catch (ParseException e)
                                                {
                                                    e.printStackTrace();
                                                }

                                                if(booking_d.compareTo(current_date_d) > 0)
                                                {

                                                    progressBar.setVisibility(View.GONE);
                                                    textView.setVisibility(View.GONE);
                                                    UpcomingBooking_recycler.setVisibility(View.VISIBLE);
                                                    myBookingArrayList.add(new MyBooking(bookingID, reason, no_person, room, date, start, end));

                                                }
                                                else if(booking_d.compareTo(current_date_d) ==0)
                                                {
                                                    if(start_t.compareTo(current_time_d) > 0)
                                                    {
                                                        if(end_t.compareTo(current_time_d) < 0)
                                                        {
                                                            progressBar.setVisibility(View.GONE);
                                                            textView.setVisibility(View.GONE);
                                                            UpcomingBooking_recycler.setVisibility(View.VISIBLE);
                                                            myBookingArrayList.add(new MyBooking(bookingID, reason, no_person, room, date, start, end));
                                                        }
                                                        else
                                                        {
                                                            progressBar.setVisibility(View.GONE);
                                                            textView.setVisibility(View.GONE);
                                                            UpcomingBooking_recycler.setVisibility(View.VISIBLE);
                                                            myBookingArrayList.add(new MyBooking(bookingID, reason, no_person, room, date, start, end));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                }
                                myBookingAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                    if(myBookingArrayList.size() == 0)
                    {
                        progressBar.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                        UpcomingBooking_recycler.setVisibility(View.GONE);
                    }
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);
                    UpcomingBooking_recycler.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context,"Database Fetching Error"+databaseError.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void getBookingsFromDB()
    {
        progressBar.setVisibility(View.VISIBLE);
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

                        Date booking_d = null;
                        Date start_t = null;
                        Date end_t = null;
                        try {
                            booking_d = date_format.parse(date);
                            start_t = time_format.parse(start);
                            end_t = time_format.parse(end);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if(booking_d.compareTo(current_date_d) > 0)
                        {

                            progressBar.setVisibility(View.GONE);
                            textView.setVisibility(View.GONE);
                            UpcomingBooking_recycler.setVisibility(View.VISIBLE);
                            myBookingArrayList.add(new MyBooking(bookingID, reason, no_person, room, date, start, end));

                        }
                        else if(booking_d.compareTo(current_date_d) ==0)
                        {
                            if(start_t.compareTo(current_time_d) > 0)
                            {
                                if(end_t.compareTo(current_time_d) < 0)
                                {
                                    progressBar.setVisibility(View.GONE);
                                    textView.setVisibility(View.GONE);
                                    UpcomingBooking_recycler.setVisibility(View.VISIBLE);
                                    myBookingArrayList.add(new MyBooking(bookingID, reason, no_person, room, date, start, end));
                                }
                                else
                                {
                                    progressBar.setVisibility(View.GONE);
                                    textView.setVisibility(View.GONE);
                                    UpcomingBooking_recycler.setVisibility(View.VISIBLE);
                                    myBookingArrayList.add(new MyBooking(bookingID, reason, no_person, room, date, start, end));
                                }
                            }
                        }
                    }
                    myBookingAdapter.notifyDataSetChanged();
                    myBookingAdapter.setOnClickListner(onClickListener);
                    if(myBookingArrayList.size() == 0)
                    {
                        progressBar.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                        UpcomingBooking_recycler.setVisibility(View.GONE);
                    }
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);
                    UpcomingBooking_recycler.setVisibility(View.GONE);
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
