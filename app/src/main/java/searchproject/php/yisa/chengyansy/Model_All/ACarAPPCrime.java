package searchproject.php.yisa.chengyansy.Model_All;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/7 0007.
 */

public class ACarAPPCrime implements Serializable{
    private static final long serialVersionUID = 1336262691538551404L;
    private String time;
    private String place;
    private String price;
    private String manager;

    private String numb;
    private String type;

    public String getNumb() {
        return numb;
    }

    public void setNumb(String numb) {
        this.numb = numb;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
}
