package com.example.meetspace.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetspace.ModelClass.list_data;
import com.example.meetspace.R;

import java.util.ArrayList;
import java.util.List;

public class List_Adapter extends RecyclerView.Adapter<List_Adapter.ViewHolder> {

    private ArrayList<list_data> listarray;
    private Context context;
    private View.OnClickListener itemClickListner;

    public List_Adapter(ArrayList<list_data> listarray, Context context) {
        this.listarray = listarray;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.room_no_txt.setText(listarray.get(position).getRoom_no());
        if(position % 5 == 0)
        {
            holder.room_image_list.setImageResource(R.drawable.meeting_room_5);
        }
        else if(position % 5 == 1)
        {
            holder.room_image_list.setImageResource(R.drawable.meeting_room_4);
        }
        else if(position % 5 == 2)
        {
            holder.room_image_list.setImageResource(R.drawable.meeting_room_3);
        }
        else if(position % 5 == 3)
        {
            holder.room_image_list.setImageResource(R.drawable.meeting_room_2);
        }
        else if(position % 5 == 4)
        {
            holder.room_image_list.setImageResource(R.drawable.meeting_room_1);
        }

    }

    @Override
    public int getItemCount() {

        return listarray.size();
    }

    public void setOnClickListner(View.OnClickListener clickListner)
    {
        itemClickListner = clickListner;
    }

    public void filterList(ArrayList<list_data> filter_search) {
        listarray = filter_search;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView room_image_list;
        TextView room_no_txt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            room_image_list = itemView.findViewById(R.id.room_img_recycler_view);
            room_no_txt = itemView.findViewById(R.id.room_no_recycler);
            itemView.setTag(this);
            itemView.setOnClickListener(itemClickListner);
        }
    }

}
