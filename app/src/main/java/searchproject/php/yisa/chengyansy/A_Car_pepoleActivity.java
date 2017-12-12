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

                       fm = getFragmentManager();
                       ft = fm.beginTransaction();
                       Bundle bundle=new Bundle();
                       bundle.putSerializable("aCarACrime",aCarACrime);
                       bundle.putString("plate",plate);
                       carPPInfoFragment.setArguments(bundle);
                       ft.add(R.id.a_car_pepole_fragment, carPPInfoFragment);
                       ft.commit();
                    break;

            }
        }
    };
    String plate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a__car_pepole);
        initTool();
        init();
        initData();
        initAction();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
             plate = bundle.getString("plate");
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
             //   final String result =  HttpConnectionUtils.sendGETRequest(HttpConnectionUtils.ipport+"/api/ycyd",hashMap,null);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final String result=initJson();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(A_Car_pepoleActivity.this,result+"",Toast.LENGTH_SHORT).show();
                    }
                });
                if(result.length()>10){
                    Message message=handler.obtainMessage();
                    message.what=200;
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
    Picasso.with(this).load(pic).into(a_car_pepole_carimg);
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
        if (a_car_pepole_progressbar.getVisibility() != View.VISIBLE) {
            switch (view.getId()) {
                case R.id.a_car_pepole_lginfo:
                    lginfo();
                    break;
                case R.id.a_car_pepole_ppCrime:
                    ppCrime();
                    break;
                case R.id.a_car_pepole_carCrime:
                    carCrime();
                    break;

            }
            ft.commit();
        }
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
        String result="{\n" +
                "  \"czxm\": \"和进水\",\n" +
                "  \"lxdh\": \"13853892375\",\n" +
                "  \"zjhm\": \"370920195202040010\",\n" +
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
