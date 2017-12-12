package searchproject.php.yisa.chengyansy.Model_All;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/1 0001.
 */

public class Car_result_kk implements Serializable {
    private static final long serialVersionUID = -3280361777154440940L;
    String totalRecords;
    String id;
    String text;
    String num;

    public String getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(String totalRecords) {
        this.totalRecords = totalRecords;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
