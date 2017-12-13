package searchproject.php.yisa.chengyansy;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import searchproject.php.yisa.chengyansy.Model_All.Car_resultJson;
import searchproject.php.yisa.chengyansy.Model_All.Read_waring_detailJson;
import searchproject.php.yisa.chengyansy.PictureDialog.ImagePagerActivity;
import searchproject.php.yisa.chengyansy.PictureDialog.PictureConfig;
import searchproject.php.yisa.chengyansy.utils.DensityUtil;
import searchproject.php.yisa.chengyansy.utils.HttpConnectionUtils;
import searchproject.php.yisa.chengyansy.utils.ResulutionJson;

/**
 * Created by Administrator on 2017/8/1 0001.
 */

public class Perimeter_WarningResultActivity extends AppCompatActivity {
    TextView zbyjr_plate_number;
    TextView zbyjr_model;
    TextView zbyjr_Randmodel;
    TextView zbyjr_time;
    TextView zbyjr_place;
    TextView zbyjr_direction;
    TextView zbyjr_reason;
    Button zbyjr_button;
    ImageView  zbyjr_img;
    Toolbar toolbar;
    TextView textView;
    private LinearLayout return_;
    private LinearLayout alarm_carinfo_acap;

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 200:
                    List<Car_resultJson> list=(List)msg.obj;
                    //  Toast.makeText(getActivity(),"img"+list.get(1).getLocationName(),Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Perimeter_WarningResultActivity.this,CarOfSearch_ResultActivity.class);
                    Bundle bundle=new Bundle();

                    bundle.putString("firstTime","");
                    bundle.putString("endTime","");
                    bundle.putString("plate_number",zbyjr_plate_number.getText().toString().trim());
                    bundle.putSerializable("list", (Serializable) list);
                    bundle.putSerializable("hashMap",(Serializable)hashMap);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case 201:
//                    Intent intent1=new Intent(getActivity(),CarOfSearch_ResultActivity.class);
//                    Bundle bundle1=new Bundle();
//                    List list1=null;
//                    bundle1.putString("firstTime",searchcar_firstTimeEdith_01.getText()+" "+searchcar_firstTimeEdith_02.getText());
//                    bundle1.putString("endTime",searchcar_endTimeEdith_01.getText()+" "+searchcar_endTimeEdith_02.getText());
//                    bundle1.putString("plate_number",editText.getText().toString());
//                    bundle1.putSerializable("list", (Serializable) list1);
//                    intent1.putExtras(bundle1);
//                    startActivity(intent1);
                    //  Toast.makeText(getActivity(),"网络异常请重试！",Toast.LENGTH_SHORT).show();
                    break;
                case 203:
                    break;
                case 300:
                    Intent A_Car_pepoleintent=new Intent(Perimeter_WarningResultActivity.this,A_Car_pepoleActivity.class);
                    Bundle ACarbundle=new Bundle();
                    ACarbundle.putString("plate",read_waring_detailJson.getPlate());
                    ACarbundle.putString("pic",read_waring_detailJson.getPic());
                    ACarbundle.putString("plateType","4");
                    A_Car_pepoleintent.putExtras(ACarbundle);
                    startActivity(A_Car_pepoleintent);
                    break;
            }
        }
    };
    Read_waring_detailJson read_waring_detailJson;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhoubianyujing_result);
        return_=(LinearLayout)findViewById(R.id.img_return);
        return_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");//设置Toolbar标题
        textView=(TextView)findViewById(R.id.toolbar_title);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
    read_waring_detailJson=(Read_waring_detailJson)bundle.getSerializable("result");
        textView.setText(read_waring_detailJson.getPlate().toString());
        initView();
        initView(read_waring_detailJson);
        initAction();
    }
    private HashMap hashMap=new HashMap();
    private void initView(final Read_waring_detailJson read_waring_detailJson){
        alarm_carinfo_acap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        zbyjr_plate_number.setText(read_waring_detailJson.getPlate());
        zbyjr_model.setText(read_waring_detailJson.getCarModel());
        zbyjr_Randmodel.setText(read_waring_detailJson.getCarInfo());
        zbyjr_time.setText(read_waring_detailJson.getCaptureTime());
        zbyjr_place.setText(read_waring_detailJson.getLocationName());
        zbyjr_direction.setText(read_waring_detailJson.getDirectionName());
        zbyjr_reason.setText(read_waring_detailJson.getReason());
//        String stringBuilder=new String();
//        stringBuilder= read_waring_detailJson.getPic();
        Picasso.with(Perimeter_WarningResultActivity.this)
//                .load("http://"+ HttpConnectionUtils.ipport+stringBuilder.substring(24))
                .load(HttpConnectionUtils.newString(read_waring_detailJson.getPic()))
                .resize(DensityUtil.dip2px(Perimeter_WarningResultActivity.this,360),DensityUtil.dip2px(Perimeter_WarningResultActivity.this,208)).into(zbyjr_img);
        zbyjr_img.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ArrayList list=new ArrayList();
               String stringBuilder=new String();
               stringBuilder= read_waring_detailJson.getPic();
             //  list.add("http://"+HttpConnectionUtils.ipport+stringBuilder.substring(24));
               list.add(HttpConnectionUtils.newString(read_waring_detailJson.getPic()));
               //   list.add(read_waring_detailJson.getPic());
               PictureConfig config = new PictureConfig.Builder()
                       .setListData((ArrayList<String>) list)//图片数据List<String> list
                       .setPosition(0)//图片下标（从第position张图片开始浏览）
                       .setDownloadPath("pictureviewer")//图片下载文件夹地址
                       .needDownload(true)//是否支持图片下载
                       .setPlacrHolder(R.drawable.icon)//占位符图片（图片加载完成前显示的资源图片，来源drawable或者mipmap）
                       .build();
               ImagePagerActivity.isFromPic=true;
               ImagePagerActivity.startActivity(Perimeter_WarningResultActivity.this, config);
           }
       });
        zbyjr_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent =new Intent(Perimeter_WarningResultActivity.this,CarOfSearch_ResultActivity.class);
