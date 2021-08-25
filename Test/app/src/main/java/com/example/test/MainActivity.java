package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.service.autofill.Sanitizer;
import android.util.Log;
import android.widget.EditText;

import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Tag1","input your code" + " ");
        try {
//            Log.i("Tag2","input your code" + " ");
//            Scanner scanner = new Scanner(System.in);
//            Log.i("tag3","");
//            String s = scanner.nextLine();
//            Log.i("tag4","");
            
            System.out.println("Hello World");
        }catch (Exception e){
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main);
    }
}