package com.example.myapplication;
import java.util.*;

import static java.lang.Double.*;

public class EntryObject {
    private String breakfast, lunch, dinner, Walk, Exercise, other;
    private Date date;


    public EntryObject() {
        this.breakfast = "0";
        this.lunch = "0";
        this.dinner = "0";
        this.Walk = "0";
        this.Exercise = "0";
        this.other = "0";

        this.date = new Date();
    }



    public String getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public String getLunch() {
        return lunch;
    }

    public void setLunch(String lunch) {
        this.lunch = lunch;
    }

    public String getDinner() {
        return dinner;
    }

    public void setDinner(String dinner) {
        this.dinner = dinner;
    }

    public String getWalk() {
        return Walk;
    }

    public void setWalk(String Walk) {
        this.Walk = Walk;
    }

    public String getExercise() {
        return Exercise;
    }

    public void setExercise(String Exercise) {
        this.Exercise = Exercise;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Date getDate() {
        return date;
    }

    public String getFoodTotal(){
        return (parseDouble(getBreakfast())+ parseDouble(getLunch())+ parseDouble(getDinner())+ "");
    }

    public String getExerciseTotal(){
        return (parseDouble(getWalk())+ parseDouble(getExercise())+ parseDouble(getOther())+ "");
    }

    public String getNKI(){
        return (parseDouble(getFoodTotal())- parseDouble(getExerciseTotal()))+ "";
    }

    @Override
    public String toString() {
        String output = "DiaryEntry{" + "breakfast='" + breakfast + '\'' +
                ", lunch='" + lunch + '\'' + ", dinner='" + dinner + '\'' +
                ", Walk='" + Walk + '\'' + ", Exercise='" + Exercise + '\'' +
                ", other='" + other + '\'' + ", date=" + date + '}';
        return output;
    }
}
