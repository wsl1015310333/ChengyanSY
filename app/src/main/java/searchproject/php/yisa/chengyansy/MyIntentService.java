package searchproject.php.yisa.chengyansy;

import android.app.IntentService;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

import searchproject.php.yisa.chengyansy.fragment.ZhoubianyujingFragment;
import searchproject.php.yisa.chengyansy.utils.HttpConnectionUtils;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
//周边预警
public class MyIntentService extends IntentService {

    private boolean isRunning = true;
    private int count = 0;
    String nameIntentservicfe="1";
    ZhoubianyujingFragment.MyReceiver mLocalBroadcastManager;

    public MyIntentService() {
        super("yisa");
        mLocalBroadcastManager = new ZhoubianyujingFragment.MyReceiver(new Handler());
    //    sendServiceStatus("服务启动");
    }


   public MyIntentService(String name) {
       super(name);
       Log.e("233","233");
       mLocalBroadcastManager = new ZhoubianyujingFragment.MyReceiver(new Handler());
      // sendServiceStatus("服务启动");
   }
//MyIntentService(){
//    this(nameIntentservicfe);
//}


   public static Socket mSocket;
    {
        try {
            //  mSocket= IO.socket("http://"+"192.168.2.86:8088"+"/warning");
               //   mSocket= IO.socket("http://10.50.235.37:2125/");
            mSocket= IO.socket("http://"+HttpConnectionUtils.ip+HttpConnectionUtils.zbport+"/"); //本地10181
            //mSocket= IO.socket("http://"+HttpConnectionUtils.ip+":10060/");
           // mSocket= IO.socket("http://"+HttpConnectionUtils.ip+":10182/");
            //   warinSocket=IO.socket("http://"+"192.168.2.86:8088"+"/alarm");

            //  warinSocket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                Log.i("链接", "链接");
            }

        }).on("warningData",new  Emitter.Listener(){

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void call(Object... objects) {
                if (objects != null){
                    Log.e("1","1");
                    Log.i("length", String.valueOf(objects.length));
//                        Log.i("shuju1", objects[0].toString());
                    if (objects[0]!=null)
                        //  sendThreadStatus("线程执行中..",objects[0].toString());
                    if(objects[0].toString().length()>10) {
                        ZhoubianyujingFragment.showData(objects[0].toString());
                    }
                }
            }
        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args){
                Log.i("取消链接","取消链接");
//                mSocket.connect();
//                if(args!=null){
//                    Log.i("quxiaoshuju",(String)args[0]);
//                }
            }
        });
        mSocket.connect();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String locationid = null;
        Bundle bundle = intent.getExtras();
        if (bundle == null) {

        } else {
            locationid = bundle.getString("location_id");
            mSocket.emit("login",locationid);
        }

//
//            try {
//               //  mSocket= IO.socket("http://"+"192.168.2.86:8088"+"/warning");
//          //      mSocket= IO.socket("http://10.50.235.37:2125/");
//                mSocket= IO.socket("http://"+HttpConnectionUtils.ip+":10060/");
//             //   warinSocket=IO.socket("http://"+"192.168.2.86:8088"+"/alarm");
//                mSocket.connect();
//              //  warinSocket.connect();
//            } catch (URISyntaxException e) {
//                e.printStackTrace();
//            }
//            mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
//
//                @Override
//                public void call(Object... args) {
//                    Log.i("链接", "链接");
//                }
//
//            }).on("warningData",new  Emitter.Listener(){
//
//                @Override
//                public void call(Object... objects) {
//                    if (objects != null){
//Log.e("1","1");
//                        Log.i("length", String.valueOf(objects.length));
////                        Log.i("shuju1", objects[0].toString());
//                        if (objects[0]!=null)
//                     //  sendThreadStatus("线程执行中..",objects[0].toString());
//                        ZhoubianyujingFragment.showData(objects[0].toString());
//                    }
//                }
//            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
//                @Override
//                public void call(Object... args){
//                    Log.i("取消链接","取消链接");
//                    mSocket.connect();
//                    if(args!=null){
//                        Log.i("quxiaoshuju",(String)args[0]);
//                    }
//                }
//            }).emit("login",locationid);

//        HashMap hashMap = new HashMap();
//        hashMap.put("location_id", locationid);
//        while (true) {
//            try {
//
//                String result = HttpConnectionUtils.sendGETRequest(HttpConnectionUtils.ipport + "/api/yj_lists", hashMap, null);
//
//                if (result .length()>50){
//                    Log.i("length", String.valueOf(result));
//                    Log.i("shuju1", result.toString());
//                    if (result!=null)
//                        sendThreadStatus("线程执行中..",result.toString());
//                }
//                Log.e("result", result);
//                Thread.sleep(5000);// 线程暂停10秒，单位毫秒
//
//            } catch (InterruptedException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//
//    }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
       // sendServiceStatus("服务结束");
     //   stopSelf();
   //     mSocket.disconnect();
    }

//    // 发送服务状态信息
//    private void sendServiceStatus(String status) {
//        Intent intent = new Intent(new Intent());
//        intent.setAction("some.action");
//        intent.putExtra("status", status);
//        sendBroadcast(intent);
//
//    }

    // 发送线程状态信息
    private void
    sendThreadStatus(String status, String json) {
        Intent intent = new Intent(new Intent());
        intent.setAction("some.action.progress");
        intent.putExtra("status", status);
        intent.putExtra("alarm",json);
        //Log.e("progress",Integer.toString(progress));

        sendBroadcast(intent);
    }
}
