package gof.scut.cwh.models.object;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/4/16.
 */
public class LabelObj implements Serializable {
    private String labelName;
    private String iconPath;
    private int memCount;

    public LabelObj(LabelObj labelObj) {
        this.labelName = labelObj.labelName;
        this.iconPath = labelObj.iconPath;
        this.memCount = labelObj.memCount;
    }

    public LabelObj(String labelName, String iconPath, int memCount) {
        this.labelName = labelName;
        this.iconPath = iconPath;
        this.memCount = memCount;
    }

    public LabelObj(String labelName) {

        this.labelName = labelName;
    }

    public void setLabelName(String labelName) {

        this.labelName = labelName;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public void setMemCount(int memCount) {
        this.memCount = memCount;
    }

    public String getLabelName() {

        return labelName;
    }

    public String getIconPath() {
        return iconPath;
    }

    public int getMemCount() {
        return memCount;
    }
}
