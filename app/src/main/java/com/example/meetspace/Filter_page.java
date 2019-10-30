package com.example.meetspace;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Filter_page extends Fragment implements View.OnClickListener{

    TextView Date_filter,Start_time,End_Time;
    RadioGroup Room_status_radiogroup;
    RadioButton Room_status_radiobutton;
    Button ApplyButton;
    Context context;
    AutoCompleteTextView autoCompleteTextView;
    List<RoomCatagory> mList;
    Custom_adapter_auto_complete_text adapter;
    Bundle data_for_filter;
    NavController navController;

    public Filter_page() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity().getApplicationContext();
        data_for_filter = new Bundle();
        navController = Navigation.findNavController(view);

        //AutoCompleteTextView
        mList = retrive_type();
        autoCompleteTextView = view.findViewById(R.id.auto_complete_text_room_catagory);
        //autoCompleteTextView.setThreshold(1);
        adapter = new Custom_adapter_auto_complete_text(context,R.layout.fragment_filter_page,R.id.auto_complete_text_custom_text_view,mList);
        adapter.notifyDataSetChanged();
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setTextColor(Color.WHITE);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            }

        });
/*
        ArrayAdapter<String> room_list_adapter = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1, room_catagory);
        room_list_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        room_list_adapter.notifyDataSetChanged();
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setTextColor(Color.WHITE);
        autoCompleteTextView.setAdapter(room_list_adapter);
        //autoCompleteTextView.setKeyListener(null);*/

        //Date and Time Picker
        Date_filter = view.findViewById(R.id.date_filter);
        Start_time = view.findViewById(R.id.start_time_filter);
        End_Time = view.findViewById(R.id.end_time_filter);
        dateAndTimePickerInit();

        //RadioButton
        Room_status_radiogroup = view.findViewById(R.id.room_status_radioGroup);
        Room_status_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Room_status_radiobutton = view.findViewById(i);
                String string = Room_status_radiobutton.getText().toString();
                data_for_filter.putString("status",string);
            }
        });

        //ApplyButton
        ApplyButton = view.findViewById(R.id.apply_filter_button);
        ApplyButton.setOnClickListener(this);


    }

    private List<RoomCatagory> retrive_type()
    {
        List<RoomCatagory> list = new ArrayList<RoomCatagory>();
        list.add(new RoomCatagory("Class Room with Resources"));
        list.add(new RoomCatagory("Class Room without Accessories"));
        list.add(new RoomCatagory("Meeting Room"));
        list.add(new RoomCatagory("Laboratory"));
        return list;
    }

    private void dateAndTimePickerInit()
    {
        Date_filter.setOnClickListener(this);
        Start_time.setOnClickListener(this);
        End_Time.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == Date_filter)
        {
            DatePickerDialog datePicker;
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);

            datePicker = new DatePickerDialog(getActivity(),R.style.TimePickerTheme,new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String date = String.valueOf(dayOfMonth)+"-"+String.valueOf(monthOfYear)+"-"+String.valueOf(year);
                    Date_filter.setText(date);
                    data_for_filter.putString("date",date);
                }
            }, yy, mm, dd);
            datePicker.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
            datePicker.show();
        }
        else if(view == Start_time)
        {
            TimePickerDialog timePicker;
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            timePicker = new TimePickerDialog(getActivity(), R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int Hour, int Minute) {

                    String timeSet = "";
                    if (Hour > 12)
                    {
                        Hour -= 12;
                        timeSet = "PM";
                    }
                    else if (Hour == 0)
                    {
                        Hour += 12;
                        timeSet = "AM";
                    }
                    else if (Hour == 12)
                    {
                        timeSet = "PM";
                    }
                    else {
                        timeSet = "AM";
                    }

                    String min = "";
                    if (Minute < 10)
                        min = "0" + Minute ;
                    else
                        min = String.valueOf(Minute);

                    String start_time = String.valueOf(Hour)+":"+String.valueOf(min)+" "+timeSet;

                    Start_time.setText(start_time);
                    data_for_filter.putString("start_time",Start_time.getText().toString());
                }
            },hour,minute,false);
            timePicker.show();
        }
        else if(view == End_Time)
        {
            TimePickerDialog timePicker;
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            timePicker = new TimePickerDialog(getActivity(), R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int Hour, int Minute) {
                    String timeSet = "";
                    if (Hour > 12)
                    {
                        Hour -= 12;
                        timeSet = "PM";
                    }
                    else if (Hour == 0)
                    {
                        Hour += 12;
                        timeSet = "AM";
                    }
                    else if (Hour == 12)
                    {
                        timeSet = "PM";
                    }
                    else {
                        timeSet = "AM";
                    }

                    String min = "";
                    if (Minute < 10)
                        min = "0" + Minute ;
                    else
                        min = String.valueOf(Minute);

                    String end_time = String.valueOf(Hour)+":"+String.valueOf(min)+" "+timeSet;
                    End_Time.setText(end_time);
                    data_for_filter.putString("end_time",end_time);
                }
            },hour,minute,false);
            timePicker.show();
        }
        else if(view == ApplyButton)
        {
            navController.navigate(R.id.action_filter_page_to_homepage,data_for_filter);
        }
    }
}
