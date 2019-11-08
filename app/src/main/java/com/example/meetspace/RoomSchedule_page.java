package com.example.meetspace;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;

import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;


public class RoomSchedule_page extends Fragment {
    //TextView date;
    public NavController navController;
    Context context;
    Event_view event_view = new Event_view();

    public RoomSchedule_page() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_room_schedule_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //date = view.findViewById(R.id.date_event);
        context = getActivity().getApplicationContext();

        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.event_list,event_view,"eventView");
        ft.addToBackStack(null);
        ft.commit();

        //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.event_list,event_view,"Event View").addToBackStack(null).commit();
       final CollapsibleCalendar collapsibleCalendar = view.findViewById(R.id.calendarView);
        collapsibleCalendar.setCalendarListener(new CollapsibleCalendar.CalendarListener() {
            @Override
            public void onDaySelect() {
                Day day = collapsibleCalendar.getSelectedDay();
                String day_txt =day.getDay()+"/"+(day.getMonth()+1)+"/"+day.getYear();
                //date.setText(day_txt);
                if(day_txt != null)
                {
                  event_view.textView.setVisibility(View.GONE);
                  event_view.Date.setVisibility(View.VISIBLE);
                  event_view.ribionIMage.setVisibility(View.VISIBLE);
                  event_view.Date.setText(day_txt);
                }

                Log.i("selection________", "Selected Day: "
                        + day.getYear() + "/" + (day.getMonth() + 1) + "/" + day.getDay());
            }

            @Override
            public void onItemClick(View view) {
            }

            @Override
            public void onDataUpdate() {

            }

            @Override
            public void onMonthChange() {

            }

            @Override
            public void onWeekChange(int i) {

            }
        });
    }

}
