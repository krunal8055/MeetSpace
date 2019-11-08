package com.example.meetspace.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetspace.ModelClass.Notification_data;
import com.example.meetspace.R;

import java.util.ArrayList;

public class Notification_list_Adapter extends RecyclerView.Adapter<Notification_list_Adapter.ViewHolder> {
    ArrayList<Notification_data> arrayList;
    Context context;
    Notification_data notification_data=new Notification_data();
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Accepted!",Toast.LENGTH_LONG).show();
                arrayList.clear();
            }
        });
        holder.Reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Rejected!",Toast.LENGTH_LONG).show();
                arrayList.clear();
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
        String title = "Somaya Chaffer";
        String message = "Invited you in Room no 102 For Metting.\nWould you like to Join?";
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.notification_title);
            Message = itemView.findViewById(R.id.notification_message);
            Date = itemView.findViewById(R.id.notification_date);
            Start = itemView.findViewById(R.id.notification_start_time);
            End = itemView.findViewById(R.id.notification_end_time);
            Accept = itemView.findViewById(R.id.accept_button_notification);
            Reject = itemView.findViewById(R.id.reject_button_notification);
            Title.setText(title);
            Message.setText(message);
            itemView.setTag(this);
        }
    }
}
