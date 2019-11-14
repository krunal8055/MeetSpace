package com.example.meetspace.ListAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetspace.Booking_Activity;
import com.example.meetspace.ModelClass.MyBooking;
import com.example.meetspace.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyBookingAdapter extends RecyclerView.Adapter<MyBookingAdapter.ViewHolder> {
    ArrayList<MyBooking> myBookingArrayList;
    Boolean aBoolean;
    Context context;
    FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
    String uid = FirebaseAuth.getInstance().getUid();
    private View.OnClickListener itemClickListner;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final MyBooking myBooking = myBookingArrayList.get(position);
        holder.roomno.setText("Room no "+myBookingArrayList.get(position).getRoom());
        holder.date.setText(myBookingArrayList.get(position).getDate());
        holder.start.setText(myBookingArrayList.get(position).getStart());
        holder.end.setText(myBookingArrayList.get(position).getEnd());
        if(aBoolean !=true) {
            holder.edit.setVisibility(View.GONE);
            holder.delete.setVisibility(View.GONE);
            holder.l1.setVisibility(View.GONE);
        }
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "Edit clicked!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, Booking_Activity.class);
                i.putExtra("flag",true);
                i.putExtra("BookingID",myBookingArrayList.get(position).getBookingID());
                i.putExtra("position",position);
                i.putExtra("Roomno",myBookingArrayList.get(position).getRoom());
                i.putExtra("Bookingdate",myBookingArrayList.get(position).getDate());
                i.putExtra("Start",myBookingArrayList.get(position).getStart());
                i.putExtra("End",myBookingArrayList.get(position).getEnd());
                i.putExtra("Reason",myBookingArrayList.get(position).getReason());
                i.putExtra("NoPeople",myBookingArrayList.get(position).getNo_people());
                view.getContext().startActivity(i);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDatabase.getReference().child("User").child(uid).child("MyBooking").child(myBookingArrayList.get(position).getBookingID()).removeValue();
                Toast.makeText(context,"Booking Deleted Successfully!",Toast.LENGTH_SHORT).show();
                myBookingArrayList.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.bind(myBooking);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*if (aBoolean == false)
                {
                    myBooking.setExpanded(aBoolean);
                }
                else
                {*/
                    boolean expanded = myBooking.isExpanded();
                    myBooking.setExpanded(!expanded);
                    notifyDataSetChanged();
                }
           // }
        });

    }
    public void setOnClickListner(View.OnClickListener clickListner)
    {
        itemClickListner = clickListner;
    }
    @Override
    public int getItemCount() {
        return myBookingArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView roomno,date,start,end;
        Button edit,delete;
        LinearLayout l1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roomno = itemView.findViewById(R.id.my_booking_item_room_no);
            date= itemView.findViewById(R.id.my_booking_item_booking_date);
            start = itemView.findViewById(R.id.my_booking_item_start_time);
            end = itemView.findViewById(R.id.my_booking_item_end_time);
            edit = itemView.findViewById(R.id.my_booking_item_edit_button);
            delete=itemView.findViewById(R.id.my_booking_item_delete_button);
            l1 = itemView.findViewById(R.id.edit_booking_linear);
            itemView.setTag(this);
            itemView.setOnClickListener(itemClickListner);
        }

        public void bind(MyBooking myBooking) {
            boolean expanded = myBooking.isExpanded();

            l1.setVisibility(expanded ? View.VISIBLE : View.GONE);
            if(aBoolean == false)
            {
                l1.setVisibility(View.GONE);
            }
        }
    }

}
