package searchproject.php.yisa.chengyansy;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.socketio.client.On;
import com.squareup.okhttp.internal.http.HttpConnection;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import searchproject.php.yisa.chengyansy.Model_All.ACarACrime;
import searchproject.php.yisa.chengyansy.fragement_carpp.CarPPCCrimeFragment;
import searchproject.php.yisa.chengyansy.fragement_carpp.CarPPCrimeFragment;
import searchproject.php.yisa.chengyansy.fragement_carpp.CarPPInfoFragment;
import searchproject.php.yisa.chengyansy.utils.DensityUtil;
import searchproject.php.yisa.chengyansy.utils.HttpConnectionUtils;
import searchproject.php.yisa.chengyansy.utils.ResulutionJson;

public class A_Car_pepoleActivity extends BaseActivity implements View.OnClickListener {
    Toolbar toolbar;
    TextView toottv;
    LinearLayout return_;
    private ImageView a_car_pepole_carimg;
    private TextView a_car_pepole_cartv;
    private LinearLayout a_car_pepole_lginfo;
    private LinearLayout a_car_pepole_ppCrime;
    private LinearLayout a_car_pepole_carCrime;
     private FrameLayout a_car_pepole_fragment;
    private ImageView a_car_pepole_lginfo_img;
    private TextView  a_car_pepole_lginfo_tv;
    private ImageView a_car_pepole_ppCrime_img;
    private TextView  a_car_pepole_ppCrime_tv;
    private ImageView a_car_pepole_carCrime_img;
    private TextView  a_car_pepole_carCrime_tv;

