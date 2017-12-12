package searchproject.php.yisa.chengyansy.FromPicFragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



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

import searchproject.php.yisa.chengyansy.Adapter_car.DetailResult_carAdapter;
import searchproject.php.yisa.chengyansy.CarOfSearch_DetailActivity;
import searchproject.php.yisa.chengyansy.CarOfSearch_ResultActivity;
import searchproject.php.yisa.chengyansy.FilesUtils;
import searchproject.php.yisa.chengyansy.Model_All.Car_resultJson;
import searchproject.php.yisa.chengyansy.PictureDialog.ImagePagerActivity;
import searchproject.php.yisa.chengyansy.PictureDialog.PictureConfig;
import searchproject.php.yisa.chengyansy.R;
import searchproject.php.yisa.chengyansy.utils.HttpConnectionUtils;
import searchproject.php.yisa.chengyansy.utils.ResulutionJson;
import searchproject.php.yisa.chengyansy.view.RefreshRecyclerView;

/**
 * Created by Administrator on 2017/7/31 0031.
 */

public class PicDefault_fragment extends Fragment {
    private static List<Car_resultJson> dataList ;
    private RefreshRecyclerView rv;
    DetailResult_carAdapter adapter;
    View view;
    static HashMap map =new HashMap();
    private int i_page_index=1;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 200:
                    List<Car_resultJson> list= (List) msg.obj;
                    //   Toast.makeText(getActivity(),list.get(0).getPlate(),Toast.LENGTH_SHORT).show();
                    dataList.addAll(list);
                    rv.notifyData();//刷新数据

                    break;
                case 201:
                    break;
                case 202:
                    break;
                case  300:
                    Bundle Bundle=   msg.getData();
                    Log.e("picccc  ",Bundle.getString("pic")+"-");
//                    List<Car_resultJson> list1= (List<Car_resultJson>) msg.obj;
                    Intent intent=new Intent(getActivity(), CarOfSearch_DetailActivity.class);
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
            }
        }
    };
    private HashMap hashMap=new HashMap();
    private HashMap hashMapdf=new HashMap();

    private  String imgurl="";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= View.inflate(getActivity(), R.layout.fragment_car_default,null);


        Bundle bundle   =  this.getArguments();
        String first=     bundle.getString("firstTime");
        String endTime=     bundle.getString("endTime");
        String plate_name=     bundle.getString("plate_name");
        String img=bundle.getString("img");
        imgurl=img;
        hashMap = (HashMap) bundle.getSerializable("hashMap");

        Iterator<Map.Entry<String, String>> it = hashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            hashMapdf.put(entry.getKey()+"",entry.getValue()+"");
            //    System.out.println("KKkey= " + entry.getKey() + " and value= " + entry.getValue());
        }
        Iterator<Map.Entry<String, String>> itt = hashMapdf.entrySet().iterator();
        while (itt.hasNext()) {
            Map.Entry<String, String> entry = itt.next();
            hashMapdf.put(entry.getKey()+"",entry.getValue()+"");
            //  System.out.println("KKkey= " + entry.getKey() + " and value= " + entry.getValue());
        }
        map.put("plate",plate_name);
        map.put("beginDate",endTime);
        map.put("endDate",plate_name);
        // dataList = (List) bundle.getSerializable("list");
        //   initView();
        initData();
        initListener();
        return view;
    }
    public  void initListener(){
        if (CarOfSearch_ResultActivity.getList().size()>0) {
            if (Integer.parseInt(CarOfSearch_ResultActivity.getList().get(0).totalRecords) >10) {
                //  Toast.makeText(God_Search_ResultActivity.this,(String) Car_info_map.get("total_fonund")+" ture",Toast.LENGTH_SHORT).show();
                rv.setLoadMoreEnable(true);//允许加载更多
                adapter.changeMoreStatus(0);
            } else {
                //   Toast.makeText(God_Search_ResultActivity.this,(String) Car_info_map.get("total_fonund")+" false",Toast.LENGTH_SHORT).show();
                adapter.changeMoreStatus(1);
                rv.setLoadMoreEnable(false);//不允许加载更多
            }
        }
        rv.setOnLoadMoreListener(new RefreshRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMoreListener() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String token = FilesUtils.sharedGetFile(getActivity(), "user", "token");
                        i_page_index++;
                        hashMapdf.put("pn",i_page_index+"");
                        Iterator iter = hashMapdf.entrySet().iterator();
                        while (iter.hasNext()) {
                            Map.Entry entry = (Map.Entry) iter.next();
                            Object key = entry.getKey();
                            Object val = entry.getValue();
                            Log.e("Defaultmodel"+(String) key, (String) val);
                        }
                        //      final String result = HttpConnectionUtils.sendGETRequest(HttpConnectionUtils.ipport+"/api/car_result", hashMapdf, token);
                     //   final String result = initJson();
                        final String     result = sendGETRequestPic(HttpConnectionUtils.ipport+"/api/ytsc", imgurl+"&pn="+i_page_index, token);

//                        mHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(getActivity(),"--"+result,Toast.LENGTH_SHORT).show();
//                            }
//                        });



                        if(result.length()<60){
                            //   Log.e("1111111111111","22222222222222222");
//                            Message message = handler.obtainMessage();
//                            message.what = NULL_STATE;
//                            handler.sendMessage(message);
//                            mHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    //   rv.notifyAll();
//                                    Toast.makeText(getActivity(),"没有更多数据了",Toast.LENGTH_SHORT).show();
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
                            Message message = mHandler.obtainMessage();
                            message.obj=list;
                            message.what=200;
                            mHandler.sendMessage(message);
                            //      getResultData=auth;
                        }
                    }
                }).start();
            }
        });
    }
    public static void setList(List<Car_resultJson> list,String firstTime,String endTime,String plateName){
        dataList=list;
        map.put("plate",firstTime);
        map.put("beginDate",endTime);
        map.put("endDate",firstTime);
    }
    //    private void initView(){
