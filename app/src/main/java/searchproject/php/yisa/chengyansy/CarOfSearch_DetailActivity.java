package searchproject.php.yisa.chengyansy;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import searchproject.php.yisa.chengyansy.FromPicFragment.PicCarModel_fragment;
import searchproject.php.yisa.chengyansy.FromPicFragment.PicDefault_fragment;
import searchproject.php.yisa.chengyansy.FromPicFragment.PicKakou_fragment;
import searchproject.php.yisa.chengyansy.Model_All.Car_resultJson;



public class CarOfSearch_DetailActivity extends AppCompatActivity implements View.OnClickListener{
    public static List<Car_resultJson> listresult;
    PicDefault_fragment default_fragment;
    PicKakou_fragment kakouFragment;
    PicCarModel_fragment carModel_fragment;

//    Default_fragment default_fragment;
//    KakouFragment kakouFragment;
//    CarModel_fragment carModel_fragment;


    TextView carinfo_plate;
    TextView carinfo_model;
    TextView carinfo_locationName;

    TextView carinfo_captureTime;
    ImageView  car_image_url;
    Fragment fragment;
    static  List Listlistresult;
    private FragmentTransaction ft;
    private FragmentManager fm;
    Toolbar toolbar;
    TextView textView;
    private List<Button> btnList=new ArrayList<Button>();
    private Button btnsearchcar_defult;
    private Button btnsearchcar_kakou;
    private Button btnsearchcar_model;
    LinearLayout nodata;
    LinearLayout return_;
    private HashMap hashMap=null;
    private List<Car_resultJson> listResult=new ArrayList<Car_resultJson>();
String pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carofsearch_detailactivity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");//设置Toolbar标题
        textView=(TextView)findViewById(R.id.toolbar_title);
        textView.setText("以图搜车");


          Bundle bundle=getIntent().getExtras();
          hashMap=(HashMap)bundle.getSerializable("hashMapImg");
          listResult =(List)bundle.getSerializable("list");
          listresult=listResult;
          String plate_number =       bundle.getString("plate_number");
          String model       =     bundle.getString("model");
          String LocationName=    bundle.getString("LocationName");
          String  CaptureTime  =   bundle.getString("CaptureTime");
          Log.e("CaptureTime",CaptureTime);
//        String plate_name=bundle.getString("plate_number");
//        String firstTime=bundle.getString("firstTime");
//        String endTime=bundle.getString("endTime");
       // String url=bundle.getString("img");
        return_=(LinearLayout)findViewById(R.id.img_return);
        return_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        List<Car_resultJson> list = (List) bundle.getSerializable("list");
        pic=bundle.getString("pic");
        Iterator iter = hashMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            Log.e((String) key, (String) val);
        }
      //  Log.e("first", (String) hashMap.get("beginDate"));
        String searchCar_result;
        if (listResult!=null&&listResult.size()>0) {
            searchCar_result = listResult.get(0).totalRecords;
        }else {
            searchCar_result = "0";
        }

        // Toast.makeText(this,"img"+list.get(2).getPic(),Toast.LENGTH_SHORT).show();
