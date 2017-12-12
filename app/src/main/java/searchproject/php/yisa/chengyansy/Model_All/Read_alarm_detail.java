package searchproject.php.yisa.chengyansy.Model_All;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/2 0002.
 */

public class Read_alarm_detail implements Serializable {
    private String id;
    private String pic;
    private String carModel;
    private String color;
    private String plate;
    private String captureTime;
    private String locationName;
    private String reason;
    private String speed;
    private String directionName;
    private String appplyUser;
    private String appplyPhone;
    private String deploySheetID;
    private String deployType;
    private String carInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getCaptureTime() {
        return captureTime;
    }

    public void setCaptureTime(String captureTime) {
        this.captureTime = captureTime;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getDirectionName() {
        return directionName;
    }

    public void setDirectionName(String directionName) {
        this.directionName = directionName;
    }

    public String getAppplyUser() {
        return appplyUser;
    }

    public void setAppplyUser(String appplyUser) {
        this.appplyUser = appplyUser;
    }

    public String getAppplyPhone() {
        return appplyPhone;
    }

    public void setAppplyPhone(String appplyPhone) {
        this.appplyPhone = appplyPhone;
    }

    public String getDeploySheetID() {
        return deploySheetID;
    }

    public void setDeploySheetID(String deploySheetID) {
        this.deploySheetID = deploySheetID;
    }

    public String getDeployType() {
        return deployType;
    }

    public void setDeployType(String deployType) {
        this.deployType = deployType;
    }

    public String getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(String carInfo) {
        this.carInfo = carInfo;
    }
}
