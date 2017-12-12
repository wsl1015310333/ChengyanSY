package searchproject.php.yisa.chengyansy.fragment;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import searchproject.php.yisa.chengyansy.Adapter_Alarm.Alarm_alarmAdapter;
import searchproject.php.yisa.chengyansy.AlarmIntentService;
import searchproject.php.yisa.chengyansy.Alarm_warning_ViewActivity;
import searchproject.php.yisa.chengyansy.Domain.AlarmDao;
import searchproject.php.yisa.chengyansy.FilesUtils;
import searchproject.php.yisa.chengyansy.MainActivity;
import searchproject.php.yisa.chengyansy.Model_All.Read_alarmJson;
import searchproject.php.yisa.chengyansy.Model_All.Read_alarm_detail;
import searchproject.php.yisa.chengyansy.R;
import searchproject.php.yisa.chengyansy.utils.HttpConnectionUtils;
import searchproject.php.yisa.chengyansy.utils.ResulutionJson;
import searchproject.php.yisa.chengyansy.view.ExpandLayout;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;


/**
 * Created by Administrator on 2017/7/17 0017.
 */

public class BukongyujingFragment extends Fragment {
    BukongyujingFragment.MyReceiver receiver;
    Alarm_alarmAdapter adapter;
    public static List<Read_alarmJson> list=new ArrayList<Read_alarmJson>();
    public static int  count=0;
  public   static ExpandLayout   bkyj_unread;
     static LinearLayout bkyj_nodate;
     static    TextView bkyj_alarm_warning_showText;
    View view;
    RecyclerView bkyj_Recycerview;

    protected WeakReference<View> mRootView;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 200:
                   String result= (String) msg.obj;
                   Read_alarm_detail read_alarm_detail=ResulutionJson.read_alarm_detailJson(result);
                    Intent intent=new Intent(getActivity(), Alarm_warning_ViewActivity.class);
                     Bundle bundle=new Bundle();
                    bundle.putSerializable("result",read_alarm_detail);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        view= View.inflate(getActivity(), R.layout.bukongyujing_fragment,null);
        // detach/attach can lead to view recreate frequently
        if (mRootView == null || mRootView.get() == null) {
        //    View view=inflater.inflate(R.layout.tab_fragment, null);
            view= View.inflate(getActivity(), R.layout.bukongyujing_fragment,null);
            mRootView = new WeakReference<View>(view);
        } else {
            ViewGroup parent = (ViewGroup) mRootView.get().getParent();
            if (parent != null) {
                parent.removeView(mRootView.get());
            }
        }

        initView();
        initData();
        initReceiver();
initListData();
        return mRootView.get();
    }
