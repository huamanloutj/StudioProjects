// IThunderListener.aidl
package com.example.aidldemo;

// Declare any non-default types here with import statements

oneway interface IThunderListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    @UnsupportedAppUsage
    void onThunderLevelChanged(int level);

}