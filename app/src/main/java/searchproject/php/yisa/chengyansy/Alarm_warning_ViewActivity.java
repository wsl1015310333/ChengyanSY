package searchproject.php.yisa.chengyansy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import searchproject.php.yisa.chengyansy.Model_All.Car_resultJson;
import searchproject.php.yisa.chengyansy.Model_All.Read_alarm_detail;
import searchproject.php.yisa.chengyansy.PictureDialog.ImagePagerActivity;
import searchproject.php.yisa.chengyansy.PictureDialog.PictureConfig;
import searchproject.php.yisa.chengyansy.utils.DensityUtil;
import searchproject.php.yisa.chengyansy.utils.HttpConnectionUtils;
import searchproject.php.yisa.chengyansy.utils.ResulutionJson;

public class Alarm_warning_ViewActivity extends AppCompatActivity {
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 200:
                    List<Car_resultJson> list=(List)msg.obj;
                    //  Toast.makeText(getActivity(),"img"+list.get(1).getLocationName(),Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Alarm_warning_ViewActivity.this,CarOfSearch_ResultActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("firstTime","");
                    bundle.putString("endTime","");
                    bundle.putString("plate_number",bkyj_plate.getText().toString().trim());
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
            }
        }
    };
    ImageButton bkyj_img;
    private TextView bkyj_plate;
    private TextView bkyj_model;
    private TextView bkyj_color;
    private TextView bkyj_findtime;
    private TextView bkyj_findplace;
    private TextView bkyj_direction;
    private TextView bkyj_speed;
    private TextView bkyj_alarm_people;
    private TextView bkyj_alarm_tel;
    private TextView bkyj_alarm_text;
    private TextView bkyj_alarm_description;
    private TextView bkyj_alarm_carinfo;
    private TextView bkyj_alarm_reason;
    private HashMap hashMap=new HashMap();
    private LinearLayout return_;
    Toolbar toolbar;
    TextView textView;
    Button bkyj_alarm_butto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bukongyujing_result);
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
        Read_alarm_detail read_alarm_detail= (Read_alarm_detail) bundle.getSerializable("result");
        textView.setText(read_alarm_detail.getPlate());
        initView();
        initData(read_alarm_detail);
    }
    public void initView(){
        bkyj_img=(ImageButton)findViewById(R.id.bkyj_img);
        bkyj_alarm_butto=(Button)findViewById(R.id.bkyj_alarm_butto);
        bkyj_plate=(TextView)findViewById(R.id.bkyj_plate);
        bkyj_model=(TextView)findViewById(R.id.bkyj_model);
        bkyj_color=(TextView)findViewById(R.id.bkyj_color);
        bkyj_findtime=(TextView)findViewById(R.id.bkyj_findtime);
        bkyj_findplace=(TextView)findViewById(R.id.bkyj_findplace);
        bkyj_direction=(TextView)findViewById(R.id.bkyj_direction);
        bkyj_speed=(TextView)findViewById(R.id.bkyj_speed);

        bkyj_alarm_people=(TextView)findViewById(R.id.bkyj_alarm_people);
        bkyj_alarm_tel=(TextView)findViewById(R.id.bkyj_alarm_tel);
        bkyj_alarm_text=(TextView)findViewById(R.id.bkyj_alarm_text);
        bkyj_alarm_description=(TextView)findViewById(R.id.bkyj_alarm_description);
        bkyj_alarm_carinfo=(TextView)findViewById(R.id.bkyj_alarm_carinfo);
        bkyj_alarm_reason=(TextView)findViewById(R.id.bkyj_alarm_reason);
    }
    public void initData(final Read_alarm_detail read_alarm_detail){
        bkyj_plate.setText(read_alarm_detail.getPlate());
        bkyj_model.setText(read_alarm_detail.getCarModel());
        bkyj_color.setText(read_alarm_detail.getColor());
        bkyj_findtime.setText(read_alarm_detail.getCaptureTime());
        bkyj_findplace.setText(read_alarm_detail.getLocationName());
        bkyj_direction.setText(read_alarm_detail.getDirectionName());
        bkyj_speed.setText(read_alarm_detail.getSpeed());
        bkyj_alarm_people.setText(read_alarm_detail.getAppplyUser());
        bkyj_alarm_tel.setText(read_alarm_detail.getAppplyPhone());
        bkyj_alarm_text.setText(read_alarm_detail.getId());
        bkyj_alarm_description.setText(read_alarm_detail.getDeployType());
        bkyj_alarm_carinfo.setText(read_alarm_detail.getCarInfo());
        bkyj_alarm_reason.setText(read_alarm_detail.getReason());
//        String stringBuilder=new String();
//        stringBuilder= read_alarm_detail.getPic();

        Picasso.with(Alarm_warning_ViewActivity.this)
//                .load("http://"+ HttpConnectionUtils.ipport+stringBuilder.substring(24))
                .load(HttpConnectionUtils.newString(read_alarm_detail.getPic()))
                .resize(DensityUtil.dip2px(Alarm_warning_ViewActivity.this,360),DensityUtil.dip2px(Alarm_warning_ViewActivity.this,208)).into(bkyj_img);
bkyj_img.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        ArrayList list=new ArrayList();
        String stringBuilder=new String();
        stringBuilder= read_alarm_detail.getPic();
      //  list.add("http://"+HttpConnectionUtils.ipport+stringBuilder.substring(24));
        list.add(HttpConnectionUtils.newString(read_alarm_detail.getPic()));

        //list.add(read_alarm_detail.getPic());
        PictureConfig config = new PictureConfig.Builder()
                .setListData((ArrayList<String>) list)//图片数据List<String> list
                .setPosition(0)//图片下标（从第position张图片开始浏览）
                .setDownloadPath("pictureviewer")//图片下载文件夹地址
                .needDownload(true)//是否支持图片下载
                .setPlacrHolder(R.drawable.icon)//占位符图片（图片加载完成前显示的资源图片，来源drawable或者mipmap）
                .build();
        ImagePagerActivity.isFromPic=true;
        ImagePagerActivity.startActivity(Alarm_warning_ViewActivity.this, config);
    }
});
        bkyj_alarm_butto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hashMap.put("plate", bkyj_plate.getText().toString().trim());
//                hashMap.put("beginDate", "");
//                hashMap.put("endDate", "");
//                hashMap.put("pn", "1");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String token = FilesUtils.sharedGetFile(Alarm_warning_ViewActivity.this, "user", "token");
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
//                                Toast.makeText(Alarm_warning_ViewActivity.this,result,Toast.LENGTH_SHORT).show();
//                            }
//                        });
                        List<Car_resultJson> list = ResulutionJson.car_resultJson(result);
                        if (list == null) {
//                            mHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(Alarm_warning_ViewActivity.this,"null",Toast.LENGTH_SHORT).show();
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
}
