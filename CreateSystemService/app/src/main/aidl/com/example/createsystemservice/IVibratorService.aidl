// IVibratorService.aidl
package com.example.createsystemservice;


interface IVibratorService {
      void vibrate(int uid, String opPkg, in VibrationEffect effect,
              in VibrationAttributes attributes, String reason, IBinder token);
      void vibrate10s(int uid, String opPkg, in VibrationEffect effect,
              in VibrationAttributes attributes, String reason, IBinder token);
}