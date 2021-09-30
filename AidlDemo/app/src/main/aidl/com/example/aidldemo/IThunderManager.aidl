// IThunderManager.aidl
package com.example.aidldemo;

import com.example.aidldemo.IThunderListener;
// Declare any non-default types here with import statements

interface IThunderManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void setThunderLevel(int level);
    int getThunderLevel();
    void addThunderListener(in IThunderListener listener);
    void removeThunderListener(in IThunderListener listener);
}