package searchproject.php.yisa.chengyansy;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import searchproject.php.yisa.chengyansy.Model_All.LoginJson;
import searchproject.php.yisa.chengyansy.factory.FragmentFactory;
import searchproject.php.yisa.chengyansy.utils.AppUpdateUtils;
import searchproject.php.yisa.chengyansy.utils.AppVersion;
import searchproject.php.yisa.chengyansy.utils.HttpConnectionUtils;
import searchproject.php.yisa.chengyansy.utils.ResulutionJson;


public class MainActivity extends AppCompatActivity {

    private final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case  300:

                    String userResult= (String) msg.obj;
                    Log.e("result",userResult);
                    LoginJson loginJson = ResulutionJson.get_loginJson(userResult);
                    try {
                        JSONObject jsonObject=new JSONObject(userResult);

                    //    Toast.makeText(MainActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                        if(jsonObject.getString("first").equals("1")){
                            AlertDialog  builder=     new AlertDialog.Builder(MainActivity.this).setTitle("系统提示")//设置对话框标题

                                    .setMessage("该账号已绑定此设备,解除绑定请联系管理员")//设置显示的内容

                                    .setPositiveButton("我知道了",new DialogInterface.OnClickListener() {//添加确定按钮



                                        @Override

                                        public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

                                            // TODO Auto-generated method stub

                                        dialog.dismiss();

                                        }

                                    }).show();//在按键响应事件中显示此对话框
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    HashMap hashMap=new HashMap();
                    hashMap.put("token",loginJson.getToken());
                    hashMap.put("account",loginJson.getAccount());
                    hashMap.put("uid",loginJson.getUid());
                //    Toast.makeText(MainActivity.this,loginJson.getUid()+"--")
                    FilesUtils.sharedToFile(MainActivity.this,"user",hashMap);
                    HashMap isLog=new HashMap();
                    isLog.put("result","true");
                    FilesUtils.sharedToFile(MainActivity.this,"isLogin",hashMap);
                    initLeftView(loginJson);

            }
        }
    };
    private Toolbar toolbar;
    private ViewPager pager;
    private MyPagerAdapter adapter;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private String[] mTitles = new String[] { "查 车", "周 边 预 警", "布 控 告 警","查 人" };
    private List<Button> mTabs = new ArrayList<Button>();
    private int currentColor = 0xFF666666;
    private ImageView user_toolbar;
    android.support.v7.app.ActionBar actionBar;
    private Button colorTrackView0;
    private Button colorTrackView1;
    private Button colorTrackView2;
 //   private Button colorTrackView3;


      //左菜单
        private LinearLayout drawlayout_quit;
        private TextView drawlayout_lastip;
        private TextView drawlayout_lasttime;
        private TextView drawlayout_name;
      //标题
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CrashApplication.mylist.add(MainActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawerlayout);

//        HttpConnectionUtils.ipport= "10.73.194.252:8088"; //WH
//        HttpConnectionUtils.ip="10.73.194.252";
//        HttpConnectionUtils.ipport= "10.153.153.83:8088";//fcg原IP
//        HttpConnectionUtils.ip="10.153.153.83";
//在login初始化HttpUti
        //toolbar 初始化
        toolbar = (Toolbar) findViewById(R.id.tl_custom);
        toolbar.setTitle("");//设置Toolbar标题
