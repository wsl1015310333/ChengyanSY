package searchproject.php.yisa.chengyansy;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import searchproject.php.yisa.chengyansy.Model_All.Car_resultJson;
import searchproject.php.yisa.chengyansy.fragment_car.CarModel_fragment;
import searchproject.php.yisa.chengyansy.fragment_car.Default_fragment;
import searchproject.php.yisa.chengyansy.fragment_car.NewKakouFragment;

public class CarOfSearch_ResultActivity extends AppCompatActivity implements View.OnClickListener{
    Default_fragment default_fragment;
 //   KakouFragment kakouFragment;
    NewKakouFragment kakouFragment;
    CarModel_fragment carModel_fragment;
    TextView searchCar_Result_plate_numberr;
    TextView searchCar_firstTime;
    TextView searchCar_endTime;
    TextView searchCar_result;
    Fragment fragment;
    static List<Car_resultJson>listresult;
    private FragmentTransaction ft;
    private FragmentManager fm;
    Toolbar toolbar;
    TextView textView;
    private List<Button> btnList=new ArrayList<Button>();
    private Button btnsearchcar_defult;
    private Button btnsearchcar_kakou;
    private Button btnsearchcar_model;

    private  boolean isNoData=false;
    LinearLayout nodata;
    LinearLayout return_;
    private HashMap hashMap=null;
    private String plate_name;
    private String firstTime;
    private String endTime;
private   LinearLayout isSHouDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CrashApplication.mylist.add(CarOfSearch_ResultActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searcar_car_default);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");//设置Toolbar标题
        textView=(TextView)findViewById(R.id.toolbar_title);

         Bundle bundle=getIntent().getExtras();
        plate_name=bundle.getString("plate_number");
        textView.setText("车牌搜车");
        firstTime=bundle.getString("firstTime");
           endTime=bundle.getString("endTime");
        return_=(LinearLayout)findViewById(R.id.img_return);
        return_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
         List<Car_resultJson> list = (List) bundle.getSerializable("list");
         hashMap=(HashMap)bundle.getSerializable("hashMap");

        Iterator iter = hashMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            Log.e("result"+(String) key, (String) val);
            }
      //  Log.e("first", (String) hashMap.get("beginDate"));
        String searchCar_result;

listresult=list;
       // Toast.makeText(this,"img"+list.get(2).getPic(),Toast.LENGTH_SHORT).show();
//        String plate_name="鲁B T8690";
//        String firstTime="17-07-31 13:00:00";
//        String endTime="17-08-31 13:00:00";
//        String searchCar_result="8888";
        if (list!=null&&list.size()>0) {

            searchCar_result = list.get(0).totalRecords;
        }else {
          //  nodata.setVisibility(View.VISIBLE);
            searchCar_result = "0";
        }
        init(plate_name,firstTime,endTime,searchCar_result);
        if (list!=null&&list.size()>0) {
//            searchCar_result = list.get(0).totalRecords;
            nodata.setVisibility(View.GONE);//设置空界面不显示，默认显示有数据
            isNoData=false;
        }else {
            isNoData=true;
            nodata.setVisibility(View.VISIBLE);//设置空界面不显示，默认显示有数据
            //     searchCar_result = "0";
        }
        initAction();
        fm=getFragmentManager();
        ft=fm.beginTransaction();
        setBackgroundColorById(R.id.btnsearchcar_defult);
if (firstTime.equals("")){
    isSHouDate.setVisibility(View.INVISIBLE);
}

        bundle.putString("firstTime", firstTime);
        bundle.putString("endTime", endTime);
        bundle.putString("plate_name", plate_name);
        bundle.putSerializable("hashMap",hashMap);
        default_fragment=new Default_fragment();
        default_fragment.setArguments(bundle);
        Default_fragment.setList(listresult,firstTime,endTime,plate_name);
        ft.add(R.id.searchcar_fragment_btn,default_fragment);
        ft.commit();

    }
    private void init(String plate_name, String firstTime, String endTime, String searchCar_result1){
        isSHouDate=(LinearLayout)findViewById(R.id.isSHouDate);
        searchCar_Result_plate_numberr=(TextView)findViewById(R.id.searchCar_Result_plate_numberr);
        searchCar_firstTime=(TextView)findViewById(R.id.searchCar_firstTime);
        searchCar_endTime=(TextView)findViewById(R.id.searchCar_endTime);
        searchCar_result=(TextView)findViewById(R.id.searchCar_result);
        btnsearchcar_defult=(Button)findViewById(R.id.btnsearchcar_defult);
        btnsearchcar_kakou=(Button)findViewById(R.id.btnsearchcar_kakou);
        btnsearchcar_model=(Button)findViewById(R.id.btnsearchcar_model);
        nodata=(LinearLayout)findViewById(R.id.nodata);

        searchCar_result.setText(searchCar_result1);
        searchCar_firstTime.setText(firstTime.equals("")==true?"":firstTime.substring(2));
        searchCar_endTime.setText(endTime.equals("")==true?"":endTime.substring(2));
        searchCar_Result_plate_numberr.setText(plate_name);

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
        if (isNoData==false) {
            fm = getFragmentManager();
            ft = fm.beginTransaction();
            //  String searchCar_result1 = searchCar_result.getText().toString();
//        String firstTime=   searchCar_firstTime.getText().toString();
//        String endTime=    searchCar_endTime.getText().toString();
//        String  plate_name  =   searchCar_Result_plate_numberr.getText().toString();
            Bundle bundle = new Bundle();
            bundle.putString("firstTime", firstTime);
            bundle.putString("endTime", endTime);
            bundle.putString("plate_name", plate_name);
            bundle.putSerializable("hashMap", hashMap);
            switch (v.getId()) {
                case R.id.btnsearchcar_defult:
                    textView.setText("车牌搜车");
                    //     bundle.putSerializable("list", (Serializable) listresult);


//                if (default_fragment==null) {
//                    default_fragment.setArguments(bundle);
//                }

                    setBackgroundColorById(R.id.btnsearchcar_defult);
                    if (!default_fragment.isAdded()) {
                        ft.add(R.id.searchcar_fragment_btn, default_fragment, "default");
                    }
                    hideFragment(ft);
                    ft.show(default_fragment);
                    break;
                case R.id.btnsearchcar_kakou:
                    textView.setText("按卡口分组");
                    if (kakouFragment == null) {
                        kakouFragment = new NewKakouFragment();
                        kakouFragment.setArguments(bundle);
                    }
                    //kakouFragment.setArguments(bundle);
                    setBackgroundColorById(R.id.btnsearchcar_kakou);
                    if (!kakouFragment.isAdded()) {
                        ft.add(R.id.searchcar_fragment_btn, kakouFragment);
                    }
                    hideFragment(ft);
                    ft.show(kakouFragment);
                    break;
                case R.id.btnsearchcar_model:
                    textView.setText("按车型分组");
                    if (carModel_fragment == null) {
                        carModel_fragment = new CarModel_fragment();
                        carModel_fragment.setArguments(bundle);

                    }
                    setBackgroundColorById(R.id.btnsearchcar_model);
                    if (!carModel_fragment.isAdded()) {
                        ft.add(R.id.searchcar_fragment_btn, carModel_fragment);
                    }
                    hideFragment(ft);
                    ft.show(carModel_fragment);
                    break;

            }
            ft.commit();
        }
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
