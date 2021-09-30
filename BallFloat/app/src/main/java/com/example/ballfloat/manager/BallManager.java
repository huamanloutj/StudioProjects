package com.example.ballfloat.manager;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.View.OnClickListener;
import android.view.WindowId;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Toast;

import com.example.ballfloat.IBall;
import com.example.ballfloat.view.Ball;
import com.example.ballfloat.view.Menu;

import java.lang.reflect.Field;

/**
 * 功能概述：实现改变一个球的外观，控制球的一切活动
 * 增加功能：一切功能都应该在AIDL中定义
 */
public class BallManager  extends IBall.Stub {

    private Ball ball;
    private WindowManager windowManager;
    private LayoutParams ballParams;

    private Menu menu;
    private LayoutParams menuParams;

    private Context context;
    private static volatile BallManager manager;//静态变量，全局唯一
    private boolean isShow = false;//默认是false

    public BallManager(Context context){
        this.context = context;
        init();
    }

    //获取BallManager实例,单例模式获取BallManager
    public static BallManager getInstance(Context context) {
        Log.e("Create Manager","******");
        if (manager == null) {
            synchronized (BallManager.class) {
                if (manager == null) {
                    manager = new BallManager(context);
                }
            }
        }
        return manager;
    }
    public boolean isShow(){
        return this.isShow;
    }
    private void init(){
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);//管理窗口的manager
        ball = new Ball(context);
        menu = new Menu(context);
        //menu的菜单
        OnTouchListener touchListener = new OnTouchListener(){
            float startX;
            float startY;
            float tempX;
            float tempY;
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getRawX();
                        startY = event.getRawY();
                        tempX = event.getRawX();
                        tempY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float x = event.getRawX() - startX;
                        float y = event.getRawY() - startY;
                        //计算偏移量，刷新视图,
                        ballParams.x += (int)x;
                        ballParams.y += (int)y;
                        ball.setDragState(true);
                        windowManager.updateViewLayout(ball, ballParams);
                        startX = event.getRawX();
                        startY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        //判断松手时View的横坐标是靠近屏幕哪一侧，将View移动到依靠屏幕
                        float endX = event.getRawX();
                        float endY = event.getRawY();
                        if (endX < getScreenWidth() / 2) {
                            endX = 0;
                        } else {
                            endX = getScreenWidth() - ball.width;
                        }
                        ballParams.x = (int) endX;
                        ball.setDragState(false);
                        windowManager.updateViewLayout(ball, ballParams);
                        //如果初始落点与松手落点的坐标差值超过6个像素，则拦截该点击事件
                        //否则继续传递，将事件交给OnClickListener函数处理
                        if (Math.abs(endX - tempX) > 6 && Math.abs(endY - tempY) > 6) {
                            return true;
                        }
                        break;
                }
                return false;
            }
        };
        OnClickListener clickListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                //windowManager.removeView(ball);
                hideBall();
                showMenu();
                menu.startAnimation();
            }
        };
        ball.setOnTouchListener(touchListener);
        ball.setOnClickListener(clickListener);
     }
    //show ball,一定要提示显示窗口
    public void showBall() {
        if (ballParams == null) {
            ballParams = new LayoutParams();
            ballParams.width = ball.width;
            ballParams.height = ball.height - getStatusHeight();
            ballParams.gravity = Gravity.TOP | Gravity.START;
            ballParams.setTitle("Ball");
            ballParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR |
                    WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
            ballParams.format = PixelFormat.RGBA_8888;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ballParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                ballParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
            }
        }
        try{
            windowManager.addView(ball, ballParams);
            isShow = true;
        }catch (Exception e){
            Toast.makeText(context,"服务已在运行，不要重复添加",Toast.LENGTH_LONG).show();
        }
//        ball.setScaleX(0.1f);
//        ball.setScaleY(0.1f);
//        ball.animate().scaleX(2f).scaleY(2f).setDuration(3000).start();
    }
    //显示底部菜单,为啥用private
    public void showMenu() {
        if (menuParams == null) {
            menuParams = new LayoutParams();
            menuParams.width = getScreenWidth();
            menuParams.height = getScreenHeight() - getStatusHeight();
            menuParams.gravity = Gravity.BOTTOM;
            menuParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR |
                    WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
            menuParams.format = PixelFormat.RGBA_8888;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                menuParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                menuParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
            }
        }
        Log.e("Show Menu","显示菜单");
        windowManager.addView(menu, menuParams);
    }
    private int getStatusHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");//?*******
            Object object = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = (Integer) field.get(object);
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            return 0;
        }
    }

    public void setColor(){
        Log.e("Set Color","换个颜色");
    }
    public void colorListener(){}
    //获取屏幕宽度,
    private int getScreenWidth() {
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        return point.x;
    }

    //获取屏幕高度
    private int getScreenHeight() {
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        return point.y;
    }

    //隐藏球
    public void hideBall(){
        if (ball != null && isShow() == true){
            windowManager.removeView(ball);
            isShow = false;
        }
    }
    //隐藏菜单,removeBall;先检查下是否下show过
    public void hideMenu(){
        if (menu != null){
            Log.e("Remove View","Revove 了");
            windowManager.removeView(menu);
        }
    }
}
