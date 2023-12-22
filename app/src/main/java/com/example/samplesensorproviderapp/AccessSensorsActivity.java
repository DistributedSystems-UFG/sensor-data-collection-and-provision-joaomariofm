package com.example.samplesensorproviderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class AccessSensorsActivity extends AppCompatActivity {

    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_sensors);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        TextView textViewLuminosity = (TextView) findViewById(R.id.textViewLuminosity);
        TextView textViewTemperature = (TextView) findViewById(R.id.textViewTemperature);

        LightSensorAccess lightSensorAccess = new LightSensorAccess(sensorManager, textViewLuminosity);
        TemperatureSensorAccess temperatureSensorAccess = new TemperatureSensorAccess(sensorManager, textViewTemperature);
    }
}

