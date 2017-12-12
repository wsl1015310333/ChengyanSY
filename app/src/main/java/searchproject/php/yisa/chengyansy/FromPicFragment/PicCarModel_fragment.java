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
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import searchproject.php.yisa.chengyansy.Adapter_car.Model_carAdapter;
import searchproject.php.yisa.chengyansy.CarOfSearch_BayonetModel_ResultActivity;
import searchproject.php.yisa.chengyansy.FilesUtils;
import searchproject.php.yisa.chengyansy.Model_All.Car_result_model;
import searchproject.php.yisa.chengyansy.R;
import searchproject.php.yisa.chengyansy.utils.ResulutionJson;
import searchproject.php.yisa.chengyansy.view.RefreshRecyclerView;

/**
 * Created by Administrator on 2017/7/31 0031.
 */

public class PicCarModel_fragment extends Fragment {
    private List<Car_result_model> dataList=new ArrayList();
    private RefreshRecyclerView rv;
    View view;
    Model_carAdapter adapter;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case  200:
                    final List<Car_result_model> list=(List)msg.obj;
                    dataList=list;
                    adapter=new Model_carAdapter(list,getActivity());

                    rv.setAdapter(adapter);
                    adapter.setOnItemClickLitener(new Model_carAdapter.OnItemClickLitener() {
                        @Override
                        public void onItemClick(View view, final int position) {
                            Intent intent=new Intent(getActivity(), CarOfSearch_BayonetModel_ResultActivity.class);
                            Bundle bundle=new Bundle();
                            new  Thread(new Runnable() {
                                @Override
                                public void run() {
                                    final String token = FilesUtils.sharedGetFile(getActivity(), "user", "token");
                                    hashMapcm.put("year_id",dataList.get(position).getYear_id());

                                    Iterator iter = hashMapcm.entrySet().iterator();
                                    while (iter.hasNext()) {
                                        Map.Entry entry = (Map.Entry) iter.next();
                                        Object key = entry.getKey();
                                        Object val = entry.getValue();
                                        Log.e("cc"+(String) key, (String) val);
                                    }
                                    //   Log.e("year_id",dataList.get(position).getYear_id());
                                    //    final String result=    HttpConnectionUtils.sendGETRequest(HttpConnectionUtils.ipport+"/api/car_result",hashMapcm,token);
                                    final String result=    initJsonModel();
                                    if(result.length()<60){

                                        //服务器没有返回200
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getActivity(),"result "+result,Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }else {
                                        List list = ResulutionJson.car_resultJson(result);
                                        Message message=handler.obtainMessage();
                                        Bundle bundle1=new Bundle();
                                        bundle1.putSerializable("list", (Serializable) list);
                                        bundle1.putSerializable("hashMap",(Serializable)hashMapcm);
                                        bundle1.putString("position", dataList.get(position).getText());
                                        // message.obj[0]=position;
                                        message.what=300;
                                        //  message.obj=list;
                                        message.setData(bundle1);
                                        handler.sendMessage(message);
                                    }
                                }
                            }).start();




                        }

                        @Override
                        public void onItemLongClick(View view, int position) {

                        }
                    });
                    rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    break;
                case  300:
                    Bundle bundle= msg.getData();
                    Intent intent=new Intent(getActivity(),CarOfSearch_BayonetModel_ResultActivity.class);
                    bundle.putSerializable("list", bundle.getSerializable("list"));
                    bundle.putSerializable("hashMap",bundle.getSerializable("hashMap"));
                    bundle.putString("position",bundle.getString("position"));
                    bundle.putString("isKK","false");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case 400:
                    List<Car_result_model> list1= (List) msg.obj;
                    //   Toast.makeText(getActivity(),list.get(0).getPlate(),Toast.LENGTH_SHORT).show();
                    dataList.addAll(list1);
                    rv.notifyData();//刷新数据
                    break;
            }
        }
    };
    private HashMap hashMap=new HashMap();

    private HashMap hashMapcm=new HashMap();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment_kakou,null);
        Bundle bundle=getArguments();
        String firstTime=     bundle.getString("firstTime");
        String endTime=       bundle.getString("endTime");
        String  plate_name=    bundle.getString("plate_name");
        hashMap = (HashMap) bundle.getSerializable("hashMap");
        Iterator<Map.Entry<String, String>> it = hashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            hashMapcm.put(entry.getKey()+"",entry.getValue()+"");
            //    System.out.println("KKkey= " + entry.getKey() + " and value= " + entry.getValue());
        }

        //   initView();
        rv=(RefreshRecyclerView)view.findViewById(R.id.kakou_RecyclerView);
        //
        initHttp(firstTime,endTime,plate_name);
        initAction();
        return view;
    }
    private void initHttp(final String firstTime, final String endTime, final String plate_name){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap hashMap=new HashMap();
                hashMap.put("plate",plate_name);
                hashMap.put("beginDate",firstTime);
                hashMap.put("endDate",endTime);
                hashMap.put("pn","1");
                final String token = FilesUtils.sharedGetFile(getActivity(), "user", "token");
                //     final String result=    HttpConnectionUtils.sendGETRequest("10.73.194.252:8088/api/car_result_model",hashMap,token);
                //  final String result=    HttpConnectionUtils.sendGETRequest(HttpConnectionUtils.ipport+"/api/car_result_model",hashMap,token);
                final String result= initJson();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),"result "+result,Toast.LENGTH_SHORT).show();
                    }
                });
                if(result.length()<60){
                    //服务器没有返回200
                    Toast.makeText(getActivity(),"没有更多数据了",Toast.LENGTH_SHORT).show();
                }else {
                    List list = ResulutionJson.car_result_modelJson(result);
                    Message message=handler.obtainMessage();
                    message.what=200;
                    message.obj=list;
                    handler.sendMessage(message);
                }

            }
        }).start();

    }
    //    private void initView(){
