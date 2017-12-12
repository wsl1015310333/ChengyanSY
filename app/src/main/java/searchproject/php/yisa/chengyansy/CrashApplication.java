package searchproject.php.yisa.chengyansy;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import searchproject.php.yisa.chengyansy.utils.CrashHandler;

/**
 * Created by Administrator on 2017/8/7 0007.
 */

public class CrashApplication extends Application {
    public static List<Activity> mylist = new ArrayList<Activity>();
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }
}
