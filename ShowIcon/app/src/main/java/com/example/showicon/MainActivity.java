package com.example.showicon;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    private ListView listView_main_regmsg;
    private int[] imgIds = new int[] { R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView_main_regmsg = (ListView) findViewById(R.id.listView_main_regmsg);
        // 创建数据源
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < imgIds.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("username", "wanglu_" + i);
            map.put("pwd", "123456_" + i);
            map.put("imgId", imgIds[i]);
            list.add(map);
        }
        /** 常用的SimpleAdapter的构造方法有五个参数：
         * @param context ：表示上下文对象或者环境对象。
         * @param data ：表示数据源。往往采用List<Map<String, Object>>集合对象。
         * @param resource ：自定义的ListView中每个item的布局文件。用R.layout.文件名的形式来调用。
         * @param from ：其实是数据源中Map的key组成的一个String数组。
         * @param to ：表示数据源中Map的value要放置在item中的哪个控件位置上。其实就是自定义的item布局文件中每个控件的id。
         * 通过R.id.id名字的形式来调用。
         */
        SimpleAdapter adapter = new SimpleAdapter(this, list,
                R.layout.item_listview_main, new String[] { "username", "pwd",
                "imgId" }, new int[] {
                R.id.text_item_listview_username,
                R.id.text_item_listview_pwd,
                R.id.imageView_item_listview_headpic });
        // 给ListView设置适配器
        listView_main_regmsg.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
