package com.v2retail.dotvik.modal.grt.cratepick;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Keep
public class ETPickList  implements Serializable {

    @SerializedName("TANUM")
    private String lgtanum;
    @SerializedName("ERDAT")
    private String lgerdat;

    private String section;
    private boolean ptl;
    private String picktype;

    public String getLgtanum() {
        return lgtanum;
    }

    public void setLgtanum(String lgtanum) {
        this.lgtanum = lgtanum;
    }

    public String getLgerdat() {
        return lgerdat;
    }

    public void setLgerdat(String lgerdat) {
        this.lgerdat = lgerdat;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public boolean isPtl() {
        return ptl;
    }

    public void setPtl(boolean ptl) {
        this.ptl = ptl;
    }

    public String getPicktype() {
        return picktype==null ? "":picktype;
    }

    public void setPicktype(String picktype) {
        this.picktype = picktype;
    }
}
