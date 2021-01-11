package com.example.weektwotest.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.weektwotest.R;

import java.util.ArrayList;

public class FreeFragment extends Fragment{
    private static final String ARG_SECTION_NUMBER = "section_number";
    TimetableView timetableView;

    public static FreeFragment newInstance(int index) {
        FreeFragment fragment = new FreeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.free_fragment, container, false);
        timetableView = view.findViewById(R.id.timetable);
        ArrayList<Schedule> schedules = new ArrayList<Schedule>();
        Schedule schedule = new Schedule();
        schedule.setClassTitle("Data Structure"); // sets subject
        schedule.setClassPlace("IT-601"); // sets place
        schedule.setProfessorName("Won Kim"); // sets professor
        schedule.setStartTime(new Time(10,0)); // sets the beginning of class time (hour,minute)
        schedule.setEndTime(new Time(13,30)); // sets the end of class time (hour,minute)
        schedule.setDay(3);
        schedules.add(schedule);
        timetableView.add(schedules);
        return view;
    }
}