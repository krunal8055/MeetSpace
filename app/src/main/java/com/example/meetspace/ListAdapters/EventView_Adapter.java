package com.example.meetspace.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetspace.ModelClass.MyBooking;
import com.example.meetspace.R;

import java.util.ArrayList;

public class EventView_Adapter extends RecyclerView.Adapter<EventView_Adapter.ViewHolder> {

    private ArrayList<MyBooking> arrayList=null;
    private Context context;

    public EventView_Adapter(ArrayList<MyBooking> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_view_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.start.setText(arrayList.get(position).getStart());
        holder.end.setText(arrayList.get(position).getEnd());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView start,end;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            start=itemView.findViewById(R.id.start_event_item);
            end = itemView.findViewById(R.id.end_event_item);
            itemView.setTag(this);
        }
    }
}
