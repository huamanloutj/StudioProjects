package com.example.myapplicationtwo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /** Called when the user taps the Send button */
    private boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }
    public void sendMessage(View view) {
        //两个activity之间发送消息
        /*
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        //这个editTextTextPersonName与sendmessage是相关联的
        EditText editText = (EditText)findViewById(R.id.editTextTextPersonName2);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
         */

        //打开第三方应用，UC浏览器
        /*
        Uri uri = Uri.parse("https://www.baidu.com");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setClassName("com.UCMobile","com.uc.browser.InnerUCMobile");
        */

        //打开高德地图，得到一个回家的导航路线
        //判断包是否存在
        if (isInstallByread("com.autonavi.minimap")) {
            //跳转处理
        }else {
            Toast.makeText(MainActivity.this,"未安装该应用",Toast.LENGTH_LONG).show();
        }
        //得到坐标
        Intent intent = new Intent("android.intent.action.VIEW", android.net.Uri.parse("amapuri://route/plan/?dlat=39.98848272&dlon=116.47560823&dev=0&t=0"));
        //"androidamap://navi?sourceApplication=appname&poiname=fangheng&lat=26.57&lon=106.71&dev=1&style=2"
        // amapuri://route/plan/?sname=A&did=BGVIS2&dlat=39.98848272&dlon=116.47560823&dname=B&dev=0&t=0
        intent.setPackage("com.autonavi.minimap");
        //context.startActivity(intent);

        //地理编码
        Intent intent2 = new Intent("android.intent.action.VIEW",android.net.Uri.parse("androidamap://keywordNavi?sourceApplication=softname&keyword=北京市&style=2"));
        intent2.setPackage("com.autonavi.minimap");
        intent2.addCategory("android.intent.category.DEFAULT");

        //启动intent
        startActivity(intent2);
    }
}