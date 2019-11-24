package com.example.meetspace;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.meetspace.ListAdapters.Resource_list_adapter;
import com.example.meetspace.ModelClass.ResourceList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class booking_f3 extends Fragment implements View.OnClickListener, Resource_list_adapter.ExtraResource {
    Context context;
    NavController navController;
    TextView Search_resource_List;
    Button NextButton;
    ProgressBar progressBar;
    RecyclerView ResourceList;
    Resource_list_adapter resource_list_adapter;
    ArrayList<com.example.meetspace.ModelClass.ResourceList> Resources,Resources_Filtered_List;
    FirebaseDatabase fb_db;
    DatabaseReference db_ref;
    SharedPreferences extraresource;
    SharedPreferences.Editor editor ;


    public booking_f3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking_f3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity().getApplicationContext();
        extraresource = getActivity().getSharedPreferences("ExtraResources",context.MODE_PRIVATE);
        editor = extraresource.edit();
        editor.clear();
        progressBar = view.findViewById(R.id.progress_bar_resource_list);
        navController = Navigation.findNavController(view);
        ResourceList = view.findViewById(R.id.resource_list_booking3);
        NextButton = view.findViewById(R.id.next_button_booking_3);
        ResourceList.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
       /* resource_list_adapter = new Resource_list_adapter(this,Resources_Filtered_List,context);
        ResourceList.setAdapter(resource_list_adapter);*/


        Search_resource_List =view.findViewById(R.id.search_bar_edit_text_resource_list);
        Search_resource_List.addTextChangedListener(new TextWatcher() {

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
        Resources = new ArrayList<ResourceList>();
        Resources_Filtered_List = new ArrayList<ResourceList>();
        resource_list_adapter = new Resource_list_adapter(this,Resources_Filtered_List,context);
        ResourceList.setAdapter(resource_list_adapter);
        getResourcesFromDB();
        NextButton.setOnClickListener(this);

    }
    //for Filter Data from Recycler view
    private void filter(String FilterString) {
        Resources_Filtered_List.clear();
        for(ResourceList item :Resources)
        {
            if(item.getResourceName().toLowerCase().contains(FilterString.toLowerCase()))
            {
                Resources_Filtered_List.add(item);
            }
        }
        resource_list_adapter.notifyDataSetChanged();
    }

    private void getResourcesFromDB()
    {
        progressBar.setVisibility(View.VISIBLE);
        fb_db = FirebaseDatabase.getInstance();
        db_ref = fb_db.getReference().child("Resource");
        Resources.clear();
        Resources_Filtered_List.clear();
        resource_list_adapter.notifyDataSetChanged();
        db_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    progressBar.setVisibility(View.GONE);
                    for (DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        if(ds.exists()) {
                            String res_name = ds.child("Resourcename").getValue().toString();
                            Resources.add(new ResourceList(res_name));
                            Resources_Filtered_List.add(new ResourceList(res_name));
                        }
                    }
                    resource_list_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == NextButton)
        {
            editor.commit();
            navController.navigate(R.id.action_booking_f3_to_booking_f4);
        }
    }

    @Override
    public void AddExtraResource(ResourceList resourceList, int position,Boolean status)
    {
            if (status == true) {

                    editor.putString(String.valueOf(position), resourceList.getResourceName());
                    //Log.i("select",resourceList.getResourceName());
            } else {
                    editor.remove(String.valueOf(position));
                //Log.i("removed",resourceList.getResourceName());
        }

    }
}
