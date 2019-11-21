package com.dhananjay.recyclerviewwithbuttonsincardview.models;

import com.google.gson.annotations.SerializedName;

public class Dob {

    @SerializedName("date")
    private String date;
    @SerializedName("age")
    private int age;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
