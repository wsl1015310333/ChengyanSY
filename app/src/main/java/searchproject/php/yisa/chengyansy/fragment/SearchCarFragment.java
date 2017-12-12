package searchproject.php.yisa.chengyansy.fragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import searchproject.php.yisa.chengyansy.CarOfSearch_ResultActivity;
import searchproject.php.yisa.chengyansy.CarSearchNullActivity;
import searchproject.php.yisa.chengyansy.Dialog.FirstDialog;
import searchproject.php.yisa.chengyansy.Dialog.MyDialog;
import searchproject.php.yisa.chengyansy.Dialog.NoPlateDialog;
import searchproject.php.yisa.chengyansy.FilesUtils;
import searchproject.php.yisa.chengyansy.Model_All.Car_resultJson;
import searchproject.php.yisa.chengyansy.R;
import searchproject.php.yisa.chengyansy.brand_activity.BaseLetterActivity;
import searchproject.php.yisa.chengyansy.brand_activity.BrandActivity;
import searchproject.php.yisa.chengyansy.utils.DensityUtil;
import searchproject.php.yisa.chengyansy.utils.HttpConnectionUtils;
import searchproject.php.yisa.chengyansy.utils.KeyboardUtil;
import searchproject.php.yisa.chengyansy.utils.ResulutionJson;
import searchproject.php.yisa.chengyansy.utils.TimeUtils;
import searchproject.php.yisa.chengyansy.utils.UploadUtil;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2017/7/17 0017.
 */

public class SearchCarFragment extends Fragment {
    static   MyDialog loadingDialog;

    MyDialog  loadingDialogCamr;
    private static final int TIME_OUT = 10000;
    EditText editText;
    private KeyboardUtil keyboardUtil;
    private LinearLayout searchcar_endTime_02;
    private LinearLayout searchcar_endTime_01;
    private LinearLayout searchcar_fristTime_02;
    private LinearLayout searchcar_fristTime_01;
    private TextView searchcar_firstTimeEdith_01;
    private TextView searchcar_endTimeEdith_01;
    private TextView searchcar_firstTimeEdith_02;
    private TextView searchcar_endTimeEdith_02;
    private LinearLayout searchcar_camera;
    public static TextView fragment_carshowTitle;
    public static TextView search_brandText;
    View view;
    private boolean isFirstEntent=true;
    //拍照
    private Uri contentUri;
    private File newFile;
    private final int NEED_CAMERA =201;
    //对话框
    LinearLayout pop_camrea;
    LinearLayout pop_openImage;
    LinearLayout pop_cancel;
    PopupWindow mPopWindow;
    private Button button_query;
    private LinearLayout search_brand;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 200:
                    loadingDialog.dismiss();
                    List<Car_resultJson> list=(List)msg.obj;
                  //  Toast.makeText(getActivity(),"img"+list.get(1).getLocationName(),Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getActivity(),CarOfSearch_ResultActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("isShowImg","false");
                    bundle.putString("firstTime",searchcar_firstTimeEdith_01.getText()+" "+searchcar_firstTimeEdith_02.getText());
                    bundle.putString("endTime",searchcar_endTimeEdith_01.getText()+" "+searchcar_endTimeEdith_02.getText());
                    bundle.putString("plate_number",editText.getText().toString());
                    bundle.putSerializable("hashMap",(Serializable)hashMap);
                    bundle.putSerializable("list", (Serializable) list);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case 201:
                    loadingDialog.dismiss();
                    Intent intentNull=new Intent(getActivity(),CarSearchNullActivity.class);
                    Bundle bundleNull=new Bundle();
              //      bundleNull.putString("isShowImg","false");
                    bundleNull.putString("firstTime",searchcar_firstTimeEdith_01.getText()+" "+searchcar_firstTimeEdith_02.getText());
                    bundleNull.putString("endTime",searchcar_endTimeEdith_01.getText()+" "+searchcar_endTimeEdith_02.getText());
                    bundleNull.putString("plate_number",editText.getText().toString());
//                    Intent intent1=new Intent(getActivity(),CarOfSearch_ResultActivity.class);
//                    Bundle bundle1=new Bundle();
//                    List list1=null;
//                    bundle1.putString("firstTime",searchcar_firstTimeEdith_01.getText()+" "+searchcar_firstTimeEdith_02.getText());
//                    bundle1.putString("endTime",searchcar_endTimeEdith_01.getText()+" "+searchcar_endTimeEdith_02.getText());
//                    bundle1.putString("plate_number",editText.getText().toString());
//                    bundle1.putSerializable("list", (Serializable) list1);
                    intentNull.putExtras(bundleNull);
                    startActivity(intentNull);
                 //  Toast.makeText(getActivity(),"网络异常请重试！",Toast.LENGTH_SHORT).show();
                    break;
                case 203:
                    break;

