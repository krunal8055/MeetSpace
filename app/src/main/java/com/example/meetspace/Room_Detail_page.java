package com.example.meetspace;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;


public class Room_Detail_page extends Fragment implements View.OnClickListener {
    Context context;
    ImageView Room_Image;
    TextView Catagory,FloorNo,Status,Capacity,SoftwareList,HardwareList;
    Button Check_Schedule,BookRoom;
    Bundle bundle;
    String SelectedRoom;
    int roomno;
    int position = 0;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    NavController navController;
    ProgressBar progressBar;
    final AvailableResource_Room availableResource_room = new AvailableResource_Room();
    ArrayList<AvailableResource_Room> resourceRoomArrayList = new ArrayList<>();
    ArrayList<AvailableResource_Room> software_arraylist = new ArrayList<>();
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
        context = getActivity().getApplicationContext();
        progressBar = view.findViewById(R.id.progress_bar_room_detail);
        navController = Navigation.findNavController(view);

        bundle = getArguments();
        SelectedRoom = bundle.getString("SelectedRoomNo");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("RoomNo "+SelectedRoom);
        Room_Image = view.findViewById(R.id.room_img_room_detail);
        Catagory = view.findViewById(R.id.catagory_room_detail);
        FloorNo = view.findViewById(R.id.floor_room_detail);
        Status = view.findViewById(R.id.room_status_room_detail);
        Capacity = view.findViewById(R.id.capacity_room_detail);
        SoftwareList = view.findViewById(R.id.software_room_detail);
        HardwareList = view.findViewById(R.id.hardware_room_detail);
        Check_Schedule = view.findViewById(R.id.chk_schedule_room_detail);
        BookRoom = view.findViewById(R.id.booking_room_detail);
        HardwareList.setText("");
        HardwareList.setMovementMethod(new ScrollingMovementMethod());
        SoftwareList.setMovementMethod(new ScrollingMovementMethod());
        SoftwareList.setText("");
        getRoomDetails();

        Check_Schedule.setOnClickListener(this);
        BookRoom.setOnClickListener(this);

    }

    private void getRoomDetails() {
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Room");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    progressBar.setVisibility(View.GONE);
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    setRoomImage();
                    Status.setText(dataSnapshot.child(SelectedRoom).child("Roomstatus").getValue().toString());
                    Catagory.setText(dataSnapshot.child(SelectedRoom).child("Roomcatagory").getValue().toString());
                    FloorNo.setText(dataSnapshot.child(SelectedRoom).child("Floorno").getValue().toString());
                    Capacity.setText(dataSnapshot.child(SelectedRoom).child("Roomcapacity").getValue().toString());

                    for (DataSnapshot ds : dataSnapshot.child(SelectedRoom).child("Roomresource").getChildren())
                    {
                        //Log.i("ResourceName",ds.child("Resourcename").getValue().toString());
                        resourceRoomArrayList.add(new AvailableResource_Room("",ds.child("Resourcename").getValue().toString()));
                    }
                    for(int i=0;i<resourceRoomArrayList.size();i++)
                    {
                        //Log.i("Resource list",resourceRoomArrayList.get(i).getResourcename());
                        HardwareList.append(resourceRoomArrayList.get(i).getResourcename()+"\n");
                        //HardwareList.append("/n");
                    }
                }
        }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Toast.makeText(context,"Error:"+databaseError.toString(),Toast.LENGTH_SHORT).show();

            }
        });
        DatabaseReference db_ref = firebaseDatabase.getReference().child("Resource");
        db_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    progressBar.setVisibility(View.GONE);
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    for (DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        if(Integer.parseInt(ds.getKey()) >18)
                        {
                            //Log.i("Software",ds.child("Resourcename").getValue().toString());
                           software_arraylist.add(new AvailableResource_Room(ds.child("Resourcename").getValue().toString(),""));
                        }
                    }
                    for(int i=0;i<software_arraylist.size();i++)
                    {
                        //Log.i("Resource list",resourceRoomArrayList.get(i).getResourcename());
                        SoftwareList.append(software_arraylist.get(i).getName()+"\n");
                        //HardwareList.append("/n");
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Toast.makeText(context,"Error:"+databaseError.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRoomImage() {
        position = bundle.getInt("SelectedPosition");
        if(position % 5 == 0)
        {
            Room_Image.setImageResource(R.drawable.meeting_room_5);
        }
        else if(position % 5 == 1)
        {
            Room_Image.setImageResource(R.drawable.meeting_room_4);
        }
        else if(position % 5 == 2)
        {
            Room_Image.setImageResource(R.drawable.meeting_room_3);
        }
        else if(position % 5 == 3)
        {
            Room_Image.setImageResource(R.drawable.meeting_room_2);
        }
        else if(position % 5 == 4)
        {
            Room_Image.setImageResource(R.drawable.meeting_room_1);
        }
    }

    @Override
    public void onClick(View view) {
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        if(view == Check_Schedule)
        {
            progressBar.setVisibility(View.GONE);
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            navController.navigate(R.id.action_room_Detail_page_to_roomSchedule_page);
        }
        else if(view == BookRoom)
        {
            progressBar.setVisibility(View.GONE);
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            //navController.navigate(R.id.action_room_Detail_page_to_room_booking_f1);
            Intent i = new Intent(getContext(), Booking_Activity.class);
            i.putExtra("Position",position);
            roomno = Integer.valueOf(SelectedRoom);
            i.putExtra("Roomno",roomno);
            startActivity(i);
            //getActivity().finish();
        }

    }
}
