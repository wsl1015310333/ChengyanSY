package searchproject.php.yisa.chengyansy.fragment;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import searchproject.php.yisa.chengyansy.Adapter_Alarm.Alarm_warningAdapter;
import searchproject.php.yisa.chengyansy.Alarm_warningWebActivity;
import searchproject.php.yisa.chengyansy.Domain.AlarmDao;
import searchproject.php.yisa.chengyansy.FilesUtils;
import searchproject.php.yisa.chengyansy.MainActivity;
import searchproject.php.yisa.chengyansy.Model_All.Read_alarmJson;
import searchproject.php.yisa.chengyansy.Model_All.Read_waring_detailJson;
import searchproject.php.yisa.chengyansy.MyIntentService;
import searchproject.php.yisa.chengyansy.Perimeter_WarningResultActivity;
import searchproject.php.yisa.chengyansy.R;
import searchproject.php.yisa.chengyansy.utils.HttpConnectionUtils;
import searchproject.php.yisa.chengyansy.utils.ResulutionJson;
import searchproject.php.yisa.chengyansy.view.ExpandLayout;


/**
 * Created by Administrator on 2017/7/17 0017.
 */

public class ZhoubianyujingFragment extends Fragment {

    public Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 200:
                    String result = (String) msg.obj;
                    Read_waring_detailJson read_alarm_detail = ResulutionJson.read_waring_detailJson(result);
                    Intent intent = new Intent(getActivity(), Perimeter_WarningResultActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("result", read_alarm_detail);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;

            }
        }
    };
    ZhoubianyujingFragment.MyReceiver receiver;

    Alarm_warningAdapter adapter;
    public static List<Read_alarmJson> list=new ArrayList<Read_alarmJson>();
    public static volatile int count=0;
    static ExpandLayout mContantExpanLayout;
    LinearLayout alarm_set_warning;
    ImageView  alarm_warning_loading_img;
    static   TextView alarm_warning_showText;
    LinearLayout alarm_warning_loading;
    LinearLayout alarm_nodate;
    RecyclerView recyclerView;
    View view;
    static Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.zhoubianyujin_fragment,null);
        // MyIntentService.mSocket.emit("123",456);
        context=getActivity();
        initView();
        initData();
        initHttp();
        return view;
    }
    public void initView(){
        mContantExpanLayout=(ExpandLayout)view.findViewById(R.id.zbyj_unread);
        alarm_set_warning=(LinearLayout)view.findViewById(R.id.alarm_set_warning);
        alarm_warning_loading_img=(ImageView)view.findViewById(R.id.alarm_warning_loading_img);
        alarm_warning_showText=(TextView)view.findViewById(R.id.alarm_warning_showText);
        alarm_warning_loading=(LinearLayout)view.findViewById(R.id.alarm_warning_loading);
        alarm_nodate=(LinearLayout)view.findViewById(R.id.alarm_nodate);
        recyclerView=(RecyclerView)view.findViewById(R.id.alarm_warning_rv);

        alarm_warning_loading.setVisibility(View.GONE);
        alarm_nodate.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);


        //动画
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.img_animation);
        LinearInterpolator lin = new LinearInterpolator();//设置动画匀速运动
        animation.setInterpolator(lin);
        alarm_warning_loading_img.startAnimation(animation);
    }
