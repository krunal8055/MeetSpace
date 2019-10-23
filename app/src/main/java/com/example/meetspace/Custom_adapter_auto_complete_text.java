package com.example.meetspace;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Custom_adapter_auto_complete_text extends ArrayAdapter {
    List<RoomCatagory> Items,tempItems,Suggestions;
    Context context;
    int item_layout;

    public Custom_adapter_auto_complete_text(Context context, int resource, int textViewResourceId, List<RoomCatagory> items) {
        super(context, resource, textViewResourceId, items);
        Items = items;
        this.context = context;
        item_layout = resource;
        tempItems = new ArrayList<RoomCatagory>(Items);
        Suggestions = new ArrayList<RoomCatagory>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_layout_autocomplete, parent, false);
        }
        RoomCatagory roomCatagory = Items.get(position);
        if (Items != null) {
            TextView type_room = (TextView) view.findViewById(R.id.auto_complete_text_custom_text_view);
            if (type_room != null)
                type_room.setText(roomCatagory.getRoom_type());
        }
        return view;
    }


    @Override
    public Filter getFilter() {
        return catagory_filter;
    }

    Filter catagory_filter = new Filter() {

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str  = ((RoomCatagory) resultValue).getRoom_type();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if(charSequence != null)
            {
                Suggestions.clear();
                for(RoomCatagory roomCatagory:tempItems)
                {
                    if(roomCatagory.getRoom_type().toLowerCase().contains(charSequence.toString().toLowerCase()))
                    {
                        Suggestions.add(roomCatagory);
                    }

                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = Suggestions;
                filterResults.count = Suggestions.size();
                return filterResults;
            }
            else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<RoomCatagory> filterList = (ArrayList<RoomCatagory>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (RoomCatagory roomCatagory : filterList) {
                    add(roomCatagory);
                    notifyDataSetChanged();
                }
            }
        }
    };
}
