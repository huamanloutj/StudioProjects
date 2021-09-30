//package com.example.ballfloat.manager.operation;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Path;
//import android.graphics.PorterDuff;
//import android.graphics.PorterDuffXfermode;
//import android.os.Handler;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.GestureDetector;
//import android.view.MotionEvent;
//import android.view.View;
//
/**
 * 功能概述：外部操作球，单击双击，press；
 */
//public class Operation extends View {
//    //View的宽度
//    private int width = 200;
//    private int height = 200;
//
//    //是否单击
//    private boolean isSingleTop;
//
//    private DoubleTap doubleTap = new DoubleTap();
//    private SingleTap singleTap = new SingleTap();
//
//    private Canvas bitmapCanvas;
//    private Bitmap bitmap;
//    private Path   path;
//    private Paint  ballPaint;
//
//    private Paint operationPaint;
//
//    private Paint textPaint;
//
//    private Context context;
//
//    private Handler handler;
//
//    private GestureDetector gestureDetector;
//    public Operation(Context context) {
//        super(context);
//        this.context = context;
//        init();
//    }
//
//    public Operation(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        this.context = context;
//        init();
//    }
//
//    public Operation(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        this.context = context;
//        init();
//    }
//
//    private void init() {
//        //初始化小球画笔
//        Log.e("init:","操作初始化呀");
//        ballPaint = new Paint();
//        ballPaint.setAntiAlias(true);
//        ballPaint.setColor(Color.argb(0xff, 0x3a, 0x8c, 0x6c));
//        //初始化文字画笔
//        textPaint = new Paint();
//        textPaint.setAntiAlias(true);
//        textPaint.setColor(Color.WHITE);
//        textPaint.setTextSize(25);
//
//        handler = new Handler();//初始化handler
//        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        bitmapCanvas = new Canvas(bitmap);
//
//        //手势监听
//        //重点在于将单击和双击操作分隔开
//        GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener() {
//            //双击
//            @Override
//            public boolean onDoubleTap(MotionEvent e) {
//                isSingleTop = false;
//                startDoubleTapAnimation();
//                return super.onDoubleTap(e);
//            }
//            //单击
//            @Override
//            public boolean onSingleTapConfirmed(MotionEvent e) {
//                isSingleTop = true;
//                startSingleTapAnimation();
//                return super.onSingleTapConfirmed(e);//全部返回的是父类的构造方法？
//            }
//        };
//        gestureDetector = new GestureDetector(context, listener);
//        setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return gestureDetector.onTouchEvent(event);
//            }
//        });
//        //接受点击操作
//        Log.e("init:","操作初始化完成" + height + "," + width);
//        setClickable(true);
//    }
//    //线程实现
//    public class DoubleTap implements Runnable{
//        @Override
//        public void run() {
//            invalidate();
//            handler.postDelayed(doubleTap, 50);
//        }
//    }
//    public class SingleTap implements Runnable{
//        @Override
//        public void run() {
//            //Similarly, if in the course of processing the event the view's appearance may need to be changed, the view will call
//            invalidate();
//            handler.postDelayed(singleTap, 100);
//        }
//    }
//
//    private void startDoubleTapAnimation() {
//        handler.postDelayed(doubleTap, 50);
//    }
//    private void startSingleTapAnimation() {
//        handler.postDelayed(singleTap, 100);
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        setMeasuredDimension(width, height);
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        //绘制圆形
//        Log.e("Draw:","画呀");
//        bitmapCanvas.drawCircle(width / 2, height / 2, width / 2, ballPaint);
//        if (!isSingleTop) {
//            //是双击
//            Log.e("Double Tap:","是双击呀");
//        } else {
//            //是单击
//            Log.e("Single Tap","是单击呀");
//        }
//        canvas.drawBitmap(bitmap, 0, 0, null);
//    }
//}
