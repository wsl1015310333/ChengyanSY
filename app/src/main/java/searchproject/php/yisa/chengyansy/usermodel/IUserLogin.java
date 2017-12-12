package searchproject.php.yisa.chengyansy.usermodel;

/**
 * Created by Administrator on 2017/7/25 0025.
 */

public interface IUserLogin {
   public void login(String username, String password, String url, OnLoginListener onLoginListener);
}