//        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        title=(TextView)findViewById(R.id.title);
        title.setText("查 车");
        setSupportActionBar(toolbar);

      // initHttp();
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        LoginJson loginJson=null;
        initViews();
        initData();
        if(intent==null){

        }else {
            try {
                if(bundle!=null) {
                    //  loginJson = ResulutionJson.get_loginJson(initJson());
                    if (bundle.containsKey("loginJson")) {
                        loginJson = (LoginJson) bundle.getSerializable("loginJson");
                        verifyStoragePermissions(this, loginJson);
                    }
                }
                }catch (Exception e){
                e.printStackTrace();
            }
        }
      if (loginJson!=null) {
          initLeftView(loginJson);
      }
    }

    private void initHttp(){
new Thread(new Runnable() {
    @Override
    public void run() {
       HashMap hashMap=new HashMap();
//       Map userMap= UserInfo.getUserInfo(MainActivity.this);
        String idcard = null;
//        if (userMap != null) {
//            idcard = (String) userMap.get("extra_1");
//
//
//       }

        HashMap hashLogin=new HashMap();

        String id="1";
        getImei();
        Log.e("imei",imei);
      // hashLogin.put("sid","370600000000000216");
       //hashLogin.put("sid","370284199212064568");
       // hashLogin.put("sid",idcard);
      //  hashLogin.put("imei",idcard);
        hashLogin.put("imei",imei);

       final String result=   HttpConnectionUtils.sendGETRequest(HttpConnectionUtils.ipport+"/api/login",hashLogin,null);
     //   final String result=   initJson();
//if(result.equals("2")){
//    handler.post(new Runnable() {
//        @Override
//        public void run() {
//            //    Toast.makeText(MainActivity.this,"该用户已经绑定其他设备",Toast.LENGTH_SHORT).show();
//            showDialog("网络异常请重试!");
//        }
//    });
//}
        String response="";
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(MainActivity.this,""+result,Toast.LENGTH_SHORT).show();
//            }
//        });
        if(result.length()<70){
            try {
                JSONObject jsonObject=new JSONObject(result);
                response=jsonObject.getString("Responses");
                if (response.equals("403")){
                   handler.post(new Runnable() {
                       @Override
                       public void run() {
                      //    Toast.makeText(MainActivity.this,"该用户已经绑定其他设备",Toast.LENGTH_SHORT).show();
                           showDialog("该账号已经绑定其他设备,请联系管理员修改。");
                       }
                   });

                }
                if (response.equals("401")){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            showDialog("该用户不存在");
                         //   Toast.makeText(MainActivity.this,"用户不存在",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if (response.equals("402")){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            showDialog("账号无权限,请联系管理员!");
                          //  Toast.makeText(MainActivity.this,"用户未开通权限",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            Message message =handler.obtainMessage();
            message.what=300;
            message.obj=result;
            handler.sendMessage(message);
        }

    }
}).start();

    }
    private void initLeftView(final LoginJson loginJson){
        drawlayout_quit=(LinearLayout)findViewById(R.id.drawlayout_quit);
        drawlayout_lastip=(TextView)findViewById(R.id.drawlayout_lastip);
        drawlayout_lasttime=(TextView)findViewById(R.id.drawlayout_lasttime);
        drawlayout_name=(TextView)findViewById(R.id.drawlayout_name);
        drawlayout_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("1","1");
//             Intent intent=new Intent(MainActivity.this,LoginActivity.class);
//                startActivity(intent);
//                final HashMap hashMap=new HashMap();
//                String account=FilesUtils.sharedGetFile(MainActivity.this,"user","account");
//                HashMap hashLogin=new HashMap();
//                hashLogin.put("sid","370600000000000216");
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        HttpConnectionUtils.sendGETRequest(HttpConnectionUtils.ipport+"/api/logout",hashMap,null);
//                    }
//                }).start();
                HashMap isLog=new HashMap();
                isLog.put("date","");
                FilesUtils.sharedToFile(MainActivity.this,"namePw",isLog);
             //   Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                if(MyIntentService.mSocket!=null){
                    MyIntentService.mSocket.disconnect();
                }
                if(AlarmIntentService.warinSocket!=null)
                {
                    AlarmIntentService.warinSocket.disconnect();
                }
//              if(BukongyujingFragment.count!=0){
//                  BukongyujingFragment.count=0;
//                  BukongyujingFragment.bkyj_unread.toggleExpand();
//              }
                //startActivity(intent);

//               Intent i = getBaseContext().getPackageManager()
//                       .getLaunchIntentForPackage(getBaseContext().getPackageName());
//               i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//               startActivity(i);
                getSupportFragmentManager().popBackStackImmediate(MyPagerAdapter.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);

                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
              //  System.exit(0);
//                Intent k = getBaseContext().getPackageManager()
//                        .getLaunchIntentForPackage("com.android.nfc");
//                k.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                getBaseContext().startActivity(k);
            }
        });
             drawlayout_lasttime.setText(loginJson.getLastTime());
             drawlayout_name.setText(loginJson.getAccount());
    }
    private void initData(){
        user_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -1, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
             for(int i=0;i<mTabs.size();i++){
                 if (i==position){
                     mTabs.get(i).setTextColor(getResources().getColor(R.color.btn_yes));
                 }else {
                     mTabs.get(i).setTextColor(getResources().getColor(R.color.Tabs_color_black));
                 }
             }
                title.setText(mTitles[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void initViews()
    {

        user_toolbar=(ImageView)findViewById(R.id.user_toolbar);
        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawlayout);
        pager.setOffscreenPageLimit(2);
        pager.setAdapter(adapter);

        colorTrackView0=(Button)findViewById(R.id.id_tab_01);
        colorTrackView1=(Button)findViewById(R.id.id_tab_02);
        colorTrackView2=(Button)findViewById(R.id.id_tab_03);
    //    colorTrackView3=(Button)findViewById(R.id.id_tab_04);

        colorTrackView0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(0);

            }
        });
        colorTrackView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(1);

            }
        });
        colorTrackView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(2);
            }
        });
//        colorTrackView3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pager.setCurrentItem(3);
//            }
//        });

        mTabs.add(colorTrackView0);
        mTabs.add(colorTrackView1);
        mTabs.add(colorTrackView2);
      //  mTabs.add(colorTrackView3);