                case 2000:
                    if(loadingDialogCamr!=null) {
                        loadingDialogCamr.dismiss();
                    }
                    String plate = null;
                    String brand_id=null;
                    String model_id=null;
                    String year_id=null;
                    String carinfo=null;
                    String result=(String)msg.obj;
                    try {
                        JSONObject jsonObject=new JSONObject(result);
                        plate=jsonObject.getString("plate");
                        if (plate.equals("")&&jsonObject.getString("carInfo").equals("")){
                            showNoPlateDialog();
                        }else
                        {
                            fragment_carshowTitle.setVisibility(View.VISIBLE);
                            carinfo = jsonObject.getString("carInfo");
                            brand_id = jsonObject.getString("brandId");
                            year_id = jsonObject.getString("yearId");
                            model_id = jsonObject.getString("modelId");
                            search_brandText.setText(carinfo);
                            BaseLetterActivity.mapModel.put("model", model_id);
                            BaseLetterActivity.mapYear.put("year", year_id);
                            BaseLetterActivity.mapBrand.put("brand", brand_id);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
//                        Toast.makeText(getActivity(),URLDecoder.decode(plate==null?"":plate, "GB2312"),Toast.LENGTH_SHORT).show();
//Toast.makeText(getActivity(),URLDecoder.decode(plate==null?"":plate, "utf-8"),Toast.LENGTH_SHORT).show();
                        editText.setText(  URLDecoder.decode(plate==null?"":plate, "GBK"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    private HashMap hashMap;
    private ImageButton brand_canel;
private ImageButton editext_canel;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view= View.inflate(getActivity(), R.layout.fragment_searchcar,null);
        initView();
       initAction();

        return  view;
    }
    private void isFirst(){

        SharedPreferences shared=getActivity().getSharedPreferences("is", MODE_PRIVATE);
        boolean isfer=shared.getBoolean("isfer", true);
        SharedPreferences.Editor editor=shared.edit();
        if(isfer){
            final FirstDialog firstDialog=new FirstDialog(getActivity());
            firstDialog.getWindow().setLayout(DensityUtil.dip2px(getActivity(),278),DensityUtil.dip2px(getActivity(),399)); //对话框大小应根据屏幕大小调整
            firstDialog.setOnButtonClickListener(new FirstDialog.OnDialogButtonClickListener() {
                @Override
                public void okButtonClick() {
                    firstDialog.dismiss();
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, NEED_CAMERA);
                    } else {
                        startCamera();
                    }
                }

                @Override
                public void cancelButtonClick() {

                }
            });
            firstDialog.setCanceledOnTouchOutside(false);
            firstDialog.show();
            editor.putBoolean("isfer", false);
            editor.commit();
        }else {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, NEED_CAMERA);
            } else {
                startCamera();
            }
        }



    }
   private void initView(){
       editext_canel=(ImageButton)view.findViewById(R.id.editext_canel);
       fragment_carshowTitle=(TextView)view.findViewById(R.id.fragment_carshowTitle);

    button_query=(Button)view.findViewById(R.id.button_query);
    editText=(EditText) view.findViewById(R.id.et_mobile);

    searchcar_fristTime_01=(LinearLayout)view.findViewById(R.id.searchca_firstTime_01);
    searchcar_fristTime_02=(LinearLayout)view.findViewById(R.id.searchca_firstTime_02);
    searchcar_endTime_01=(LinearLayout)view.findViewById(R.id.searchca_endTime_01);
    searchcar_endTime_02=(LinearLayout)view.findViewById(R.id.searchca_endTime_02);

       search_brand=(LinearLayout)view.findViewById(R.id.search_brand);
       search_brandText=(TextView)view.findViewById(R.id.search_brandText);
    searchcar_camera=(LinearLayout)view.findViewById(R.id.searchcar_camera);
       brand_canel=(ImageButton)view.findViewById(R.id.brand_canel);
    searchcar_firstTimeEdith_01=(TextView)view.findViewById(R.id.searchca_firstTimeEditext_01);
    searchcar_endTimeEdith_01=(TextView)view.findViewById(R.id.searchca_endTimeEditext_01);
    searchcar_firstTimeEdith_02=(TextView)view.findViewById(R.id.searchca_firstTimeEditext_02);
    searchcar_endTimeEdith_02=(TextView)view.findViewById(R.id.searchca_endTimeEditext_02);
    int [] time= TimeUtils.getTimeNow();
    searchcar_firstTimeEdith_01.setText(time[0]+"-"+(time[1]<10?"0"+time[1]:time[1]+"")+"-"+((time[2]-2)<10?"0"+(time[2]-2):(time[2]-2)));
    searchcar_firstTimeEdith_02.setText((time[3]<10?"0"+time[3]:time[3])+":"+(time[4]<10?"0"+time[4]:time[4]+"")+":"+"00");
    searchcar_endTimeEdith_01.setText(time[0]+"-"+(time[1]<10?"0"+time[1]:time[1]+"")+"-"+((time[2])<10?"0"+(time[2]):(time[2])));
    searchcar_endTimeEdith_02.setText((time[3]<10?"0"+time[3]:time[3])+":"+(time[4]<10?"0"+time[4]:time[4]+"")+":"+"00");

}
    private void initAction(){
        search_brand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

Intent intent=new Intent(getActivity(), BrandActivity.class);
                startActivity(intent);
                if (keyboardUtil!=null) {
                    editText.clearFocus();
                    keyboardUtil.hideKeyboard();
                }
            }
        });
        editext_canel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });
        brand_canel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_brandText.setText("");
                fragment_carshowTitle.setVisibility(View.INVISIBLE);
                BaseLetterActivity.mapBrand.clear();
                BaseLetterActivity.mapModel.clear();
                BaseLetterActivity.mapYear.clear();

            }
        });
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.requestFocus();
               editText. setCursorVisible(true);
                Log.e("e","e");
                if(keyboardUtil == null){
                    keyboardUtil = new KeyboardUtil(getActivity(), editText);
                    keyboardUtil.hideSoftInputMethod();
                    keyboardUtil.showKeyboard();
                }else {
                    keyboardUtil = new KeyboardUtil(getActivity(), editText);
                    keyboardUtil.hideSoftInputMethod();
                    keyboardUtil.showKeyboard();
                }
                return false;
            }
        });
        button_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String firstTime=searchcar_firstTimeEdith_01.getText().toString()+" "+searchcar_firstTimeEdith_02.getText().toString();
                final String endTime=searchcar_endTimeEdith_01.getText().toString()+" "+searchcar_endTimeEdith_02.getText().toString();
                final String plate_name= editText.getText().toString();
                if(isPlateRight(plate_name)==false){

                    Toast.makeText(getActivity(),"车牌号格式不正确！",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    hashMap = new HashMap();
                    Log.e("palte",plate_name.replaceAll("\\s*", ""));
                    hashMap.put("plate", plate_name.equals("")?"":plate_name);
                    hashMap.put("beginDate", firstTime);
                    hashMap.put("endDate", endTime);
                    hashMap.put("pn", "1");

                        if (search_brandText.getText().toString().length()>0){
                            counter=0;
                            int i=stringNumbers(search_brandText.getText().toString());
//                            Log.e("i",Integer.toString(i));
//                            Log.e("SearchCarFragment",BaseLetterActivity.mapBrand.get("brand"));
                            if(i==1){
                                hashMap.put("brand_id", BaseLetterActivity.mapBrand.get("brand"));
                                hashMap.put("model_id", BaseLetterActivity.mapModel.get("model"));
                            }else  if(i==2){
                                hashMap.put("brand_id", BaseLetterActivity.mapBrand.get("brand"));
                                hashMap.put("model_id", BaseLetterActivity.mapModel.get("model"));
                                hashMap.put("year_id", BaseLetterActivity.mapYear.get("year"));
                            }else {
                                hashMap.put("brand_id", BaseLetterActivity.mapBrand.get("brand"));
                            }

                    }
                    Iterator<Map.Entry<String, String>> it = hashMap.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<String, String> entry = it.next();
                        System.out.println("Searchkey= " + entry.getKey() + " and value= " + entry.getValue());
                    }
                showDiaglog(true);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final String token = FilesUtils.sharedGetFile(getActivity(), "user", "token");
//                           mHandler.post(new Runnable() {
//                               @Override
//                               public void run() {
//                                   Toast.makeText(getActivity(),HttpConnectionUtils.ipport,Toast.LENGTH_SHORT).show();
//                               }
//                           });

                         //  final String result = HttpConnectionUtils.sendGETRequest(HttpConnectionUtils.ipport+"/api/car_result", hashMap, token);
                            final  String result=initJson();
                       //     final String result = HttpConnectionUtils.sendGETRequest("10.73.194.252:8088/api/car_result", hashMap, token);

                        //    List<Car_resultJson> list = ResulutionJson.car_resultJson(result);
                            if (result.length()<60) {
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mHandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                             //   Toast.makeText(getActivity(),"result"+result,Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        Toast.makeText(getActivity(),"找不到过车记录",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                //数据为空
                                Message message = mHandler.obtainMessage();
                                message.what = 201;
//                            message.obj=(String)name;
                                mHandler.sendMessage(message);
                            } else {
                                List<Car_resultJson> list = ResulutionJson.car_resultJson(result);
                                Message message = mHandler.obtainMessage();
                                message.what = 200;
                                message.obj = (List) list;
                                mHandler.sendMessage(message);
                           }

                        }
                    }).start();


               }
            }
        });
        searchcar_fristTime_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (keyboardUtil!=null) {
                    editText.clearFocus();
                    keyboardUtil.hideKeyboard();
                }
              firstTimeDialog(false);
            }
        });
        searchcar_fristTime_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (keyboardUtil!=null) {
                    editText.clearFocus();
                    keyboardUtil.hideKeyboard();
                }
            endTimeDialog(false);
            }
        });
        searchcar_endTime_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (keyboardUtil!=null) {
                    editText.clearFocus();
                    keyboardUtil.hideKeyboard();
                }
