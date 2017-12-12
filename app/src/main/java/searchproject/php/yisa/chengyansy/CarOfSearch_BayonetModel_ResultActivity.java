package searchproject.php.yisa.chengyansy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import searchproject.php.yisa.chengyansy.Adapter_car.SearchCar_KakouModelAdapter;
import searchproject.php.yisa.chengyansy.Dialog.MyDialog;
import searchproject.php.yisa.chengyansy.Model_All.Car_resultJson;
import searchproject.php.yisa.chengyansy.PictureDialog.ImagePagerActivity;
import searchproject.php.yisa.chengyansy.PictureDialog.PictureConfig;
import searchproject.php.yisa.chengyansy.utils.DensityUtil;
import searchproject.php.yisa.chengyansy.utils.HttpConnectionUtils;
import searchproject.php.yisa.chengyansy.utils.ResulutionJson;
import searchproject.php.yisa.chengyansy.view.RefreshRecyclerView;

public class CarOfSearch_BayonetModel_ResultActivity extends AppCompatActivity {
    LinearLayout searchCar_date;
    private List<Car_resultJson> dataList = new ArrayList();
    private RefreshRecyclerView rv;
    SearchCar_KakouModelAdapter adapter;
    View view;
    Toolbar toolbar;
    TextView textView;
    HashMap hashMap;
    TextView kk_model;
    TextView searchCar_Result_model;
    TextView searchCar_Result_plate_numberr;
    TextView searchCar_firstTime;
    TextView searchCar_endTime;
    private String position;
    private TextView searchCar_result;
    private LinearLayout return_;
    private int  positionClick=0;
    private HashMap hashMapdf=new HashMap();
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 300:
                    List<Car_resultJson> list= (List) msg.obj;
                    //   Toast.makeText(getActivity(),list.get(0).getPlate(),Toast.LENGTH_SHORT).show();
                    dataList.addAll(list);
                    rv.notifyData();//刷新数据
                    break;
                case 400:
                    loadingDialog.dismiss();
                    Bundle Bundle=   msg.getData();
                    Log.e("picccc  ",Bundle.getString("pic"));
//                    List<Car_resultJson> list1= (List<Car_resultJson>) msg.obj;
                    Intent intent=new Intent(CarOfSearch_BayonetModel_ResultActivity.this, CarOfSearch_DetailActivity.class);
                    Bundle bundle =new Bundle();

                    List<Car_resultJson> list1= (List<Car_resultJson>) Bundle.getSerializable("list");
                    bundle.putSerializable("hashMapImg",hashMap);
                    bundle.putString("isShowImg","true");
                    bundle.putString("pic",Bundle.getString("pic"));
//                    bundle.putString("plate_number", (String) hashMap.get("plate"));
//                    bundle.putString("firstTime", (String) hashMap.get("beginDate"));
//                    bundle.putString("endTime", (String) hashMap.get("endDate"));
                    bundle.putString("plate_number",dataList.get(positionClick).getPlate());
                    bundle.putString("model",dataList.get(positionClick).getModel());
                    bundle.putString("LocationName",dataList.get(positionClick).getLocationName());
                    bundle.putString("CaptureTime",dataList.get(positionClick).getCaptureTime());
                    bundle.putSerializable("list", (Serializable) list1);

                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case 301:
                    Bundle Bundle1=   msg.getData();
                    Intent intentNull=new Intent(CarOfSearch_BayonetModel_ResultActivity.this,NullFromPicResultActivity.class);
                    Bundle bundleNull=new Bundle();
                    //      bundleNull.putString("isShowImg","false");
                    bundleNull.putString("pic",Bundle1.getString("pic"));
//                    bundle.putString("plate_number", (String) hashMap.get("plate"));
//                    bundle.putString("firstTime", (String) hashMap.get("beginDate"));
//                    bundle.putString("endTime", (String) hashMap.get("endDate"));
                    bundleNull.putString("plate_number",dataList.get(positionClick).getPlate());
                    bundleNull.putString("model",dataList.get(positionClick).getModel());
                    bundleNull.putString("LocationName",dataList.get(positionClick).getLocationName());
                    bundleNull.putString("CaptureTime",dataList.get(positionClick).getCaptureTime());
//                    Intent intent1=new Intent(getActivity(),CarOfSearch_ResultActivity.class);
//                    Bundle bundle1=new Bundle();
//                    List list1=null;
//                    bundle1.putString("firstTime",searchcar_firstTimeEdith_01.getText()+" "+searchcar_firstTimeEdith_02.getText());
//                    bundle1.putString("endTime",searchcar_endTimeEdith_01.getText()+" "+searchcar_endTimeEdith_02.getText());
//                    bundle1.putString("plate_number",editText.getText().toString());
//                    bundle1.putSerializable("list", (Serializable) list1);
                    intentNull.putExtras(bundleNull);
                    startActivity(intentNull);
                    break;

            }
        }
    };
    String plate;
    private String first;
    private String end;
    private MyDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_car__kakou_model__result);
        kk_model=(TextView)findViewById(R.id.kk_model);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");//设置Toolbar标题
        textView=(TextView)findViewById(R.id.toolbar_title);

        return_=(LinearLayout)findViewById(R.id.img_return);
        return_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent= getIntent();
        Bundle bundle=  intent.getExtras();
        dataList= (List<Car_resultJson>) bundle.getSerializable("list");

        hashMap= (HashMap) bundle.getSerializable("hashMap");
        plate=(String)bundle.getString("plate");
