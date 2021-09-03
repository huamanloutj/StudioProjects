package com.example.listphoneapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 功能说明：
 程序大概分成三个部分：

1.获取手机已安装的所有应用package的信息(其中包括用户自己安装的，还有系统自带的)；

2.滤除系统自带应用；

3.通过列表显示出应用程序的图标(icon),和其他文字信息(应用名称，包名称package name,版本号等等)
 */

public class MainActivity extends AppCompatActivity {
    private List<Map<String,Object>> data;
    Map<String,Object> item;
    private ListView listView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        listView = new ListView(this);
        data = new ArrayList<Map<String,Object>>();
        listPackages();
//        SimpleAdapter adapter = new Adapter2(
//                this,
//                data,
//                R.layout.vlist,
//                new String[]{"appname", "pname", "icon"},
//                new int[]{R.id.appname, R.id.pname, R.id.icon}
//        );
//        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
//            public boolean setViewValue(View view, Object data,
//                                        String textRepresentation) {
//                if (view instanceof ImageView && data instanceof Drawable) {
//                    ImageView iv = (ImageView) view;
//                    iv.setImageDrawable((Drawable) data);
//                    return true;
//                } else
//                    return false;
//            }
//        });
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.app_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //为什么不是显示调用这个函数,逻辑调用关系。

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //修改成parent，也可以实现效果，改成listView？，输出的内容上不一样
                //String str = parent.getItemAtPosition(position) + "";
                //这里使用参数和真实数据的区别
                //String pname = parent.getItemAtPosition(position).toString();//不是map对象。
                Log.e("tggg", "here ", new Throwable());
                String pname = data.get(position).get("pname").toString();
                Log.d("check:", "OnItemClick 实现\n" + pname + "\n position:" + position);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("即将跳转，是否确定？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("check：","点击了确定按钮！！！！");//which的值是-1
                        //createSpinner();
                        //1.这里弹框，提示选择比例
                        //2.点击选择对应的比例，然后响应，再进入app。
                        //启动系统应用失败，返回Attempt to invoke virtual method 'java.lang.String
                        // android.content.Intent.toString()' on a null object reference
                        Intent intent = getPackageManager().getLaunchIntentForPackage(pname);
                        if (intent != null) {
                            startActivity(intent);
                            // start
                        } else {
                            Toast.makeText(MainActivity.this,"系统应用，无法打开",Toast.LENGTH_LONG).show();
                        }

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("check：","点击了取消按钮！！！！！");
                        // 如何在屏幕显示告诉用户什么都没做
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        listView.setAdapter(new Adapter2());
        //listView.setAdapter(adapter);
    }
    private void createSpinner(){
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        Log.e("spinner:",spinner + " ");
        // 建立数据源
        String[] mItems = {"4:3","16:9","18:9","21:9"};
        //String[] mItems = getResources().getStringArray(R.array.ratio);
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        //绑定 Adapter到控件
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                //String[] ratio = getResources().getStringArray(R.array.ratio);
                Toast.makeText(MainActivity.this, "你选择的是:"+mItems[pos], Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }
    class PInfo {
        private String appname = "";
        private String pname = "";
        private String versionName = "";
        private int versionCode = 0;
        private Drawable icon = null;
        //显示图像的位图
        //private Bitmap bm;
        private void prettyPrint() {
            Log.i("taskmanger", appname + "\t" + pname + "\t" + versionName
                    + "\t" + versionCode + "\t");
        }
    }

    ArrayList<PInfo> pInfos = new ArrayList<>();
    private void listPackages(){
        pInfos = getSystemApp(this);
        final int max = pInfos.size();
        for (int i = 0 ; i < max ; i ++){
            item = new HashMap<String,Object>();
            item.put("appname",pInfos.get(i).appname);
            item.put("pname",pInfos.get(i).pname);
            item.put("versionName",pInfos.get(i).icon);
            data.add(item);
        }
    }
    class Adapter2 extends BaseAdapter {
        @Override
        public int getCount() {
            return pInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return pInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v;
            if (convertView == null) {
                v = getLayoutInflater().inflate(R.layout.vlist, null);
            }else {
                v = convertView;
            }
            TextView t1 = v.findViewById(R.id.appname);
            t1.setText(pInfos.get(position).appname);
            //t1.setTextColor(0xff0000ff);//颜色的代码不对，全是0的话无法正常显示，但是在vlist代码里面改成全0就可以
            TextView t2 = v.findViewById(R.id.pname);
            t2.setText(pInfos.get(position).pname);
            //t2.setTextColor(0xff0000ff);
            ImageView icon = v.findViewById(R.id.icon);
            icon.setImageDrawable(pInfos.get(position).icon);
            return v;
        }
    }
    public  ArrayList getSystemApp(Context context){
        PackageManager mPackageManager = context.getApplicationContext().getPackageManager();
        LauncherApps mLauncherApps = (LauncherApps) context.getSystemService(Context.LAUNCHER_APPS_SERVICE);
        UserManager mUserManager = (UserManager) context.getSystemService(Context.USER_SERVICE);
        List<UserHandle> users = mUserManager.getUserProfiles();
        List<UserHandle> profiles= users == null ? Collections.<UserHandle>emptyList() : users;
        Log.i("tag","users:"+ users);
        ArrayList<PInfo> list = new ArrayList<>();
        //根据手机所有用户获取每个用户下的应用
        for (UserHandle user : profiles) {
            // Query for the set of apps
            final List<LauncherActivityInfo> apps = mLauncherApps.getActivityList(null, user);
            // Fail if we don't have any apps
            // TODO: Fix this. Only fail for the current user.
            if (apps == null || apps.isEmpty()) {
                continue;
            }
            // Create the ApplicationInfos
            for (int i = 0; i < apps.size(); i++) {

                LauncherActivityInfo app = apps.get(i);
                PInfo newInfo = new PInfo();
                // This builds the icon bitmaps.
                ComponentName componentName = app.getComponentName();
                String appName =getSystemApplicationName(componentName.getPackageName(),mPackageManager);
                if(!TextUtils.isEmpty(appName)){
                    newInfo.appname = app.getApplicationInfo().loadLabel(getPackageManager()).toString();
                    newInfo.pname = app.getComponentName().getPackageName();
                    newInfo.icon = (Drawable) app.getApplicationInfo().loadIcon(getPackageManager());

                }
                Log.d("tag:","icon=" + newInfo.icon + " " + (newInfo.icon instanceof Drawable));
                list.add(newInfo);
            }
        }
        return list;
    }

    public static String getSystemApplicationName(String packageName, PackageManager packageManager) {
        String applicationName = null;
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            //filter system app
//            if ((applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0 ||
//                    (applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
            applicationName = (String) packageManager.getApplicationLabel(applicationInfo);
            //}

        } catch (PackageManager.NameNotFoundException e) {

        }
        return applicationName;
    }

}
