package searchproject.php.yisa.chengyansy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NullCarSearChResultActivity extends AppCompatActivity implements View.OnClickListener{
    TextView searchCar_Result_plate_numberr;
    TextView searchCar_firstTime;
    TextView searchCar_endTime;
    private String plate_name;
    private String firstTime;
    private String endTime;

    private List<Button> btnList=new ArrayList<Button>();
    private Button btnsearchcar_defult;
    private Button btnsearchcar_kakou;
    private Button btnsearchcar_model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searcar_car_default);
        Bundle bundle=getIntent().getExtras();
        plate_name=bundle.getString("plate_number");
        firstTime=bundle.getString("firstTime");
        endTime=bundle.getString("endTime");
        initData(plate_name,firstTime,endTime);
    }
    private void initData(String plate_name, String firstTime, String endTime){
        searchCar_Result_plate_numberr=(TextView)findViewById(R.id.searchCar_Result_plate_numberr);
        searchCar_firstTime=(TextView)findViewById(R.id.searchCar_firstTime);
        searchCar_endTime=(TextView)findViewById(R.id.searchCar_endTime);

        searchCar_firstTime.setText(firstTime.substring(2));
        searchCar_endTime.setText(endTime.substring(2));
        searchCar_Result_plate_numberr.setText(plate_name);

    }

    @Override
    public void onClick(View view) {

    }
}
