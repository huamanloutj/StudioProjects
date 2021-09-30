package com.example.createsystemservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
//import android.app.ThunderManager;
import android.app.ThunderManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioAttributes;
import android.os.Bundle;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.app.ThunderListener;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public void test(View view) {

        ThunderManager thunderManager = ThunderManager.getThunderManager();
//        @SuppressLint("WrongConstant")
//        //ThunderManager thunderManager = (ThunderManager) getSystemService("thunder");
        ThunderListener thunderListener = new ThunderListener() {
            @Override
            public void onThunderLevelChanged(int level) {
                Log.e("Test","onThunderLevelChanged:" + level );
            }
        };
        Log.e("TAG",thunderListener + ":" + thunderManager);
        try {
            thunderManager.addThunderListener(thunderListener);
        } catch (Exception e){
            Log.e("TAG","" + e);
        }
        thunderManager.setThunderLevel(12);
        thunderManager.setThunderLevel(35);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}