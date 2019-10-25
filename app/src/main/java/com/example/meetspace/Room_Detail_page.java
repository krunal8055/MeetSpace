package com.example.meetspace;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Room_Detail_page extends Fragment {
    TextView RoomNo;
    Bundle bundle;

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
        bundle = getArguments();
        String room_no = bundle.getString("SelectedRoomNo");
        RoomNo = view.findViewById(R.id.room_no_room_detail);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("RoomNo "+room_no);
        RoomNo.setText(room_no);

    }
}
