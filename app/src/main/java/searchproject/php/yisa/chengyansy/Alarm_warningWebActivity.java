package searchproject.php.yisa.chengyansy;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import searchproject.php.yisa.chengyansy.Model_All.Location_id;
import searchproject.php.yisa.chengyansy.utils.HttpConnectionUtils;

public class Alarm_warningWebActivity extends AppCompatActivity {
    private WebView displayWebview;
    private LinearLayout return_;
    Toolbar toolbar;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_warning_web);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");//设置Toolbar标题
        textView=(TextView)findViewById(R.id.toolbar_title);
        textView.setText("选择预警点位");
        return_=(LinearLayout)findViewById(R.id.img_return);
        return_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
      //  initView();
        initAction();
    }
    @SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
    private void initAction(){
        displayWebview=(WebView)findViewById(R.id.AlarmwebView);
        displayWebview.getSettings().setJavaScriptEnabled(true);
        displayWebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        displayWebview.getSettings().setSupportMultipleWindows(true);
        displayWebview.setWebViewClient(new WebViewClient());
        displayWebview.setWebChromeClient(new WebChromeClient());
        displayWebview. getSettings(). setDomStorageEnabled(true);
        displayWebview.addJavascriptInterface(new JavaScriptinterface(this),
                "android");
        String Ln="";
        String name=getLngAndLatWithNetwork();
        if (name.length()>10){
            Ln=name;
        }else {
           Ln=HttpConnectionUtils.Ln;
          //  Ln="118.73,35.8";
            //Ln=119.1558545600,36.7063965800//
        }
//        Toast.makeText(Alarm_warningWebActivity.this,HttpConnectionUtils.ipport,Toast.LENGTH_SHORT).show();
            displayWebview.loadUrl("file:///android_asset/tmap.html?host="+ HttpConnectionUtils.ipport+"&center="+Ln);
      // displayWebview.loadUrl("file:///android_asset/tmap.html?host="+ip+"&center="+Ln);
    }
//        Context context;
//        public JavaScriptinterface(Context c) {
//            context= c;
//        }

        /**
         * 与js交互时用到的方法，在js里直接调用的
         */

    public class JavaScriptinterface {
        Context context;
        public JavaScriptinterface(Context c) {
            context= c;
        }

        /**
         * 与js交互时用到的方法，在js里直接调用的
         */
        @JavascriptInterface
        public void showToast() {
            mOnItemClickLitener.ShowLoadingMethod();
         //   Toast.makeText(context, "跳转", Toast.LENGTH_SHORT).show();
finish();
        }
            @JavascriptInterface
            public void ClearAll(){
              //  Toast.makeText(Alarm_warningWebActivity.this,"Alarm_warningWebActivity",Toast.LENGTH_SHORT).show();
                HashMap hashMap=new HashMap();
                hashMap.put("location_id","");
                //    Toast.makeText(MainActivity.this,loginJson.getUid()+"--")
                FilesUtils.sharedToFile(Alarm_warningWebActivity.this,"location",hashMap);
                mOnItemClickLitener.ShowNoOptionLocation();

            }
            public void showNodata(){

            }
        @JavascriptInterface
        public void localStorage(String json) {

//            if (json.length() > 0) {
        //    Toast.makeText(Alarm_warningWebActivity.this,"1",Toast.LENGTH_SHORT).show();

//               ZhoubianyujingFragment. alarm_warning_loading.setVisibility(View.VISIBLE);
//                alarm_nodate.setVisibility(View.GONE);
               // Toast.makeText(context, json + "--", Toast.LENGTH_LONG).show();
                // List<Location_id> list=Location_id(json);
                String location_idString = Location_idString(json);
         //     Toast.makeText(Alarm_warningWebActivity.this, "location" + location_idString, Toast.LENGTH_SHORT).show();
            //    Intent intent = new Intent(Alarm_warningWebActivity.this, MyIntentService.class);
                Bundle bundle = new Bundle();
                bundle.putString("location_id", location_idString);
            HashMap hashMap=new HashMap();
            hashMap.put("location_id",location_idString);
            //    Toast.makeText(MainActivity.this,loginJson.getUid()+"--")
            FilesUtils.sharedToFile(Alarm_warningWebActivity.this,"location",hashMap);

            if (MyIntentService.mSocket==null){
                Intent intent = new Intent(Alarm_warningWebActivity.this, MyIntentService.class);
                intent.putExtras(bundle);
                startService(intent);
            }else {
                MyIntentService.mSocket.emit("login",location_idString);
            }
//
//                intent.putExtras(bundle);
//                startService(intent);


            }
    //    }
    }

    List  Location_id (String json){
        try {
            List idList=new ArrayList();
            JSONArray jsonArray=new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                Location_id location_id =new Location_id();
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                location_id.setId(jsonObject.getString("id"));
                location_id.setId(jsonObject.getString("text"));
                idList.add(location_id);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    String  Location_idString (String json){
        String location_idString="";
        try {

            JSONArray jsonArray=new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
            //    Location_id location_id =new Location_id();
                JSONObject jsonObject=jsonArray.getJSONObject(i);
//                location_id.setId(jsonObject.getString("id"));
//                location_id.setId(jsonObject.getString("text"));
            String         location_id=jsonObject.getString("id");
                location_idString=location_idString+location_id+",";
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return location_idString;


    }

    public static Alarm_warningWebActivity.ShowLoading mOnItemClickLitener;

    public static void setOnItemClickLitener(Alarm_warningWebActivity.ShowLoading mOnItemClickLitener1)
    {
        mOnItemClickLitener = mOnItemClickLitener1;
    }
 public static     interface ShowLoading {
        void ShowLoadingMethod();
     void ShowNoOptionLocation();
    }

    //从网络获取经纬度
    public String getLngAndLatWithNetwork() {
        double latitude = 0.0;
        double longitude = 0.0;
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.e("LanAndLat","null");
            return "";
        }
        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
        }catch (Exception e){

        }


        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            latitude = location.getLatitude();
            Log.e("latitude", String.valueOf(latitude));
            longitude = location.getLongitude();
        }else {
            Log.e("null","null");
        }
        return longitude + "," + latitude;
    }


    LocationListener locationListener = new LocationListener() {

        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {

        }

        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {

        }

        //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
        }
    };



}
