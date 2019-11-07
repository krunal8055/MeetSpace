package com.example.meetspace;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Resource_list_adapter extends RecyclerView.Adapter<Resource_list_adapter.ViewHolder>{
    private ArrayList<ResourceList> resourceListArrayList;
    private Context context;

    public Resource_list_adapter(ArrayList<ResourceList> resourceListArrayList, Context context) {
        this.resourceListArrayList = resourceListArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_resource_list_item,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.ResourceName.setText(resourceListArrayList.get(position).getResourceName());
        holder.CancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.CancleButton.setVisibility(View.GONE);
                holder.ResourceQTY.setVisibility(View.GONE);
                holder.AddButton.setVisibility(View.VISIBLE);
            }
        });
        holder.AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.AddButton.setVisibility(View.GONE);
                holder.ResourceQTY.setVisibility(View.VISIBLE);
                holder.CancleButton.setVisibility(View.VISIBLE);
                Toast.makeText(context,"Added!!!",Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {

        return resourceListArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ResourceName;
        EditText ResourceQTY;
        Button AddButton;
        ImageButton CancleButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ResourceName = itemView.findViewById(R.id.resource_name_list);
            ResourceQTY = itemView.findViewById(R.id.qty_resource_list);
            AddButton = itemView.findViewById(R.id.add_button_resource_list);
            CancleButton = itemView.findViewById(R.id.cancel_resource_btn);
            itemView.setTag(this);
        }
    }

}
