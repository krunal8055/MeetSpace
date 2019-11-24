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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meetspace.ListAdapters.List_Adapter;
import com.example.meetspace.ModelClass.list_data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Homepage extends Fragment {

    NavController navController;
    Context context;
    FirebaseAuth  firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    EditText editText;
    ArrayList<list_data> datalist,filter_search;
    RecyclerView recyclerView;
    List_Adapter listAdapter;
    Bundle recieved_data,SelectedRoom;
    ProgressBar progressBar;
    TextView error_search;
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
        /*recieved_data=getArguments();
        if(recieved_data!=null)
        {
            if(recieved_data.get("date") != null) {
                Log.i("Recieved Data : Date", recieved_data.getString("date"));
            }
            if(recieved_data.getString("start_time") != null){
                Log.i("Recieved Data : Start",recieved_data.getString("start_time"));
            }
            if(recieved_data.getString("end_time") != null) {
                Log.i("Recieved Data : End",recieved_data.getString("end_time"));
            }
        }*/

        context = getActivity().getApplicationContext();
        error_search = view.findViewById(R.id.error_search_home);
        /*---------------- For Search Bar -----------------*/

        editText = view.findViewById(R.id.search_bar_edit_text_home);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter_search(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        /*-------------- Recycler View Start --------------------*/
        recyclerView = view.findViewById(R.id.recycler_home_page);
        datalist = new ArrayList<list_data>();
        filter_search = new ArrayList<list_data>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        if(editText.getText().length() == 0)
        {
            error_search.setVisibility(View.GONE);
            listAdapter = new List_Adapter(datalist,context);
        }
        listAdapter = new List_Adapter(filter_search,context);
        recyclerView.setAdapter(listAdapter);
        /*-------------- Recycler View End --------------------*/
        navController = Navigation.findNavController(view);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        fromFirebaseToList();
    }

    private void filter_search(String toString) {
        filter_search.clear();
        for(list_data item: datalist)
        {
            if(item.getRoom_no().contains(toString))
            {
                filter_search.add(item);
            }
            if(filter_search.size() == 0)
            {
                error_search.setVisibility(View.VISIBLE);
            }
            else {
                error_search.setVisibility(View.GONE);
            }
        }
        listAdapter.notifyDataSetChanged();
    }
    private void fromFirebaseToList()
    {
        progressBar.setVisibility(View.VISIBLE);
        datalist.clear();
        //error_search.setVisibility(View.GONE);
        filter_search.clear();
        listAdapter.notifyDataSetChanged();
        databaseReference.child("Room").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    for(DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        datalist.add(new list_data(ds.getKey()));
                        filter_search.add(new list_data(ds.getKey()));
                    }
                    progressBar.setVisibility(View.GONE);
                    listAdapter.notifyDataSetChanged();
                    listAdapter.setOnClickListner(onClickListner);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context,"Database error!Please Try Again.",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public View.OnClickListener onClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();

            SelectedRoom = new Bundle();
            SelectedRoom.putString("SelectedRoomNo",datalist.get(position).getRoom_no());
            SelectedRoom.putInt("SelectedPosition",position);
            //ON Fragment change hide Keyboard
            InputMethodManager keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            keyboard.hideSoftInputFromWindow(viewHolder.itemView.getApplicationWindowToken(), 0);
            navController.navigate(R.id.action_homepage_to_room_Detail_page,SelectedRoom);
        }
    };

}
