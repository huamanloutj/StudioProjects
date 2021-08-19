package com.example.showdialog;

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
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
功能描述：获取手机所有帶图标的app的管理入口
已完成：获取入口，点击可以进入
未解决：图标大小问题如何适应，大小不一致
下一目标：在安卓代码中执行shell脚本
2021-08-17 14：36
 */
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //为什么不是显示调用这个函数,逻辑调用关系。
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //修改成parent，也可以实现效果，改成listView？，输出的内容上不一样
                //String str = parent.getItemAtPosition(position) + "";
                //这里使用参数和真实数据的区别
                //String pname = parent.getItemAtPosition(position).toString();//不是map对象。
                //Log.e("tggg", "here ", new Throwable());
                String pname = data.get(position).get("pname").toString();
                //Log.d("check:", "OnItemClick 实现\n" + pname + "\n position:" + position);
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
        listView.setAdapter(new Adapter2());
        //listView.setAdapter(adapter);
        setContentView(listView);
    }

    class PInfo {
        private String appname = "";
        private String pname = "";
        private String versionName = "";
        private int versionCode = 0;
        private Drawable icon = null;
        //显示图像的位图
        //private Bitmap bm;
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

        private Rect mOldBounds = new Rect();
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
            Drawable icon = pInfos.get(position).icon;

                //final Resources resources = getContext().getResources();
            int width = 300;//(int) resources.getDimension(android.R.dimen.app_icon_size);
            int height = 300;//(int) resources.getDimension(android.R.dimen.app_icon_size);

            final int iconWidth = icon.getIntrinsicWidth();
            final int iconHeight = icon.getIntrinsicHeight();
            if (pInfos.get(position).appname == "淘宝"){
                Log.d("Tag********","iconwidth:" + iconWidth + "iconHeight:" + iconHeight + "*" + pInfos.get(position).pname);
            }


            if (icon instanceof PaintDrawable) {
                PaintDrawable painter = (PaintDrawable) icon;
                painter.setIntrinsicWidth(width);
                painter.setIntrinsicHeight(height);
            }

            if (width > 0 && height > 0 && (width < iconWidth || height < iconHeight)) {
//                final float ratio = (float) iconWidth / iconHeight;
//
//                if (iconWidth > iconHeight) {
//                    height = (int) (width / ratio);
//                } else if (iconHeight > iconWidth) {
//                    width = (int) (height * ratio);
//                }

                final Bitmap.Config c =
                        icon.getOpacity() != PixelFormat.OPAQUE ?
                                Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
                final Bitmap thumb = Bitmap.createBitmap(width, height, c);
                final Canvas canvas = new Canvas(thumb);
                canvas.setDrawFilter(new PaintFlagsDrawFilter(Paint.DITHER_FLAG, 0));
                // Copy the old bounds to restore them later
                // If we were to do oldBounds = icon.getBounds(),
                // the call to setBounds() that follows would
                // change the same instance and we would lose the
                // old bounds
                //Rect mOldBounds;
                mOldBounds.set(icon.getBounds());
                icon.setBounds(0, 0, width, height);
                icon.draw(canvas);
                icon.setBounds(mOldBounds);
                icon = pInfos.get(position).icon = new BitmapDrawable(thumb);


            }
            //ImageView image = (ImageView) v.findViewById(R.id.icon);
            ImageView image = (ImageView) v.findViewById(R.id.icon);//这里改成image是null
            image.setImageDrawable(pInfos.get(position).icon);
            return v;
        }
    }
    public  ArrayList getSystemApp(Context context){
        PackageManager mPackageManager = context.getApplicationContext().getPackageManager();
        LauncherApps mLauncherApps = (LauncherApps) context.getSystemService(Context.LAUNCHER_APPS_SERVICE);
        UserManager mUserManager = (UserManager) context.getSystemService(Context.USER_SERVICE);
        List<UserHandle> users = mUserManager.getUserProfiles();
        List<UserHandle> profiles= users == null ? Collections.<UserHandle>emptyList() : users;
        //Log.i("tag","users:"+ users);
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
                        //newInfo.icon = (Drawable) app.getBadgedIcon(DisplayMetrics.DENSITY_DEFAULT);
                        newInfo.icon = (Drawable) app.getApplicationInfo().loadIcon(getPackageManager());

                }
                //Log.d("tag:","icon=" + newInfo.icon + " " + (newInfo.icon instanceof Drawable));
                list.add(newInfo);
            }
        }
        return list;
    }
    public void bindView(View view,LauncherActivityInfo ai){

        Drawable icon = ai.getBadgedIcon(DisplayMetrics.DENSITY_DEFAULT);
        ImageView image = (ImageView) view.findViewById(R.id.image);
        image.setImageDrawable(icon);
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