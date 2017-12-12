package searchproject.php.yisa.chengyansy;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class NullFromPicResultActivity extends AppCompatActivity {

    TextView carinfo_plate;
    TextView carinfo_model;
    TextView carinfo_locationName;

    TextView carinfo_captureTime;
    ImageView car_image_url;
    Fragment fragment;
    static List Listlistresult;
    private FragmentTransaction ft;
    private FragmentManager fm;
    Toolbar toolbar;
    TextView textView;
//    private List<Button> btnList=new ArrayList<Button>();
//    private Button btnsearchcar_defult;
//    private Button btnsearchcar_kakou;
//    private Button btnsearchcar_model;
//    LinearLayout nodata;
    LinearLayout return_;
    private HashMap hashMap=null;
  //  private List<Car_resultJson> listResult=new ArrayList<Car_resultJson>();
    String pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_null_from_pic_result);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");//设置Toolbar标题
        textView=(TextView)findViewById(R.id.toolbar_title);
        textView.setText("以图搜车");
        return_=(LinearLayout)findViewById(R.id.img_return);
        return_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Bundle bundle=getIntent().getExtras();
      //  hashMap=(HashMap)bundle.getSerializable("hashMapImg");
      //  listResult =(List)bundle.getSerializable("list");
       // listresult=listResult;
        pic=bundle.getString("pic");
        String plate_number =       bundle.getString("plate_number");
        String model       =     bundle.getString("model");
        String LocationName=    bundle.getString("LocationName");
        String  CaptureTime  =   bundle.getString("CaptureTime");
        init(plate_number, CaptureTime, model,"0", pic, LocationName);

    }
    private void init(String plate_name, String firstTime, String endTime, String searchCar_result1,String url,String locationname){

        carinfo_plate=(TextView)findViewById(R.id.carinfo_plate);
        carinfo_model=(TextView)findViewById(R.id.carinfo_model);
        carinfo_locationName=(TextView)findViewById(R.id.carinfo_locationName);
        carinfo_captureTime=(TextView)findViewById(R.id.carinfo_captureTime);
        car_image_url=(ImageView)findViewById(R.id.car_image_url);

        Picasso.with(NullFromPicResultActivity.this).load(pic).into(car_image_url);

//        btnsearchcar_defult=(Button)findViewById(R.id.btnsearchcar_defult);
//        btnsearchcar_kakou=(Button)findViewById(R.id.btnsearchcar_kakou);
//        btnsearchcar_model=(Button)findViewById(R.id.btnsearchcar_model);
      //  nodata=(LinearLayout)findViewById(R.id.nodata);

        carinfo_model.setText(endTime);
        carinfo_plate.setText(searchCar_result1);
        carinfo_captureTime.setText(firstTime.substring(2));

        carinfo_locationName.setText(locationname);
        carinfo_locationName.setMovementMethod(ScrollingMovementMethod.getInstance());
        carinfo_plate.setText(plate_name);


    }
}
