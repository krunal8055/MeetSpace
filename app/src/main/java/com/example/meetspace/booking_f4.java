package com.example.meetspace;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


public class booking_f4 extends Fragment implements View.OnClickListener {
    NavController navController;
    ProgressBar progressBar;
    Button BookRoomButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String UID;
    Context context;
    SharedPreferences sharedPreferences,inviteList_token,inviteList_ruid,extraresource;
    TextView Roomno,Bookingdate,Bookingstart,Bookingend,Bookingreason,NoOfPerson;
    String roomno,date,start,end,reason,no_of_person;
    Bundle bundle;
    Long time = System.currentTimeMillis();
    String time_string = time.toString();
    String InviteMessage,CurrentUserName;

    final Booking_info booking_info = new Booking_info();
    ArrayList<Booking_info> bookinglist = new ArrayList<>();
    boolean data_exist = false;

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
        GetInfoCurrentUser();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        UID = user.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        checkExistingBooking();

        BookRoomButton.setOnClickListener(this);
        getDataFromSharedPref();

    }

    private void getDataFromSharedPref() {
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);//Block UI
        sharedPreferences = getActivity().getSharedPreferences("BookingData",context.MODE_PRIVATE);

        roomno = sharedPreferences.getString("Roomno","");
        date = sharedPreferences.getString("BookingDate","");
        start = sharedPreferences.getString("StartTime","");
        end = sharedPreferences.getString("EndTime","");
        reason = sharedPreferences.getString("Reason","");
        no_of_person = sharedPreferences.getString("NoOfPerson","");

        Roomno.setText(roomno);
        Bookingdate.setText(date);
        Bookingstart.setText(start);
        Bookingend.setText(end);
        Bookingreason.setText(reason);
        NoOfPerson.setText(no_of_person);
        progressBar.setVisibility(View.GONE);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);//UnBlock UI
    }
    @Override
    public void onClick(View view) {
        if(view == BookRoomButton)
        {

            bookRoom();
        }
    }
    public void checkExistingBooking()
    {
        databaseReference = firebaseDatabase.getReference().child("User");
        databaseReference.child(UID).child("MyBooking").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {

                    for(DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        bookinglist.add(new Booking_info(ds.child("Bookingdate").getValue().toString(),
                                ds.child("Start_time").getValue().toString(),
                                ds.child("End_time").getValue().toString(),
                                ds.child("Roomno").getValue().toString(),
                                ds.child("Booking_reason").getValue().toString(),
                                ds.child("No_of_person").getValue().toString()));

                        //Log.i("Booking_List_Room",bookinglist.get(0).getRoomNO());
                        // toget value from List converting value from object to jsonarray
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context,"Something is wrong with DB. Try Again!",Toast.LENGTH_SHORT).show();
            }
        });
    }
//Booking Process
    private void bookRoom()
    {
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);//Block UI

            databaseReference = firebaseDatabase.getReference().child("User").child(UID);
            //Check for Fields are filled or empty on Bookingf1
            if(roomno != "" && date != "" && start != "" && end != "" && reason != "" && no_of_person != "" ) {
                //checkExistingBooking();
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);//UnBlock UI
                //checking for duplicate booking
                for (int i=0;i<bookinglist.size();i++){
                    if(bookinglist.get(i).getRoomNO().equals(roomno)&&
                            bookinglist.get(i).getStartTime().equals(start)&&
                            bookinglist.get(i).getEndTime().equals(end)&&
                            bookinglist.get(i).getBookingDate().equals(date)){
                        data_exist = true;
                        break;
                    }
                }
                if(!data_exist){

                    //Toast.makeText(context, "Its Unique.", Toast.LENGTH_SHORT).show();
                    databaseReference.child("MyBooking").child(time_string).child("Roomno").setValue(Roomno.getText().toString());
                    databaseReference.child("MyBooking").child(time_string).child("Bookingdate").setValue(Bookingdate.getText().toString());
                    databaseReference.child("MyBooking").child(time_string).child("Start_time").setValue(Bookingstart.getText().toString());
                    databaseReference.child("MyBooking").child(time_string).child("End_time").setValue(Bookingend.getText().toString());
                    databaseReference.child("MyBooking").child(time_string).child("Booking_reason").setValue(Bookingreason.getText().toString());
                    databaseReference.child("MyBooking").child(time_string).child("No_of_person").setValue(NoOfPerson.getText().toString());
                    InviteListData();
                    ExtraResourceData();
                    progressBar.setVisibility(View.GONE);
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);//UnBlock UI
                    navController.navigate(R.id.action_booking_f4_to_booking_done);
                }
                else {
                    progressBar.setVisibility(View.GONE);
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);//UnBlock UI
                    Toast.makeText(context, "You Already Booked This Room!", Toast.LENGTH_SHORT).show();
                }

            }
            else
            {
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);//UnBlock UI
                Toast.makeText(context,"Null Values Found.Can not Book Room!",Toast.LENGTH_SHORT).show();
            }
    }

    public void InviteListData()
    {

        databaseReference = firebaseDatabase.getReference().child("User").child(UID).child("MyBooking").child(time_string);
        inviteList_token = getActivity().getSharedPreferences("InviteList_Token",context.MODE_PRIVATE);
        Map<String,?> allInvitedUser_token = inviteList_token.getAll();
        for(Map.Entry<String,?> entry_token:allInvitedUser_token.entrySet())
        {
            //Log.i("key",entry_token.getKey());
            //Log.i("value", String.valueOf(entry_token.getValue()));

            databaseReference.child("MyInviteList").child(entry_token.getKey()).child("Invited_User_token_id").setValue(entry_token.getValue().toString());
            InviteMessage = CurrentUserName+" Invited you for "+Bookingreason.getText().toString() +" in Room no "+Roomno.getText().toString()+" On Date "+Bookingdate.getText().toString()+" From "+Bookingstart.getText().toString()+" To "+Bookingend.getText().toString()+".";
            databaseReference.child("MyInviteList").child(entry_token.getKey()).child("Message").setValue(InviteMessage);
            databaseReference.child("MyInviteList").child(entry_token.getKey()).child("Invitation_Status").setValue("Pending");

        }
        inviteList_ruid = getActivity().getSharedPreferences("InviteList_RUID",context.MODE_PRIVATE);
        Map<String,?> allInvitedUser_UID = inviteList_ruid.getAll();
        for(Map.Entry<String,?> entry_uid:allInvitedUser_UID.entrySet())
        {
            databaseReference.child("MyInviteList").child(entry_uid.getKey()).child("Reciever_UID").setValue(entry_uid.getValue().toString());
        }
    }

    public void ExtraResourceData()
    {
        databaseReference = firebaseDatabase.getReference().child("User").child(UID).child("MyBooking").child(time_string);
        extraresource = getActivity().getSharedPreferences("ExtraResources",context.MODE_PRIVATE);
        Map<String,?> allExtraResources = extraresource.getAll();
        for(Map.Entry<String,?> entry:allExtraResources.entrySet())
        {
            databaseReference.child("ExtraResources").child(entry.getKey()).setValue(entry.getValue().toString());
        }
    }

    public void GetInfoCurrentUser()
    {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("User");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {

                    CurrentUserName= dataSnapshot.child(UID).child("Firstname").getValue().toString()+" "+dataSnapshot.child(UID).child("Lastname").getValue().toString();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
