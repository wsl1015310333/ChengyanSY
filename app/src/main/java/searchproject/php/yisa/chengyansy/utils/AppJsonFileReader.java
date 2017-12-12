package searchproject.php.yisa.chengyansy.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/8 0008.
 */

public class AppJsonFileReader {
    public static String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static List<Map<String, String>> setData(String str) {
        try {
            List<Map<String, String>> data = new ArrayList<Map<String, String>>();
            JSONArray array = new JSONArray(str);
            int len = array.length();
            Map<String, String> map;
            for (int i = 0; i < len; i++) {
                JSONObject object = array.getJSONObject(i);
                map = new HashMap<String, String>();
                map.put("operator", object.getString("operator"));
                map.put("loginDate", object.getString("loginDate"));
                map.put("logoutDate", object.getString("logoutDate"));
                data.add(map);
            }
            return data;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

public static List<Map.Entry<String, String> > setNewYearData(String str,String flags){
    List<Map<String, String>> data = new ArrayList<Map<String, String>>();
    Map<String, String> dataresult=new HashMap<String, String>();
    List<Map<String, String>> resultdata = new ArrayList<Map<String, String>>();
    Map<String,String> map = new HashMap<String, String>();
    try {

        JSONObject jsonObject = new JSONObject(str);

        //   String result = (String) jsonObject.getJSONObject("brand").getJSONObject(i+"").get("v");
        JSONObject jsonObject2 = jsonObject.getJSONObject("year");
        Map<String,String> hashMap = new HashMap<String,String>();
        JSONArray jsonModel = jsonObject2.getJSONArray(flags);
        int length= jsonModel.length();
        for(int i=0;i<length;i++){
            JSONObject oj = jsonModel.getJSONObject(i);
            Log.e("year--"+oj.get("k"),oj.get("v").toString());
            map.put((String) oj.get("v"),(String)oj.get("k"));
            hashMap.put((String) oj.get("v"),(String)oj.get("k"));
        }

        data.add(map);

    } catch (JSONException e) {
        e.printStackTrace();
    }
    List<Map.Entry<String, String>> infoIds =
            new ArrayList<Map.Entry<String, String>>(map.entrySet());
    Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
        @Override
        public int compare(Map.Entry<String, String> stringStringEntry, Map.Entry<String, String> t1) {
            Log.e("---",(stringStringEntry.getKey()).toString().substring(0,4));
            return (stringStringEntry.getKey()).toString().substring(0,4).compareTo(t1.getKey().substring(0,4));
        }
    });
    Collections.reverse(infoIds);
    for(Map.Entry<String,String> mapping:infoIds){
        dataresult.put(mapping.getKey(),mapping.getValue());
        System.out.println(mapping.getKey()+"--:--"+mapping.getValue());
    }
    resultdata.add(dataresult);

    for(Map<String,String> mapping:resultdata){
        for (String s : mapping.keySet()) {
            System.out.println("key:" + s);
            System.out.println("values:" + map.get(s));
        }
        // System.out.println(mapping.getKey()+":"+mapping.getValue());
    }

    return infoIds;

}
    public static List<Map<String, String>> setListDataYear(String str, String flags) {
        Log.e("                      ",flags);
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        Map<String, String> dataresult=new HashMap<String, String>();
        List<Map<String, String>> resultdata = new ArrayList<Map<String, String>>();
        Map<String,String> map = new HashMap<String, String>();
        try {

            JSONObject jsonObject = new JSONObject(str);

            //   String result = (String) jsonObject.getJSONObject("brand").getJSONObject(i+"").get("v");
            JSONObject jsonObject2 = jsonObject.getJSONObject("year");
           Map<String,String> hashMap = new HashMap<String,String>();
            JSONArray jsonModel = jsonObject2.getJSONArray(flags);
            int length= jsonModel.length();
            for(int i=0;i<length;i++){
                JSONObject oj = jsonModel.getJSONObject(i);
                Log.e("year--"+oj.get("k"),oj.get("v").toString());
                map.put((String) oj.get("v"),(String)oj.get("k"));
                hashMap.put((String) oj.get("v"),(String)oj.get("k"));
            }

            data.add(map);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<Map.Entry<String, String>> infoIds =
                new ArrayList<Map.Entry<String, String>>(map.entrySet());
Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
    @Override
    public int compare(Map.Entry<String, String> stringStringEntry, Map.Entry<String, String> t1) {
       Log.e("---",(stringStringEntry.getKey()).toString().substring(0,4));
        return (stringStringEntry.getKey()).toString().substring(0,4).compareTo(t1.getKey().substring(0,4));
    }
});
        Collections.reverse(infoIds);
        for(Map.Entry<String,String> mapping:infoIds){
            dataresult.put(mapping.getKey(),mapping.getValue());
            System.out.println(mapping.getKey()+"--:--"+mapping.getValue());
        }
        resultdata.add(dataresult);

