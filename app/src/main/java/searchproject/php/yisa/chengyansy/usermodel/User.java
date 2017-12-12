package searchproject.php.yisa.chengyansy.usermodel;

/**
 * Created by Administrator on 2017/7/25 0025.
 */

public class User {
    private String account;
    private String lastIpTime;
    private String lastIp;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getLastIpTime() {
        return lastIpTime;
    }

    public void setLastIpTime(String lastIpTime) {
        this.lastIpTime = lastIpTime;
    }

    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }
}
