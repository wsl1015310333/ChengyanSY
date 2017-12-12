package searchproject.php.yisa.chengyansy.usermodel;

/**
 * Created by Administrator on 2017/7/25 0025.
 */

public interface OnLoginListener {
    void loginSuccess(String user);

    void loginFailed();
}
