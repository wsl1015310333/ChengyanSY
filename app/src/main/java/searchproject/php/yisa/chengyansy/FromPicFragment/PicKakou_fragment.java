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

import searchproject.php.yisa.chengyansy.Adapter_car.Kakou_carAdapter;
import searchproject.php.yisa.chengyansy.CarOfSearch_BayonetModel_ResultActivity;
import searchproject.php.yisa.chengyansy.FilesUtils;
import searchproject.php.yisa.chengyansy.Model_All.Car_result_kk;
import searchproject.php.yisa.chengyansy.R;
import searchproject.php.yisa.chengyansy.utils.HttpConnectionUtils;
import searchproject.php.yisa.chengyansy.utils.ResulutionJson;
import searchproject.php.yisa.chengyansy.view.RefreshRecyclerView;

/**
 * Created by Administrator on 2017/7/31 0031.
 */

public class PicKakou_fragment extends Fragment {
    private List<Car_result_kk> dataList=new ArrayList();
    private RefreshRecyclerView rv;
    View view;
    Kakou_carAdapter adapter;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case  200:
                    List list=(List)msg.obj;
                    dataList=list;
                    adapter=new Kakou_carAdapter(list,getActivity());
                    adapter.setOnItemClickLitener(new Kakou_carAdapter.OnItemClickLitener() {
                        @Override
                        public void onItemClick(View view, final int position) {
                            Intent intent=new Intent(getActivity(), CarOfSearch_BayonetModel_ResultActivity.class);
                            Bundle bundle=new Bundle();
                            new  Thread(new Runnable() {
                                @Override //D:/andro/YISA_SearchProject/app/src/main/assets/images/
                                public void run() {
                                    final String token = FilesUtils.sharedGetFile(getActivity(), "user", "token");
                                    hashMapkk.put("location_id",dataList.get(position).getId());
                                    Iterator iter = hashMapkk.entrySet().iterator();
                                    while (iter.hasNext()) {
                                        Map.Entry entry = (Map.Entry) iter.next();
                                        Object key = entry.getKey();
                                        Object val = entry.getValue();
                                        Log.e("kk"+(String) key, (String) val);
                                    }
                                    //     final String result=    HttpConnectionUtils.sendGETRequest(HttpConnectionUtils.ipport+"/api/car_result",hashMapkk,token);
                                    final String result=    initJsonClick();
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
                                        bundle1.putSerializable("hashMap",(Serializable)hashMapkk);
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
                    rv.setAdapter(adapter);
                    break;
                case 300:
                    Bundle bundle= msg.getData();
                    Intent intent=new Intent(getActivity(),CarOfSearch_BayonetModel_ResultActivity.class);
                    bundle.putSerializable("list", bundle.getSerializable("list"));
                    bundle.putSerializable("hashMap",bundle.getSerializable("hashMap"));
                    bundle.putString("position",bundle.getString("position"));
                    bundle.putString("isKK","true");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case 400:
                    List<Car_result_kk> list1= (List) msg.obj;
                    //   Toast.makeText(getActivity(),list.get(0).getPlate(),Toast.LENGTH_SHORT).show();
                    dataList.addAll(list1);
                    rv.notifyData();//刷新数据

                    break;
            }
        }
    };
    private HashMap hashMap=new HashMap();
    private HashMap hashMapkk=new HashMap();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment_kakou,null);
        Bundle bundle=getArguments();
        String firstTime=     bundle.getString("firstTime");
        String endTime=      bundle.getString("endTime");
        String  plate_name  =    bundle.getString("plate_name");
        hashMap = (HashMap) bundle.getSerializable("hashMap");

        Iterator<Map.Entry<String, String>> it = hashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            hashMapkk.put(entry.getKey()+"",entry.getValue()+"");
            //    System.out.println("KKkey= " + entry.getKey() + " and value= " + entry.getValue());
        }
        Iterator<Map.Entry<String, String>> itt = hashMapkk.entrySet().iterator();
        while (itt.hasNext()) {
            Map.Entry<String, String> entry = itt.next();
            hashMapkk.put(entry.getKey()+"",entry.getValue()+"");
            System.out.println("KKkey= " + entry.getKey() + " and value= " + entry.getValue());
        }
        //   initView();
        rv=(RefreshRecyclerView)view.findViewById(R.id.kakou_RecyclerView);
        //    initData();
        initHttp(firstTime,endTime,plate_name);
        initData(firstTime,endTime,plate_name);
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

//                final String result=    HttpConnectionUtils.sendGETRequest("10.73.194.252:8088/api/car_result_kk",hashMap,token);
                // final String result=   HttpConnectionUtils.sendGETRequest(HttpConnectionUtils.ipport+"/api/car_result_kk",hashMap,token);
                final String result= initJson();
                if(result.equals("1")||result.equals("2")){
//                    //服务器没有返回200
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getActivity(),"result "+result,Toast.LENGTH_SHORT).show();
//                        }
//                    });
                }else {
                    List list = ResulutionJson.car_result_kkJson(result);
                    Message message=handler.obtainMessage();
                    message.what=200;
                    message.obj=list;
                    handler.sendMessage(message);
                }

            }
        }).start();

    }public int i_page_index=1;
    void  initData(final String firstTime, final String endTime, final String plate_name){

        rv.setLoadMoreEnable(true);//允许加载更多
        rv.setOnLoadMoreListener(new RefreshRecyclerView.OnLoadMoreListener() {


            @Override
            public void loadMoreListener() {

                final String token = FilesUtils.sharedGetFile(getActivity(), "user", "token");
                i_page_index++;
                HashMap hashMap1=new HashMap();
                hashMap1.put("plate",plate_name);
                hashMap1.put("beginDate",firstTime);
                hashMap1.put("endDate",endTime);
                hashMap1.put("pn",i_page_index+"");
                Iterator iter = hashMap1.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    Object key = entry.getKey();
                    Object val = entry.getValue();
                    Log.e("kkmodel" + (String) key, (String) val);
                }
                //hashMapkk.put("pn",i_page_index+"");
//        Iterator iter = hashMapkk.entrySet().iterator();
//        while (iter.hasNext()) {
//            Map.Entry entry = (Map.Entry) iter.next();
//            Object key = entry.getKey();
//            Object val = entry.getValue();
//            Log.e("Defaultmodel"+(String) key, (String) val);
//        }

                  final String result =  HttpConnectionUtils.sendGETRequest(HttpConnectionUtils.ipport+"/api/car_result_kk", hashMap, null);
            //    final String result = initJson();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),"--"+result,Toast.LENGTH_SHORT).show();
                    }
                });



                if(result.length()<60){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

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
                    List<Car_result_kk> list = ResulutionJson.car_result_kkJson(result);
                    Message message = handler.obtainMessage();
                    message.obj=list;
                    message.what=400;
                    handler.sendMessage(message);
                    //      getResultData=auth;
                }

            }
        });
    }

    private String initJson(){
        String result="\n" +
                "{\n" +
                "  \"totalRecords\": 14,\n" +
                "  \"kkList\": [\n" +
                "    {\n" +
                "      \"text\": \"海阳市海政路-公园街路口抓拍点\",\n" +
                "      \"id\": \"5990f564-99e3-2aea-b175-b05e23a2812a\",\n" +
                "      \"count\": 1,\n" +
                "      \"num\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"text\": \"海阳市海东路-龙山街路口抓拍点\",\n" +
                "      \"id\": \"5990f4fc-ff5d-0d34-b78b-f2c665f14644\",\n" +
                "      \"count\": 1,\n" +
                "      \"num\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"text\": \"龙山街育才中学卡口\",\n" +
                "      \"id\": \"5990f4ab-79ef-275e-7676-f22aadcf3a00\",\n" +
                "      \"count\": 1,\n" +
                "      \"num\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"text\": \"黄海大道-海翔路海阳市抓拍点\",\n" +
                "      \"id\": \"5990eba4-5617-eb26-f2d9-86dbb1b4b45c\",\n" +
                "      \"count\": 2,\n" +
                "      \"num\": 2\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        return result;
    }