//        for(int i=0;i<24;i++){
//            dataList.add(i);
//        }
//
//    }
    private int  positionClick=0;
    private void initData(){
        rv= view.findViewById(R.id.default_recycler);
        rv.setFooterResource(R.layout.item_footer);//设置脚布局
        dataList=CarOfSearch_DetailActivity.getList();
        adapter=new DetailResult_carAdapter(dataList,getActivity());
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);
        adapter.setOnItemClickLitener(new DetailResult_carAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, final int position) {
                positionClick=   position;
                ArrayList list=new ArrayList();
                String stringBuilder=new String();
                stringBuilder= dataList.get(position).getPic();
                list.add("http://"+HttpConnectionUtils.ipport+stringBuilder.substring(24));
                //  list.add(dataList.get(position).getPic());
                PictureConfig config = new PictureConfig.Builder()
                        .setListData((ArrayList<String>) list)//图片数据List<String> list
                        .setPosition(0)//图片下标（从第position张图片开始浏览）
                        .setDownloadPath("pictureviewer")//图片下载文件夹地址
                        .needDownload(true)//是否支持图片下载
                        .setPlacrHolder(R.drawable.icon)//占位符图片（图片加载完成前显示的资源图片，来源drawable或者mipmap）
                        .build();
                ImagePagerActivity.isFromPic=true;
//                ImagePagerActivity.setOnItemClickLitener(new ImagePagerActivity.OnItemClickLitener() {
//                    @Override
//                    public void onItemClick() {
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                //记得传img路径
//                                final  String result=initJson();
//                                if (result.length()<60) {
//                                    mHandler.post(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            Toast.makeText(getActivity(),"找不到过车记录",Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                                    //数据为空
//                                    Log.e("null", "null");
//                                    Message message = mHandler.obtainMessage();
//                                    message.what = 301;
//                  //                message.obj=(String)name;
//                                    mHandler.sendMessage(message);
//                                } else {
//                                    Log.e("pic",PictureConfig.list.get(0));
//                                    List<Car_resultJson> list = ResulutionJson.car_resultJson(result);
//                                    Bundle bundle=new Bundle();
//                                    bundle.putString("pic","https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=1770449409,1544649105&fm=173&s=02101ECD4C1238C412058C2003002053&w=640&h=438&img.JPEG");
//                                    bundle.putSerializable("list", (Serializable) list);
//                                    Message message = mHandler.obtainMessage();
//                                    message.setData(bundle);
//                                    message.what = 300;
//
//
//                                    mHandler.sendMessage(message);
//                                }
//                            }
//                        }).start();
//
//                    }
//
//                    @Override
//                    public void onItemLongClick(View view, int position) {
//
//                    }
//                });I
                ImagePagerActivity.startActivity(getActivity(), config);
                //   ImagePagerActivity imagePagerActivity=new ImagePagerActivity();
//                imagePagerActivity.setOnItemClickLitener(new ImagePagerActivity.OnItemClickLitener() {
//                    @Override
//                    public void onItemClick() {
//                        Intent intent=new Intent(getActivity(), CarOfSearch_DetailActivity.class);
//                        startActivity(intent);
//                    }
//
//                    @Override
//                    public void onItemLongClick(View view, int position) {
//
//                    }
//                });

            }
            @Override
            public void onItemLongClick(View view, int position) {
            }
        });

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

}
