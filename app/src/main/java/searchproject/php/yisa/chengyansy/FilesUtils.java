package searchproject.php.yisa.chengyansy;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public class FilesUtils {
    public static void sharedToFile(Context context,String name,Map map) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            editor.putString(entry.getKey(), entry.getValue());
        }
        editor.commit();

    }
    public static String  sharedGetFile(Context context,String filename,String filedetail){
              //显示用户此前录入的数据
               SharedPreferences sPreferences=context.getSharedPreferences(filename, Context.MODE_PRIVATE);
               String result=sPreferences.getString(filedetail, "");
          return result;

    }

}
