package com.example.meetspace;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetspace.ListAdapters.Notification_list_Adapter;
import com.example.meetspace.ModelClass.Notification_data;

import java.util.ArrayList;

public class NoitifcationPage extends Fragment {
    Context context;
    RecyclerView notification_list;
    Notification_list_Adapter notificationListAdapter;
    ArrayList<Notification_data> notificationDataArrayList;


    public NoitifcationPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_noitifcation_page, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity().getApplicationContext();
        notification_list = view.findViewById(R.id.recycler_notification);
        notificationDataArrayList = new ArrayList<>();
        notification_list.setLayoutManager(new LinearLayoutManager(context));
        notificationListAdapter = new Notification_list_Adapter(notificationDataArrayList,context);
        notification_list.setAdapter(notificationListAdapter);
        for(int i=0;i<1;i++) {
            notificationDataArrayList.add(new Notification_data("qwertyuiop", "asdfghjkl", "102", "11/11/2019", "10:20", "18:20"));
        }
        notificationListAdapter.notifyDataSetChanged();

    }


}
