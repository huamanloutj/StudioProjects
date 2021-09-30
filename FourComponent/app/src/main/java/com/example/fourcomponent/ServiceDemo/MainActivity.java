package com.example.fourcomponent.ServiceDemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import static com.example.fourcomponent.Test.TEST;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fourcomponent.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //这个R代表的是Res？
        setContentView(R.layout.activity_main);
        Log.i("Kathy", "Thread ID = " + Thread.currentThread().getId());
        Log.i("Kathy", "before StartService");

        //连续启动Service
        Intent intentOne = new Intent(this, MyService.class);
        startService(intentOne);
        Intent intentTwo = new Intent(this, MyService.class);
        startService(intentTwo);
        Intent intentThree = new Intent(this, MyService.class);
        startService(intentThree);

        //停止Service
        Intent intentFour = new Intent(this, MyService.class);
        stopService(intentFour);

        //再次启动Service
        Intent intentFive = new Intent(this, MyService.class);
        startService(intentFive);

        Log.i(TEST, "after StartService");
    }
}