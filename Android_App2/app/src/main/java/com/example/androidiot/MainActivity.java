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

public class MainActivity extends AppCompatActivity {
    ImageView bgapp,cloverimg;
    LinearLayout splashtext,hometext,menus;
    Animation frombottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frombottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        bgapp = (ImageView) findViewById(R.id.bgapp);
        cloverimg = (ImageView) findViewById(R.id.cloverimg);
        splashtext  = (LinearLayout) findViewById(R.id.splashtext) ;
        hometext  = (LinearLayout) findViewById(R.id.hometext) ;
        menus  = (LinearLayout) findViewById(R.id.menus) ;

        bgapp.animate().translationY(-1200).setDuration(800).setStartDelay(300);
        cloverimg.animate().alpha(0).setDuration(800).setStartDelay(600);
        splashtext.animate().translationY(140).alpha(0).setDuration(800).setStartDelay(800);
        hometext.startAnimation(frombottom);
        menus.startAnimation(frombottom);
    }
    public void switchTab(View v){
        Intent  display  = new Intent(this, SwitchTabActivity.class);
        startActivity(display);

    }
    public void switchControl(View v){
        Intent control  = new Intent(this, SwitchControlActivity.class);
        startActivity(control);
    }
    public void switchCamera(View v){
        Intent camera  = new Intent(this, SwitchCameraActivity.class);
        startActivity(camera);
    }

}