//    public static void showDialog(){
//        alarm_warning_loading.setVisibility(View.VISIBLE);
//        alarm_nodate.setVisibility(View.GONE);
//        recyclerView.setVisibility(View.GONE);
//    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("onResume","onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("onPause","onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("onStop","onStop");
    }

    public void   initHttp(){
        final String location = FilesUtils.sharedGetFile(getActivity(), "location", "location_id");
        AlarmDao sdao = new AlarmDao(getActivity());
        List<Read_alarmJson> read_alarmJson = null;
        List<Read_alarmJson> read_UnalarmJson=null;
        String uid=   FilesUtils.sharedGetFile(context,"user","uid");
        if(uid.length()>0) {
            read_alarmJson= sdao.findRead("Read_ZBalarmJson_Read");
            read_UnalarmJson= sdao.findRead("Read_ZBalarmJson_UnRead");
            if (read_alarmJson.size() != 0) {
                Toast.makeText(getActivity(), "read_alarmJson.size()=" + read_alarmJson.size() + "-" + read_alarmJson.get(0).getId() + read_alarmJson.get(0).getPic() + read_alarmJson.get(0).getPlate() + read_alarmJson.get(0).getCaptureTime() + read_alarmJson.get(0).getLocationName() + read_alarmJson.get(0).getReason(), Toast.LENGTH_SHORT).show();
                list.addAll(0, read_alarmJson);
                adapter.notifyDataSetChanged();
                alarm_warning_loading.setVisibility(View.GONE);
                alarm_nodate.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

            }
            if (read_UnalarmJson.size() != 0) {
                Toast.makeText(getActivity(), "read_UnalarmJson.size()=" + read_UnalarmJson.size(), Toast.LENGTH_SHORT).show();


                count = read_UnalarmJson.size();
                alarm_warning_showText.setText("你有" + read_UnalarmJson.size() + "条新数据");
                cacheList.addAll(0, read_UnalarmJson);
            } else {
                mContantExpanLayout.collapse();
            }
        }else {
            mContantExpanLayout.collapse();
        }

        //   Toast.makeText(getActivity(),location+"---",Toast.LENGTH_SHORT).show();
        if(location.equals("")){

        }else {
            if(read_alarmJson!=null&&read_alarmJson.size()==0) {
                alarm_nodate.setVisibility(View.GONE);
                // recyclerView.setVisibility(View.GONE);
                alarm_warning_loading.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "read_alarmJson.size()==0", Toast.LENGTH_SHORT).show();
            }

            if (MyIntentService.mSocket==null) {
                //   Toast.makeText(getActivity(),location+"null",Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bundle bundle = new Bundle();
                        bundle.putString("location_id", location);

                        Intent intent = new Intent(getActivity(), MyIntentService.class);
                        intent.putExtras(bundle);
                        getActivity().startService(intent);
                    }
                }).start();

            }else {
                MyIntentService.mSocket.connect();
                //  Toast.makeText(getActivity(),location+"!==null",Toast.LENGTH_SHORT).show();
                MyIntentService.mSocket.emit("login",location);
            }


        }

    }
    private void initData(){

        adapter=new Alarm_warningAdapter(list,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        Alarm_warningWebActivity.setOnItemClickLitener(new Alarm_warningWebActivity.ShowLoading() {
            @Override
            public void ShowLoadingMethod() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        alarm_nodate.setVisibility(View.GONE);
                        // recyclerView.setVisibility(View.GONE);
                        alarm_warning_loading.setVisibility(View.VISIBLE);
                    }
                });



                //      Toast.makeText(getActivity(),"222",Toast.LENGTH_SHORT).show();
            }

            @Override
            public  void ShowNoOptionLocation() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),"ShowNoOptionLocation",Toast.LENGTH_SHORT).show();
                        AlarmDao alarmDao=new AlarmDao(getActivity());
                        alarmDao.deleteUnRead("Read_ZBalarmJson_Read");
                    //    alarmDao.deleteUnRead("Read_ZBalarmJson_UnRead");
                        list.clear();
                       // cacheList.clear();
                        //    recyclerView.notifyAll();
                        adapter.notifyDataSetChanged();
                        recyclerView.setVisibility(View.GONE);
                        alarm_warning_loading.setVisibility(View.GONE);
                        alarm_nodate.setVisibility(View.VISIBLE);
                        if(MyIntentService.mSocket!=null) {
                            MyIntentService.mSocket.disconnect();
                        }

                    }
                });

            }
        });
        adapter.setOnItemClickLitener(new Alarm_warningAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, final int position) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        HashMap hashMap=new HashMap();
                        hashMap.put("id",list.get(position).getId());
//                        mHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(getActivity(),list.get(position).getId(),Toast.LENGTH_SHORT).show();
//                            }
//                        });
                        final String result= HttpConnectionUtils.sendGETRequestRight(HttpConnectionUtils.ipport+"/api/real_alarm_detail",hashMap,null);
                        //    String result=initJson();
//                        mHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(getActivity(),result+" ",Toast.LENGTH_SHORT).show();
//                            }
//                        });
                        if(result.length()<30){

                        }else {
                            Message message=mHandler.obtainMessage();
                            message.obj=result;
                            message.what=200;
                            mHandler.sendMessage(message);
                        }
                    }
                }).start();
            }
            @Override
            public void onItemLongClick(View view, int position) {
            }
        });


        mContantExpanLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlarmDao sdao = new AlarmDao(getActivity());
                list.addAll(0,cacheList);
                for(Read_alarmJson read_alarmJson :cacheList) {
                    sdao.addRead(read_alarmJson, "Read_ZBalarmJson_Read");
                }
                if(list.size()>100){
                    int n=list.size()-100;
                    for (int i = 0; i < n; i++) {
                        list.remove(list.size() -1);
                    }
                }
                sdao.delete("100","Read_ZBalarmJson_Read");
                cacheList.clear();
                sdao.deleteUnRead("Read_ZBalarmJson_UnRead");
                count=0;
                mContantExpanLayout.collapse();
                adapter.notifyDataSetChanged();
                alarm_warning_loading.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
        alarm_set_warning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                receiver = new ZhoubianyujingFragment.MyReceiver(mHandler);
                //  Intent startIntent = new Intent(getActivity(), MyIntentService.class);
                //  getActivity().startService(startIntent);
                getActivity(). registerReceiver(receiver, new IntentFilter("some.action")); // Register receiver
                getActivity().registerReceiver(receiver,new IntentFilter(("some.action.progress")));