//        String plate_name="鲁B T8690";
//        String firstTime="17-07-31 13:00:00";
//        String endTime="17-08-31 13:00:00";
//        String searchCar_result="8888";




    init(plate_number, CaptureTime, model, listResult.size()>0?"0":listResult.get(0).totalRecords, pic, LocationName);
        initAction();
        nodata.setVisibility(View.GONE);//设置空界面不显示，默认显示有数据
        fm=getFragmentManager();
        ft=fm.beginTransaction();
      //  setBackgroundColorById(R.id.btnsearchcar_defult);

        bundle.putString("firstTime",listResult.size()>0? listResult.get(0).getCaptureTime():"0");
        bundle.putString("endTime",listResult.size()>0?listResult.get(0).getCaptureTime():"0");
        bundle.putString("plate_name", listResult.size()>0?listResult.get(0).getPlate():"0");
        bundle.putString("img",pic);
        bundle.putSerializable("hashMap",hashMap);
        default_fragment=new PicDefault_fragment();
        default_fragment.setArguments(bundle);
        PicDefault_fragment.setList(listResult, listResult.size()>0?listResult.get(0).getCaptureTime():"0", listResult.size()>0?listResult.get(0).getCaptureTime():"0", listResult.size()>0?listResult.get(0).getPlate():"0");
        ft.add(R.id.searchcar_fragment_btn,default_fragment);
        ft.commit();

    }
    private void init(String plate_name, String firstTime, String endTime, String searchCar_result1,String url,String locationname){

                carinfo_plate=(TextView)findViewById(R.id.carinfo_plate);
        carinfo_model=(TextView)findViewById(R.id.carinfo_model);
        carinfo_locationName=(TextView)findViewById(R.id.carinfo_locationName);
        carinfo_captureTime=(TextView)findViewById(R.id.carinfo_captureTime);
        car_image_url=(ImageView)findViewById(R.id.car_image_url);

        Picasso.with(CarOfSearch_DetailActivity.this).load(pic).into(car_image_url);

        btnsearchcar_defult=(Button)findViewById(R.id.btnsearchcar_defult);
        btnsearchcar_kakou=(Button)findViewById(R.id.btnsearchcar_kakou);
        btnsearchcar_model=(Button)findViewById(R.id.btnsearchcar_model);
        nodata=(LinearLayout)findViewById(R.id.nodata);

        carinfo_model.setText(endTime);
        carinfo_plate.setText(searchCar_result1);
        carinfo_captureTime.setText(firstTime.substring(2));

        carinfo_locationName.setText(locationname);
        carinfo_locationName.setMovementMethod(ScrollingMovementMethod.getInstance());
        carinfo_plate.setText(plate_name);

        btnList.add(btnsearchcar_defult);
        btnList.add(btnsearchcar_kakou);
        btnList.add(btnsearchcar_model);
    }
    public static  List<Car_resultJson>getList(){
        return  listresult;
    }
    private void initAction(){
        btnsearchcar_defult.setOnClickListener(this);
        btnsearchcar_kakou.setOnClickListener(this);
        btnsearchcar_model.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
//        fm=getFragmentManager();
//        ft=fm.beginTransaction();
//        String searchCar_result1 = searchCar_result.getText().toString();
//        String firstTime=   searchCar_firstTime.getText().toString();
//        String endTime=    searchCar_endTime.getText().toString();
//        String  plate_name  =   searchCar_Result_plate_numberr.getText().toString();
//        Bundle bundle = new Bundle();
//        bundle.putString("firstTime", firstTime);
//        bundle.putString("endTime", endTime);
//        bundle.putString("plate_name", plate_name);
//        bundle.putSerializable("hashMap",hashMap);
//        switch (v.getId()){
//            case R.id.btnsearchcar_defult:
//                //     bundle.putSerializable("list", (Serializable) listresult);
//
//
////                if (default_fragment==null) {
////                    default_fragment.setArguments(bundle);
////                }
//
//                setBackgroundColorById(R.id.btnsearchcar_defult);
//                if (!default_fragment.isAdded())
//                {  ft.add(R.id.searchcar_fragment_btn,default_fragment,"default");}
//                hideFragment(ft);
//                ft.show(default_fragment);
//                break;
//            case R.id.btnsearchcar_kakou:
//                if (kakouFragment==null) {
//                    kakouFragment=new KakouFragment();
//                    kakouFragment.setArguments(bundle);
//                }
//                //kakouFragment.setArguments(bundle);
//                setBackgroundColorById(R.id.btnsearchcar_kakou);
//                if (!kakouFragment.isAdded())
//                {    ft.add(R.id.searchcar_fragment_btn,kakouFragment);}
//                hideFragment(ft);
//                ft.show(kakouFragment);
//                break;
//            case  R.id.btnsearchcar_model:
//
//                if (carModel_fragment==null) {
//                    carModel_fragment=new CarModel_fragment();
//                    carModel_fragment.setArguments(bundle);
//
//                }
//                setBackgroundColorById(R.id.btnsearchcar_model);
//                if (!carModel_fragment.isAdded()) {
//                    ft.add(R.id.searchcar_fragment_btn, carModel_fragment);
//                }
//                hideFragment(ft);
//                ft.show(carModel_fragment);
//                break;
//
//        }
//        ft.commit();

    }
    private void setBackgroundColorById(int btnId) {
        for (Button btn : btnList) {
            if (btn.getId() == btnId){
                btn.setBackgroundColor(getResources().getColor(R.color.btn_yes));
                btn.setTextColor(getResources().getColor(R.color.btn_no));
            }else {
                btn.setTextColor(getResources().getColor(R.color.Tabs_color));
                btn.setBackgroundColor(getResources().getColor(R.color.btn_no));
            }
        }
    }
    //隐藏所有的fragment
    private void hideFragment(FragmentTransaction transaction){
        if(default_fragment != null){
            transaction.hide(default_fragment);
        }
        if(carModel_fragment != null){
            transaction.hide(carModel_fragment);
        }
        if(kakouFragment != null){
            transaction.hide(kakouFragment);
        }
    }
}
