package searchproject.php.yisa.chengyansy.utils;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.List;

import searchproject.php.yisa.chengyansy.MainActivity;
import searchproject.php.yisa.chengyansy.R;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

/**
 * Created by Administrator on 2017/9/14 0014.
 */

public class NotificationUtils {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public    static void sendBKNotification(Context context){
        NotificationManager mNotificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context.getApplicationContext());
        builder.setSmallIcon(R.drawable.activity);
        builder.setTicker("布控告警");
        builder.setContentTitle("布控告警");
        builder.setContentText("你有最新的消息");
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.activity));
        builder.setDefaults(Notification.DEFAULT_ALL);

        Intent intent=new Intent(context.getApplicationContext(),MainActivity.class);
//        com.example.pc.policetext2.Model.Message message=initJson();

//            Bundle bundle=new Bundle();
        intent.putExtra("message","false");
        //     intent.putExtras(bundle);

        PendingIntent pendingIntent=PendingIntent.getActivity(context.getApplicationContext(), 1, intent, FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        Notification notification = builder.build();
        notification.icon=R.drawable.activity;
        mNotificationManager.notify(123, notification);
    }
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {

                    return true;
                }else{

                    return false;
                }
            }
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static   void sendZBNotification(Context context){
        NotificationManager mNotificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context);

        ApplicationInfo appInfo = context.getApplicationInfo();
//得到该图片的id(name 是该图片的名字，"drawable" 是该图片存放的目录，appInfo.packageName是应用程序的包)
        int resID = context.getResources().getIdentifier("activity", "drawable", appInfo.packageName);

        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.activity));
        builder.setSmallIcon(resID);
        builder.setTicker("周边预警");
        builder.setContentTitle("周边预警");
        builder.setContentText("你有最新消息");
        builder.setDefaults(Notification.DEFAULT_ALL);

        Intent intent=new Intent(context,MainActivity.class);
//        com.example.pc.policetext2.Model.Message message=initJson();

//        Bundle bundle=new Bundle();
        intent.putExtra("message","true");
        // intent.putExtras(bundle);

        PendingIntent pendingIntent=PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        Notification notification = builder.build();
        notification.icon=R.drawable.activity;
        mNotificationManager.notify(321, notification);
    }
}
