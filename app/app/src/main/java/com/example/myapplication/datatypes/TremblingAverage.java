package com.example.myapplication.datatypes;

import java.io.Serializable;

public class TremblingAverage implements Serializable {
    public float average;
    public float time;

    public TremblingAverage(float average, float time){
        this.average=average;
        this.time=time;
    }

    public float getAverage() {
        return average;
    }

    public float getTime() {
        return time;
    }
}
