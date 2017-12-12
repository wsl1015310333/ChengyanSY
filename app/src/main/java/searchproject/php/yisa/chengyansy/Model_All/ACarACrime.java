package searchproject.php.yisa.chengyansy.Model_All;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/7 0007.
 */

public class ACarACrime implements Serializable {
    private static final long serialVersionUID = 9023822401258342641L;
    private String isFugitive;
    private  String isDug;
    private String iscrimeRecord;
    private String isfocal;
    private String isInvolved;
    private String name;
    private String tel;
    private String type;
    private String numCard;

    public String getNumCard() {
        return numCard;
    }

    public void setNumCard(String numCard) {
        this.numCard = numCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsFugitive() {
        return isFugitive;
    }

    public void setIsFugitive(String isFugitive) {
        this.isFugitive = isFugitive;
    }

    public String getIsDug() {
        return isDug;
    }

    public void setIsDug(String isDug) {
        this.isDug = isDug;
    }

    public String getIscrimeRecord() {
        return iscrimeRecord;
    }

    public void setIscrimeRecord(String iscrimeRecord) {
        this.iscrimeRecord = iscrimeRecord;
    }

    public String getIsfocal() {
        return isfocal;
    }

    public void setIsfocal(String isfocal) {
        this.isfocal = isfocal;
    }

    public String getIsInvolved() {
        return isInvolved;
    }

    public void setIsInvolved(String isInvolved) {
        this.isInvolved = isInvolved;
    }
}
