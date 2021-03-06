package com.example.studolist.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.studolist.Model.Events;
import com.example.studolist.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GridAdapter extends ArrayAdapter {

    List<Date> dates;
    Calendar currentDate;
    List<Events> events;
    LayoutInflater inflater;

    public GridAdapter(@NonNull Context context, List<Date> dates, Calendar currentDate, List<Events> events) {
        super(context, R.layout.single_cell_layout);

        this.dates = dates;
        this.currentDate = currentDate;
        this.events = events;
        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Date monthsDate = dates.get(position);
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(monthsDate);
        int dayNo = dateCalendar.get(Calendar.DAY_OF_MONTH);
        int displaymonth = dateCalendar.get(Calendar.MONTH)+1;
        int displayYear = dateCalendar.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH)+1;
        int currentYear = currentDate.get(Calendar.YEAR);


        View view = convertView;
        if(view == null){
            view = inflater.inflate(R.layout.single_cell_layout, parent, false);
        }

        if(displaymonth == currentMonth && displayYear == currentYear){
            view.setBackgroundColor(getContext().getResources().getColor(R.color.green));
        }else{
            view.setBackgroundColor(Color.parseColor("#cccccc"));
        }

        TextView Day_Number = view.findViewById(R.id.calendarDay);
        TextView eventNumber = view.findViewById(R.id.eventId);
        Day_Number.setText(String.valueOf(dayNo));
        //
        Calendar eventCalendar = Calendar.getInstance();
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < events.size(); i++){
            eventCalendar.setTime(ConvertStringtoDate(events.get(i).getDATE()));
            //
            if(dayNo == eventCalendar.get(Calendar.DAY_OF_MONTH) && displaymonth == eventCalendar.get(Calendar.MONTH)+1
            && displayYear == eventCalendar.get(Calendar.YEAR)){
                arrayList.add(events.get(i).getEVENT());
                eventNumber.setText(arrayList.size()+ " Events");
            }
        }


        return view;
    }

    private Date ConvertStringtoDate(String eventDate){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = null;
        try{
            date= format.parse(eventDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    @Override
    public int getCount() {
        return dates.size();
    }


    @Override
    public int getPosition(@Nullable Object item) {
        return dates.indexOf(item);
    }


    @Nullable
    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }
}
