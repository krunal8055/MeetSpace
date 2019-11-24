package com.example.meetspace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meetspace.ListAdapters.Notification_list_Adapter;
import com.example.meetspace.ModelClass.Notification_data;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NotificationPage extends AppCompatActivity {
    RecyclerView notification_list;
    Notification_list_Adapter notificationListAdapter;
    ArrayList<Notification_data> notificationDataArrayList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    String uid;
    Toolbar toolbar;
    String TokenID = "";
    ProgressBar progressBar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_page);
        progressBar = findViewById(R.id.progressBar_notification);
        textView = findViewById(R.id.nil_text);
        //Toolbar
        toolbar = findViewById(R.id.notification_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Notification");
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        FirebaseMessaging.getInstance().subscribeToTopic("pushNotifications");
        FirebaseMessaging.getInstance().unsubscribeFromTopic("pushNotifications");
        notification_list = findViewById(R.id.recycler_notification);
        notificationDataArrayList = new ArrayList<>();
        ((SimpleItemAnimator) notification_list.getItemAnimator()).setSupportsChangeAnimations(false);
        notification_list.setLayoutManager(new LinearLayoutManager(this));
        notificationListAdapter = new Notification_list_Adapter(notificationDataArrayList,this);
        notification_list.setHasFixedSize(true);
        notification_list.setAdapter(notificationListAdapter);
        GetToakenID();
        getFirebaseMessage();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void getFirebaseMessage()
    {
        progressBar.setVisibility(View.VISIBLE);
        //
        Calendar c = Calendar.getInstance();
        int yy = c.get(Calendar.YEAR);
        int mm = c.get(Calendar.MONTH);
        int dd = c.get(Calendar.DAY_OF_MONTH);
        final String today_date = dd + "/" + (mm + 1) + "/" + yy;

        final String[] BD = new String[1];
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        final Date[] bookD = {null};
        Date today = null;
        try {
            today = sdf.parse(today_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //
            firebaseAuth = FirebaseAuth.getInstance();
            uid = firebaseAuth.getUid();
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference().child("User");

        final Date finalToday = today;
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    if(dataSnapshot.exists())
                    {

                        for(DataSnapshot main: dataSnapshot.getChildren())
                        {

                            if (!main.getKey().equals(uid))
                            {

                                for (DataSnapshot ds1 : main.child("MyBooking").getChildren())
                                {
                                    //if(ds1.child("Bookingdate").getValue())
                                    //Log.i("Booking Date:",ds1.child("Bookingdate").getValue().toString());
                                    BD[0] = ds1.child("Bookingdate").getValue().toString();
                                    try {
                                        bookD[0] = sdf.parse(BD[0]);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    for (DataSnapshot ds2 : ds1.child("MyInviteList").getChildren())
                                    {
                                            if (uid.equals(ds2.child("Reciever_UID").getValue().toString()))
                                            {
                                                if(bookD[0].compareTo(finalToday) >0) {
                                                    if (ds2.child("Invitation_Status").getValue().toString().equals("Pending")) {
                                                        progressBar.setVisibility(View.GONE);
                                                        notificationDataArrayList.add(new Notification_data(ds2.child("Message").getValue().toString()));
                                                    } else {
                                                        progressBar.setVisibility(View.GONE);
                                                    }
                                                }
                                            }
                                            else
                                                {
                                                    progressBar.setVisibility(View.GONE);
                                            }
                                    }
                                    notificationListAdapter.notifyDataSetChanged();
                                }
                            }
                            else {
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    }
                    else {
                        progressBar.setVisibility(View.GONE);
                    }

                    if(notificationDataArrayList.size() == 0)
                    {
                        progressBar.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                        notification_list.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
    }
    private void GetToakenID()
    {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if(task.isSuccessful())
                {
                    TokenID = task.getResult().getToken();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), (CharSequence) task.getException(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
