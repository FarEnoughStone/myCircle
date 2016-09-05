package com.kui.timetables.widget;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/9/1.
 */
public class DaemonRunnable2 implements Runnable {
    private Context context;
    public DaemonRunnable2(Context c) {
        context = c;
    }
    @Override
    public void run() {
        try {
            while (true) {
                if (!WidgetProvider.thread1.isAlive()){
                    WidgetProvider.thread1.start();
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            (Toast.makeText(context,"线程2错误",Toast.LENGTH_SHORT)).show();
        }
    }
}