//        for(int i=0;i<24;i++){
//            dataList.add(i);
//        }
//        rv=(RecyclerView)view.findViewById(R.id.kakou_RecyclerView);
//    }
    public int i_page_index=1;
    private void initAction(){
        rv.setLoadMoreEnable(true);//允许加载更多
        rv.setOnLoadMoreListener(new RefreshRecyclerView.OnLoadMoreListener() {


            @Override
            public void loadMoreListener() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(),"load",Toast.LENGTH_SHORT).show();
                            }
                        });
                        final String token = FilesUtils.sharedGetFile(getActivity(), "user", "token");
                        i_page_index++;
                        hashMapcm.put("pn",i_page_index+"");
                        Iterator iter = hashMapcm.entrySet().iterator();
                        while (iter.hasNext()) {
                            Map.Entry entry = (Map.Entry) iter.next();
                            Object key = entry.getKey();
                            Object val = entry.getValue();
                            Log.e("ccmodel"+(String) key, (String) val);
                        }
                        // final String result = HttpConnectionUtils.sendGETRequest(HttpConnectionUtils.ipport+"/api/car_result_model", hashMapcm, token);
                        final String result = initJson();

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(),"--"+result,Toast.LENGTH_SHORT).show();
                            }
                        });



                        if(result.length()<60){
                            //   Log.e("1111111111111","22222222222222222");
//                            Message message = handler.obtainMessage();
//                            message.what = NULL_STATE;
//                            handler.sendMessage(message);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {

                                    //   rv.notifyAll();
                                    Toast.makeText(getActivity(),"没有更多数据了",Toast.LENGTH_SHORT).show();
                                }
                            });
                            return;
                        }else {
//                           handler.post(new Runnable() {
//                               @Override
//                               public void run() {
//                                   Toast.makeText(getBaseContext(),"加载成功",Toast.LENGTH_SHORT).show();
//                               }
//                           });
                            List<Car_result_model> list = ResulutionJson.car_result_modelJson(result);
                            Message message = handler.obtainMessage();
                            message.obj=list;
                            message.what=400;
                            handler.sendMessage(message);
                            //      getResultData=auth;
                        }
                    }
                }).start();
            }
        });
    }
    private String  initJson(){
        String result="{\n" +
                "    \"totalRecords\": 215,\n" +
                "    \"modelList\": [\n" +
                "        {\n" +
                "            \"text\": \"大众-POLO劲取-2009,2008,2006\",\n" +
                "            \"id\": \"59928959-50d1-edd9-cc5d-b0b027f46c07\",\n" +
                "            \"year_id\": 4324,\n" +
                "            \"brand_id\": \"26\",\n" +
                "            \"model_id\": \"315\",\n" +
                "            \"count\": 1,\n" +
                "            \"num\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"text\": \"雪佛兰-赛欧三厢-2016,2015\",\n" +
                "            \"id\": \"599288e2-dd99-910a-f79b-d500aee69ba0\",\n" +
                "            \"year_id\": 6510,\n" +
                "            \"brand_id\": \"129\",\n" +
                "            \"model_id\": \"1239\",\n" +
                "            \"count\": 28,\n" +
                "            \"num\": 28\n" +
                "        },\n" +
                "        {\n" +
                "            \"text\": \"路虎-揽胜运动版-2016\",\n" +
                "            \"id\": \"599288a3-b607-435a-9964-8ac9383b0e60\",\n" +
                "            \"year_id\": 5548,\n" +
                "            \"brand_id\": \"82\",\n" +
                "            \"model_id\": \"841\",\n" +
                "            \"count\": 3,\n" +
                "            \"num\": 3\n" +
                "        },\n" +
                "        {\n" +
                "            \"text\": \"雪佛兰-赛欧三厢-2016,2015\",\n" +
                "            \"id\": \"59928886-c70e-0d1b-e485-91eed3f6f608\",\n" +
                "            \"year_id\": 3823,\n" +
                "            \"brand_id\": \"129\",\n" +
                "            \"model_id\": \"1239\",\n" +
                "            \"count\": 10,\n" +
                "            \"num\": 10\n" +
                "        },\n" +
                "        {\n" +
                "            \"text\": \"宝马-X1-2015,2014,2013,2012,2010\",\n" +
                "            \"id\": \"59928883-53af-b043-c530-2f41f2fc3aa0\",\n" +
                "            \"year_id\": 5823,\n" +
                "            \"brand_id\": \"7\",\n" +
                "            \"model_id\": \"95\",\n" +
                "            \"count\": 6,\n" +
                "            \"num\": 6\n" +
                "        },\n" +
                "        {\n" +
                "            \"text\": \"别克-英朗XT-2014,2013,2012,2010\",\n" +
                "            \"id\": \"59928876-3187-c588-c0f0-e981aa93d2fc\",\n" +
                "            \"year_id\": 4091,\n" +
                "            \"brand_id\": \"5\",\n" +
                "            \"model_id\": \"65\",\n" +
                "            \"count\": 1,\n" +
                "            \"num\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"text\": \"MINI-CABRIO-2011\",\n" +
                "            \"id\": \"5992880d-a776-d10c-fd08-9546de09c818\",\n" +
                "            \"year_id\": 5551,\n" +
                "            \"brand_id\": \"97\",\n" +
                "            \"model_id\": \"969\",\n" +
                "            \"count\": 14,\n" +
                "            \"num\": 14\n" +
                "        },\n" +
                "        {\n" +
                "            \"text\": \"别克-凯越-2015,2013,2011,2009\",\n" +
                "            \"id\": \"599287df-db25-2113-c980-fe63f6fb11a0\",\n" +
                "            \"year_id\": 4077,\n" +
                "            \"brand_id\": \"5\",\n" +
                "            \"model_id\": \"58\",\n" +
                "            \"count\": 1,\n" +
                "            \"num\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"text\": \"货车车尾-厢式货车-B款\",\n" +
                "            \"id\": \"59928686-e9c6-e013-8860-2b48d3c802c7\",\n" +
                "            \"year_id\": 7129,\n" +
                "            \"brand_id\": \"227\",\n" +
                "            \"model_id\": \"2737\",\n" +
                "            \"count\": 1,\n" +
                "            \"num\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"text\": \"奥迪-Q7-2015,2014,2013,2012,2011,2010\",\n" +
                "            \"id\": \"5992851b-8f45-41e8-ba1c-8a7ba2609032\",\n" +
                "            \"year_id\": 5785,\n" +
                "            \"brand_id\": \"1\",\n" +
                "            \"model_id\": \"11\",\n" +
                "            \"count\": 18,\n" +
                "            \"num\": 18\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        return result;
    }
    private void initData(){
        adapter=new Model_carAdapter(dataList,getActivity());
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setOnItemClickLitener(new Model_carAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getActivity(), CarOfSearch_BayonetModel_ResultActivity.class);
                Bundle bundler=new Bundle();
                bundler.putString("isKK","false");
                bundler.putString("model",dataList.get(position).getText());
                bundler.putSerializable("hashMap",hashMap);
                intent.putExtras(bundler);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        rv.setAdapter(adapter);


    }
    public String initJsonModel(){
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
