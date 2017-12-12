package searchproject.php.yisa.chengyansy.userview;

/**
 * Created by Administrator on 2017/7/25 0025.
 */

public interface IUserLoginView {
    String getAccount();

    String getpassword();

    void clearUserName();

    void clearPassword();

    void showLoading();

    void hideLoading();

    void toMainActivity(String user);

    void showFailedError();
}
