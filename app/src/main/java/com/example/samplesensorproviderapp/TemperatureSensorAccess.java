package com.example.samplesensorproviderapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;

import java.util.UUID;

public class TemperatureSensorAccess implements SensorEventListener  {
    private SensorManager sensorManager;
    private Sensor mAmbientTemperature;
    private TextView sensor_field;

    public static final String brokerURI = "3.89.70.15";

    public TemperatureSensorAccess(SensorManager sm, TextView tv){
        sensorManager = sm;
        sensor_field = tv;
        mAmbientTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorManager.registerListener(this, mAmbientTemperature, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        // The temperature sensor returns a single value.
        float temp = event.values[0];
        // Show luminosity value on the text field
        sensor_field.setText(String.valueOf(temp));

        this.sendTemperatureValue(String.valueOf(temp));
    }

    public final void sendTemperatureValue(String value) {
        Mqtt5BlockingClient client = Mqtt5Client.builder()
                .identifier(UUID.randomUUID().toString())
                .serverHost(brokerURI)
                .buildBlocking();

        client.connect();
        client.publishWith()
                .topic("/temperature-value")
                .qos(MqttQos.AT_LEAST_ONCE)
                .payload(String.valueOf(value).getBytes())
                .send();
        client.disconnect();
    }

    @Override
    protected void finalize() {
        sensorManager.unregisterListener(this);
    }
}
