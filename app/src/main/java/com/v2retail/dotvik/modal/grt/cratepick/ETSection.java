package com.v2retail.dotvik.modal.grt.cratepick;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Keep
public class ETSection  implements Serializable {

    @SerializedName("MANDT")
    private String lgmandt;
    @SerializedName("LGNUM")
    private String lglgnum;
    @SerializedName("ZSECTION")
    private String lgzsection;
    @SerializedName("MERGE")
    private String lgmerge;
    @SerializedName("BIN_MERGE")
    private String lgbinmerge;

    public String getLgmandt() {
        return lgmandt;
    }

    public void setLgmandt(String lgmandt) {
        this.lgmandt = lgmandt;
    }

    public String getLglgnum() {
        return lglgnum;
    }

    public void setLglgnum(String lglgnum) {
        this.lglgnum = lglgnum;
    }

    public String getLgzsection() {
        return lgzsection;
    }

    public void setLgzsection(String lgzsection) {
        this.lgzsection = lgzsection;
    }

    public String getLgmerge() {
        return lgmerge;
    }

    public void setLgmerge(String lgmerge) {
        this.lgmerge = lgmerge;
    }

    public String getLgbinmerge() {
        return lgbinmerge;
    }

    public void setLgbinmerge(String lgbinmerge) {
        this.lgbinmerge = lgbinmerge;
    }
}