//                Bundle bundle=new Bundle();
//                startActivity(intent);
                hashMap.put("plate",zbyjr_plate_number.getText().toString().trim());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String token = FilesUtils.sharedGetFile(Perimeter_WarningResultActivity.this, "user", "token");
//                           mHandler.post(new Runnable() {
//                               @Override
//                               public void run() {
//                                   Toast.makeText(getActivity(),firstTime+" "+endTime+" "+plate_name,Toast.LENGTH_SHORT).show();
//                               }
//                           });
                        final String result = HttpConnectionUtils.sendGETRequest(HttpConnectionUtils.ipport+"/api/car_result", hashMap, token);
                        //     final String result = HttpConnectionUtils.sendGETRequest("10.73.194.252:8088/api/car_result", hashMap, token);
//                        mHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(Perimeter_WarningResultActivity.this,result,Toast.LENGTH_SHORT).show();
//                            }
//                        });
                        List<Car_resultJson> list = ResulutionJson.car_resultJson(result);
                        if (list == null) {
//                            mHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(Perimeter_WarningResultActivity.this,"null",Toast.LENGTH_SHORT).show();
//                                }
//                            });
                            //数据为空
                            Log.e("null", "null");
                            Message message = mHandler.obtainMessage();
                            message.what = 201;
//                            message.obj=(String)name;
                            mHandler.sendMessage(message);
                        } else {
//数据不为空
                            Message message = mHandler.obtainMessage();
                            message.what = 200;
                            message.obj = (List) list;
                            mHandler.sendMessage(message);
                        }

                    }
                }).start();
            }
        });
    }
    private void  initView(){
        alarm_carinfo_acap=(LinearLayout)findViewById(R.id.alarm_carinfo_acap);
        zbyjr_plate_number=(TextView)findViewById(R.id.zbyjr_plate_number);
        zbyjr_model=(TextView)findViewById(R.id.zbyjr_model);
        zbyjr_Randmodel=(TextView)findViewById(R.id.zbyjr_Randmodel);
        zbyjr_time=(TextView)findViewById(R.id.zbyjr_time);
        zbyjr_place=(TextView)findViewById(R.id.zbyjr_place);
        zbyjr_direction=(TextView)findViewById(R.id.zbyjr_direction);
        zbyjr_reason=(TextView)findViewById(R.id.zbyjr_reason);
        zbyjr_button=(Button)findViewById(R.id.zbyjr_button);
        zbyjr_img=(ImageView)findViewById(R.id.zbyjr_img);

    }
    private void initAction(){
//        zbyjr_plate_number.setText("");
//        zbyjr_model.setText("");
//        zbyjr_Randmodel.setText("");
//        zbyjr_time.setText("");
//        zbyjr_place.setText("");
//        zbyjr_direction.setText("");
//        zbyjr_reason.setText("");
    }
}
