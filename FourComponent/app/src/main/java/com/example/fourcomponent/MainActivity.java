package com.example.fourcomponent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("test","onCreate");


    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("test", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("test", "onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("test", "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("test", "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("test", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("test", "onDestroy");
    }
}