public void initListData(){
    AlarmDao sdao = new AlarmDao(getActivity());
    List<Read_alarmJson> read_alarmJson=null;
    List<Read_alarmJson> read_UnalarmJson=null;
    String uid=   FilesUtils.sharedGetFile(getActivity(),"user","uid");
    if(uid.length()>0) {
        read_alarmJson= sdao.findRead("Read_BKalarmJson_Read");
         read_UnalarmJson= sdao.findRead("Read_BKalarmJson_UnRead");

        if (read_alarmJson!=null&&read_alarmJson.size() != 0) {
        //    Toast.makeText(getActivity(), "read_alarmJson.size()=" + read_alarmJson.size() + "-" + read_alarmJson.get(0).getId() + read_alarmJson.get(0).getPic() + read_alarmJson.get(0).getPlate() + read_alarmJson.get(0).getCaptureTime() + read_alarmJson.get(0).getLocationName() + read_alarmJson.get(0).getReason(), Toast.LENGTH_SHORT).show();
            list.addAll(0, read_alarmJson);
            adapter.notifyDataSetChanged();

            bkyj_nodate.setVisibility(View.GONE);
            bkyj_Recycerview.setVisibility(View.VISIBLE);

        }
        if (read_UnalarmJson.size() != 0) {
          //  Toast.makeText(getActivity(), "read_UnalarmJson.size()=" + read_UnalarmJson.size(), Toast.LENGTH_SHORT).show();


            count = read_UnalarmJson.size();
            bkyj_alarm_warning_showText.setText("你有" + read_UnalarmJson.size() + "条新数据");
            cacheList.addAll(0, read_UnalarmJson);
        } else {
            bkyj_unread.collapse();
        }
    }else {
        bkyj_unread.collapse();
    }


}

    private void initReceiver(){
        receiver = new BukongyujingFragment.MyReceiver(handler);
        Intent startIntent = new Intent(getActivity(), AlarmIntentService.class);
        getActivity().startService(startIntent);
        getActivity().registerReceiver(receiver,new IntentFilter(("some.action.alarm")));


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity(). unregisterReceiver(receiver);
    }

    private void initView(){
        bkyj_unread=(ExpandLayout)view.findViewById(R.id.bkyj_unread);
        bkyj_nodate=(LinearLayout)view.findViewById(R.id.bkyj_nodate);
        bkyj_nodate.setVisibility(View.VISIBLE);
        bkyj_alarm_warning_showText=(TextView)view.findViewById(R.id.bkyj_alarm_warning_showText);
        bkyj_Recycerview=(RecyclerView)view.findViewById(R.id.bkyj_Recycerview);
     //   bkyj_unread.toggleExpand();
    }
    private void initData(){
        adapter=new Alarm_alarmAdapter(list,getActivity());
        bkyj_Recycerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        bkyj_Recycerview.setAdapter(adapter);
        adapter.setOnItemClickLitener(new Alarm_alarmAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, final int position) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        HashMap hashMap=new HashMap();
                        hashMap.put("id",list.get(position).getId());
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                           Toast.makeText(getActivity(),list.get(position).getId(),Toast.LENGTH_SHORT).show();
//                            }
//                        });
                       final String result= HttpConnectionUtils.sendGETRequestRight(HttpConnectionUtils.ipport+"/api/bk_detail",hashMap,null);
                     //    String result=initJson();
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(getActivity(),result+" ",Toast.LENGTH_SHORT).show();
//                            }
//                        });
                        if(result.length()<30){

                        }else {
                            Message message=handler.obtainMessage();
                            message.obj=result;
                            message.what=200;
                            handler.sendMessage(message);
                        }
                    }
                }).start();

            }
            @Override
            public void onItemLongClick(View view, int position) {
            }
        });


        bkyj_unread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlarmDao sdao = new AlarmDao(getActivity());
                list.addAll(0,cacheList);
                for(Read_alarmJson read_alarmJson :cacheList) {
                    sdao.addRead(read_alarmJson, "Read_BKalarmJson_Read");
                }
                if(list.size()>100){
                    int n=list.size()-100;
                    for (int i = 0; i < n; i++) {
                        list.remove(list.size() -1);
                    }
                }
                sdao.delete("100","Read_BKalarmJson_Read");
                cacheList.clear();
                sdao.deleteUnRead("Read_BKalarmJson_UnRead");
                bkyj_unread.collapse();
                count=0;
                adapter.notifyDataSetChanged();
                bkyj_nodate.setVisibility(View.GONE);
            }
        });
    }
    static List<Read_alarmJson> cacheList =new ArrayList();
    public  static   class MyReceiver extends BroadcastReceiver {

        private final Handler handler; // Handler used to execute code on the UI thread

        public MyReceiver(Handler handler) {
            this.handler = handler;
        }



        //            private static MyReceiver instance = null;
//
//           public static MyReceiver getInstance() {
//             if (instance == null) {                              //line 12
//                   instance = new MyReceiver();          //line 13
//                }
//             return instance;
//            }

        private boolean isOpen=true;
        private String saveCrashInfo2File(String  json) {

//        StringBuffer sb = new StringBuffer();
//        for (Map.Entry<String, String> entry : infos.entrySet()) {
//            String key = entry.getKey();
//            String value = entry.getValue();
//            sb.append(key + "=" + value + "\n");
//        }

//        Writer writer = new StringWriter();
//        PrintWriter printWriter = new PrintWriter(writer);
//        ex.printStackTrace(printWriter);
//        Throwable cause = ex.getCause();
//        while (cause != null) {
//            cause.printStackTrace(printWriter);
//            cause = cause.getCause();
//        }
//        printWriter.close();
//        String result = writer.toString();
//        sb.append(result);
            try {
                Random random=new Random();

                String timestamp = System.currentTimeMillis()+random.nextInt(1000)+"";
                String time="2017"+timestamp;
                String fileName ="-" + time + "-" + timestamp + ".log";
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download/";
                    File dir = new File(path);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    FileOutputStream fos = new FileOutputStream(path + fileName);
                    fos.write(json.toString().getBytes());
                    fos.close();
                }
                return fileName;
            } catch (Exception e) {

            }
            return null;
        }
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        private    void sendNotification(Context context){
            NotificationManager mNotificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder builder = new Notification.Builder(context.getApplicationContext());
            builder.setSmallIcon(R.drawable.activity);
            builder.setTicker("布控告警");
            builder.setContentTitle("布控告警");
            builder.setContentText("你有最新的消息");
            builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.activity));
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
        public  boolean isBackground(Context context) {
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
        @Override
        public void onReceive(final Context context, Intent intent) {
            switch (intent.getAction()){
//                case "some.action.alarm":
//                    if (intent!=null){
//
//                    }
//                    break;
                case  "some.action.alarm":
                    if (intent!=null){

                        final String json=   intent.getStringExtra("alarm");
              //        Toast.makeText(context,"bukong-"+json,Toast.LENGTH_SHORT).show();
                       if(isBackground(context)){
                          // Toast.makeText(context,"---"+json,Toast.LENGTH_SHORT).show();
                           sendNotification(context);
                       }
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                saveCrashInfo2File(json);
//                            }
//                        }).start();
                        Log.e("toggle",Integer.toString(count));
                        count++;

                        final List<Read_alarmJson> read_alarmJson= ResulutionJson.read_alarmJson(json);

                        cacheList.addAll(read_alarmJson);
                        AlarmDao sdao = new AlarmDao(context);
                        for(Read_alarmJson alarmJson : read_alarmJson){
                            sdao.addUnRead(alarmJson,"Read_BKalarmJson_UnRead");
                        }
                        if (count==1) {
                            bkyj_unread .expand();
                        }



                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                bkyj_nodate.setVisibility(View.GONE);
                                bkyj_alarm_warning_showText .setText("你有"+(count+read_alarmJson.size()-1)+"条新数据");
                                count=count+read_alarmJson.size()-1;

                            }
                        });
                        //     Log.e("status",status);
                    }
                    break;
            }

            // Post the UI updating code to our Handler

        }
    }
    private String saveCrashInfo2File(String  json) {

//        StringBuffer sb = new StringBuffer();
//        for (Map.Entry<String, String> entry : infos.entrySet()) {
//            String key = entry.getKey();
//            String value = entry.getValue();
//            sb.append(key + "=" + value + "\n");
//        }

//        Writer writer = new StringWriter();
//        PrintWriter printWriter = new PrintWriter(writer);
//        ex.printStackTrace(printWriter);
//        Throwable cause = ex.getCause();
//        while (cause != null) {
//            cause.printStackTrace(printWriter);
//            cause = cause.getCause();
//        }
//        printWriter.close();
//        String result = writer.toString();
//        sb.append(result);
        try {
            Random random=new Random();

            String timestamp = System.currentTimeMillis()+random.nextInt(1000)+"";
            String time="2017"+timestamp;
            String fileName ="-" + time + "-" + timestamp + ".log";
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download/";
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(json.toString().getBytes());
                fos.close();
            }
            return fileName;
        } catch (Exception e) {

        }
        return null;
    }
    String initJson(){
        String result="{\n" +
                "    \"rid\": 8177,\n" +
                "    \"cap_id\": 20,\n" +
                "    \"direction_id\": 4,\n" +
                "    \"location_id\": \"370602001594\",\n" +
                "    \"image_url\": \"\",\n" +
                "    \"year_id\": 372,\n" +
                "    \"info_id\": \"599809c8-aea7-2d4c-b201-fe1f6973cabd\",\n" +
                "    \"plate_number\": \"鲁FT9806\",\n" +
                "    \"plate_number2\": \"鲁FT9806\",\n" +
                "    \"match_lp_id\": 1,\n" +
                "    \"capture_time\": \"2017-08-19 17:50:00\",\n" +
                "    \"bk_model_id\": \"\",\n" +
                "    \"bk_year_id\": \"\",\n" +
                "    \"accept_uid\": \"49\",\n" +
                "    \"user_name\": \"管理员\",\n" +
                "    \"plate_type_id\": \"-1\",\n" +
                "    \"monitor_type\": \"\",\n" +
                "    \"style_id\": 1,\n" +
                "    \"reason\": \"车联网短信测试\",\n" +
                "    \"phone_num\": \"13800000000,18660060166\",\n" +
                "    \"alarm_method\": \"1,2\",\n" +
                "    \"car_info\": \"\",\n" +
                "    \"bk_plate\": \"鲁FT9806\",\n" +
                "    \"captureTime\": \"2017-08-19 17:50:00\",\n" +
                "    \"speed\": \"30km/h\",\n" +
                "    \"carInfo\": \"现代-名驭-2009\",\n" +
                "    \"locationName\": \"(芝罘区)北马路与青年路（卡口）\",\n" +
                "    \"pic\": \"http://10.50.235.37/img.php?img_uuid=ftp%3A%2F%2Fguoche1%3Aguoche1%4010.231.49.77%3A21%2Fxinluwei%2F201708%2FSignalway%2F600301006002%2F19%2F17%2F20170819174957895_4_140_1.jpg\",\n" +
                "    \"color\": \"蓝色\",\n" +
                "    \"directionName\": \"由北向南\",\n" +
                "    \"applyUser\": \"管理员\",\n" +
                "    \"applyPhone\": \"13800000000,18660060166\",\n" +
                "    \"deployType\": \"精确车牌布控\",\n" +
                "    \"bk_info\": \"鲁FT9806\",\n" +
                "    \"alarm_method_name\": \"系统告警，短信告警，\"\n" +
                "}";
        return result;
    }
}
