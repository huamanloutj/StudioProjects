package com.example.aidldemo;


import static android.Manifest.permission.WRITE_SECURE_SETTINGS;


import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;

import android.os.IBinder;
import android.os.Looper;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.ArrayMap;

import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.Executor;



/*
 *@hide
 */
/*
1.解决锁的问题，加synchronized（mListeners）
2.解决分发的问题，新建一个类Reciever，HashMap<Object,ArrayList<>>?,
HashMap<Object,Reciever>,Reciever是个广播？
3.recievers中实现deathrecipient接口，重写了binderDied
 */
public class ThunderManager {
    //解决问题2：多个LIstener对应的是一个Transpor，Listener的结构是ArrayList，每一个ArrayList对应一个transport
    @GuardedBy("mListeners")
    //private final ArrayMap<ThunderListener, ThunderListenerTransport> mListeners = new ArrayMap<>();
    //Listener用HashMap也行，实现的是一个name和ThunderListenner的对应关系,但是Map中对应的name可能相同。
    //class也可能相同，定义成static?放到一个set里面？
    //是<<>,>还是<,<>>,在于Transport。
    private final ArrayMap<Listener, ThunderListenerTransport> mListeners = new ArrayMap<>();
    private HashSet<String> nameSet = new HashSet<>();

    ArrayList<Listener> listeners;
    ThunderListenerTransport transport;
    private final IThunderManager mService ;//这是
    public ThunderManager(IThunderManager service){
        mService = service;
        //mService = IThunderManager.Stub.asInterface(ServiceManager.getService("thunder"));
        //通过这句话来建立一个链接调用的是将IThunderManager -> IThunderManger.Stub(返回的是代理对象Proxy)->实现的是com.example.aidldemo.IThunderManager
        //
    }

    void setThunderLevel(int level){
        try{
            mService.setThunderLevel(level);//调用的是IThunderService.set......方法，private final IThunderManager mService
        }catch (RemoteException e){
        }
    }
    int getThunderLevel(){
        try{
            return mService.getThunderLevel();
        }catch (RemoteException e){

        }

        return -1;
    }

    public void addThunderListener(ThunderListener listener) {
        synchronized (mListeners) {
            ThunderListenerTransport transport = mListeners.get(listener);
            if (transport == null) {
                transport = new ThunderListenerTransport(listener);
                mListeners.put(listener, transport);
            }
            transport = mListeners.get(listener);
            try {
                mService.addThunderListener(transport);//这里是调用mService的addThunderListenner?
            } catch (RemoteException e) {

            }
        }
    }
    public void removeThunderListener(@NonNull ThunderListener listener) {
        ThunderListenerTransport transport = mListeners.remove(listener);
        if (transport == null)
            return;
        try {
            mService.removeThunderListener(transport);
        } catch (RemoteException e) {

        }
    }
//
//        synchronized (mListeners) {
//            ThunderListenerTransport transport = mListeners.get(listener);
//            try {
//                mService.requestThunderUpdates(transport);
//                registered = true;
//            } catch (RemoteException e) {
//                throw e.rethrowFromSystemServer();
//            } finally {
//                if (!registered) {
//                    // allow gc after exception
//                    transport.unregister();
//                    mListeners.remove(listener);
//                }
//            }
//        }

    //ThunderListenerTransport是继承的IIThunderListener.Stub，将ThunderListener封装成IThunderListener
    //核心作用是封装
    private class ThunderListenerTransport extends IThunderListener.Stub {
        private final ThunderListener mListener;
        private ThunderListenerTransport(@NonNull ThunderListener listener) {
            mListener = listener;
        }

        @Override
        public void onThunderLevelChanged(int level) throws RemoteException {
            mListener.onThunderLevelChanged(level);
            for (Listener l : listeners) {
            }
        }
    }
    final class Reciever implements IBinder.DeathRecipient{
        final IThunderListener mListener = null;
        @Override
        public void binderDied() {
            //也要加锁？

        }
    }
    final class Listener{
        ArrayList<ThunderListener> listeners;
        String name;
    }
}
