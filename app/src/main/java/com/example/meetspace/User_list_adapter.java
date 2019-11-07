package com.example.meetspace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class User_list_adapter extends RecyclerView.Adapter<User_list_adapter.ViewHolder> {

    private ArrayList<UserList> userListArrayList;
    private Context context;

    public User_list_adapter(ArrayList<UserList> userListArrayList, Context context) {
        this.userListArrayList = userListArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_invite_list_item,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.UserName.setText(userListArrayList.get(position).getFirstName()+" "+userListArrayList.get(position).getLastName());
        holder.cancel_invite_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context,"Invitation Cancle Successfully! "+position,Toast.LENGTH_SHORT).show();
                holder.InviteButton.setText("Invite");
                holder.cancel_invite_btn.setVisibility(View.GONE);
                holder.InviteButton.setVisibility(View.VISIBLE);
            }
        });
        holder.InviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context,userListArrayList.get(position).FirstName+"Invited!",Toast.LENGTH_SHORT).show();
                   // Toast.makeText(context,"Item button Clicked at "+position,Toast.LENGTH_SHORT).show();
                    holder.cancel_invite_btn.setVisibility(View.VISIBLE);
                    holder.InviteButton.setVisibility(View.GONE);
                }
        });
    }


    @Override
    public int getItemCount() {

        return userListArrayList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView UserName;
        Button InviteButton;
        ImageButton cancel_invite_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            UserName = itemView.findViewById(R.id.user_name_invite_list);
            InviteButton = itemView.findViewById(R.id.invite_Button);
            cancel_invite_btn = itemView.findViewById(R.id.cancel_invite_btn);
            itemView.setTag(this);
        }
    }
}