//                Intent intent1 = new Intent(getActivity(), MyIntentService.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("location_id", "1,2,");
//               getActivity(). startService(intent1);
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                receiver = new ZhoubianyujingFragment.MyReceiver(mHandler);
//                              //  Intent startIntent = new Intent(getActivity(), MyIntentService.class);
//                              //  getActivity().startService(startIntent);
//                                getActivity(). registerReceiver(receiver, new IntentFilter("some.action")); // Register receiver
//                                getActivity().registerReceiver(receiver,new IntentFilter(("some.action.progress")));
//
//
//                            }
//                        });
                //     SystemClock.sleep(2000);

//                        mHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                alarm_warning_loading.setVisibility(View.GONE);
//                                alarm_nodate.setVisibility(View.GONE);
//                                recyclerView.setVisibility(View.VISIBLE);
//                            }
//                        });
//                    }
//                }).start();
                Intent intent=new Intent(getActivity(), Alarm_warningWebActivity.class);
                startActivity(intent);
//                alarm_warning_loading.setVisibility(View.VISIBLE);
//                alarm_nodate.setVisibility(View.GONE);
//                recyclerView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200){


        }
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
        @Override
        public  void onReceive(final Context context, Intent intent) {
            switch (intent.getAction()){
                case "some.action":
                    if (intent!=null){

                    }
                    break;
                case  "some.action.progress":
                    if (intent!=null){


                        final String json=   intent.getStringExtra("alarm");
//                        Toast.makeText(context,"zhoubian-"+json,Toast.LENGTH_SHORT).show();
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                saveCrashInfo2File(json);
//                            }
//                        }).start();
//                        Log.e("toggle",Integer.toString(count));
                        count++;
                        //    Toast.makeText(context,"Zhoubian-"+json,Toast.LENGTH_SHORT).show();
                        List<Read_alarmJson> read_alarmJson= ResulutionJson.read_alarmJson(json);
                        //  list.addAll(read_alarmJson);
                        cacheList.addAll(read_alarmJson);
                        if (count==1) {

                            mContantExpanLayout.expand();


                        }



                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                alarm_warning_showText .setText("你有"+count+"条新数据");
                            }
                        });
                        //     Log.e("status",status);
                    }
                    break;
            }

            // Post the UI updating code to our Handler

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void showData(final String json){
        if (isBackground( context)){
            sendNotification();
        }else {

        }
//        handle.post(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(context,"zhoubian-"+json,Toast.LENGTH_SHORT).show();
//                saveCrashInfo2File(json);
//            }
//        });
        //  final String json=   intent.getStringExtra("alarm");
//                        Toast.makeText(context,"zhoubian-"+json,Toast.LENGTH_SHORT).show();
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                saveCrashInfo2File(json);
//                            }
//                        }).start();
//                        Log.e("toggle",Integer.toString(count));
        count++;
        //    Toast.makeText(context,"Zhoubian-"+json,Toast.LENGTH_SHORT).show();
        final List<Read_alarmJson> read_alarmJson= ResulutionJson.read_alarmJson(json);
        //  list.addAll(read_alarmJson);
        cacheList.addAll(read_alarmJson);

        //插入UnRead表
        AlarmDao sdao = new AlarmDao(context);
        for(Read_alarmJson alarmJson : read_alarmJson){
            sdao.addUnRead(alarmJson,"Read_ZBalarmJson_UnRead");
        }
        if (count==1) {
            handle.post(new Runnable() {
                @Override
                public void run() {

                    mContantExpanLayout.expand();
                }
            });



        }



        handle.post(new Runnable() {
            @Override
            public void run() {

                alarm_warning_showText .setText("你有"+(count+read_alarmJson.size()-1)+"条新数据");
                count=count+read_alarmJson.size()-1;
            }
        });
        //     Log.e("status",status);
    }
    static Handler handle=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private static String saveCrashInfo2File(String  json) {

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
    private static   void sendNotification(){
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
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    Log.i("后台", appProcess.processName);
                    return true;
                }else{
                    Log.i("前台", appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }

}
