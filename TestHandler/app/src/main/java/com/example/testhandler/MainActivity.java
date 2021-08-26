package com.example.testhandler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
public class MainActivity extends Activity implements OnClickListener{
    private Button button1 = null;
    private Button button2 = null;
    private Button button3 = null;
    //private Button button4 = null;
    private MyHandler mHandler = null;
    private MyHandler mInnerHandler = null;
    private Message msg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button)findViewById(R.id.button_1);
        button2 = (Button)findViewById(R.id.button_2);
        button3 = (Button)findViewById(R.id.button_3);
        //button4 = (Button)findViewById(R.id.button_4);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        //button4.setOnClickListener(this);
        Log.e("test1","onCreate");
    }
    @Override
    public void onClick(View v) {
        Log.e("test1","onClick");
        dealOnClick(v.getId());
    }
    /**
     * 自定义一个处理onClick事件的方法
     * @param msgId
     */
    private void dealOnClick(int msgId) {
        mHandler = new MyHandler(); //子线程和主线程都可以操作这个mHandler，当然也可以在switch的具体case中才创建

        switch(msgId){
            case R.id.button_1:
                // 主线程中创建MyHandler对象，mHandler创建Handler消息类型为1、消息对象为字符串的Message对象
                //mHandler = new MyHandler();
                msg = mHandler.obtainMessage(1, (Object)"Main thread send message by Message Object");
                //https://blog.csdn.net/h3c4lenovo/article/details/7914902为什么使用obtainMessage，
                /**
                 * Returns a new {@link android.os.Message Message} from the global message pool. More efficient than
                 * creating and allocating new instances. The retrieved message has its handler set to this instance (Message.target == this).
                 * If you don't want that facility, just call Message.obtain() instead.
                 */
                msg.sendToTarget(); //Handler 发送消息
                break;
            case R.id.button_2:
                InnerThread innerThread = new InnerThread();
                innerThread.start(); //内部线程异步处理button_2的事件
                break;
            case R.id.button_3:
                //mHandler = new MyHandler();
                SubThread subThread = new SubThread(mHandler); //把主线程中创建的mHandler对象传递给子线程
                subThread.start(); //子线程对象，用于异步处理Handler Message消息
                break;
        }
    }

    /**
     * 自定义MyHandler，继承自Handler，覆盖父类的 handleMessage() 方法，msg.obj
     * 是Message对象传递过来的Object对象，本例中为String对象 。
     * @author lion
     *
     */
    public class MyHandler extends Handler{
        public MyHandler(Looper myLooper){
            super(myLooper);  //重写构造方法，通过Looper创建MyHandler对象
        }
        public MyHandler(){

        }
        @Override
        public void handleMessage(Message msg){
            Log.e("test1","MyHandler handleMessage " + msg.what);
            String str = "";
            switch(msg.what){
                case 1:
                    str = "1: " + msg.obj;
                    break;
                case 2:
                    str = "2: " + msg.obj;
                    break;
                case 3:
                    str = "3: " + msg.obj;
//                    Button b = findViewById(R.id.button_3);
//                    b.setVisibility(View.INVISIBLE);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            Toast toast = Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private class InnerThread extends Thread{
        @Override
        public void run(){

            //只能在Activity的主线程中使用不带Looper对象的构造函数来创建Handler对象，所以此行会抛出异常
            //mHandler = new MyHandler();

            //Lopper.myLooper()获取Looper为null，所以此行会抛出异常
            //mHandler = new MyHandler(Looper.myLooper());

            //通过Looper.getMainLooper()获取父类的looper可以成功创建Handler对象并将Message了送到父类
            Log.e("test1","InnerThread  prepare ");
            Looper.prepare();
            Log.e("test1","InnerThread  after prepare ");
            mInnerHandler = new MyHandler();
//            mHandler = new MyHandler();

            Message msg = mInnerHandler.obtainMessage(2, (Object)"Inner thread send message by Message Object");
            msg.sendToTarget();
            Looper.loop();
        }
    }

    /**
     * 子线程通过Handler对象创建Message对象，并在子线程中发送Handler消息，这个消息将由主线程在handleMessage中接收并响应
     * @author lion
     *
     */
    public class SubThread extends Thread{
        private Handler mHandler;
        public SubThread(Handler mHandler){
            this.mHandler = mHandler;
        }
        @Override
        public void run(){
            Log.e("test1","SubThread  run ");
            Message msg = mInnerHandler.obtainMessage(3, (Object)"Other thread send message by myHandler");
            mInnerHandler.sendMessage(msg);

            msg = mInnerHandler.obtainMessage(4, (Object)"Other thread send message by myHandler");
            mInnerHandler.sendMessageDelayed(msg, 3000);

        }
    }
}