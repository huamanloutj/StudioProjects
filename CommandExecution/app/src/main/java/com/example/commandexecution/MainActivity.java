package com.example.commandexecution;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.io.DataOutputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Log.d("TAG:", CommandExecution.execCommand("echo 1+1",true).toString());
        String[] cmds = {"mkdir ~/test\n","echo 1+1"};
        try {
            doCmds(cmds);
        }catch (Exception e){

        }
        setContentView(R.layout.activity_main);
    }
    public void test(){
        Log.d("tag::::::::","Hello Test");
    }
    public  void doCmds(String[] cmds) throws Exception {
        Process process = Runtime.getRuntime().exec("sh");//不是root用户执行的是sh
        DataOutputStream os = new DataOutputStream(process.getOutputStream());

        for (String tmpCmd : cmds) {
            Log.i("Tag******",tmpCmd + " ");
            os.writeBytes(tmpCmd+"\n");
        }

        os.writeBytes("exit\n");
        os.flush();
        os.close();

        process.waitFor();
    }
}