package com.example.meetspace;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetspace.ListAdapters.User_list_adapter;
import com.example.meetspace.ModelClass.UserList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class booking_f2 extends Fragment implements View.OnClickListener, User_list_adapter.InviteUser {
    Context context;
    TextView Search_user_List;
    Button NextButton;
    RecyclerView PeopleInviteList;
    User_list_adapter user_list_adapter;
    ArrayList<UserList> Users,Users_Filtered_list;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase fb_db;
    DatabaseReference db_ref;
    ProgressBar progressBar;
    NavController navController;
    SharedPreferences InviteList_Token,InviteList_RUID;
    SharedPreferences.Editor editor_token,editor_RUID;


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
        InviteList_Token = getActivity().getSharedPreferences("InviteList_Token",context.MODE_PRIVATE);
        InviteList_RUID = getActivity().getSharedPreferences("InviteList_RUID",context.MODE_PRIVATE);
        editor_token = InviteList_Token.edit();
        editor_RUID= InviteList_RUID.edit();
        editor_token.clear();
        editor_RUID.clear();
        //Initialization
        progressBar = view.findViewById(R.id.progress_bar_invite_list);
        navController = Navigation.findNavController(view);
        //Recycler View
        PeopleInviteList = view.findViewById(R.id.invite_list_booking2);
        NextButton = view.findViewById(R.id.next_button_booking_2);

        PeopleInviteList.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        user_list_adapter = new User_list_adapter(this,Users_Filtered_list,context);
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
        user_list_adapter = new User_list_adapter(this,Users_Filtered_list,context);
        PeopleInviteList.setAdapter(user_list_adapter);
        getUserListFromDB();
        NextButton.setOnClickListener(this);
    }

    private void getUserListFromDB() {
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        final String UID = firebaseUser.getUid();
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
                        if(!ds.getKey().contains(UID) && ds.hasChild("Tokenid")) {
                            String tokenid = ds.child("Tokenid").getValue().toString();
                            String reciever_uid = ds.child("UID").getValue().toString();
                            String fName = ds.child("Firstname").getValue().toString();
                            String lName = ds.child("Lastname").getValue().toString();
                            Users.add(new UserList(reciever_uid,tokenid,fName, lName));
                            Users_Filtered_list.add(new UserList(reciever_uid,tokenid,fName, lName));
                        }
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

   /* public View.OnClickListener onClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            Log.i("Selected Position", Users.get(position).toString());
        }
    };*/

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


   @Override
    public void onClick(View view) {
        if(view == NextButton)
        {
            editor_token.commit();
            editor_RUID.commit();
            navController.navigate(R.id.action_booking_f2_to_booking_f3);
        }
    }

    @Override
    public void AddUser(UserList userList, boolean status,int position) {
            if (status == true) {
                //editor.putString(String.valueOf(position), userList.getFirstName() + "" + userList.getLastName());
                editor_token.putString(String.valueOf(position), userList.getTokenID());
                editor_RUID.putString(String.valueOf(position),userList.getReciever_UID());
            } else {
                editor_token.remove(String.valueOf(position));
                editor_RUID.remove(String.valueOf(position));
                //InviteBundle.remove(String.valueOf(position));
                //Toast.makeText(context, userList.getFirstName() + "" + userList.getLastName() + " removed!", Toast.LENGTH_SHORT).show();
        }

    }
}
