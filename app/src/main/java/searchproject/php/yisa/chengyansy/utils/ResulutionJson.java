package searchproject.php.yisa.chengyansy.utils;



import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import searchproject.php.yisa.chengyansy.Model_All.ACarACrime;
import searchproject.php.yisa.chengyansy.Model_All.Car_resultJson;
import searchproject.php.yisa.chengyansy.Model_All.Car_result_kk;
import searchproject.php.yisa.chengyansy.Model_All.Car_result_model;
import searchproject.php.yisa.chengyansy.Model_All.LoginJson;
import searchproject.php.yisa.chengyansy.Model_All.Person;
import searchproject.php.yisa.chengyansy.Model_All.Person_Detail;
import searchproject.php.yisa.chengyansy.Model_All.Read_alarmJson;
import searchproject.php.yisa.chengyansy.Model_All.Read_alarm_detail;
import searchproject.php.yisa.chengyansy.Model_All.Read_wainingJson;
import searchproject.php.yisa.chengyansy.Model_All.Read_waring_detailJson;

/**
 * Created by Administrator on 2017/8/1 0001.
 */

public class ResulutionJson {
    public static List<Car_resultJson> car_resultJson(String json){
        List<Car_resultJson> car_resultJsons=new ArrayList<Car_resultJson>();
        try {


            JSONObject jsonObject=new JSONObject(json);
            String totalRecords= Integer.toString(jsonObject.getInt("totalRecords"));
            if (totalRecords.equals("0")){
                return null;
            }
            Car_resultJson.totalRecords=totalRecords;
            JSONArray jsonArray=jsonObject.getJSONArray("searchList");
            int length=jsonArray.length();
            for(int i=0;i<length;i++){
                Car_resultJson car_resultJson=new Car_resultJson();
                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                String type=jsonObject1.getString("plateType");
                String plate=jsonObject1.getString("plate");
                String model=jsonObject1.getString("model");
                String locationName=jsonObject1.getString("locationName");
                String captureTime=jsonObject1.getString("captureTime");
                String pic=jsonObject1.getString("pic");
                car_resultJson.setType(type);
                car_resultJson.setPlate(plate);
                car_resultJson.setLocationName(locationName);
                car_resultJson.setCaptureTime(captureTime);
                car_resultJson.setPic(pic);
                car_resultJson.setModel(model);
                car_resultJsons.add(car_resultJson);
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }

        return car_resultJsons;
    }
    public static ACarACrime
    aCarACrime(String json){
        try {
            JSONObject jsonObject=new JSONObject(json);
            ACarACrime aCarACrime=new ACarACrime();
            aCarACrime.setType(jsonObject.getString("zjlx"));
            //aCarACrime.setName(jsonObject.getString("czxm"));
            aCarACrime.setName(jsonObject.getString("SYR"));
            Log.e("SYR",jsonObject.getString("SYR"));
            aCarACrime.setTel(jsonObject.getString("JYHGBZBH"));
            aCarACrime.setNumCard(jsonObject.getString("SFZMHM"));
            aCarACrime.setIsDug(jsonObject.getString("sdry"));
            aCarACrime.setIscrimeRecord(jsonObject.getString("sary"));
            aCarACrime.setIsfocal(jsonObject.getString("zdry"));
            aCarACrime.setIsFugitive(jsonObject.getString("ztry"));
            aCarACrime.setIsInvolved(jsonObject.getString("sary"));
            aCarACrime.setCllx(jsonObject.getString("cllx"));
            aCarACrime.setClxh(jsonObject.getString("CLXH"));
            aCarACrime.setHmzl(jsonObject.getString("hpzl"));




            aCarACrime.setColor(jsonObject.getString("CSYS"));
            aCarACrime.setClyt(jsonObject.getString("CLYT"));
            aCarACrime.setClsbdh(jsonObject.getString("CLSBDH"));
            aCarACrime.setDjzsbh(jsonObject.getString("DJZSBH"));
            aCarACrime.setDabh(jsonObject.getString("GLBM"));
            aCarACrime.setSyxz(jsonObject.getString("SYXZ"));
            aCarACrime.setHdfs(jsonObject.getString("HDFS"));
            aCarACrime.setSyqxz(jsonObject.getString("SYQ"));
            aCarACrime.setJdczt(jsonObject.getString("ZT"));
            aCarACrime.setDybj(jsonObject.getString("DYBJ"));
            aCarACrime.setZjdjrq("-");//最近定检日期
            aCarACrime.setYcyxqz("-");//检测有效日期
            aCarACrime.setQzbfqz(jsonObject.getString("QZBFQZ"));
            aCarACrime.setZsxxdz(jsonObject.getString("ZSXXDZ"));
            aCarACrime.setZzxxdz(jsonObject.getString("ZZXXDZ"));
            aCarACrime.setZzjzzm("-");//
            aCarACrime.setCcdjrq(jsonObject.getString("CCDJRQ"));
            aCarACrime.setBxzzrq(jsonObject.getString("BXZZRQ"));
            aCarACrime.setGxsj(jsonObject.getString("GXRQ"));
            aCarACrime.setFprq(jsonObject.getString("FPRQ"));
            aCarACrime.setFhjzrq(jsonObject.getString("FHGZRQ"));

            aCarACrime.setFdjzsrq(jsonObject.getString("FDJRQ"));
            aCarACrime.setBhlhszcs(jsonObject.getString("BZCS"));
            aCarACrime.setBhlhpcs(jsonObject.getString("BPCS"));
            aCarACrime.setBhlzscs(jsonObject.getString("FPRQ"));
            aCarACrime.setFhjzrq(jsonObject.getString("BDJCS"));
            aCarACrime.setGlbm(jsonObject.getString("GLBM"));

            return aCarACrime;
        } catch (JSONException e) {
            e.printStackTrace();
        }
      return null;
    }
    public static List<Car_result_kk> car_result_kkJson(String json){
        List<Car_result_kk> list=new ArrayList();
        try {
            JSONObject jsonObject=new JSONObject(json);
           String totalRecords= Integer.toString(jsonObject.getInt("totalRecords"));
            JSONArray jsonArray=jsonObject.getJSONArray("kkList");
            int length=jsonArray.length();
            for(int i=0;i<length;i++){
                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                Car_result_kk car_result_kk=new Car_result_kk();
                car_result_kk.setTotalRecords(totalRecords);
                car_result_kk.setId(jsonObject1.getString("id"));
                car_result_kk.setNum(jsonObject1.getString("num"));
                car_result_kk.setText(jsonObject1.getString("text"));
                list.add(car_result_kk);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List car_result_modelJson(String json){
        List list=new ArrayList();
        try {
            JSONObject jsonObject=new JSONObject(json);
            String totalRecords= Integer.toString(jsonObject.getInt("totalRecords"));
            JSONArray jsonArray=jsonObject.getJSONArray("modelList");
            int length=jsonArray.length();
            for(int i=0;i<length;i++){
                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                Car_result_model car_result_model =new Car_result_model();
                car_result_model.setText(jsonObject1.getString("text"));
                car_result_model.setNum((jsonObject1.getString("num")));
                car_result_model.setYear_id(jsonObject1.getString("year_id"));
                car_result_model.setTotalRecords(totalRecords);
                list.add(car_result_model);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
    public static List get_personJson(String json){
        List list=new ArrayList();
        try {
            JSONArray jsonArray=new JSONArray(json);
            int length=jsonArray.length();
            for(int i=0;i<length;i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                Person person=new Person();
                person.setIdCard(jsonObject.getString("idCard"));
                person.setPic(jsonObject.getString("pic"));
                person.setName(jsonObject.getString("name"));
                person.setSimilar((jsonObject).getString("similar"));
                list.add(person);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  list;
    }
    public static LoginJson get_loginJson(String json){
        LoginJson loginJson=new LoginJson();
        try {
            JSONObject jsonObject=new JSONObject(json);

            loginJson.setToken(jsonObject.getString("token"));
            JSONObject jsonObject1=jsonObject.getJSONObject("user");
            loginJson.setAccount(jsonObject1.getString("account"));
            loginJson.setLastTime(jsonObject1.getString("lastTime"));
            loginJson.setRealName(jsonObject1.getString("realName"));
            loginJson.setUid(jsonObject1.getString("uid"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return loginJson;
    }
    public static Person_Detail person_detailJson(String json){
        Person_Detail person_detail=new Person_Detail();
        try {
            JSONObject jsonObject=new JSONObject(json);
            person_detail.setIdCard(jsonObject.getString("idCard"));
            person_detail.setName(jsonObject.getString("name"));
            person_detail.setPic(jsonObject.getString("address"));
            person_detail.setIsDeplcy(Integer.toString(jsonObject.getInt("isDeploy")));
            person_detail.setPersonnel(jsonObject.getString("personnel"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return person_detail;
    }
    public static List<Read_alarmJson> read_warningJson(String json){
        List<Read_alarmJson>list = new ArrayList<Read_alarmJson>();

        try {
            JSONArray jsonArray=new JSONArray(json);
            int len=jsonArray.length();
            for(int i=0;i<len;i++) {
                Read_alarmJson read_alarmJson=new Read_alarmJson();
                JSONObject jsonObject = jsonArray.getJSONObject(i);

              //  JSONObject jsonObject = new JSONObject(json);
                read_alarmJson.setPlate(jsonObject.getString("plate"));
                read_alarmJson.setPic(jsonObject.getString("pic"));
                read_alarmJson.setCaptureTime(jsonObject.getString("captureTime"));
                read_alarmJson.setId(jsonObject.getString("id"));
                read_alarmJson.setLocationName(jsonObject.getString("locationName"));
                read_alarmJson.setReason(jsonObject.getString("warningType"));
                list.add(read_alarmJson);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  list;
    }
    public static List<Read_alarmJson> read_alarmJson(String json){
        List<Read_alarmJson>list = new ArrayList<Read_alarmJson>();

        try {
            JSONArray jsonArray=new JSONArray(json);
            int len=jsonArray.length();
            for(int i=0;i<len;i++) {
                Read_alarmJson read_alarmJson=new Read_alarmJson();
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                //  JSONObject jsonObject = new JSONObject(json);
                read_alarmJson.setPlate(jsonObject.getString("plate"));
                read_alarmJson.setPic(jsonObject.getString("pic"));
                read_alarmJson.setCaptureTime(jsonObject.getString("captureTime"));
                read_alarmJson.setId(jsonObject.getString("id"));
                read_alarmJson.setLocationName(jsonObject.getString("locationName"));
                read_alarmJson.setReason(jsonObject.getString("reason"));
                list.add(read_alarmJson);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  list;
    }

    public static Read_alarm_detail read_alarm_detailJson(String json){
        Read_alarm_detail read_alarm_detail=new Read_alarm_detail();
        try {
            JSONObject jsonObject=new JSONObject(json);
            read_alarm_detail.setPlate(jsonObject.getString("plate_number"));
            read_alarm_detail.setReason(jsonObject.getString("reason"));
            read_alarm_detail.setLocationName(jsonObject.getString("locationName"));
            read_alarm_detail.setId(jsonObject.getString("cap_id"));
            read_alarm_detail.setCaptureTime(jsonObject.getString("captureTime"));
            read_alarm_detail.setAppplyPhone(jsonObject.getString("applyPhone"));
            read_alarm_detail.setCarInfo(jsonObject.getString("carInfo"));
            read_alarm_detail.setCarModel(jsonObject.getString("carInfo"));
            read_alarm_detail.setColor(jsonObject.getString("color"));
            read_alarm_detail.setAppplyUser(jsonObject.getString("applyUser"));
            read_alarm_detail.setDeploySheetID(jsonObject.getString("cap_id"));
            read_alarm_detail.setDirectionName(jsonObject.getString("directionName"));
            read_alarm_detail.setSpeed(jsonObject.getString("speed"));
            read_alarm_detail.setDeployType(jsonObject.getString("deployType"));
            read_alarm_detail.setPic(jsonObject.getString("pic"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return read_alarm_detail;
    }
    public static Read_wainingJson read_wainingJson(String json){
        Read_wainingJson read_wainingJson=new Read_wainingJson();
        try {
            JSONObject jsonObject=new JSONObject(json);
            read_wainingJson.setPic(jsonObject.getString("pic"));
            read_wainingJson.setReason(jsonObject.getString("reason"));
            read_wainingJson.setCaptureTime(jsonObject.getString("captureTime"));
            read_wainingJson.setId(jsonObject.getString("id"));
            read_wainingJson.setLocationName(jsonObject.getString("locationName"));
            read_wainingJson.setPlate(jsonObject.getString("plate"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return read_wainingJson;
    }
    public static Read_waring_detailJson read_waring_detailJson(String json){
        Read_waring_detailJson read_waring_detailJson=new Read_waring_detailJson();
        try {
            JSONObject jsonObject=new JSONObject(json);
            read_waring_detailJson.setId(jsonObject.getString("id"));
            read_waring_detailJson.setPlate(jsonObject.getString("plate_number"));
            read_waring_detailJson.setLocationName(jsonObject.getString("locationName"));
            read_waring_detailJson.setReason(jsonObject.getString("reason"));
            read_waring_detailJson.setCaptureTime(jsonObject.getString("update_time"));
//            read_waring_detailJson.setApplyPhone(jsonObject.getString("applyPhone"));
            // read_waring_detailJson.setApplyUser(jsonObject.getString("applyUser"));
            read_waring_detailJson.setCarInfo(jsonObject.getString("carModel"));
            read_waring_detailJson.setCarModel(jsonObject.getString("carModel"));
            //   read_waring_detailJson.setColor(jsonObject.getString("color"));
            //   read_waring_detailJson.setDeploySheetID(jsonObject.getString("deploySheetID"));
            //   read_waring_detailJson.setDeployType(jsonObject.getString("deployType"));
            read_waring_detailJson.setDirectionName(jsonObject.getString("directionName"));
            read_waring_detailJson.setPic(jsonObject.getString("pic"));
//            read_waring_detailJson.setSpeed(jsonObject.getString("speed"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return read_waring_detailJson;
    }

}
