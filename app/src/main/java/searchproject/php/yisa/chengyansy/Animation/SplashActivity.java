package searchproject.php.yisa.chengyansy.Animation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import searchproject.php.yisa.chengyansy.FilesUtils;
import searchproject.php.yisa.chengyansy.LoginActivity;
import searchproject.php.yisa.chengyansy.MainActivity;
import searchproject.php.yisa.chengyansy.Model_All.LoginJson;
import searchproject.php.yisa.chengyansy.R;
import searchproject.php.yisa.chengyansy.Strategy_Factory.FactoryCity;
import searchproject.php.yisa.chengyansy.utils.ResulutionJson;


public class SplashActivity extends Activity {
    private ImageView iv_mainview;
    private AnimationSet as;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);  //全屏
        initHttpUtile();
        initView();
        startAnimation();
        initEvent();
    }
    private void startAnimation(){


    }
    private void initHttpUtile(){

//        HttpConnectionUtils.ipport= "192.168.0.223:9906";//fcg原IP
//        HttpConnectionUtils.ip="192.168.0.223:9906";
//        HttpConnectionUtils.ipport= "10.153.153.83:8088";//fcg原IP
//        HttpConnectionUtils.ip="10.153.153.83";
//        HttpConnectionUtils.isNeedSubString=true;
//        HttpConnectionUtils.subNumber=25;

    }
    private void initView() {
        iv_mainview=(ImageView)findViewById(R.id.iv_splash_mainview);
        as=new AnimationSet(false);
        AlphaAnimation aa=new AlphaAnimation(1,1);
        aa.setDuration(2000);
        aa.setFillAfter(true);
        as.addAnimation(aa);
        iv_mainview.startAnimation(as);
    }
    private void initEvent(){
as.setAnimationListener(new Animation.AnimationListener() {
    @Override
    public void onAnimationStart(Animation animation) {

    }
    @Override
    public void onAnimationEnd(Animation animation) {
         String oldDate= FilesUtils.sharedGetFile(SplashActivity.this, "namePw", "date");

        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String newDate = formatter.format(curDate);
        if (oldDate.length()>0) {
         boolean  isBig=   isDateOneBigger(newDate, oldDate);
            if (isBig==true){
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }else {
                String userResult=FilesUtils.sharedGetFile(SplashActivity.this,"namePw","result");
                LoginJson loginJson = ResulutionJson.get_loginJson(userResult);
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("loginJson", loginJson);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        }else {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
//if (result.equals("")) {
//    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//    startActivity(intent);
//    finish();
//}else {
//    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//    startActivity(intent);
//    finish();
//}
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
});
    }

    public  boolean isDateOneBigger(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long  between = dt1.getTime() - dt2.getTime();
       // if (between >= (24* 3600000)) {
        if(between>=(12* 3600000))
        {
            isBigger = true;
        } else  {
            isBigger = false;
        }
        return isBigger;
    }


}