//		mTabs.add((ColorTrackView) findViewById(R.id.id_tab_01));
//		mTabs.add((ColorTrackView) findViewById(R.id.id_tab_02));
//		mTabs.add((ColorTrackView) findViewById(R.id.id_tab_03));
//		mTabs.add((ColorTrackView) findViewById(R.id.id_tab_04));
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentColor", currentColor);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentColor = savedInstanceState.getInt("currentColor");
        //changeColor(currentColor);
    }



    public class MyPagerAdapter extends FragmentStatePagerAdapter {

        private final String[] TITLES = { "查 车",  "周 边 预 警", "布 控 告 警" };

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
           // title.setText(TITLES[position]);
            return FragmentFactory.getRragment(position);
            //return SuperAwesomeCardFragment.newInstance(position);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==300||resultCode==300){
            Toast.makeText(MainActivity.this,"300",Toast.LENGTH_SHORT).show();
        }
    }
    String initJson(){

String result="{\n" +
        "  \"user\": {\n" +
        "    \"account\": \"test\",\n" +
        "    \"uid\": 17,\n" +
        "    \"lastTime\": \"2017-08-16 15:34:44\",\n" +
        "    \"realName\": \"test\"\n" +
        "  },\n" +
        "  \"token\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC8xMC41MC4yMzUuMzc6ODA4OCIsImF1ZCI6Imh0dHA6XC9cLzEwLjUwLjIzNS4zNzo4MDg4IiwiaWF0IjoxMzU2OTk5NTI0LCJuYmYiOjEzNTcwMDAwMDAsInNpZCI6IjM3MDI4NDE5OTIxMjA2NDU2OCIsImltZWkiOiIxMjM0NTYifQ.8ZhWPPiS0PbEOa-t8K9M68FUExqOMiZIKcOuAsOr4gA\",\n" +
        "  \"message\": \"首次绑定\"\n" +
        "}\n";
        return  result;
    }

    String imei="111112222233333";
        public void getImei(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            toast("需要动态获取权限");
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 10000);
        }else{
//            toast("不需要动态获取权限");
            TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
            imei = TelephonyMgr.getDeviceId();
        }
//        TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
//        String imeiCode =null;
//        try {
//            imeiCode=manager.getDeviceId();
//        }catch(Exception e){
//            imeiCode=getMacAddress(context);
//        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            moveTaskToBack(false);
            return true;

        }
        return super.onKeyDown(keyCode, event);
    }
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    public  void verifyStoragePermissions(Activity activity,LoginJson loginJson) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }else {
              //  Log.e("login",""+loginJson.getUrl()+"--"+loginJson.getContent());
                AppVersion av = new AppVersion();
                av.setApkName("AppUpdate"+loginJson.getVersion()+".apk");
                av.setSha1("FCDA0D0E1E7D620A75DA02A131E2FFEDC1742AC8");
                av.setAppName("神眼搜车");
                //   av.setUrl("https://www.baidu.com/link?url=meiCYcEu7nP4ta1Fn4I-Qcd9KhwtoVFmssuy9qiUjen3nUeaqdcWed_7xJ6VKCS30AslbwDrQSAHUI_eVdGi7fjBnNqV77zd77TSMXa4c-y&wd=&eqid=f257061a0002441f0000000259cd1928");
           //    Toast.makeText(activity,""+loginJson.getUrl(),Toast.LENGTH_SHORT).show();
                av.setUrl(loginJson.getUrl());
                // av.setContent("1、测试升级;2、测试升级2！！;3、一大波功能");
                av.setContent(loginJson.getContent());
                av.setVerCode(1);
                av.setVersionName(loginJson.getVersion());
                AppUpdateUtils.init(activity, av, true,false);
                AppUpdateUtils.upDate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**监听对话框里面的button点击事件*/
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
    {
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                    finish();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                    break;
                default:
                    break;
            }
        }
    };
    private void showDialog(String msg){

        AlertDialog  builder=     new AlertDialog.Builder(MainActivity.this).setTitle("系统提示")//设置对话框标题

                .setMessage(msg)//设置显示的内容

                .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮



                    @Override

                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

                        // TODO Auto-generated method stub

                        finish();

                    }

                }).show();//在按键响应事件中显示此对话框

        builder.setCancelable(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10000 && grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
            imei = TelephonyMgr.getDeviceId();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if(intent!=null){

        if(intent.getStringExtra("message")!=null&&!intent.getStringExtra("message").equals("")){

        if(intent.getStringExtra("message").equals("true")){
            pager.setCurrentItem(1);
        }else {
            pager.setCurrentItem(2);
        }
    }
        }

      //  pager.setCurrentItem(1);
    }
}
