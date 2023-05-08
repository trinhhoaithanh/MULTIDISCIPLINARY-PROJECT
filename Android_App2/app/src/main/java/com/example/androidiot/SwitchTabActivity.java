package com.example.androidiot;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;
//import android.widget.TogglenButton;

import androidx.appcompat.app.AppCompatActivity;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class SwitchTabActivity extends AppCompatActivity {
    MQTTHelper mqttHelper;
    TextView temp_screen, humi_screen, light_screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_tab);
        temp_screen = findViewById(R.id.temp_screen);
        humi_screen = findViewById(R.id.humi_screen);
        light_screen = findViewById(R.id.light_screen);

        startMQTT();
    }

    public void sendDataMQTT(String topic, String value) {
        MqttMessage msg = new MqttMessage();
        msg.setId(1234);
        msg.setQos(0);
        msg.setRetained(false);

        byte[] b = value.getBytes(StandardCharsets.UTF_8);
        msg.setPayload(b);

        try {
            mqttHelper.mqttAndroidClient.publish(topic, msg);
        } catch (MqttException e) {
        }
    }

    public void startMQTT() {
        mqttHelper = new MQTTHelper(this);

        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                String value = message.toString();
                if (topic.contains("sensor2")) {
                    temp_screen.setText(value + "°C");
                } else if (topic.contains("sensor1")) {
                    humi_screen.setText(value + "%");
                } else if (topic.contains("sensor3")) {
                    light_screen.setText(value + " Lux");
                }

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }
}

