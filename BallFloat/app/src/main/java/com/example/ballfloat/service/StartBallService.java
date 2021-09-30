package com.example.ballfloat.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.ballfloat.manager.BallManager;
import com.example.ballfloat.view.Ball;

public class StartBallService extends Service {
    public final String SERVICE_TEST = "Service Test";
    public StartBallService(){
        Log.e(SERVICE_TEST,"manager in onCreate");
    }
    private BallManager getManager(){
        return BallManager.getInstance(this);
    }
    @Override
    public IBinder onBind(Intent intent){
        return new BallManager(this);
    }


    @Override
    public void onCreate() {
        BallManager manager = BallManager.getInstance(this);
        Log.e(SERVICE_TEST,"manager in onCreate" + manager);
        manager.showBall();
        super.onCreate();
    }
}
