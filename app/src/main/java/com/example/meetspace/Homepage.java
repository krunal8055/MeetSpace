package com.example.meetspace;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Homepage extends Fragment {

    NavController navController;
    Context context;
    FirebaseAuth  firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    EditText editText;
    ImageButton Filter_button;
    ArrayList<list_data> datalist,filtered_list;
    RecyclerView recyclerView;
    List_Adapter listAdapter;
    Bundle recieved_data,SelectedRoom;
    ProgressBar progressBar;

    public Homepage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homepage, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.progressBar_home);
        recieved_data=getArguments();
        if(recieved_data!=null)
        {
            Log.i("Recieved Data : Status",recieved_data.getString("status"));
            Log.i("Recieved Data : Date",recieved_data.getString("date"));
            Log.i("Recieved Data : Start",recieved_data.getString("start_time"));
            Log.i("Recieved Data : End",recieved_data.getString("end_time"));
        }
        context = getActivity().getApplicationContext();
       // ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
        recyclerView = view.findViewById(R.id.recycler_home_page);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        listAdapter = new List_Adapter(filtered_list,context);
        recyclerView.setAdapter(listAdapter);

        navController = Navigation.findNavController(view);

//Search Bar code start
        editText = view.findViewById(R.id.search_bar_edit_text_home);

        Filter_button = view.findViewById(R.id.filter_button_home);

        editText.addTextChangedListener(new TextWatcher() {

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
//search bar code end


        /*--------- List Adapter Configuration---------*/
        datalist = new ArrayList<list_data>();
        filtered_list = new ArrayList<list_data>();
        listAdapter = new List_Adapter(filtered_list,context);
        recyclerView.setAdapter(listAdapter);


        /*-------List Adapter Configuration enf----------*/
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        fromFirebaseToList();


        //FilterButton Action start
        Filter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_homepage_to_filter_page);

            }
        });
        //FilterButton Action End
    }

    private void fromFirebaseToList()
    {
        progressBar.setVisibility(View.VISIBLE);
        datalist.clear();
        filtered_list.clear();
        listAdapter.notifyDataSetChanged();
        databaseReference.child("Room").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {

                    //JSONObject object = new JSONObject();
                    for(DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        datalist.add(new list_data(ds.getKey()));
                        filtered_list.add(new list_data(ds.getKey()));
                    }
                    progressBar.setVisibility(View.GONE);
                    listAdapter.notifyDataSetChanged();
                    listAdapter.setOnClickListner(onClickListner);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


//for Filter Data from Recycler view
    private void filter(String FilterString) {
        filtered_list.clear();
        for(list_data item :datalist)
        {
            if(item.getRoom_no().toLowerCase().startsWith(FilterString.toLowerCase()))
            {
                    filtered_list.add(item);
            }
        }
        listAdapter.notifyDataSetChanged();
    }


    public View.OnClickListener onClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            //Toast.makeText(getActivity().getApplicationContext(),datalist.get(position).getRoom_no()+" Selected!",Toast.LENGTH_LONG).show();
            SelectedRoom = new Bundle();
            SelectedRoom.putString("SelectedRoomNo",datalist.get(position).getRoom_no());

            navController.navigate(R.id.action_homepage_to_room_Detail_page,SelectedRoom);
            //navController.navigate(R.id.To_Room_Detail,Data);   to_room_detail is id from navigation graph and data is data which u can send to that page with bundle
        }
    };

}