firstTimeDialog(true);
            }
        });
        searchcar_endTime_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (keyboardUtil!=null) {
                    editText.clearFocus();
                    keyboardUtil.hideKeyboard();
                }
               endTimeDialog(true);
            }
        });

        searchcar_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isFirst();
             //   showBottomPop(view);
                if (keyboardUtil!=null) {
                    editText.clearFocus();
                    keyboardUtil.hideKeyboard();
                }
            // showBottomPop(view);
            //    showBottomPop(view);

            }
        });
    }

    private void firstTimeDialog(final boolean isEnd){
    int nowData[]=new int[5];
    int i=0;
    if(isEnd){
     String enddata  = searchcar_endTimeEdith_01.getText().toString();
        String endData[]=enddata.split("-");
        for(String end_data:endData){
            nowData[i++]= Integer.parseInt(end_data);
        }
    }else {
        String enddata  = searchcar_firstTimeEdith_01.getText().toString();
        String endData[]=enddata.split("-");
        for(String end_data:endData){
            nowData[i++]= Integer.parseInt(end_data);
        }
    }
    DatePickerDialog dd = new DatePickerDialog(getActivity(),
            new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String dateIn="";
                    try {
                        String dateInString = year + "-" + (monthOfYear + 1 < 10 ? "0" + (monthOfYear + 1) : monthOfYear + 1 + "") + "-" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
                        String end_time_Text;
                        if(isEnd==false) {
                          dateIn  = year + "-" + (monthOfYear + 1 < 10 ? "0" + (monthOfYear + 1) : monthOfYear + 1 + "") + "-" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + " " +
                               searchcar_firstTimeEdith_02.getText().toString();
                        end_time_Text = searchcar_endTimeEdith_01.getText().toString() + " " + searchcar_endTimeEdith_02.getText().toString();
                   }else {
                       dateIn  = year + "-" + (monthOfYear + 1 < 10 ? "0" + (monthOfYear + 1) : monthOfYear + 1 + "") + "-" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + " " +
                               searchcar_endTimeEdith_02.getText().toString();
                            end_time_Text = searchcar_firstTimeEdith_01.getText().toString() + " " + searchcar_firstTimeEdith_02.getText().toString();


                        }
                        java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date d1 = null;
                        Date d2 = null;


                        Log.e("end",end_time_Text);
                        Log.e("first",dateInString);
                        if (!end_time_Text.equals("")) {
                            try {if (isEnd){
                                d1 = df.parse(dateIn);//后
                                d2 = df.parse(end_time_Text);//前
                            }else {
                                d1 = df.parse(end_time_Text);
                                d2 = df.parse(dateIn);
                            }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        if(Math.abs((d2.getTime()-d1.getTime())/(1000*24*60*60))>90){
                            Toast.makeText(getActivity(), "结束时间和开始时间不能相差90天！", Toast.LENGTH_LONG).show();
return;
                        }
                            if (d1.getTime() <= d2.getTime()) {
Log.e("1",""+Long.toString(d2.getTime())+"---"+Long.toString(d1.getTime()));
                                Log.e("2",""+Long.toString((d2.getTime()-d1.getTime())/(1000*24*60*60)));


                                Toast.makeText(getActivity(), "结束时间不能等于或早于开始时间！", Toast.LENGTH_SHORT).show();
                                return;
                            }else {
                                // Date date = formatter.parse(dateInString);
                                Log.e("txtdata", dateInString);
                                if (isEnd) {
                                    searchcar_endTimeEdith_01.setText(dateInString);

                                } else {
                                    searchcar_firstTimeEdith_01.setText(dateInString);
                                }
                            }



                        }catch(Exception ex){

                        }
                }
            }, nowData[0], nowData[1]-1, nowData[2]);
    dd.show();
}
    private void endTimeDialog(final boolean isEnd){
        int nowData[]=new int[3];
        int i=0;
        if(isEnd){
            String enddata  = searchcar_endTimeEdith_02.getText().toString();
            String endData[]=enddata.split(":");
            for(String end_data:endData){
                nowData[i++]= Integer.parseInt(end_data);
            }
        }else {
            String enddata  = searchcar_firstTimeEdith_02.getText().toString();
            String endData[]=enddata.split(":");
            for(String end_data:endData){
                nowData[i++]= Integer.parseInt(end_data);
            }
        }
        TimePickerDialog td = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String dtStart;
                        String dtStartTime;
                        try{
                            if (isEnd) {
                              dtStart  =(hourOfDay < 10 ? "0" + hourOfDay : hourOfDay + "") + ":" + (minute < 10 ? "0" + minute : minute + "");
                                dtStartTime=searchcar_endTimeEdith_01.getText().toString()+" "+ dtStart+":"+"00";
                            }else{
dtStart=(hourOfDay < 10 ? "0" + hourOfDay : hourOfDay + "") + ":" + (minute < 10 ? "0" + minute : minute + "");
                                dtStartTime=searchcar_firstTimeEdith_01.getText().toString()+" "+ dtStart+":"+"00";
                            }

                            java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date d1 = null;
                            Date d2 = null;

                            String end_time_Text = searchcar_endTimeEdith_01.getText().toString() + " " + searchcar_endTimeEdith_02.getText().toString();
                            Log.e("dtStart",dtStartTime);
                            Log.e("end_time",end_time_Text);
                            if (!end_time_Text.equals("")) {
                                try {
                                    if (isEnd){
                                        d2 = df.parse(dtStartTime);
                                        d1 = df.parse(searchcar_firstTimeEdith_01.getText().toString() + " " + searchcar_firstTimeEdith_02.getText().toString());

                                    }else {
                                        d1 = df.parse(dtStartTime);
                                        d2 = df.parse(searchcar_endTimeEdith_01.getText().toString() + " " + searchcar_endTimeEdith_02.getText().toString());

                                    }
                                          } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            if(Math.abs((d2.getTime()-d1.getTime())/(1000*24*60*60))>90){
                                Toast.makeText(getActivity(), "结束时间和开始时间不能相差90天！", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (d1.getTime()>= d2.getTime()) {

                                Toast.makeText(getActivity(), "结束时间不能等于或早于开始时间！", Toast.LENGTH_SHORT).show();
                                return;
                            }else {

                                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                                Log.e("dataStart",dtStart);
                                if (isEnd){
                                    searchcar_endTimeEdith_02.setText(dtStart+":"+"00");
                                }else {
                                    searchcar_firstTimeEdith_02.setText(dtStart+":"+"00");
                                }
                                Time timeValue = new Time(format.parse(dtStart).getTime());
//                                            txttime.setText(String.valueOf(timeValue));
//                                            String amPm = hourOfDay % 12   ":"   minute   " "   ((hourOfDay >= 12) ? "PM" : "AM");
//                                            txttime.setText(amPm   "
//                                                    "   String.valueOf(timeValue));
                            }




                        } catch (Exception ex) {
                            //  txttime.setText(ex.getMessage().toString());
                        }
                    }
                },
                nowData[0], nowData[1],
                DateFormat.is24HourFormat(getActivity())
        );
        td.show();
    }
    private void showAnimation(View popView) {
        AnimationSet animationSet = new AnimationSet(false);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1.0f);
        alphaAnimation.setDuration(300);
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f
        );
        translateAnimation.setDuration(300);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(translateAnimation);
        popView.startAnimation(animationSet);
    }
    private void showBottomPop(View parent) {
        final View popView = View.inflate(getActivity(), R.layout.popupwindows_camrea, null);
        showAnimation(popView);//开启动画
        mPopWindow  = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
      //  clickPopItem(popView, mPopWindow);//条目的点击
        pop_camrea=(LinearLayout)popView.findViewById(R.id.pop_camera);
        pop_openImage=(LinearLayout)popView.findViewById(R.id.pop_openImage);
        pop_cancel=(LinearLayout)popView.findViewById(R.id.pop_cancel);
        pop_camrea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, NEED_CAMERA);
                } else {
                    startCamera();
                }
            }
        });
        pop_openImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, NEED_CAMERA);
                }else {
                    mPopWindow.dismiss();
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");

                    startActivityForResult(intent, 203);
                }
            }
        });
        pop_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mPopWindow && mPopWindow.isShowing()){
                    mPopWindow.dismiss();
                    if(null == mPopWindow){
                        Log.e("MainActivity","null == mPopupWindow");
                    }
                }
            }
        });
        mPopWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopWindow.showAtLocation(parent,
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setFocusable(true);
        mPopWindow.update();
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.7f;
        getActivity().getWindow().setAttributes(lp);

        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==NEED_CAMERA){
            ContentResolver contentProvider = getActivity().getContentResolver();
            ParcelFileDescriptor mInputPFD;
            try {
                //获取contentProvider图片
                mInputPFD = contentProvider.openFileDescriptor(contentUri, "r");
                FileDescriptor fileDescriptor = mInputPFD.getFileDescriptor();
//                Log.e("contentUri",contentUri.toString());
//              //  String imagepath=getRealFilePath(getActivity(),contentUri);
//              //  Log.e("imageapth",imagepath);
//                Log.e("fileDescriptor",fileDescriptor.toString());
//                Log.e("newFile",newFile.toString());
//                Log.e("newFileLength",newFile.length()+"");
                Bitmap bitmap= BitmapFactory.decodeFile(newFile.toString(),getBitmapOption(2));
                 final File retureImg=saveBitmapFile(bitmap);
//                Log.e("retureImg",retureImg.toString());
//                Log.e("retureImg",retureImg.length()+"");
                final HashMap hashMap=new HashMap();
                final String token = FilesUtils.sharedGetFile(getActivity(), "user", "token");
                showDiaglogCamr(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                       // UploadUtil uploadUtil=UploadUtil.getInstance();
//                        final String result=  uploadUtil.toUploadFile(newFile.toString(),"file","http://10.73.194.252:8088/api/get_plate",null,token);
                       //  final String  resulLogin=HttpConnectionUtils.sendGETRequest("http://10.0.2.2:8088/UploadFiles/LoginServlet",null,null);
                      //   final String result1=  HttpConnectionUtils.uploadFile(retureImg,"http://192.168.4.122:80/app/public/index.php/index/api/get_plate",null,token);
                        final String result1=  HttpConnectionUtils.uploadFile(retureImg,"http://"+HttpConnectionUtils.ipport+"/api/get_plate",null,token);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {

                                //Toast.makeText(getActivity(),result1+"result",Toast.LENGTH_SHORT).show();
                            }
                        });
                        retureImg.delete();
//                        mHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                             //   Toast.makeText(getActivity(),resulLogin+"resulLogin-1",Toast.LENGTH_SHORT).show();
//                            }
//                        });
                        if (result1!=null&&result1.length()>2){
                            Message message=mHandler.obtainMessage();
                            message.what=2000;
                            message.obj=result1;
                            mHandler.sendMessage(message);
                        }else {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    loadingDialogCamr.dismiss();
                                    showNoPlateDialog();
                                }
                            });
                        }
                    }
                }).start();
              //  retureImg.delete();
                getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory())));

