package com.example.listphoneapp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class MyHandler extends Handler {
    //
    static final String HandlerTest = "HandlerTest";
    public MyHandler(Looper myLooper){
        super(myLooper);  //重写构造方法，通过Looper创建MyHandler对象
    }
    public MyHandler(){}
    @Override
    public void handleMessage(Message msg){
        Log.e(HandlerTest,"MyHandler handleMessage " + msg.what);
        String str = "";
        switch(msg.what){
            case 1:
                str = "1: " + msg.obj;
                break;
            case 2:
                str = "2: " + msg.obj;
                break;
            case 3:
                str = "3: " + msg.obj;
//                    Button b = findViewById(R.id.button_3);
//                    b.setVisibility(View.INVISIBLE);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
        }
//        Toast toast = Toast.makeText(, str, Toast.LENGTH_SHORT);
//        toast.show();
    }
}