//    private void initView(){
//        for(int i=0;i<24;i++){
//            dataList.add(i);
//        }
//        rv=(RecyclerView)view.findViewById(R.id.kakou_RecyclerView);
//    }
//    private void initData(){
//        adapter=new Kakou_carAdapter(dataList,getActivity());
//        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
//        rv.setAdapter(adapter);
//
//
//    }
//    public  void toResult(int position){
//        Intent intent=new Intent(getActivity(), CarOfSearch_BayonetModel_ResultActivity.class);
//        Bundle bundle=new Bundle();
//        bundle.putSerializable("kakou", (Serializable) dataList.get(position));
//
//        intent.putExtras(bundle);
//        startActivity(intent);
//
//    }

    public String initJsonClick(){
        String result="{\"totalRecords\":2,\"searchList\":[{\"plate\":\"鲁Y11W17\",\"model\":\"标致-3008-2015,2014,2013\",\"locationName\":\"(高速)观海路烟海高速出入口卡口\",\"captureTime\":\"2017-08-15 09:48:33\",\"pic\":\"http:\\/\\/10.50.145.101:8000\\/image\\/data7\\/64418900000\\/2017\\/08\\/15\\/09\\/64418900000_500421023100_609000000513402_02_03_20170815094833372657_1.jpg\"},{\"plate\":\"鲁Y11W17\",\"model\":\"标致-3008-2015,2014,2013\",\"locationName\":\"(高速)观海路烟海高速出入口卡口\",\"captureTime\":\"2017-08-15 09:48:33\",\"pic\":\"http:\\/\\/10.50.145.101:8000\\/image\\/data7\\/64418900000\\/2017\\/08\\/15\\/09\\/64418900000_500421023100_609000000513402_02_03_20170815094833372657_1.jpg\"}]}";
        return result;
    }
}
