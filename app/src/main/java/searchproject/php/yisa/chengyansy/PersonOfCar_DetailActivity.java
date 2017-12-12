package searchproject.php.yisa.chengyansy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import searchproject.php.yisa.chengyansy.Model_All.Person_Detail;

public class PersonOfCar_DetailActivity extends AppCompatActivity {
    private ImageView mSpd_img;
    private TextView mspd_readname;
    private TextView mspr_idCard;
    private TextView mspd_place;
    private TextView  mspd_iddetail;
    private TextView spd_isalarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchpepole_detail);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        Person_Detail person_detail= (Person_Detail) bundle.getSerializable("person_detail");
        String   Pic=     person_detail.getPic();
        String   Name=     person_detail.getName();
        String   IdCard=     person_detail.getIdCard();
        String   IsDeplcy=     person_detail.getIsDeplcy();
        String   Address=     person_detail.getAddress();
        String   Personne=     person_detail.getPersonnel();
        initView();
        initData(Pic,Name,IdCard,IsDeplcy,Address,Personne);
    }
    private void initView(){

             mSpd_img=(ImageView)findViewById(R.id.spd_img);
             mspd_readname=(TextView)findViewById(R.id.spd_readname);
             mspr_idCard=(TextView)findViewById(R.id.spd_idCard);
             mspd_place=(TextView)findViewById(R.id.spd_place);
             mspd_iddetail=(TextView)findViewById(R.id.spd_iddetail);
             spd_isalarm=(TextView)findViewById(R.id.spd_isalarm);


    }
    private void initData(String pic, String name, String idCard, String isDeplcy, String address, String personne){
                mspd_readname.setText(name);
                mspr_idCard.setText(idCard);
                mspd_place.setText(address);
        mspd_iddetail.setText(personne);
                spd_isalarm.setText(isDeplcy);
        Picasso.with(PersonOfCar_DetailActivity.this).load("http://artimg.chefafa.com/upload/Image/20151121/1448071981933706.jpg").into(mSpd_img);
    }
}
