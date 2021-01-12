package com.example.weektwotest.ui.main;

import java.util.ArrayList;

public class Subject{
    private String subjectName;
    private Time startTime;
    private Time endTime;
    private ArrayList<String> students;

    public Subject(){
        students = new ArrayList<String>();
    }

    void setSubjectName(String subjectName){
        this.subjectName = subjectName;
    }
    void setStartTime(Time startTime){
        this.startTime = startTime;
    }
    void setEndTime(Time endTime){
        this.endTime = endTime;
    }
    void setStudents(ArrayList<String> students){
        this.students = students;
    }

    String getSubjectName(){
        return subjectName;
    }
    Time getStartTime(){
        return startTime;
    }
    Time setEndTime(){
        return endTime;
    }
    ArrayList<String> getStudents(){
        return students;
    }
}