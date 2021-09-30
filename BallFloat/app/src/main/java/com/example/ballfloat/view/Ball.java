package com.example.ballfloat.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.ballfloat.R;

/**
 * 定义球的属性和基本绘画
 */
public class Ball extends View {
    //定义属性
    public int width = 150;
    public int height = 150;

    private String text = "50%";//默认显示的文本

    private boolean isDrag; //是否在拖动

    private Paint ballPaint;

    private Paint textPaint;

    private Bitmap bitmap;

    //球的构造方法,构造方法
    public Ball(Context context){
        super(context);
        init();
    }

    public Ball(Context context, AttributeSet attr){
        super(context,attr);
        init();
    }
    public Ball(Context context,AttributeSet attr,int style){
        super(context,attr,style);
        init();
    }
    private void init() {
        ballPaint = new Paint();
        ballPaint.setColor(Color.GRAY);
        ballPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setTextSize(25);
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
        textPaint.setFakeBoldText(true);

        Bitmap src = BitmapFactory.decodeResource(getResources(), R.drawable.ninja);
        //将图片裁剪到指定大小
        bitmap = Bitmap.createScaledBitmap(src, width, height, true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isDrag) {
            canvas.drawCircle(width / 2, height / 2, width / 2, ballPaint);
            float textWidth = textPaint.measureText(text);
            Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
            float dy = -(fontMetrics.descent + fontMetrics.ascent) / 2;
            canvas.drawText(text, width / 2 - textWidth / 2, height / 2 + dy, textPaint);
        } else {
            //正在被拖动时则显示指定图片
            canvas.drawBitmap(bitmap, 0, 0, null);
        }
    }

    //设置当前移动状态
    public void setDragState(boolean isDrag) {
        this.isDrag = isDrag;
        invalidate();
    }
}
