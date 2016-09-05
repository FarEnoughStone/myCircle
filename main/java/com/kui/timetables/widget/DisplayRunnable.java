package com.kui.timetables.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.kui.timetables.R;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/9/2.
 */
public class DisplayRunnable implements Runnable {
    private Context context;
    private String tv1, tv2, tv3, tv4, tv5, tv6;

    public DisplayRunnable(Context c) {
        context = c;
    }

    @Override
    public void run() {
        try {
            init();
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget);
            rv.setTextViewText(R.id.tv1,tv1);
            rv.setTextViewText(R.id.tv2,tv2);
            rv.setTextViewText(R.id.tv3,tv3);
            rv.setTextViewText(R.id.tv4,tv4);
            rv.setTextViewText(R.id.tv5,tv5);
            rv.setTextViewText(R.id.tv6,tv6);
            //将该界面显示到插件中
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName componentName = new ComponentName(context,WidgetProvider.class);
            appWidgetManager.updateAppWidget(componentName, rv);
        } catch (Exception e) {
            (Toast.makeText(context, "线程1错误", Toast.LENGTH_SHORT)).show();
        }
    }

    private void init() {
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
        int second = c.get(Calendar.SECOND);
        //int weekOfYear = c.get(Calendar.WEEK_OF_YEAR);
        int minuteOfDay = c.get(Calendar.HOUR_OF_DAY) * 60 + c.get(Calendar.MINUTE);
        tv2 = context.getResources().getString(R.string.nowProject);
        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                tv1 = tv2 = tv3 = tv4 = tv5 = tv6 = null;
                tv2 = "明天课程：" + context.getResources().getString(R.string.bandaoti);
                break;
            case 7:
                tv1 = tv2 = tv3 = tv4 = tv5 = tv6 = null;
                tv2 = "后天课程：" + context.getResources().getString(R.string.bandaoti);
                break;
            case 2:
                if (minuteOfDay < 615) {
                    //上课前
                    tv1 = tv2 = tv3 = tv4 = tv5 = tv6 = null;
                    tv4=context.getResources().getString(R.string.nextProject)+
                            context.getResources().getString(R.string.bandaoti);
                    tv5 = context.getResources().getString(R.string.classRoom_A240);
                    tv6 = "距上课时间还有：" + (615 - 1 - minuteOfDay) + ":" + (60 - second);
                } else if (minuteOfDay >= 615 && minuteOfDay < 720) {
                    //上课中
                    tv2=context.getResources().getString(R.string.nowProject)+
                            context.getResources().getString(R.string.bandaoti);
                    tv3 = context.getResources().getString(R.string.classRoom_A240);
                    tv1 = "距下课时间还有：" + (720 - 1 - minuteOfDay) + ":" + (60 - second);
                    tv4=context.getResources().getString(R.string.nextProject)+
                            context.getResources().getString(R.string.verilog);
                    tv5 = context.getResources().getString(R.string.classRoom_B205);
                    tv6 = "距上课时间还有：" + (870 - 1 - minuteOfDay) + ":" + (60 - second);
                }else if (minuteOfDay >= 720 && minuteOfDay < 870) {
                    //中午
                    tv1 = tv2 = tv3 = null;
                    tv4=context.getResources().getString(R.string.nextProject)+
                            context.getResources().getString(R.string.verilog);
                    tv5 = context.getResources().getString(R.string.classRoom_B205);
                    tv6 = "距上课时间还有：" + (870 - 1 - minuteOfDay) + ":" + (60 - second);
                }else if (minuteOfDay >= 870 && minuteOfDay < 975) {
                    //上课中
                    tv2 = context.getResources().getString(R.string.nextProject) +
                            context.getResources().getString(R.string.verilog);
                    tv3 = context.getResources().getString(R.string.classRoom_B205);
                    tv1 = "距下课时间还有：" + (976 - minuteOfDay) + ":" + (60 - second);
                    tv4 = "明天课程：" + context.getResources().getString(R.string.maogai);
                } else if (minuteOfDay >= 975) {
                    tv1 = tv2 = tv3 = tv4 = tv5 = tv6 = null;
                    tv4 = "明天课程：" + context.getResources().getString(R.string.maogai);
                }break;
            case 3:
                if (minuteOfDay < 975) {
                    //早上
                    tv1 = tv2 = tv3 = tv4 = tv5 = tv6 = null;
                    tv4=context.getResources().getString(R.string.nextProject)+
                            context.getResources().getString(R.string.maogai);
                    tv5=context.getResources().getString(R.string.classRoom_A244);
                } else if (minuteOfDay >= 975 && minuteOfDay < 995) {
                    //第三节课下课~第四节课上课
                    tv6= "距上课时间还有：" + (995 - 1 - minuteOfDay) + ":" + (60 - second);
                }else if (minuteOfDay >= 995 && minuteOfDay < 1100) {
                    //第四节课上课中
                    tv2 = context.getResources().getString(R.string.nowProject) +
                            context.getResources().getString(R.string.maogai);
                    tv3 = context.getResources().getString(R.string.classRoom_A244);
                    tv1 = "距下课时间还有：" + (1100 - 1 - minuteOfDay) + ":" + (60 - second);
                    tv4 = "明天课程为：" + context.getResources().getString(R.string.bandaoti);
                    tv5 = tv6 = null;
                }else if (minuteOfDay >= 1100) {
                    //晚上
                    tv1 = tv2 = tv3 = tv4 = tv5 = tv6 = null;
                    tv4 = "明天课程为：" + context.getResources().getString(R.string.bandaoti);
                }break;
            case 4:
                if (minuteOfDay < 480) {
                    //早上
                    tv1 = tv2 = tv3 = tv4 = tv5 = tv6 = null;
                    tv4 = context.getResources().getString(R.string.nextProject)
                            + context.getResources().getString(R.string.bandaoti);
                } else if (minuteOfDay >= 480 && minuteOfDay < 585) {
                    //第一节课上课中
                    tv2=context.getResources().getString(R.string.nowProject)+
                            context.getResources().getString(R.string.bandaoti);
                    tv3 = context.getResources().getString(R.string.classRoom_A201);
                    tv1 = "距下课时间还有：" + (585 - 1 - minuteOfDay) + ":" + (60 - second);
                    tv4=context.getResources().getString(R.string.nextProject)+
                            context.getResources().getString(R.string.szxtzcysj);
                    tv5 = context.getResources().getString(R.string.classRoom_A208);
                    tv6 = "距上课时间还有：" + (615 - 1 - minuteOfDay) + ":" + (60 - second);
                }else if (minuteOfDay >= 585 && minuteOfDay < 615) {
                    //第一节课下课~第二节课上课
                    tv1 = tv2 = tv3 = null;
                    tv4=context.getResources().getString(R.string.nextProject)+
                            context.getResources().getString(R.string.szxtzcysj);
                    tv5 = context.getResources().getString(R.string.classRoom_A208);
                    tv6 = "距上课时间还有：" + (615 - 1 - minuteOfDay) + ":" + (60 - second);
                }else if (minuteOfDay >= 615 && minuteOfDay < 720) {
                    //第二节课上课中
                    tv2=context.getResources().getString(R.string.nextProject)+
                            context.getResources().getString(R.string.szxtzcysj);
                    tv3 = context.getResources().getString(R.string.classRoom_A208);
                    tv1 = "距下课时间还有：" + (720 - 1 - minuteOfDay) + ":" + (60 - second);
                    tv4 = "明天课程为：" + context.getResources().getString(R.string.maogai);
                    tv5 = tv6 = null;
                }else if (minuteOfDay >= 720 ) {
                    //中午后
                    tv1 = tv2 = tv3 = tv4 = tv5 = tv6 = null;
                    tv4 = "明天课程为：" + context.getResources().getString(R.string.maogai);
                }break;
            case 5:
                if (minuteOfDay < 975) {
                    //早上
                    tv1 = tv2 = tv3 = tv4 = tv5 = tv6 = null;
                    tv4=context.getResources().getString(R.string.nextProject)+
                            context.getResources().getString(R.string.maogai);
                    tv5=context.getResources().getString(R.string.classRoom_A120);
                } else if (minuteOfDay >= 975 && minuteOfDay < 995) {
                    //第三节课下课~第四节课上课
                    tv6= "距上课时间还有：" + (995 - 1 - minuteOfDay) + ":" + (60 - second);
                }else if (minuteOfDay >= 995 && minuteOfDay < 1100) {
                    //第四节课上课中
                    tv2 = context.getResources().getString(R.string.nowProject) +
                            context.getResources().getString(R.string.maogai);
                    tv3 = context.getResources().getString(R.string.classRoom_A120);
                    tv1 = "距下课时间还有：" + (1100 - 1 - minuteOfDay) + ":" + (60 - second);
                    tv4 = "明天课程为：" + context.getResources().getString(R.string.bandaoti);
                    tv5 = tv6 = null;
                }else if (minuteOfDay >= 1100) {
                    //晚上
                    tv1 = tv2 = tv3 = tv4 = tv5 = tv6 = null;
                    tv4 = "明天课程为：" + context.getResources().getString(R.string.szxtzcysj);
                }break;
            case 6:
                if (minuteOfDay < 585) {
                    //早上
                    tv1 = tv2 = tv3 = tv4 = tv5 = tv6 = null;
                    tv4=context.getResources().getString(R.string.nextProject)+
                            context.getResources().getString(R.string.szxtzcysj);
                    tv5=context.getResources().getString(R.string.classRoom_A412);
                }else if (minuteOfDay >= 585 && minuteOfDay < 615) {
                    //第一节课下课~第二节课上课
                    tv6= "距上课时间还有：" + (615 - 1 - minuteOfDay) + ":" + (60 - second);
                }else if (minuteOfDay >= 615 && minuteOfDay < 720) {
                    //第二节课上课中
                    tv2 = context.getResources().getString(R.string.nowProject) +
                            context.getResources().getString(R.string.szxtzcysj);
                    tv3 = context.getResources().getString(R.string.classRoom_A412);
                    tv1 = "距下课时间还有：" + (720 - 1 - minuteOfDay) + ":" + (60 - second);
                    tv4 = "下周课程为：" + context.getResources().getString(R.string.bandaoti);
                    tv5 = tv6 = null;
                }else if (minuteOfDay >= 720 ) {
                    //中午后
                    tv1 = tv2 = tv3 = tv4 = tv5 = tv6 = null;
                    tv4 = "下周课程为：" + context.getResources().getString(R.string.bandaoti);
                }break;
            default:
                (Toast.makeText(context, "Diaplay线程出错！", Toast.LENGTH_LONG)).show();
//                if (minuteOfDay < 480) {
//                    //早上
//                    tv1 = tv2 = tv3 = tv4 = tv5 = tv6 = null;
//                } else if (minuteOfDay >= 480 && minuteOfDay < 585) {
//                    //第一节课上课中
//                }else if (minuteOfDay >= 585 && minuteOfDay < 615) {
//                    //第一节课下课~第二节课上课
//                }else if (minuteOfDay >= 615 && minuteOfDay < 720) {
//                    //第二节课上课中
//                }else if (minuteOfDay >= 720 && minuteOfDay < 870) {
//                    //中午
//                }else if (minuteOfDay >= 870 && minuteOfDay < 975) {
//                    //第三节课上课中
//                }else if (minuteOfDay >= 975 && minuteOfDay < 995) {
//                    //第三节课下课~第四节课上课
//                }else if (minuteOfDay >= 995 && minuteOfDay < 1100) {
//                    //第四节课上课中
//                }else if (minuteOfDay >= 1100) {
//                    //晚上
//                }
        }
    }
}