//                Intent intent=new Intent(getActivity(),MainActivity.class);
//                Bundle bundle=new Bundle();
//                String plate_number=editText.getText().
//                bundle.putString("imgpath",newFile.toString());
                //请求网络
//               intent.putExtras(bundle);
//               startActivity(intent);
          //     mImageView.setImageBitmap(BitmapFactory.decodeFileDescriptor(fileDescriptor));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else  if(requestCode==300||resultCode==300){
Toast.makeText(getActivity(),"fra--300",Toast.LENGTH_SHORT).show();
        }

            Log.e("LocalUri","start");
            if(data!=null){
                Uri localUri = data.getData();
                Log.e("LocalUri",localUri.toString());
               // Intent intent=new Intent(getActivity(),CarOfSearch_ResultActivity .class);
                Bundle bundle=new Bundle();
                final String imagepath=getRealFilePath(getActivity(),localUri);
                Log.e("imagepath",imagepath);
                bundle.putString("imgpath",imagepath);
                Bitmap bitmap= BitmapFactory.decodeFile(newFile.toString(),getBitmapOption(2));
                final File retureImg=saveBitmapFile(bitmap);
                final HashMap hashMap=new HashMap();
                final String token = FilesUtils.sharedGetFile(getActivity(), "user", "token");
new Thread(new Runnable() {
    @Override
    public void run() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
            }
        });
        UploadUtil uploadUtil=UploadUtil.getInstance();
  //final String result=  uploadUtil.toUploadFile(imagepath,"file"," http://192.168.4.139:8088/UploadFiles/UploadFileServlet",null,token);///10.73.194.252:8088 http://192.168.4.139:8088/UploadFiles/UploadFileServlet
     // final String result1=  HttpConnectionUtils.uploadFile(new File(imagepath),"http://192.168.4.122:80/app/public/index.php/index/api/get_plate",null,token);
        final String result1=  HttpConnectionUtils.uploadFile(retureImg,"http://"+HttpConnectionUtils.ipport+"/api/get_plate",null,token);
        mHandler.post(new Runnable() {
            @Override
            public void run() {

                retureImg.delete();
             //     Toast.makeText(getActivity(),result1+"result",Toast.LENGTH_SHORT).show();
//                try {
//                    Toast.makeText(getActivity(),URLDecoder.decode(result1,"utf-8"),Toast.LENGTH_SHORT).show();
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
            //    Toast.makeText(getActivity(),result1+"--",Toast.LENGTH_SHORT).show();

            }
        });
      if (result1!=null&&result1.length()>2){
          Message message=mHandler.obtainMessage();
          message.what=2000;
          message.obj=result1;
          mHandler.sendMessage(message);
      }else {
          mHandler.post(new Runnable() {
              @Override
              public void run() {
                  showNoPlateDialog();
              }
          });
      }

    }
}).start();


             //   intent.putExtras(bundle);
              //  startActivity(intent);

            }
        }





    /**
     * 打开相机获取图片
     */
    private void startCamera() {
        File imagePath = new File(Environment.getExternalStorageDirectory(), "images");
        if (!imagePath.exists()) imagePath.mkdirs();
        newFile = new File(imagePath, DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA))+ "default_image.jpg");

        //第二参数是在manifest.xml定义 provider的authorities属性 yisa.fileprovider
        contentUri = FileProvider.getUriForFile(getActivity(), "yisa.fileprovider_wf", newFile);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //兼容版本处理，因为 intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION) 只在5.0以上的版本有效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ClipData clip =
                    ClipData.newUri(getActivity().getContentResolver(), "A photo", contentUri);
            intent.setClipData(clip);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            List<ResolveInfo> resInfoList =
                    getActivity().getPackageManager()
                            .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                getActivity().grantUriPermission(packageName, contentUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        startActivityForResult(intent, NEED_CAMERA);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case NEED_CAMERA:
                // 如果权限被拒绝，grantResults 为空
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCamera();
                } else {
                    Toast.makeText(getActivity(), "功能需要相机和读写文件权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }
    public static String getRealFilePath(final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
    int  counter=0;
    public  int stringNumbers(String str)
    {

        if (str.indexOf("-")==-1)
        {
            return 0;
        }
        else if(str.indexOf("-") != -1)
        {

                counter++;
                stringNumbers(str.substring(str.indexOf("-") + 1));

            return counter;
        }
        return 0;
    }
public boolean isPlateRight(String plate){
if (plate.length()>8){
    return false;
}
    if (plate.length()>0){
if(plate.subSequence(0,1).equals("?")) {
    return false;
}
if(isNumeric(plate)||ischeckCharacter(plate)){
    return false;
}

    }


    return true;
}
    public static boolean isNumeric(String str){//判断第一个字符是不是数字
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
    public        boolean   ischeckCharacter(String   fstrData)////判断第一个字符是不是字母
    {
        char   c   =   fstrData.charAt(0);
        if(((c>='a'&&c<='z')   ||   (c>='A'&&c<='Z')))
        {
            return   true;
        }else{
            return   false;
        }
    }

    private void showDiaglog(Boolean isShow){
        loadingDialog=new MyDialog(getActivity(),"正在加载中···");
        loadingDialog.getWindow().setLayout(DensityUtil.dip2px(getActivity(),278), DensityUtil.dip2px(getActivity(),122));
        loadingDialog.setCanceledOnTouchOutside(false);
            loadingDialog.show();

    }
    private void showDiaglogCamr(Boolean isShow){
        loadingDialogCamr=new MyDialog(getActivity(),"正在识别中···");
        loadingDialogCamr.getWindow().setLayout(DensityUtil.dip2px(getActivity(),278), DensityUtil.dip2px(getActivity(),122));
        loadingDialogCamr.setCanceledOnTouchOutside(false);
        loadingDialogCamr.show();

    }
    private BitmapFactory.Options getBitmapOption(int inSampleSize){
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }
    public File saveBitmapFile(Bitmap bitmap) {
        File imagePath = new File(Environment.getExternalStorageDirectory(), "images");

        File file = new File(imagePath, DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + "img.jpg");//将要保存图片的路径
        Log.e("newFile",file.getAbsolutePath());
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  file;
    }
    private void  showNoPlateDialog(){
        final NoPlateDialog noPlateDialog=new NoPlateDialog(getActivity());
        noPlateDialog.getWindow().setLayout(DensityUtil.dip2px(getActivity(),278),DensityUtil.dip2px(getActivity(),122)); //对话框大小应根据屏幕大小调整

        noPlateDialog.setOnButtonClickListener(new NoPlateDialog.OnDialogButtonClickListener() {
            @Override
            public void okButtonClick() {
                noPlateDialog.dismiss();
            }

            @Override
            public void cancelButtonClick() {

            }
        });
        noPlateDialog.setCanceledOnTouchOutside(false);
        noPlateDialog.show();
    }

    public String initJson(){
      String result="{\n" +
              "  \"totalRecords\": 619238,\n" +
              "  \"searchList\": [\n" +
              "    {\n" +
              "      \"plate\": \"鲁B510FB\",\n" +
              "      \"model\": \"\",\n" +
              "      \"locationName\": \"银河路古镇社区测速\",\n" +
              "      \"captureTime\": \"2017-12-12 10:56:03\",\n" +
              "      \"pic\": \"http://10.49.222.84:8088/img.php?img_uuid=http%3A%2F%2F192.168.0.6%2Ffkk%2F20171212%2Fkakou%2Fyinhelucesu-westttoeast%2FB510FB__2017-12-12-10-56-03-833_370214000000030119-34-60_2_2.JPG\",\n" +
              "      \"plateType\": \"4\"\n" +
              "    },\n" +
              "    {\n" +
              "      \"plate\": \"鲁B069SU\",\n" +
              "      \"model\": \"\",\n" +
              "      \"locationName\": \"长城路高架桥下路口\",\n" +
              "      \"captureTime\": \"2017-12-12 10:56:03\",\n" +
              "      \"pic\": \"http://10.49.222.84:8088/img.php?img_uuid=http%3A%2F%2F172.23.174.241%3A80%2Ffkk%2F20171212%2Fkakou%2Fchangchengluheilongjiang-easttowest%2FB069SU__2017-12-12-10-56-03-587_370214000000010029-0-0_1_1.JPG\",\n" +
              "      \"plateType\": \"4\"\n" +
              "    },\n" +
              "    {\n" +
              "      \"plate\": \"鲁BM17J3\",\n" +
              "      \"model\": \"\",\n" +
              "      \"locationName\": \"春阳路与荟城西支路\",\n" +
              "      \"captureTime\": \"2017-12-12 10:56:03\",\n" +
              "      \"pic\": \"http://10.49.222.84:8088/img.php?img_uuid=http%3A%2F%2F172.23.174.240%3A80%2Ffkk%2F20171212%2Fkakou%2Fchunyanghuichengxizhi-westttoeast%2FBM17J3__2017-12-12-10-56-03-420_370214000000010439-0-0_2_1.JPG\",\n" +
              "      \"plateType\": \"4\"\n" +
              "    },\n" +
              "    {\n" +
              "      \"plate\": \"鲁B899H0\",\n" +
              "      \"model\": \"\",\n" +
              "      \"locationName\": \"河东路与汇海路路口西侧\",\n" +
              "      \"captureTime\": \"2017-12-12 10:56:03\",\n" +
              "      \"pic\": \"http://10.49.222.84:8088/img.php?img_uuid=http%3A%2F%2F172.23.2.84%3A6120%2Fpic%3F8ddc31ze0-%3Ds120106033571e-%3Dt1i8m%2A%3Dp1p2i%3Dd1s%2Ai5d97%2A6d5%3D%2A1b2i25175fc6a820c3-90a436-534i4ed%2Ae832i54%3D\",\n" +
              "      \"plateType\": \"4\"\n" +
              "    },\n" +
              "    {\n" +
              "      \"plate\": \"鲁B5865警\",\n" +
              "      \"model\": \"\",\n" +
              "      \"locationName\": \"204国道与铁骑山路\",\n" +
              "      \"captureTime\": \"2017-12-12 10:56:03\",\n" +
              "      \"pic\": \"http://10.49.222.84:8088/img.php?img_uuid=http%3A%2F%2F172.23.174.242%3A8099%2F20171212%2Fkakou%2Ferlingsiguodaoyutiejishanlu-easttowest%2FB5865__2017-12-12-10-56-03-138_370214000000010117-31-1000_3_1.JPG\",\n" +
              "      \"plateType\": 0\n" +
              "    },\n" +
              "    {\n" +
              "      \"plate\": \"鲁B57PP9\",\n" +
              "      \"model\": \"\",\n" +
              "      \"locationName\": \"新青威路与春阳路路口\",\n" +
              "      \"captureTime\": \"2017-12-12 10:56:03\",\n" +
              "      \"pic\": \"http://10.49.222.84:8088/img.php?img_uuid=http%3A%2F%2F192.168.0.3%3A80%2Ffkk%2F20171212%2Fkakou%2Fxinqingweiluyuchunyanglulukou-northtosouth%2FB57PP9__2017-12-12-10-56-03-686_370214000000010294-0-0_3_1.JPG\",\n" +
              "      \"plateType\": \"4\"\n" +
              "    },\n" +
              "    {\n" +
              "      \"plate\": \"鲁B8YJ90\",\n" +
              "      \"model\": \"\",\n" +
              "      \"locationName\": \"长城路与兴阳路路口\",\n" +
              "      \"captureTime\": \"2017-12-12 10:56:03\",\n" +
              "      \"pic\": \"http://10.49.222.84:8088/img.php?img_uuid=http%3A%2F%2F172.23.174.240%3A80%2Ffkk%2F20171212%2Fkakou%2Fchangchengluyuxingyang-southtonorth%2FB8YJ90__2017-12-12-10-56-03-575_370214000000010034-0-0_3_1.JPG\",\n" +
              "      \"plateType\": \"4\"\n" +
              "    },\n" +
              "    {\n" +
              "      \"plate\": \"鲁B3CQ63\",\n" +
              "      \"model\": \"\",\n" +
              "      \"locationName\": \"凤凰山路与老青威路路口\",\n" +
              "      \"captureTime\": \"2017-12-12 10:56:03\",\n" +
              "      \"pic\": \"http://10.49.222.84:8088/img.php?img_uuid=http%3A%2F%2F192.168.0.3%3A80%2Ffkk%2F20171212%2Fkakou%2Ffenghuagnshangyulaoqingwei-northtosouth%2FB3CQ63__2017-12-12-10-56-03-525_370214000000010167-0-0_2_1.JPG\",\n" +
              "      \"plateType\": \"4\"\n" +
              "    },\n" +
              "    {\n" +
              "    \n" +
              "      \"plate\": \"鲁B6DH79\",\n" +
              "      \"model\": \"\",\n" +
              "      \"locationName\": \"204国道与正阳路\",\n" +
              "      \"captureTime\": \"2017-12-12 10:56:03\",\n" +
              "      \"pic\": \"http://10.49.222.84:8088/img.php?img_uuid=http%3A%2F%2F172.23.174.240%3A80%2Ffkk%2F20171212%2Fkakou%2Ferlingyuzhengyang-southtonorth%2FB6DH79__2017-12-12-10-56-03-546_370214000000010278-0-0_4_1.JPG\",\n" +
              "      \"plateType\": \"4\"\n" +
              "    },\n" +
              "    {\n" +
              "      \"plate\": \"鲁BX818P\",\n" +
              "      \"model\": \"\",\n" +
              "      \"locationName\": \"春阳路与国城路\",\n" +
              "      \"captureTime\": \"2017-12-12 10:56:03\",\n" +
              "      \"pic\": \"http://10.49.222.84:8088/img.php?img_uuid=http%3A%2F%2F172.23.174.240%3A80%2Ffkk%2F20171212%2Fkakou%2Fchunyangguocheng-easttowest%2FBX818P__2017-12-12-10-56-03-500_370214000000010431-0-0_2_1.JPG\",\n" +
              "      \"plateType\": \"4\"\n" +
              "    }\n" +
              "  ]\n" +
              "}";
        return result;
    }


}
