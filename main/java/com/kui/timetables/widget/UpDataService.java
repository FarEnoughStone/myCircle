package com.kui.timetables.widget;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.kui.timetables.MainActivity;
import com.kui.timetables.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/9/1.
 */
public class UpDataService extends Service {
    private SimpleDateFormat df = new SimpleDateFormat("HHmmss");
    public static Context context;
    private Thread thread = null;
    // 覆盖基类的抽象方法
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // 在本服务创建时将监听系统时间的BroadcastReceiver注册
    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK); // 时间的流逝
        intentFilter.addAction(Intent.ACTION_TIME_CHANGED); // 时间被改变，人为设置时间
        registerReceiver(boroadcastReceiver, intentFilter);
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pend = PendingIntent.getActivity(this,0, intent,0);

        //创建Notification
        NotificationCompat.Builder nc = new NotificationCompat.Builder(this);
        nc.setContentTitle("前台Service");
        nc.setContentText("有新消息");
        nc.setSmallIcon(R.drawable.app);
        nc.setContentIntent(pend);
        Notification notifivatiion = nc.build();
        //开启模式为     前台Service
        startForeground(1,notifivatiion);
        thread=new Thread(){
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(1000);
                        context.sendBroadcast(new Intent(WidgetProvider.broadCastString));
                    }
                } catch (Exception e) {
                    (Toast.makeText(context,"服务线程错误",Toast.LENGTH_SHORT)).show();
                }
            }
        };
        thread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!thread.isAlive()) {
            thread.start();
        }
        return START_STICKY;
    }

    // 在服务停止时解注册BroadcastReceiver
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(boroadcastReceiver);
    }

    // 用于监听系统时间变化Intent.ACTION_TIME_TICK的BroadcastReceiver，此BroadcastReceiver须为动态注册
    private BroadcastReceiver boroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context acontext, Intent intent) {
            if (!thread.isAlive()) {
                thread.start();
            }
        }
    };


}
