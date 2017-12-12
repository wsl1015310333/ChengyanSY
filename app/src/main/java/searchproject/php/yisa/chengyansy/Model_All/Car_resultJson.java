package searchproject.php.yisa.chengyansy.Model_All;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/1 0001.
 */

public class Car_resultJson implements Serializable {

    public static final long serialVersionUID = 7147033502264563813L;
    public static String totalRecords ;
    String plate ;
    String model ;
    String locationName ;
    String captureTime ;
    String pic ;
    String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
//    public String getTotalRecords() {
//        return totalRecords;
//    }
//
//    public void setTotalRecords(String totalRecords) {
//        this.totalRecords = totalRecords;
//    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getCaptureTime() {
        return captureTime;
    }

    public void setCaptureTime(String captureTime) {
        this.captureTime = captureTime;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
