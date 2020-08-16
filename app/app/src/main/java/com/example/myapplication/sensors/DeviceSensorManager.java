package com.example.myapplication.sensors;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.myapplication.datatypes.TremblingAverage;

import java.util.ArrayList;

public class DeviceSensorManager implements SensorEventListener {


    public SensorManager sensorManager;
    public Sensor accelerometerSensor;

    public boolean is_accelerometerSensor_available =false;
    private  boolean itIsNOTFirstTime = false;

    // current sensor positions
    private float currentX = 0f;
    private  float currentY = 0f;
    private  float currentZ= 0f;

    // last captured sensor positions
    private  float lastX= 0f;
    private  float lastY = 0f;
    private  float lastZ= 0f;

    // the difference between the current and the last positions
    private float xDifference = 0f;
    private  float yDifference = 0f;
    private  float zDifference = 0f;

    // arbitrary threshold
    private float shakeThreshold = 5f;

    // the time is in seconds for demo purposes
    public float time=0f;

    // the data to send
    public ArrayList<TremblingAverage> list ;

    public DeviceSensorManager(){
        this.list= new  ArrayList<TremblingAverage>();
    }

    @SuppressLint("ServiceCast")
    public void initialiseSensorManager(Context context){
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            is_accelerometerSensor_available = true;
        } else {
            System.out.println("accelerometerSensor not available");
            is_accelerometerSensor_available = false;
        }


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
//        println(event!!.values[0].toString() + " m/s2")
//        println(event.values[1].toString() + " m/s2")
//        println(event.values[2].toString() + " m/s2")

        currentX = event.values[0];
        currentY = event.values[1];
        currentZ = event.values[2];

        if (itIsNOTFirstTime) {
            xDifference = Math.abs(lastX - currentX);
            yDifference = Math.abs(lastY - currentY);
            zDifference = Math.abs(lastZ - currentZ);

            float eucDist =(float) Math.sqrt(Math.pow(xDifference,2) + Math.pow(yDifference,2) + Math.pow(zDifference,2)) ;
            this.time++;

            TremblingAverage data= new TremblingAverage(eucDist,this.time);
            this.list.add(data);


            if (xDifference > shakeThreshold && yDifference > shakeThreshold || xDifference > shakeThreshold && zDifference > shakeThreshold || yDifference > shakeThreshold && zDifference > shakeThreshold) {
                System.out.println("it is shaking");
            }
        }

        lastX = currentX;
        lastY = currentY;
        lastZ = currentZ;

        itIsNOTFirstTime = true;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}
