package com.example.commandexecution;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] cmds = {"wm size 1200x1920","cd /sdcard","touch 1.txt"};
        /*动态输入命令*/
        Log.i("CommandExecution******",CommandExecution.execCommand(cmds,false).successMsg);

        setContentView(R.layout.activity_main);
    }
//    public  void doCmds(String[] cmds) throws Exception {
//        try {
//            Process process = Runtime.getRuntime().exec("su");//不是root用户执行的是sh
//
//            DataOutputStream os = new DataOutputStream(process.getOutputStream());
//            for (String tmpCmd : cmds) {
//                Log.i("Tag******",tmpCmd + " ");
//                os.writeBytes(tmpCmd+"\n");
//            }
//
//            os.writeBytes("exit\n");
//            os.flush();
//            os.close();
//            BufferedReader successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String s;
//            StringBuilder successMessage = new StringBuilder();
//            while((s = successResult.readLine()) != null) successMessage.append(s);
//            Log.d("******result*****",successMessage.toString());
//        }catch (Throwable e){
//            e.printStackTrace();
//        }
//
//        //process.waitFor();
//    }
}