//        Iterator<Map.Entry<String, String>> it = hashMap.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry<String, String> entry = it.next();
//            hashMapdf.put(entry.getKey()+"",entry.getValue()+"");
//            //    System.out.println("KKkey= " + entry.getKey() + " and value= " + entry.getValue());
//        }
        Iterator<Map.Entry<String, String>> it = hashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            hashMapdf.put(entry.getKey()+"",entry.getValue()+"");
            //    System.out.println("KKkey= " + entry.getKey() + " and value= " + entry.getValue());
        }
        Iterator iter = hashMapdf.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            Log.e("Defaul_tmodel "+(String) key, (String) val);
        }
        searchCar_date=(LinearLayout)findViewById(R.id.searchCar_date);
        position=(String)bundle.getString("position");

        if (bundle.getString("isKK").equals("true")){
            kk_model.setText("卡口 ：");
            textView.setText("按卡口分组");
        }else {
            kk_model.setText("车型 :");
            textView.setText("按车型分组");
        }
        initView();
        initData();
    }
    private void initView(){
//        for(int i=0;i<24;i++){
//            dataList.add(i);
//        }



        searchCar_Result_model=(TextView)findViewById(R.id.searchCar_Result_model);
        searchCar_Result_plate_numberr=(TextView)findViewById(R.id.searchCar_Result_plate_numberr);
        searchCar_firstTime=(TextView)findViewById(R.id.searchCar_firstTime);
        searchCar_endTime=(TextView)findViewById(R.id.searchCar_endTime);
        searchCar_result=(TextView)findViewById(R.id.searchCar_result);
        rv=(RefreshRecyclerView)findViewById(R.id.searchcar_kakou_model_RecyclerView);

        searchCar_Result_model.setText(position);
        searchCar_result.setText(dataList.get(0).totalRecords);
        searchCar_Result_plate_numberr.setText(plate);
         first=hashMap.get("beginDate")+"";
         end=hashMap.get("endDate")+"";
        if(first.length()<8){
            searchCar_date.setVisibility(View.INVISIBLE);
        }
        searchCar_firstTime.setText(first.substring(2));
        searchCar_endTime.setText(end.substring(2));

    } public int i_page_index=1;
    private void initData(){
        adapter=new SearchCar_KakouModelAdapter(dataList,CarOfSearch_BayonetModel_ResultActivity.this);
        rv.setLayoutManager(new LinearLayoutManager(CarOfSearch_BayonetModel_ResultActivity.this));
        if(dataList.size()<10){
            rv.setLoadMoreEnable(false);//不允许加载更多
           adapter. changeMoreStatus(1);
        }else {
            rv.setLoadMoreEnable(true);//允许加载更多
        }
        rv.setAdapter(adapter);
        adapter.setOnItemClickLitener(new SearchCar_KakouModelAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, final int position) {
                positionClick=   position;
                ArrayList list=new ArrayList();
                String stringBuilder=new String();
                stringBuilder= dataList.get(position).getPic();
              //  list.add("http://"+HttpConnectionUtils.ipport+stringBuilder.substring(24));
                list.add(HttpConnectionUtils.newString(dataList.get(position).getPic()));
             //   list.add(dataList.get(position).getPic());
                //  list.add(dataList.get(position).getPic());
                PictureConfig config = new PictureConfig.Builder()
                        .setListData((ArrayList<String>) list)//图片数据List<String> list
                        .setPosition(0)//图片下标（从第position张图片开始浏览）
                        .setDownloadPath("pictureviewer")//图片下载文件夹地址
                        .needDownload(true)//是否支持图片下载
                        .setPlacrHolder(R.drawable.icon)//占位符图片（图片加载完成前显示的资源图片，来源drawable或者mipmap）
                        .build();
                ImagePagerActivity.isFromPic=false;
                ImagePagerActivity.setOnItemClickLitener(new ImagePagerActivity.OnItemClickLitener() {
                    @Override
                    public void onItemClick() {
showDiaglog(true);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                final HashMap hashMapClick=new HashMap();
                                final String token = FilesUtils.sharedGetFile(CarOfSearch_BayonetModel_ResultActivity.this, "user", "token");
                                try {
                                    Log.e("pic", PictureConfig.list.get(1));
                                    //hashMapClick.put("image_url",PictureConfig.list.get(0));
                                }catch (Exception e){

                                }
                                //    bitmaptoString(getBitmap(PictureConfig.list.get(0)),20);

                                //记得传img路径
                                String result;

                                result = sendGETRequestPic(HttpConnectionUtils.ipport+"/api/ytsc",dataList.get(position).getPic(), token);

                                final String finalResult1 = result;
//                                handler.post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Toast.makeText(CarOfSearch_BayonetModel_ResultActivity.this, finalResult1,Toast.LENGTH_SHORT).show();
//                                    }
//                                });
                                //  final  String result=initJson();
                                if (result.length()<60) {
                                    final String finalResult = result;
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            loadingDialog.dismiss();
                                            Toast.makeText(CarOfSearch_BayonetModel_ResultActivity.this, "请上传车头图片！",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    Bundle bundle=new Bundle();
                                    bundle.putString("pic",PictureConfig.list.get(0));

                                    //数据为空
                                    Log.e("null", "null");
                                    Message message = handler.obtainMessage();
                                    message.setData(bundle);
                                    message.what = 301;
//                            message.obj=(String)name;
                                    handler.sendMessage(message);
                                } else {
                                    Log.e("pic",PictureConfig.list.get(0));
                                    List<Car_resultJson> list = ResulutionJson.car_resultJson(result);
                                    Bundle bundle=new Bundle();
                                    bundle.putString("pic",PictureConfig.list.get(0));
                                    bundle.putSerializable("list", (Serializable) list);
                                    Message message = handler.obtainMessage();
                                    message.setData(bundle);
                                    message.what = 400;


                                    handler.sendMessage(message);
                                }
                            }
                        }).start();

                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });
                ImagePagerActivity.startActivity(CarOfSearch_BayonetModel_ResultActivity.this, config);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }

            @Override
            public void onItemACarAppClick(View itemView, int position) {
                String plate=dataList.get(position).getPlate();
                String pic=dataList.get(position).getPic();
                String type=dataList.get(position).getType();
                //调用Http
                Intent intent=new Intent(CarOfSearch_BayonetModel_ResultActivity.this, A_Car_pepoleActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("plate",plate);
                bundle.putString("pic",pic);
                bundle.putString("plateType",type);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        rv.setOnLoadMoreListener(new RefreshRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMoreListener() {
                new Thread(new Runnable() {


                    @Override
                    public void run() {
                        final String token = FilesUtils.sharedGetFile(CarOfSearch_BayonetModel_ResultActivity.this, "user", "token");
                        i_page_index++;
                        hashMapdf.put("pn",i_page_index+"");
                        Iterator iter = hashMapdf.entrySet().iterator();
                        while (iter.hasNext()) {
                            Map.Entry entry = (Map.Entry) iter.next();
                            Object key = entry.getKey();
                            Object val = entry.getValue();
                            Log.e("result_carofsearch"+(String) key, (String) val);
                        }
                            final String result = HttpConnectionUtils.sendGETRequest(HttpConnectionUtils.ipport+"/api/car_result", hashMapdf, token);
                       // final String result = initJson();

//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(CarOfSearch_BayonetModel_ResultActivity.this,"--"+result,Toast.LENGTH_SHORT).show();
//                            }
//                        });



                        if(result.length()<60){
                            //   Log.e("1111111111111","22222222222222222");
//                            Message message = handler.obtainMessage();
//                            message.what = NULL_STATE;
//                            handler.sendMessage(message);
//                            handler.post(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    //   rv.notifyAll();
//                                    Toast.makeText(CarOfSearch_BayonetModel_ResultActivity.this,"没有更多数据了",Toast.LENGTH_SHORT).show();
//                                }
//                            });
                            return;
                        }else {
//                           handler.post(new Runnable() {
//                               @Override
//                               public void run() {
//                                   Toast.makeText(getBaseContext(),"加载成功",Toast.LENGTH_SHORT).show();
//                               }
//                           });
                            List<Car_resultJson> list = ResulutionJson.car_resultJson(result);
                            Message message = handler.obtainMessage();
                            message.obj=list;
                            message.what=300;
                            handler.sendMessage(message);
                            //      getResultData=auth;
                        }
                    }
                }).start();
            }
        });
    }
    public String initJson(){
        String result="{\n" +
                "  \"totalRecords\": 41,\n" +
                "  \"searchList\": [\n" +
                "    {\n" +
                "      \"plate\": \"鲁F8759D\",\n" +
                "      \"model\": \"五菱-荣光面包车-2015,2014\",\n" +
                "      \"locationName\": \"(海阳市)海阳市海天路与海阳路抓拍点\",\n" +
                "      \"captureTime\": \"2017-08-14 14:51:42\",\n" +
                "      \"pic\": \"http://10.50.194.8:9003/PhotoRS/showfile?fileType=ExtVehicleAlarmPhoto&trackId=99851686814&xwPicUrl=b90d70fb00798593a8195ef921be0f8f\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"plate\": \"鲁F87590\",\n" +
                "      \"model\": \"五菱-荣光面包车-2015,2014\",\n" +
                "      \"locationName\": \"(海阳市)海阳市海天路与海阳路抓拍点\",\n" +
                "      \"captureTime\": \"2017-08-14 14:34:41\",\n" +
                "      \"pic\": \"http://10.50.194.8:9003/PhotoRS/showfile?fileType=ExtVehicleAlarmPhoto&trackId=99851674954&xwPicUrl=b90d706b00798593f14be2f78238e397\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"plate\": \"鲁F87590\",\n" +
                "      \"model\": \"五菱-荣光面包车-2015,2014\",\n" +
                "      \"locationName\": \"(海阳市)海阳市海天路与海阳路抓拍点\",\n" +
                "      \"captureTime\": \"2017-08-14 12:37:34\",\n" +
                "      \"pic\": \"http://10.50.194.8:9003/PhotoRS/showfile?fileType=ExtVehicleAlarmPhoto&trackId=99851606186&xwPicUrl=b90d702b00798593ace342e9bc2d9c1f\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"plate\": \"鲁F87590\",\n" +
                "      \"model\": \"五菱-荣光面包车-2015,2014\",\n" +
                "      \"locationName\": \"(海阳市)海阳市海阳路-公园街抓拍点\",\n" +
                "      \"captureTime\": \"2017-08-14 12:35:02\",\n" +
                "      \"pic\": \"http://10.50.194.8:9003/PhotoRS/showfile?fileType=ExtVehicleAlarmPhoto&trackId=99851605046&xwPicUrl=b90d709b00798593b8914bea9ba7e4da\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"plate\": \"鲁F8759D\",\n" +
                "      \"model\": \"五菱-五菱宏光V-2015\",\n" +
                "      \"locationName\": \"(海阳市)海阳市海阳路与龙山街路口卡口\",\n" +
                "      \"captureTime\": \"2017-08-14 12:34:41\",\n" +
                "      \"pic\": \"http://10.50.194.8:9003/PhotoRS/showfile?fileType=ExtVehicleAlarmPhoto&trackId=99851604893&xwPicUrl=b90d702b007985933f4144e95fc532b2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"plate\": \"鲁F8759D\",\n" +
                "      \"model\": \"五菱-五菱宏光V-2015\",\n" +
                "      \"locationName\": \"(海阳市)海阳市海阳路与龙山街路口卡口\",\n" +
                "      \"captureTime\": \"2017-08-14 12:34:12\",\n" +
                "      \"pic\": \"http://10.50.194.8:9003/PhotoRS/showfile?fileType=ExtVehicleAlarmPhoto&trackId=99851604675&xwPicUrl=b90d703b00798593c5b86be96e996a71\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"plate\": \"鲁F8759D\",\n" +
                "      \"model\": \"五菱-五菱宏光V-2015\",\n" +
                "      \"locationName\": \"(海阳市)海阳市虎山街-海阳路抓拍点\",\n" +
                "      \"captureTime\": \"2017-08-14 12:26:57\",\n" +
                "      \"pic\": \"http://10.50.194.8:9003/PhotoRS/showfile?fileType=ExtVehicleAlarmPhoto&trackId=99851601313&xwPicUrl=b90d704b007985931806dde8fe551110\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"plate\": \"鲁YT8770\",\n" +
                "      \"model\": \"五菱-五菱宏光V-2015\",\n" +
                "      \"locationName\": \"(海阳市)海阳市虎山街-海阳路抓拍点\",\n" +
                "      \"captureTime\": \"2017-08-14 12:26:57\",\n" +
                "      \"pic\": \"http://10.50.194.8:9003/PhotoRS/showfile?fileType=ExtVehicleAlarmPhoto&trackId=99851601333&xwPicUrl=b90d70eb007985930cbc19e886bc90cd\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"plate\": \"鲁F8759D\",\n" +
                "      \"model\": \"五菱-五菱宏光V-2015\",\n" +
                "      \"locationName\": \"(海阳市)海阳市海天路与海阳路抓拍点\",\n" +
                "      \"captureTime\": \"2017-08-14 12:05:48\",\n" +
                "      \"pic\": \"http://10.50.194.8:9003/PhotoRS/showfile?fileType=ExtVehicleAlarmPhoto&trackId=99851578001&xwPicUrl=b90d70eb0079859341a1d0e40230e54b\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"plate\": \"鲁F8759D\",\n" +
                "      \"model\": \"五菱-荣光面包车-2015,2014\",\n" +
                "      \"locationName\": \"(海阳市)海阳市北京路-海翔路抓拍点\",\n" +
                "      \"captureTime\": \"2017-08-14 11:35:58\",\n" +
                "      \"pic\": \"http://10.50.194.8:9003/PhotoRS/showfile?fileType=ExtVehicleAlarmPhoto&trackId=99851569021&xwPicUrl=b90d70eb007985930b9829e2df146cf9\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        return result;
    }
    public String  sendGETRequestPic(String path, String pic,String token) {
        String result =null;


        byte[] data = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        OutputStream os = null;
        HttpURLConnection conn;
        try {
            //发送地http://192.168.100.91:8080/videoService/login?username=abc&password=123
            // StringBuilder是用来组拼请求地址和参数
//            StringBuilder sb = new StringBuilder();
//            sb.append(path).append("?");
//            if (params != null && params.size() != 0) {
//                for (Map.Entry<String, String> entry : params.entrySet()) {
////如果请求参数中有中文，需要进行URLEncoder编码
//                    sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "utf-8"));
//                    sb.append("&");
//                }
//                sb.deleteCharAt(sb.length() - 1);
//            }
            Log.e("url", "http://" +path+"?"+"image_url="+ pic);
            URL url = new URL("http://" +path+"?"+"image_url="+ pic);
            conn = (HttpURLConnection) url.openConnection();
            if(token!=null) {
                //     conn.setRequestProperty("Authorization","token " +token);
                conn.addRequestProperty("Authorization","token " +token);
            }
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.connect();
            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024 * 8];
                int size = 0;
                while ((size = is.read(buffer)) >= 1) {
                    baos.write(buffer, 0, size);
                }
                data = baos.toByteArray();
                result = new String(data);

                return result;



            }
            else {
                return Integer.toString(conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();

        }


        return "2";
    }
    private void showDiaglog(Boolean isShow){
        loadingDialog=new MyDialog(CarOfSearch_BayonetModel_ResultActivity.this,"正在识别中···");
        loadingDialog.getWindow().setLayout(DensityUtil.dip2px(CarOfSearch_BayonetModel_ResultActivity.this,278), DensityUtil.dip2px(CarOfSearch_BayonetModel_ResultActivity.this,122));
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.show();

    }
}
