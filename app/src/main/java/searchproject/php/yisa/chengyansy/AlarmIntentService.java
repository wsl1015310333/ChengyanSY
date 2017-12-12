package searchproject.php.yisa.chengyansy;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import searchproject.php.yisa.chengyansy.Model_All.Location_id;
import searchproject.php.yisa.chengyansy.fragment.BukongyujingFragment;
import searchproject.php.yisa.chengyansy.utils.HttpConnectionUtils;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class AlarmIntentService extends IntentService {

    private boolean isRunning = true;
    private int count = 0;
    String nameIntentservicfe="1";
    BukongyujingFragment.MyReceiver mLocalBroadcastManager;

    public AlarmIntentService() {
        super("yisa");
        mLocalBroadcastManager = new BukongyujingFragment.MyReceiver(new Handler());
    //    sendServiceStatus("服务启动");
    }

   public AlarmIntentService(String name) {
       super(name);
       Log.e("233","233");
       mLocalBroadcastManager = new BukongyujingFragment.MyReceiver(new Handler());
      // sendServiceStatus("服务启动");
   }
//MyIntentService(){
//    this(nameIntentservicfe);
//}
List<Location_id> list=new ArrayList<Location_id>();

    Socket mSocket;
   public static Socket warinSocket;
    @Override
    protected void onHandleIntent(Intent intent) {
//        Bundle bundle= intent.getExtras();
//        if (bundle==null){
//
//        }else {
//            list= (List<Location_id>) bundle.getSerializable("list");
//        }
        String uid=FilesUtils.sharedGetFile(getApplicationContext(),"user","uid");

Log.e("uid--",uid);
            try {
               //  mSocket= IO.socket("http://"+"192.168.2.86:8088"+"/warning");
            //   warinSocket=IO.socket("http://10.50.235.37:2124/");
            //    warinSocket=IO.socket("http://"+HttpConnectionUtils.ip+":10059/");
                warinSocket=IO.socket("http://"+HttpConnectionUtils.ip+HttpConnectionUtils.bkport+"/"); //本地
               // warinSocket=IO.socket("http://"+HttpConnectionUtils.ip+":10181/");
                // mSocket.connect();
                warinSocket.connect();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            warinSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    Log.i("链接", "链接");
                }

            }).on("alarmData",new  Emitter.Listener(){

                @Override
                public void call(Object... objects) {
                    if (objects != null){
                     //   Log.i("length", String.valueOf(objects.length));
                      //  Log.i("shuju1", objects[0].toString());
                        if(objects[0].toString().length()>10) {
                            sendThreadStatus("线程执行中..", objects[0].toString());
                        }
                    }
                }
            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args){
                    Log.i("取消链接","取消链接");
                    warinSocket.connect();
                    if(args!=null){
                        Log.i("quxiaoshuju",(String)args[0]);
                    }
                }

            }).emit("login",uid);


//        HashMap hashMap = new HashMap();
//        hashMap.put("uid", uid);
//        while (true) {
//            try {
//                String result = HttpConnectionUtils.sendGETRequest(HttpConnectionUtils.ipport + "/api/bk_lists", hashMap, null);
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
//        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
       // sendServiceStatus("服务结束");
      //  stopSelf();
      //  warinSocket.disconnect();
    }



//    // 发送服务状态信息
//    private void sendServiceStatus(String status) {
//        Intent intent = new Intent(new Intent());
//        intent.setAction("some.action");
//        intent.putExtra("status", status);
//        sendBroadcast(intent);
//    }

    // 发送线程状态信息
    private void sendThreadStatus(String status, String json) {
        Intent intent = new Intent(new Intent());
        intent.setAction("some.action.alarm");
        intent.putExtra("status", status);
        intent.putExtra("alarm",json);
        //Log.e("progress",Integer.toString(progress));

        sendBroadcast(intent);
    }
}
