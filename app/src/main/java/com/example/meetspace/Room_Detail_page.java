package com.example.meetspace;

import android.content.Intent;
import android.graphics.Color;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Room_Detail_page extends Fragment implements View.OnClickListener {
    TextView RoomNo,Catagory,FloorNo,Status,Capacity,SoftwareList,HardwareList;
    Button Check_Schedule,BookRoom;
    Bundle bundle;
    String SelectedRoom;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    NavController navController;
    public Room_Detail_page() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_room__detail_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        bundle = getArguments();
        SelectedRoom = bundle.getString("SelectedRoomNo");
        //RoomNo = view.findViewById(R.id.room_no_room_detail);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("RoomNo "+SelectedRoom);
        //RoomNo.setText(room_no);


        Catagory = view.findViewById(R.id.catagory_room_detail);
        FloorNo = view.findViewById(R.id.floor_room_detail);
        Status = view.findViewById(R.id.room_status_room_detail);
        Capacity = view.findViewById(R.id.capacity_room_detail);
        SoftwareList = view.findViewById(R.id.software_room_detail);
        HardwareList = view.findViewById(R.id.hardware_room_detail);
        Check_Schedule = view.findViewById(R.id.chk_schedule_room_detail);
        BookRoom = view.findViewById(R.id.booking_room_detail);

        getRoomDetails();

        Check_Schedule.setOnClickListener(this);
        BookRoom.setOnClickListener(this);

    }

    private void getRoomDetails() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Room");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String status = dataSnapshot.child(SelectedRoom).child("Roomstatus").getValue().toString();

                        if(status.contains("Booked"))
                        {
                            Status.setTextColor(Color.RED);
                            Status.setText(status);
                        }
                        else
                        {
                            Status.setTextColor(Color.GREEN);
                            Status.setText(status);
                        }
                        Catagory.setText(dataSnapshot.child(SelectedRoom).child("Roomcatagory").getValue().toString());
                        FloorNo.setText(dataSnapshot.child(SelectedRoom).child("Floorno").getValue().toString());
                        Capacity.setText(dataSnapshot.child(SelectedRoom).child("Roomcapacity").getValue().toString());

        }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == Check_Schedule)
        {
            navController.navigate(R.id.action_room_Detail_page_to_roomSchedule_page);
        }
        else if(view == BookRoom)
        {
            //navController.navigate(R.id.action_room_Detail_page_to_room_booking_f1);
            Intent i = new Intent(getContext(), Booking_Activity.class);
            startActivity(i);
            getActivity().finish();
        }

    }
}
