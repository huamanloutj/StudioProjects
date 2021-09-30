package com.example.fourcomponent;

import static com.example.fourcomponent.Test.TEST;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.fourcomponent.ServiceDemo.MyService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //这里修改会报错，主进程与main线程？
        setContentView(R.layout.activity_main);
    }

    public void startService(View view) {
        Intent intent = new Intent(this,MyService.class);
        startService(intent);
        finish();
    }


}