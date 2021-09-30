package com.example.ballfloat.view;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import com.example.ballfloat.R;
import com.example.ballfloat.manager.BallManager;

/**
 * 定义点击球之后实现的功能菜单，动画实现
 */
public class Menu extends LinearLayout {

    private TranslateAnimation animation;

    public Menu(Context context) {

        super(context);//没有自己的构造函数

        View root = View.inflate(context, R.layout.menu,null);
        LinearLayout layout = root.findViewById(R.id.layout);
        //为什么用SELF
        animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,
                Animation.RELATIVE_TO_SELF,0,
                Animation.RELATIVE_TO_SELF,1.0F,
                Animation.RELATIVE_TO_SELF,0
                );
        animation.setDuration(3000);//动画完成时间
        animation.setFillAfter(true);//如果fillAfter的值为真的话，动画结束后，控件停留在执行后的状态
        animation.setBackgroundColor(0xFFFFFF33);//设置到那里了
        layout.setAnimation(animation);
        //匿名函数
        root.setOnTouchListener((v,event) -> {
            BallManager manager = BallManager.getInstance(context);
            manager.showBall();
            manager.hideMenu();
            Log.e("Menu Test","显示Ball，删除Menu" + manager);
            return false;//为什么是return false
        });
        addView(root);
    }
    public void startAnimation(){animation.start();}
}