    private List<ImageView> imgList=new ArrayList<ImageView>();
    private List<TextView> tvList=new ArrayList<TextView>();
    private List<LinearLayout> llList=new ArrayList<LinearLayout>();
    private ProgressBar a_car_pepole_progressbar;
    private FragmentTransaction ft;
    private FragmentManager fm;
    CarPPInfoFragment carPPInfoFragment=new CarPPInfoFragment();
    CarPPCCrimeFragment carPPCCrimeFragment=new CarPPCCrimeFragment();
    CarPPCrimeFragment carPPCrimeFragment=new CarPPCrimeFragment();
    int i=0;
    private Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result=(String)msg.obj;
            switch(msg.what){
                   case 200:
                       Log.e("acar","a");
                       a_car_pepole_progressbar.setVisibility(View.GONE);
                       a_car_pepole_fragment.setVisibility(View.VISIBLE);
                       ACarACrime aCarACrime= ResulutionJson.aCarACrime(result);
                     if(aCarACrime==null){
                         Log.e("aCarACrim","NUll");
                     }
                       fm = getFragmentManager();
                       ft = fm.beginTransaction();
                       Bundle bundle=new Bundle();
                       bundle.putString("isNull","no");
                       bundle.putSerializable("aCarACrime",aCarACrime);
                       bundle.putString("plate",plate);
                       carPPInfoFragment.setArguments(bundle);
                       carPPCrimeFragment.setArguments(bundle);
                       if(i==0) {
                           ft.add(R.id.a_car_pepole_fragment, carPPInfoFragment);
                           //   ft.add(R.id.a_car_pepole_fragment, carPPCrimeFragment);
                       }
                       else if(i==1){
                           ft.add(R.id.a_car_pepole_fragment, carPPCrimeFragment);
                       }else if(i==2){
                           ft.add(R.id.a_car_pepole_fragment, carPPCCrimeFragment);
                       }
                           ft.commit();
                    break;
                case 400:
                    Log.e("acar","a");
                    a_car_pepole_progressbar.setVisibility(View.GONE);
                    a_car_pepole_fragment.setVisibility(View.VISIBLE);
                   // ACarACrime aCarACrime= ResulutionJson.aCarACrime(result);
//                    if(aCarACrime==null){
//                        Log.e("aCarACrim","NUll");
//                    }
                    fm = getFragmentManager();
                    ft = fm.beginTransaction();
                    Bundle bundleNull=new Bundle();
                    bundleNull.putString("isNull","yes");
                    bundleNull.putString("plate",plate);
                    carPPInfoFragment.setArguments(bundleNull);
                    carPPCrimeFragment.setArguments(bundleNull);
                    ft.add(R.id.a_car_pepole_fragment, carPPInfoFragment);
                    //   ft.add(R.id.a_car_pepole_fragment, carPPCrimeFragment);
                    ft.commit();
                    break;

            }
        }
    };
    String plate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CrashApplication.mylist.add(A_Car_pepoleActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a__car_pepole);

        initTool();
        init();
        initData();
        initAction();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
             plate = bundle.getString("plate");
            toottv.setText(plate+"（蓝色）");
             String plateType = bundle.getString("plateType");
            String img = bundle.getString("pic");
            Log.e("acap",plate+plateType+img);
            initPic(img);//显示图片
            initHttp(plate, plateType);
        }
    }

   public void  initHttp(final String plate, final String plateType){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap hashMap=new HashMap();
                hashMap.put("plate_number",plate);
                hashMap.put("plate_type_id",plateType);
               final String result =  HttpConnectionUtils.sendGETRequest(HttpConnectionUtils.ipport+"/api/ycyd",hashMap,null);
            // final String result=initJson();
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(A_Car_pepoleActivity.this,result+"",Toast.LENGTH_SHORT).show();
//                    }
//                });
                if(result.length()>100){
                    Message message=handler.obtainMessage();
                    message.what=200;
                    message.obj=result;
                    handler.sendMessage(message);
                }else {
                    Message message=handler.obtainMessage();
                    message.what=400;
                    message.obj=result;
                    handler.sendMessage(message);
                }

            }
        }).start();
    }

    @Override
    public void initTool() {
        super.initTool();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");//设置Toolbar标题
        toottv=(TextView)findViewById(R.id.toolbar_title);
        toottv.setText("鲁B256KT（蓝色）");
        return_=(LinearLayout)findViewById(R.id.img_return);
        return_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
private void initPic(String pic){
    Picasso.with(this).load(pic).resize(DensityUtil.dip2px(this,360),DensityUtil.dip2px(this,208)).into(a_car_pepole_carimg);
}
    @Override
    public void init() {
        a_car_pepole_progressbar=(ProgressBar)findViewById(R.id.a_car_pepole_progressbar);
        a_car_pepole_carimg=(ImageView)findViewById(R.id.a_car_pepole_carimg);
       // a_car_pepole_cartv=(TextView)findViewById(R.id.a_car_pepole_cartv);
        a_car_pepole_lginfo=(LinearLayout)findViewById(R.id.a_car_pepole_lginfo);
        a_car_pepole_ppCrime=(LinearLayout)findViewById(R.id.a_car_pepole_ppCrime);
        a_car_pepole_carCrime=(LinearLayout)findViewById(R.id.a_car_pepole_carCrime);
        a_car_pepole_fragment=(FrameLayout)findViewById(R.id.a_car_pepole_fragment);
        a_car_pepole_lginfo_img=(ImageView)findViewById(R.id.a_car_pepole_lginfo_img);
        a_car_pepole_lginfo_tv=(TextView)findViewById(R.id.a_car_pepole_lginfo_tv);
        a_car_pepole_ppCrime_img=(ImageView)findViewById(R.id.a_car_pepole_ppCrime_img);
        a_car_pepole_ppCrime_tv=(TextView)findViewById(R.id.a_car_pepole_ppCrime_tv);
        a_car_pepole_carCrime_img=(ImageView)findViewById(R.id.a_car_pepole_carCrime_img);
        a_car_pepole_carCrime_tv=(TextView)findViewById(R.id.a_car_pepole_carCrime_tv);
    }

    @Override
    public void initData() {
        imgList.add(a_car_pepole_lginfo_img);
        imgList.add(a_car_pepole_ppCrime_img);
        imgList.add(a_car_pepole_carCrime_img);
        tvList.add(a_car_pepole_lginfo_tv);
        tvList.add(a_car_pepole_ppCrime_tv);
        tvList.add(a_car_pepole_carCrime_tv);
        llList.add(a_car_pepole_lginfo);
        llList.add(a_car_pepole_ppCrime);
        llList.add(a_car_pepole_carCrime);
    }

    @Override
    public void initAction() {
         a_car_pepole_lginfo.setOnClickListener(this);
        a_car_pepole_ppCrime.setOnClickListener(this);
        a_car_pepole_carCrime.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        setBackgroundColorById(view.getId());
        fm = getFragmentManager();
        ft = fm.beginTransaction();

            switch (view.getId()) {
                case R.id.a_car_pepole_lginfo:
                    if (a_car_pepole_progressbar.getVisibility() != View.VISIBLE) {
                        lginfo();
                    }
                    i=0;
                    break;
                case R.id.a_car_pepole_ppCrime:
                    if (a_car_pepole_progressbar.getVisibility() != View.VISIBLE) {
                        ppCrime();
                    }
                    i=1;
                    break;
                case R.id.a_car_pepole_carCrime:
                    if (a_car_pepole_progressbar.getVisibility() != View.VISIBLE) {
                        carCrime();
                    }
                    i=2;
                    break;

            }
            ft.commit();
        }

    public void lginfo(){
        //toottv.setText("车牌搜车");
        if (carPPInfoFragment == null) {
            carPPInfoFragment = new CarPPInfoFragment();
        }
        if (!carPPInfoFragment.isAdded()) {
            ft.add(R.id.a_car_pepole_fragment, carPPInfoFragment);
        }
        hideFragment(ft);
        ft.show(carPPInfoFragment);

    }
    public void ppCrime(){
        if (carPPCrimeFragment == null) {
            carPPCrimeFragment = new CarPPCrimeFragment();
        }
        if (!carPPCrimeFragment.isAdded()) {
            ft.add(R.id.a_car_pepole_fragment, carPPCrimeFragment);
        }
        hideFragment(ft);
        ft.show(carPPCrimeFragment);
    }
    public void carCrime(){
        if (carPPCCrimeFragment == null) {
            carPPCCrimeFragment = new CarPPCCrimeFragment();
        }
        if (!carPPCCrimeFragment.isAdded()) {
            ft.add(R.id.a_car_pepole_fragment, carPPCCrimeFragment);
        }
        hideFragment(ft);
        ft.show(carPPCCrimeFragment);
    }
    private void setBackgroundColorById(int btnId) {

            if (btnId == R.id.a_car_pepole_lginfo){
                setImgBackgroundColorById(R.id.a_car_pepole_lginfo_img);
                setTvBackgroundColorById(R.id.a_car_pepole_lginfo_tv);
//                btn.setBackgroundColor(getResources().getColor(R.color.btn_yes));
//                btn.setTextColor(getResources().getColor(R.color.btn_no));
            }else if(btnId==R.id.a_car_pepole_ppCrime) {
//                btn.setTextColor(getResources().getColor(R.color.Tabs_color));
//                btt
                setImgBackgroundColorById(R.id.a_car_pepole_ppCrime_img);
                setTvBackgroundColorById(R.id.a_car_pepole_ppCrime_tv);
            } else{
                setImgBackgroundColorById(R.id.a_car_pepole_carCrime_img);
                setTvBackgroundColorById(R.id.a_car_pepole_carCrime_tv);
                }
        }


    private void setImgBackgroundColorById(int imgId){
//        for (ImageView img : imgList) {
//            if (img.getId() == imgId){
//                img.setBackgroundColor(getResources().getColor(R.color.btn_yes));
//              //  img.setTextColor(getResources().getColor(R.color.btn_no));
//            }else {
//               // img.setTextColor(getResources().getColor(R.color.Tabs_color));
//                img.setBackgroundColor(getResources().getColor(R.color.img_tv));
//            }
//        }
        if(imgId==R.id.a_car_pepole_lginfo_img){
            a_car_pepole_lginfo_img.setBackgroundResource(R.drawable.jibenblue);
            a_car_pepole_carCrime_img.setBackgroundResource(R.drawable.weizhang);
            a_car_pepole_ppCrime_img.setBackgroundResource(R.drawable.weifa);
        }else if(imgId==R.id.a_car_pepole_carCrime_img){
            a_car_pepole_lginfo_img.setBackgroundResource(R.drawable.jiben);
            a_car_pepole_carCrime_img.setBackgroundResource(R.drawable.weizhangblue);
            a_car_pepole_ppCrime_img.setBackgroundResource(R.drawable.weifa);
        }else {
            a_car_pepole_lginfo_img.setBackgroundResource(R.drawable.jiben);
            a_car_pepole_carCrime_img.setBackgroundResource(R.drawable.weizhang);
            a_car_pepole_ppCrime_img.setBackgroundResource(R.drawable.weifablue);
        }
    }
    private void setTvBackgroundColorById(int tvId){
        for (TextView tv : tvList) {
            if (tv.getId() == tvId){
             //   tv.setBackgroundColor(getResources().getColor(R.color.btn_yes));
                tv.setTextColor(getResources().getColor(R.color.btn_yes));
            }else {
                tv.setTextColor(getResources().getColor(R.color.img_tv));
              //  tv.setBackgroundColor(getResources().getColor(R.color.btn_no));
            }
        }
    }
    //隐藏所有的fragment
    private void hideFragment(FragmentTransaction transaction){
        if(carPPInfoFragment != null){
            transaction.hide(carPPInfoFragment);
        }
        if(carPPCCrimeFragment != null){
            transaction.hide(carPPCCrimeFragment);
        }
        if(carPPCrimeFragment != null){
            transaction.hide(carPPCrimeFragment);
        }
    }
    public String initJson(){
        String reslt="fdsafs";
      String result ="\n" +
              "{\n" +
              "  \"HLJ\": \"1290\",\n" +
              "  \"JYHGBZBH\": \"173732576868\",\n" +
              "  \"ZHKRKSJ\": \"2017-03-01 13:36:01.0\",\n" +
              "  \"LSH\": \"1J41201381400\",\n" +
              "  \"SFZMMC\": \"A\",\n" +
              "  \"BXZZRQ\": \"2017-08-25 00:00:00.0\",\n" +
              "  \"GCJK\": \"A\",\n" +
              "  \"DJZSBH\": \"370002933689\",\n" +
              "  \"FDJRQ\": \"2004-12-01 10:11:10\",\n" +
              "  \"SYQ\": \"2\",\n" +
              "  \"XGZL\": \"ABMJZ\",\n" +
              "  \"ZJ\": \"2350\",\n" +
              "  \"CLSBDH\": \"106391\",\n" +
              "  \"SYR\": \"和进水\",\n" +
              "  \"CLXH\": \"SC6371\",\n" +
              "  \"SFZMHM\": \"370920195202040010\",\n" +
              "  \"RLZL\": \"A\",\n" +
              "  \"PZBH1\": \"0009332\",\n" +
              "  \"FZRQ\": \"2004-12-01 10:11:01\",\n" +
              "  \"ZS\": \"2\",\n" +
              "  \"ZT\": \"A\",\n" +
              "  \"ZXXS\": \"1\",\n" +
              "  \"SYXZ\": \"A\",\n" +
              "  \"YZBM1\": \"271200\",\n" +
              "  \"FPRQ\": \"2004-12-01 10:10:44\",\n" +
              "  \"CCRQ\": \"2004-06-21 00:00:00\",\n" +
              "  \"GLBM\": \"370900000400\",\n" +
              "  \"YTSX\": \"9\",\n" +
              "  \"QZBFQZ\": \"2099-12-31 00:00:00\",\n" +
              "  \"ZZCMC\": \"长安汽车（集团）有限责任公司\",\n" +
              "  \"FZJG\": \"鲁J\",\n" +
              "  \"YXQZ\": \"2017-12-31 00:00:00\",\n" +
              "  \"NSZMBH\": \"370115590\",\n" +
              "  \"JYW\": \"396A05030E0783958286677B050601097C62027A7D705B2073402C4359185057421A040476060405050C7F0B0274770A000102090B035B4070455A435B1D522F37\",\n" +
              "  \"ZZXZQH\": \"370982\",\n" +
              "  \"BZCS\": \"0\",\n" +
              "  \"ZSXZQH\": \"370982\",\n" +
              "  \"DYBJ\": \"0\",\n" +
              "  \"YXH\": \"1\",\n" +
              "  \"FDJXH\": \"JL474Q\",\n" +
              "  \"ZSXXDZ\": \"山东省新泰市青云街道办事处市副食品公司宿舍\",\n" +
              "  \"CLPP1\": \"长安\",\n" +
              "  \"FDJH\": \"A24478\",\n" +
              "  \"HPZL\": \"02\",\n" +
              "  \"NSZM\": \"1\",\n" +
              "  \"LXDH\": \"7213005\",\n" +
              "  \"CCDJRQ\": \"2004-12-01 10:10:44\",\n" +
              "  \"ZQYZL\": \"0\",\n" +
              "  \"XH\": \"37090004258844\",\n" +
              "  \"CLYT\": \"P1\",\n" +
              "  \"HDZK\": \"7\",\n" +
              "  \"XZQH\": \"370982\",\n" +
              "  \"GXRQ\": \"2016-12-23 18:39:50\",\n" +
              "  \"HPHM\": \"JD5693\",\n" +
              "  \"LTGG\": \"165/70R13LT\",\n" +
              "  \"JBR\": \"郭雪梅\",\n" +
              "  \"BDJCS\": \"0\",\n" +
              "  \"src\": \"jdc\",\n" +
              "  \"HDFS\": \"A\",\n" +
              "  \"ZZXXDZ\": \"山东省新泰市青云街道办事处市副食品公司宿舍\",\n" +
              "  \"CLLX\": \"K31\",\n" +
              "  \"CLLY\": \"1\",\n" +
              "  \"FHGZRQ\": \"2013-12-03 15:01:22\",\n" +
              "  \"DJRQ\": \"2016-12-23 00:00:00\",\n" +
              "  \"V_DEALFLAG\": \"0\",\n" +
              "  \"CWKC\": \"3725\",\n" +
              "  \"BPCS\": \"0\",\n" +
              "  \"LTS\": \"4\",\n" +
              "  \"LLPZ1\": \"A\",\n" +
              "  \"PL\": \"1310\",\n" +
              "  \"CWKK\": \"1560\",\n" +
              "  \"ZZL\": \"1580\",\n" +
              "  \"CWKG\": \"1895\",\n" +
              "  \"QLJ\": \"1280\",\n" +
              "  \"ZBZL\": \"1000\",\n" +
              "  \"ZZG\": \"156\",\n" +
              "  \"GL\": \"60\",\n" +
              "  \"SJHM\": \"13853892375\",\n" +
              "  \"CSYS\": \"B\",\n" +
              "  \"sort\": 1482489590,\n" +
              "  \"hpzl\": \"小型汽车号牌(02)\",\n" +
              "  \"cllx\": \"小型普通客车(K31)\",\n" +
              "  \"zjlx\": \"居民身份证(A)\",\n" +
              "  \"sdry\": 0,\n" +
              "  \"ztry\": 0,\n" +
              "  \"qkry\": 0,\n" +
              "  \"zdry\": 0,\n" +
              "  \"sary\": 0\n" +
              "}";
        return result;
    }
}
