package com.example.aidldemo;

import android.os.RemoteException;
import android.util.Log;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ThunderService extends IThunderManager.Stub{
    private int mLevel;
    HashSet<Object> mSet = new HashSet<>();//定义泛型；
    @Override
    public void setThunderLevel(int level) throws RemoteException {
        Log.e("Test",level+"");
        if (mLevel != level) {
            mLevel = level;
            //notify all listeners
            //遍历集合
            //mSet.forEach(((IThunderListener)s) -> s.onThunderLevelChanged(level));
            for (Iterator it = mSet.iterator();it.hasNext();) {
                IThunderListener listener = (IThunderListener) it.next();//强转
                listener.onThunderLevelChanged(level);
            }
        }

    }

    @Override
    public int getThunderLevel() throws RemoteException {
        return mLevel;
    }

    @Override
    public void addThunderListener(IThunderListener listener) throws RemoteException {
        Log.e("Tag","add a listener");
        mSet.add(listener);
    }

    @Override
    public void removeThunderListener(IThunderListener listener) throws RemoteException {
        mSet.remove(listener);
    }
}
