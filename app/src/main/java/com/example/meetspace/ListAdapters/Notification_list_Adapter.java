package com.example.meetspace.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetspace.ModelClass.Notification_data;
import com.example.meetspace.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Notification_list_Adapter extends RecyclerView.Adapter<Notification_list_Adapter.ViewHolder> {
    ArrayList<Notification_data> arrayList;
    Context context;
    String uid;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public Notification_list_Adapter(ArrayList<Notification_data> notificationDataArrayList, Context context) {
        arrayList = notificationDataArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Notification_data notification_data = arrayList.get(position);

        holder.Title.setText("Invitation Message");
        holder.Message.setText(arrayList.get(position).getMessage());
        holder.Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Accepted!",Toast.LENGTH_LONG).show();
                arrayList.remove(position);
                setStatus_in_Notification("Accepted");
                notifyDataSetChanged();
            }
        });
        holder.Reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Rejected!",Toast.LENGTH_LONG).show();
                arrayList.remove(position);
                setStatus_in_Notification("Rejected");
                notifyDataSetChanged();
            }
        });
        holder.bind(notification_data);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean expanded = notification_data.isExpanded();
                notification_data.setExpanded(!expanded);
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Title,Message,Date,Start,End;
        Button Accept,Reject;
        LinearLayout button_layout;
        //String title = "Somaya Chaffer";
        //String message = "Invited you in Room no 102 For Metting.\nWould you like to Join?";
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.notification_title);
            Message = itemView.findViewById(R.id.notification_message);
            Accept = itemView.findViewById(R.id.accept_button_notification);
            Reject = itemView.findViewById(R.id.reject_button_notification);
            button_layout = itemView.findViewById(R.id.linear_button_notification);
            itemView.setTag(this);
        }
        private void bind(Notification_data notification_data) {
            boolean expanded = notification_data.isExpanded();

            button_layout.setVisibility(expanded ? View.VISIBLE : View.GONE);
        }
    }


    public void setStatus_in_Notification(final String status)
    {
        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("User");
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

                                for (DataSnapshot ds2 : ds1.child("MyInviteList").getChildren())
                                {

                                    if(ds2.child("Invited_User_token_id").exists())
                                    {

                                        if (uid.equals(ds2.child("Reciever_UID").getValue().toString()))
                                        {

                                            if (ds2.child("Invitation_Status").getValue().toString().equals("Pending"))
                                            {
                                                ds2.child("Invitation_Status").getRef().setValue(status);
                                                //Log.i("Status",ds2.child("Invitation_Status").getValue().toString());
                                                //Log.i("Given_Status",status);
                                                //notificationDataArrayList.add(new Notification_data(ds2.child("Message").getValue().toString()));
                                            }

                                        }

                                    }

                                }
                                //notificationListAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
