package com.example.phoneapp;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.LauncherApps;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.LauncherApps;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private List<Map<String,Object>> data;
    Map<String,Object> item;
    private ListView listView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = new ListView(this);
        data = new ArrayList<Map<String,Object>>();
        listPackages();
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                data,
                R.layout.vlist,
                new String[]{"appname", "versionName", "icon"},
                new int[]{R.id.appname, R.id.versionName, R.id.icon}
        );
        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                if (view instanceof ImageView && data instanceof Drawable) {
                    ImageView iv = (ImageView) view;
                    iv.setImageDrawable((Drawable) data);
                    return true;
                } else
                    return false;
            }
        });
        listView.setAdapter(adapter);
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
                        Log.d("check：","点击了确定按钮！！！！" + which);//which的值是-1
                        //启动系统应用失败，返回Attempt to invoke virtual method 'java.lang.String
                        // android.content.Intent.toString()' on a null object reference
                        Intent intent = getPackageManager().getLaunchIntentForPackage(pname);
                        if (intent != null) {
                            startActivity(intent);
                            // start
                        } else {
                            Toast.makeText(MainActivity.this,"系统应用，无法打开",Toast.LENGTH_LONG).show();
//                            Toast toast = Toast.makeText(global_context, str, showTime);
//                            toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL , 0, 0);  //设置显示位置
//                            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
//                            v.setTextColor(Color.YELLOW);     //设置字体颜色
//                            toast.show();
                            // Toast
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
        //listView.setAdapter(new Adapter2());
        setContentView(listView);
        //setContentView(R.layout.activity_main);
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

    ArrayList<PInfo> apps = new ArrayList<>();
    private void listPackages() {
        apps = getInstalledApps(false);
        final int max = apps.size();
        for (int i = 0; i < max; i++) {
            //apps.get(i).prettyPrint();
            item = new HashMap<String, Object>();
            item.put("appname", apps.get(i).appname);
            item.put("pname", apps.get(i).pname);
            item.put("versionName",apps.get(i).versionName);
            item.put("icon",apps.get(i).icon);
            //item.put("bm",apps.get(i).icon);
            //apps.get(i).prettyPrint();
            data.add(item);
        }
    }

    //    class Adapter2 extends BaseAdapter {
//
//
//        @Override
//        public int getCount() {
//            return apps.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return apps.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            View v;
//            if (convertView == null) {
//                v = getLayoutInflater().inflate(R.layout.vlist, null);
//            }else {
//                v = convertView;
//            }
//            TextView t1 = v.findViewById(R.id.appname);
//            t1.setText(apps.get(position).appname);
//            t1.setTextColor(0xff0000ff);
//
//            ImageView icon = v.findViewById(R.id.icon);
//            icon.setImageDrawable(apps.get(position).icon);
//            return v;
//        }
//    }
    private ArrayList<PInfo> getInstalledApps(boolean getSysPackages) {
        ArrayList<PInfo> res = new ArrayList<PInfo>();
        //List<PackageInfo> packs = new LauncherApps().getActivityList();
        List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if ((!getSysPackages) && (p.versionName == null)) {
                continue;
            }
            PInfo newInfo = new PInfo();
            newInfo.appname = p.applicationInfo.loadLabel(getPackageManager()).toString();
            newInfo.pname = p.packageName;
            newInfo.versionName = p.versionName;
            newInfo.versionCode = p.versionCode;
            newInfo.icon = p.applicationInfo.loadIcon(getPackageManager());
            //newInfo.icon = p.applicationInfo.loadIcon(getPackageManager());//R.drawable.ic_launcher_background;
            //Log.d("tag:","icon=" + newInfo.icon + " " + (newInfo.icon instanceof Drawable));
            //BitmapDrawable bd = (BitmapDrawable) newInfo.icon;
            //newInfo.bm = bd.getBitmap();
            res.add(newInfo);
        }
        return res;
    }
}