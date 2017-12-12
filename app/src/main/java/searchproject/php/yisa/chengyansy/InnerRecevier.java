package searchproject.php.yisa.chengyansy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2017/11/22 0022.
 */
public class InnerRecevier extends BroadcastReceiver {

    final String SYSTEM_DIALOG_REASON_KEY = "reason";

    final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";

    final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)) {
            String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
            if (reason != null) {
                if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
                    //监听home

                  System.exit(0);
                } else if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
                 //   Toast.makeText(context, "多任务键被监听", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
