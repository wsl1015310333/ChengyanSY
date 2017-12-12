package searchproject.php.yisa.chengyansy.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/7/30 0030.
 */

public class TimeUtils {
    public static int[] getTimeNow(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm");//设置日期格式
        String timeAll=df.format(new Date());
        Log.e("time",timeAll);
          String[] dataAll=timeAll.split("-");
        int []datatime=new int[5];
int i=0;
        for (String time1: dataAll) {
                Log.e("integer", Integer.toString(Integer.parseInt(time1)));
                datatime[i++]= Integer.parseInt(time1);

        }

        return datatime;
    }
}
