package com.example.ballfloat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.example.ballfloat.manager.BallManager;
import com.example.ballfloat.service.StartBallService;

public class MainActivity extends AppCompatActivity {
    private final String MAIN_TEST = "MAIN_TEST";
    private static IBall mBall;
    private static Intent intent;//非静态方法使用静态变量
    private BallManager manager ;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBall = IBall.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void start(View view){
        //startService(intent);
        //manager = BallManager.getInstance(MainActivity.this);
        Log.e("Manager Test",":"+manager);
        intent = new Intent(this, StartBallService.class) ;
        //startService(intent);//这里不能用start
        bindService(intent,mServiceConnection,Context.BIND_AUTO_CREATE);////cotext的各种选项值是什么意思
        finish();
    }
    public void change(View view) {
        //bindService(intent,mServiceConnection,Context.BIND_AUTO_CREATE);
        Log.e("Change","start Change");
        try {
            Log.e("Changed","can Change");
            mBall.setColor();
        }catch (RemoteException e){
            e.printStackTrace();
        }
    }

    public void close(View view) {
        Log.e("Close","Close Ball");
        try{
            mBall.setColor();
        }catch (RemoteException e){
            e.printStackTrace();
        }
    }
}