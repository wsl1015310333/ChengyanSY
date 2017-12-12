package searchproject.php.yisa.chengyansy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CarSearchNullActivity extends AppCompatActivity {
    TextView searchCar_Result_plate_numberr;
    TextView searchCar_firstTime;
    TextView searchCar_endTime;

    Toolbar toolbar;
    TextView textView;

    LinearLayout return_;

    private String plate_name;
    private String firstTime;
    private String endTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_search_null);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");//设置Toolbar标题
        textView=(TextView)findViewById(R.id.toolbar_title);
        textView.setText("车牌搜车");

        return_=(LinearLayout)findViewById(R.id.img_return);
        return_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Bundle bundle=getIntent().getExtras();
        plate_name=bundle.getString("plate_number");
        firstTime=bundle.getString("firstTime");
        endTime=bundle.getString("endTime");

        searchCar_Result_plate_numberr=(TextView)findViewById(R.id.searchCar_Result_plate_numberr);
        searchCar_firstTime=(TextView)findViewById(R.id.searchCar_firstTime);
        searchCar_endTime=(TextView)findViewById(R.id.searchCar_endTime);

        searchCar_firstTime.setText(firstTime.equals("")==true?"":firstTime.substring(2));
        searchCar_endTime.setText(endTime.equals("")==true?"":endTime.substring(2));
        searchCar_Result_plate_numberr.setText(plate_name);
    }

}
