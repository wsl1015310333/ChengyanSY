package searchproject.php.yisa.chengyansy.presenter;

import android.os.Handler;

import searchproject.php.yisa.chengyansy.usermodel.IUserLogin;
import searchproject.php.yisa.chengyansy.usermodel.OnLoginListener;
import searchproject.php.yisa.chengyansy.usermodel.UserBiz;
import searchproject.php.yisa.chengyansy.userview.IUserLoginView;


/**
 * Created by Administrator on 2017/7/25 0025.
 */

public class UserLoginPresenter {
    private IUserLoginView userLoginView;
    private IUserLogin userLogin;
    private Handler mHandler=new Handler();
    public UserLoginPresenter(IUserLoginView userLoginView){
        this.userLoginView=userLoginView;
        this.userLogin=new UserBiz();
    }
    public void login(){
        userLoginView.showLoading();
        userLogin.login(userLoginView.getAccount(), userLoginView.getpassword(), "10.73.194.252:8088/api/login", new OnLoginListener() {
            @Override
            public void loginSuccess(final String user) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        userLoginView.toMainActivity(user);
                        userLoginView.hideLoading();
                    }
                });
            }

            @Override
            public void loginFailed() {
//需要在UI线程执行
                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        userLoginView.showFailedError();
                        userLoginView.hideLoading();
                    }
                });
            }
        });
    }


}
