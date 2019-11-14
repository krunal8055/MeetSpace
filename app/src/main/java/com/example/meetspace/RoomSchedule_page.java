package com.example.meetspace;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;

import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class RoomSchedule_page extends Fragment {
    //TextView date;
    public NavController navController;
    Context context;
    Event_view event_view = new Event_view();
    Bundle bundle;
    String roomno;

    public RoomSchedule_page() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        roomno = getArguments().getString("Roomno");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("RoomNo "+roomno);
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
                String d;
                if(day.getDay()<10)
                {
                    d = "0"+day.getDay();
                }
                else
                    {
                        d = String.valueOf(day.getDay());
                    }
                String day_txt =d+"/"+(day.getMonth()+1)+"/"+day.getYear();


                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String c_date = sdf.format(calendar.getTime());
                try {
                    Date current_date = sdf.parse(c_date);
                    Date selected_date = sdf.parse(day_txt);
                    if(selected_date.compareTo(current_date) >0 || selected_date.compareTo(current_date) ==0)
                    {
                        //Log.i("Date Comparison","Selected date is after current date");
                        if(day_txt != null)
                        {
                            event_view.getRoomno(getArguments().getString("Roomno"));
                            event_view.getdataFromDB(day_txt);
                        }
                    }
                    else
                    {
                        event_view.textView.setVisibility(View.VISIBLE);
                        Toast.makeText(context,"Please Selected Valid Date",Toast.LENGTH_SHORT).show();
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
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
