package searchproject.php.yisa.chengyansy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import searchproject.php.yisa.chengyansy.Model_All.LoginJson;
import searchproject.php.yisa.chengyansy.presenter.UserLoginPresenter;
import searchproject.php.yisa.chengyansy.userview.IUserLoginView;
import searchproject.php.yisa.chengyansy.utils.HttpConnectionUtils;
import searchproject.php.yisa.chengyansy.utils.ResulutionJson;


public class LoginActivity extends AppCompatActivity implements IUserLoginView {
Handler handler=new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
  switch (msg.what){
      case 200:
          String result=(String)msg.obj;
          toMainActivity(result);
          break;
      case 300:
          break;
  }
    }
};

    private Button  login_imgbtn;

    private EditText login_edith;

    private EditText login_passWord;
    private UserLoginPresenter mUserLoginPresenter=new UserLoginPresenter(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CrashApplication.mylist.add(LoginActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        login_imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // mUserLoginPresenter.login();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String username = getAccount();
                        String password = getpassword();
                        HashMap hashMap = new HashMap();
                        hashMap.put("account", username);
                        hashMap.put("password", password);
                        final String result = HttpConnectionUtils.sendGETRequest(HttpConnectionUtils.ipport+"/api/login", hashMap, null); http://192.168.3.195:3000/api/login
                       // final String result = HttpConnectionUtils.submitPostData("http://"+HttpConnectionUtils.ipport+"/api/login", hashMap,"utf-8");

                        if(result.equals("2")) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "网络异常请重试！", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        Message message = handler.obtainMessage();
                        if (result.equals("1") || result.equals("2")) {

                        } else {
                            SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
                            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                            String str = formatter.format(curDate);
                            hashMap.put("result",result);
                            hashMap.put("date",str);
                            FilesUtils.sharedToFile(LoginActivity.this,"namePw",hashMap);
                            message.what = 200;
                            message.obj=result;
                            handler.sendMessage(message);
                        }
                    }
                }).start();

            }
        });

    }

    private void initView(){
        login_imgbtn=(Button)findViewById(R.id.login_imgbtn);
        login_edith=(EditText)findViewById(R.id.login_edith);
        login_passWord=(EditText)findViewById(R.id.login_passWord);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public String getAccount() {
        return login_edith.getText().toString();
    }

    @Override
    public String getpassword() {
        return login_passWord.getText().toString();
    }

    @Override
    public void clearUserName() {
        login_edith.setText("");
    }

    @Override
    public void clearPassword() {
        login_passWord.setText("");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void toMainActivity(String  userResult) {
      //  Toast.makeText(LoginActivity.this,"user"+userResult,Toast.LENGTH_SHORT).show();
        if (userResult.equals("{\"Responses\":403,\"message\":\"账号或密码错误\"}")){
            Toast.makeText(LoginActivity.this,"账号或密码错误",Toast.LENGTH_SHORT).show();
        }
        else {
            LoginJson loginJson = ResulutionJson.get_loginJson(userResult);
            HashMap hashMap=new HashMap();
            hashMap.put("token",loginJson.getToken());
            hashMap.put("account",loginJson.getAccount());
            FilesUtils.sharedToFile(this,"user",hashMap);
            Bundle bundle = new Bundle();
            bundle.putSerializable("loginJson", loginJson);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void showFailedError() {
     //   Toast.makeText(this,"账号密码失败",Toast.LENGTH_SHORT).show();
    }
}