package searchproject.php.yisa.chengyansy.Model_All;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/1 0001.
 */

public class Car_result_model implements Serializable {
    private static final long serialVersionUID = 3099244992708047959L;
    String totalRecords;
    String id;
    String text;
    String num;

    public String getYear_id() {
        return year_id;
    }

    public void setYear_id(String year_id) {
        this.year_id = year_id;
    }

    String year_id;

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

    public String getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(String totalRecords) {
        this.totalRecords = totalRecords;
    }
}
