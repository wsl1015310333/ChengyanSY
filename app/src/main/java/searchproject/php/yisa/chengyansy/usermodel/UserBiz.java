package searchproject.php.yisa.chengyansy.usermodel;

import android.os.Handler;
import android.os.Message;

import java.util.HashMap;

import searchproject.php.yisa.chengyansy.utils.HttpConnectionUtils;

/**
 * Created by Administrator on 2017/7/25 0025.
 */

public class UserBiz implements  IUserLogin {
Handler handler=new Handler(){
    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            case 200:
          //      Toast.makeText(LoginActivity.class,"ff",Toast.LENGTH_SHORT).show();
                break;
        }
    }
};
    @Override
    public void login(final String username, final String password, final String url, final OnLoginListener onLoginListener) {
        new Thread(){
            @Override
            public void run() {
                HashMap hashMap=new HashMap();
                hashMap.put("account",username);
                hashMap.put("password",password);


             String result=   HttpConnectionUtils.sendGETRequest(url,hashMap,null);
            //    String result="chenggong";
//               Message message=handler.obtainMessage();
//                message.what=200;
//                message.obj=result;
//                handler.sendMessage(message);
//                if (result!=null){
//                    User user = new User();
//                    user.setAccount("张警官");
//                    user.setLastIp("111.111.111.111");
//                    user.setLastIpTime("2013-07-29");
//                    onLoginListener.loginSuccess(user);
//                }
            }
        }.start();
    }
}
