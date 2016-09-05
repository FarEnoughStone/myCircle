package com.kui.timetables.widget;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Administrator on 2016/9/1.
 */
public class DaemonRunnable1 implements Runnable {
    private Context context;
    public DaemonRunnable1(Context c) {
        context = c;
    }
    @Override
    public void run() {
        try {
            while (true) {
                if (!WidgetProvider.thread2.isAlive()){
                    WidgetProvider.thread2.start();
                }
                context.sendBroadcast(new Intent(WidgetProvider.broadCastString));
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            (Toast.makeText(context,"线程1错误",Toast.LENGTH_SHORT)).show();
        }
    }
    public boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }
}
