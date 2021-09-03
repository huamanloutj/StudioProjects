package com.example.createsystemservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ThunderManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioAttributes;
import android.os.Bundle;
import android.os.Parcel;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        
        setContentView(R.layout.activity_main);

    }
//    private void VibratorInterfaceDemo(){
//        String opPkg = "com.android.";
//        VibrationEffect effect = new VibrationEffect() {
//            @Override
//            public void validate() {
//
//            }
//
//            @Override
//            public long getDuration() {
//                return 0;
//            }
//
//            @Override
//            public void writeToParcel(Parcel dest, int flags) {
//
//            }
//        };
//        Vibrator mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
//        if (mVibrator.hasVibrator())
//            mVibrator.vibrate10s(10000,opPkg,effect,"test");
//        else {
//            Log.i("infor", "" + mVibrator.hasVibrator());
//            Log.d("debug:", "无振动器");
//        }
//
//    }
    private void ThunderManagerDemo(){
        //ThunderManager系统服务代码实例
        ThunderManager manager = (ThunderManager)getSystemService("thunder");
        Log.d("Tag:",manager + " ");
        //ThunderManager manager = (ThunderManager)getSystemService(Context.THUNDER_SERVICE);
        int level = manager.getThunderLevel();
        //加个Listenner；
        Log.d("luoah", "[MainActivity.java] -- test -- 1 level:" + level);
        manager.setThunderLevel(98);
        manager.prepareThunder();
        manager.justThunder();
    }

}