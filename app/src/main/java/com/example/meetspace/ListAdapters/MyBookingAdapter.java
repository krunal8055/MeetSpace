package com.example.meetspace.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetspace.ModelClass.MyBooking;
import com.example.meetspace.R;

import java.util.ArrayList;

public class MyBookingAdapter extends RecyclerView.Adapter<MyBookingAdapter.ViewHolder> {
    ArrayList<MyBooking> myBookingArrayList;
    Boolean aBoolean;
    Context context;

    public MyBookingAdapter(ArrayList<MyBooking> myBookingArrayList, Context context, Boolean aBoolean) {
        this.myBookingArrayList = myBookingArrayList;
        this.context = context;
        this.aBoolean = aBoolean;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_booking_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.roomno.setText("Room no "+myBookingArrayList.get(position).getRoom());
        holder.date.setText(myBookingArrayList.get(position).getDate());
        holder.start.setText(myBookingArrayList.get(position).getStart());
        holder.end.setText(myBookingArrayList.get(position).getEnd());
        if(aBoolean !=true) {
            holder.edit.setVisibility(View.GONE);
        }
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Edit clicked!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return myBookingArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView roomno,date,start,end;
        ImageView edit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roomno = itemView.findViewById(R.id.my_booking_item_room_no);
            date= itemView.findViewById(R.id.my_booking_item_booking_date);
            start = itemView.findViewById(R.id.my_booking_item_start_time);
            end = itemView.findViewById(R.id.my_booking_item_end_time);
            edit = itemView.findViewById(R.id.my_booking_item_edit_icon);
            itemView.setTag(this);
        }
    }

}