        for(Map<String,String> mapping:resultdata){
                  for (String s : mapping.keySet()) {
           System.out.println("key:" + s);
           System.out.println("values:" + map.get(s));
       }
           // System.out.println(mapping.getKey()+":"+mapping.getValue());
        }

        return resultdata;

    }
    public static List<Map<String, String>> setListData(String str, String flags) {
        Log.e("                      ",flags);
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
      //  List <Map> dataresult=new ArrayList<Map>();
        Map map = new HashMap<String, String>();
        try {

            JSONObject jsonObject = new JSONObject(str);

            //   String result = (String) jsonObject.getJSONObject("brand").getJSONObject(i+"").get("v");
           JSONObject jsonObject2 = jsonObject.getJSONObject("model");
            Map hashMap = new HashMap();
            JSONArray jsonModel = jsonObject2.getJSONArray(flags);
           int length= jsonModel.length();
            for(int i=0;i<length;i++){
                JSONObject oj = jsonModel.getJSONObject(i);
                Log.e("model--"+oj.get("k"),oj.get("v").toString());
                map.put(oj.get("v"),oj.get("k"));
            }
            data.add(map);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;

    }
    public static List<Map<String, String>> setListDataBrand(String str) {
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        List<Map> dataresult=new ArrayList<Map>();
        Map map = new HashMap<String, String>();
        try {
            JSONObject jsonObject = new JSONObject(str);
            JSONObject jsonObject2 =  jsonObject.getJSONObject("brand");
            Iterator<String> sIterator = jsonObject2.keys();
            while(sIterator.hasNext()){
                // 获得key
                String key = sIterator.next();
                // 根据key获得value, value也可以是JSONObject,JSONArray,使用对应的参数接收即可
                String value = jsonObject2.getJSONObject(key).getString("v");
                String keyV = jsonObject2.getJSONObject(key).getString("k");
                map.put(value,keyV);

               }
            data.add(map);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;

    }
/**
 *  public static List<Map<String, String>> setListData(String str,String flags) {
 List<Map<String, String>> data = new ArrayList<Map<String, String>>();
 try {

 JSONObject jsonObject = new JSONObject(str);
 //     int len = array.length();
 Map<String, String> map;
 //            for (int i = 0; i < len; i++) {
 //                JSONObject object = array.getJSONObject(i);
 //                map = new HashMap<String, String>();
 //                map.put("imageId", object.getString("imageId"));
 //                map.put("title", object.getString("title"));
 //                map.put("subTitle", object.getString("subTitle"));
 //                map.put("type", object.getString("type"));
 //                data.add(map);
 //            }
 //   String result = (String) jsonObject.getJSONObject("brand").getJSONObject(i+"").get("v");
 JSONObject jsonObject2 =  jsonObject.getJSONObject("brand");
 //            for(int i=1;i<100;i++) {
 //                if(i!=60&&i!=73&&i!=132&&i!=145&&i!=148&&i!=149&&i!=150)
 //                Log.e("result",(String)jsonObject2.getJSONObject(i+"").get("v"));
 //            }
 Map hashMap =new HashMap();
 hashMap.put((String)jsonObject2.getJSONObject(1+"").get("v"),"1");
 Log.e("result",(String)jsonObject2.getJSONObject(1+"").get("v"));
 JSONArray jsonModel = jsonObject.getJSONObject("model").getJSONArray((String) hashMap.get("奥迪"));
 //            Iterator it=jsonModel.iterator();
 //            while(it.hasNext()){
 //                String key = (String) it.next();
 //                String value = jsonModel.getString(key);
 //                Log.i("key",key);
 //                Log.i("value",value);
 //
 //            }

 int length = jsonModel.length();
 String aa = "";
 for(int i = 0; i < length; i++) {//遍历JSONArray

 JSONObject oj = jsonModel.getJSONObject(i);
 aa = aa  +"|"+ oj.get("v")+"   ";
 hashMap.put(oj.get("v"),oj.get("k"));
 }

 Log.d("debugTest"," "+aa);

 JSONArray jsonYear = jsonObject.getJSONObject("year").getJSONArray((String)hashMap.get("A3"));
 int length1 = jsonYear.length();
 String aaa = "";
 for(int i = 0; i < length1; i++) {//遍历JSONArray
 JSONObject oj = jsonYear.getJSONObject(i);
 aaa = aaa  + oj.get("v")+" ";
 Log.e(oj.get("k")+"",oj.get("v")+"");
 hashMap.put(oj.get("v"),oj.get("k"));
 }
 Log.e("aaaa",aaa);
 } catch (JSONException e) {
 e.printStackTrace();
 }
 return data;

 }
 */
}
