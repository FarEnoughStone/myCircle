package com.kui.timetables.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

public class WidgetProvider extends AppWidgetProvider {
    static final String broadCastString = "com.kui.WidgetUpdate";//定义我们要发送的事件
    static Thread thread1 = null;
    static Thread thread2 = null;
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // TODO Auto-generated method stub
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // TODO Auto-generated method stub
        thread1 = new Thread(new DaemonRunnable1(context));
        thread2 = new Thread(new DaemonRunnable2(context));
        thread1.start();
        thread2.start();
        super.onEnabled(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // TODO Auto-generated method stub
        if (!thread1.isAlive()) {
            thread1 = new Thread(new DaemonRunnable1(context));
            thread2 = new Thread(new DaemonRunnable2(context));
            thread1.start();
            thread2.start();
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
    @Override
    public void onReceive(Context context, Intent intent)
    {
        //当判断到是该事件发过来时， 我们就获取插件的界面， 然后将index自加后传入到textview中
        if(intent.getAction().equals(broadCastString)) {
            new Thread(new DisplayRunnable(context)).start();
//            index++;
//            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget);
//            rv.setTextViewText(R.id.tv2, Integer.toString(index));
//            Date date=new Date();
//            DateFormat format=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
//            String time=format.format(date);
//            Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
//            int year = c.get(Calendar.YEAR);
//            int month = c.get(Calendar.MONTH);
//            int date = c.get(Calendar.DATE);
//            int hour = c.get(Calendar.HOUR_OF_DAY);
//            int minute = c.get(Calendar.MINUTE);
//            int second = c.get(Calendar.SECOND);
////            System.out.println(year + "/" + month + "/" + date + " " +hour + ":" +minute + ":" + second);
//            String s =c.get(Calendar.DAY_OF_WEEK)+" "+c.get(Calendar.WEEK_OF_YEAR)+" "+ year + "/" + month + "/" + date + " "+hour + ":" + minute + ":" + second;
//            rv.setTextViewText(R.id.tv3,s);
//            //将该界面显示到插件中
//            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//            ComponentName componentName = new ComponentName(context,WidgetProvider.class);
//            appWidgetManager.updateAppWidget(componentName, rv);
        }
        // TODO Auto-generated method stub
        super.onReceive(context, intent);
    }
}
