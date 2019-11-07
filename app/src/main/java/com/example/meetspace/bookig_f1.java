package com.example.meetspace;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class bookig_f1 extends Fragment implements View.OnClickListener {
    ImageView RoomImage;
    TextView RoomNo,BookingDate,BookingStart,BookingEnd;
    EditText BookingReason,No_of_Person;
    Button NextButton;
    SharedPreferences sharedPreferences;
    int position,Roomno;

    String start_time,end_time;
    SimpleDateFormat sdf;

    Context context;
    private NavController navController;

    public bookig_f1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookig_f1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getActivity().getApplicationContext();
        sharedPreferences = getActivity().getSharedPreferences("BookingData",context.MODE_PRIVATE);
        navController = Navigation.findNavController(view);
        RoomImage = view.findViewById(R.id.room_img_booking_1);
        RoomNo = view.findViewById(R.id.room_no_booking1);
        BookingDate= view.findViewById(R.id.date_room_booking);
        BookingStart = view.findViewById(R.id.start_time_booking);
        BookingEnd = view.findViewById(R.id.end_time_booking);
        dateAndTimePickerInit();
        SetImageBooking_And_Room_No();
        BookingReason = view.findViewById(R.id.reason_booking1);
        No_of_Person = view.findViewById(R.id.no_person_booking1);
        NextButton = view.findViewById(R.id.next_button_booking_1);
        NextButton.setOnClickListener(this);

    }
    private void dateAndTimePickerInit()
    {
        BookingDate.setOnClickListener(this);
        BookingStart.setOnClickListener(this);
        BookingEnd.setOnClickListener(this);
    }
    private  void SetImageBooking_And_Room_No()
    {
        position = getActivity().getIntent().getIntExtra("Position",0);
        Roomno = getActivity().getIntent().getIntExtra("Roomno",0);
        RoomNo.setText(String.valueOf(Roomno));
        if(position % 5 == 0)
        {
            RoomImage.setImageResource(R.drawable.meeting_room_5);
        }
        else if(position % 5 == 1)
        {
            RoomImage.setImageResource(R.drawable.meeting_room_4);
        }
        else if(position % 5 == 2)
        {
            RoomImage.setImageResource(R.drawable.meeting_room_3);
        }
        else if(position % 5 == 3)
        {
            RoomImage.setImageResource(R.drawable.meeting_room_2);
        }
        else if(position % 5 == 4)
        {
            RoomImage.setImageResource(R.drawable.meeting_room_1);
        }
    }

//Booking Date
    @Override
    public void onClick(View view) {
        if(view == BookingDate)
        {
            DatePickerDialog datePicker;
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);

            datePicker = new DatePickerDialog(getActivity(), R.style.TimePickerTheme,new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String date = String.valueOf(dayOfMonth)+"-"+String.valueOf(monthOfYear)+"-"+String.valueOf(year);
                    BookingDate.setText(date);
                    Log.i("StartTime_booking:",BookingDate.getText().toString());
                }
            }, yy, mm, dd);
            datePicker.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
            datePicker.show();
        }
        //Start Time
        else if(view == BookingStart)
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

                    start_time = String.valueOf(Hour)+":"+String.valueOf(min)+" "+timeSet;
                    BookingStart.setText(start_time);
                    Log.i("Booking Start Time :",BookingStart.getText().toString());
                }
            },hour,minute,false);
            timePicker.show();
        }
        //End Time
        else if(view == BookingEnd)
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

                    end_time = String.valueOf(Hour)+":"+String.valueOf(min)+" "+timeSet;
                    BookingEnd.setText(end_time);
                    Log.i("Booking End Time :",BookingEnd.getText().toString());
                }
            },hour,minute,false);
            timePicker.show();
        }
        else if(view == NextButton)
        {
            if(BookingDate.getText().length() == 0)
            {
                Toast.makeText(context, "Please enter the booking date!", Toast.LENGTH_SHORT).show();
            }
            else if(BookingStart.getText().length() == 0)
            {
                Toast.makeText(context, "Please enter the booking start time!", Toast.LENGTH_SHORT).show();
            }
            else if(BookingEnd.getText().length() == 0)
            {
                Toast.makeText(context, "Please enter the booking end time!", Toast.LENGTH_SHORT).show();
            }
            else if(BookingReason.getText().length() == 0)
            {
                Toast.makeText(context, "Please enter the booking reason!", Toast.LENGTH_SHORT).show();
            }
            else if(No_of_Person.getText().length() == 0)
            {
                Toast.makeText(context, "Please enter no of person!", Toast.LENGTH_SHORT).show();
            }
            else {
                  if(compareTime())
                  {
                      SharedPreferences.Editor editor = sharedPreferences.edit();
                      editor.putString("Roomno", RoomNo.getText().toString());
                      editor.putString("BookingDate", BookingDate.getText().toString());
                      editor.putString("StartTime", BookingStart.getText().toString());
                      editor.putString("EndTime", BookingEnd.getText().toString());
                      editor.putString("Reason", BookingReason.getText().toString());
                      editor.putString("NoOfPerson", No_of_Person.getText().toString());
                      editor.commit();
                      navController.navigate(R.id.action_bookig_f1_to_booking_f2);
                  }
                  else
                      {
                          Toast.makeText(context, "End Time Should grater then start time!", Toast.LENGTH_SHORT).show();
                      }
            }

        }

    }
    private boolean compareTime()
    {
        sdf = new SimpleDateFormat("hh:mm");
        if(start_time!=null && end_time!=null) {
            try {
                Date start = sdf.parse(start_time);
                Date end = sdf.parse(end_time);
                if (isTimeAfter(start, end)) {
                    //Toast.makeText(context,"End time is greater then Start time",Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    //Toast.makeText(context,"End time is not greater then Start time!!!",Toast.LENGTH_SHORT).show();
                    return false;
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
        }
        else
        {
            return false;
        }
    }
    boolean isTimeAfter(Date start, Date end) {
        if (end.before(start))
        {
            return false;
        }
        else
            {
            return true;
        }
    }
}
