package searchproject.php.yisa.chengyansy.Model_All;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/2 0002.
 */

public class Person_Detail implements Serializable {
    String idCard;
    String pic;
    String name;
    String address;
    String isDeplcy;
    String personnel;

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIsDeplcy() {
        return isDeplcy;
    }

    public void setIsDeplcy(String isDeplcy) {
        this.isDeplcy = isDeplcy;
    }

    public String getPersonnel() {
        return personnel;
    }

    public void setPersonnel(String personnel) {
        this.personnel = personnel;
    }
}
