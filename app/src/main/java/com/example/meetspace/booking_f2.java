package com.example.meetspace;


import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class booking_f2 extends Fragment {
    Context context;
    TextView Search_user_List;
    RecyclerView PeopleInviteList;
    User_list_adapter user_list_adapter;
    ArrayList<UserList> Users,Users_Filtered_list;
    FirebaseDatabase fb_db;
    DatabaseReference db_ref;
    ProgressBar progressBar;



    public booking_f2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking_f2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity().getApplicationContext();
        //Initialization

        progressBar = view.findViewById(R.id.progress_bar_invite_list);

        //Recycler View
        PeopleInviteList = view.findViewById(R.id.invite_list_booking2);
        PeopleInviteList.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        user_list_adapter = new User_list_adapter(Users_Filtered_list,context);
        PeopleInviteList.setAdapter(user_list_adapter);


        Search_user_List =view.findViewById(R.id.search_bar_edit_text_user_list);
        Search_user_List.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        //List Adapter Config
        Users = new ArrayList<UserList>();
        Users_Filtered_list = new ArrayList<UserList>();
        user_list_adapter = new User_list_adapter(Users_Filtered_list,context);
        PeopleInviteList.setAdapter(user_list_adapter);
        getUserListFromDB();
    }

    private void getUserListFromDB() {
        progressBar.setVisibility(View.VISIBLE);
        fb_db = FirebaseDatabase.getInstance();
        db_ref = fb_db.getReference().child("User");

        Users.clear();
        Users_Filtered_list.clear();
        user_list_adapter.notifyDataSetChanged();

        db_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    for(DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        progressBar.setVisibility(View.GONE);
                        String fName = ds.child("Firstname").getValue().toString();
                        String lName = ds.child("Lastname").getValue().toString();
                        //Log.i("FirstName",ds.child("Firstname").getValue().toString());
                        //Log.i("LastName",ds.child("Lastname").getValue().toString());
                        Users.add(new UserList(fName,lName));
                        Users_Filtered_list.add(new UserList(fName,lName));
                    }
                    user_list_adapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(context,"Something went wrong. Please try Again!",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public View.OnClickListener onClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            Log.i("Selected Position", String.valueOf(position));
        }
    };


    //for Filter Data from Recycler view
    private void filter(String FilterString) {
        Users_Filtered_list.clear();
        for(UserList item :Users)
        {
            if(item.getFirstName().toLowerCase().startsWith(FilterString.toLowerCase()))
            {
                Users_Filtered_list.add(item);
            }
        }
        user_list_adapter.notifyDataSetChanged();
    }


}
