package com.example.androidiot;


import android.os.Bundle;

import android.widget.ToggleButton;


import androidx.appcompat.app.AppCompatActivity;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


import java.nio.charset.StandardCharsets;

public class SwitchControlActivity extends AppCompatActivity {
    ToggleButton pumpbtn, lightbtn, fanbtn;
    MQTTHelper mqttHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_control);
        pumpbtn = findViewById(R.id.pumpbtn);
        lightbtn = findViewById(R.id.lightbtn);
        fanbtn = findViewById(R.id.fanbtn);

//        fanbtn.setOnCheckedChangeListener((nutnhanView, isChecked) -> {
//            if (isChecked) {
//                sendDataMQTT("clowz/feeds/nutnhan2", "1");
//            } else {
//                sendDataMQTT("clowz/feeds/nutnhan2", "0");
//            }
//        });
        pumpbtn.setOnCheckedChangeListener((compoundButton, isOn) -> {
            if (isOn) {
                sendDataMQTT("hungneet/feeds/button1", "1");
            } else {
                sendDataMQTT("hungneet/feeds/button1", "0");
            }
        });

//        lightbtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                sendDataMQTT("hungneet/feeds/button3", "1");
//            } else {
//                sendDataMQTT("hungneet/feeds/button3", "0");
//            }
//        });

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
                if (topic.contains("button1")) {
                    pumpbtn.setChecked(value.equals("1"));
                }
//                else if (topic.contains("button3")) {
//                    lightbtn.setChecked(value.equals("1"));
//                }
//                else if (topic.contains("nutnhan3")) {
//                    lightbtn.setChecked(value.equals("1"));
//                }
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
        }
}
