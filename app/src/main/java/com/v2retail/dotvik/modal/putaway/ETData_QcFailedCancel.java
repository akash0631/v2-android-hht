package com.v2retail.dotvik.modal.putaway;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Keep
public class ETData_QcFailedCancel implements Serializable {

    @SerializedName("LGNUM")
    private String lgnum;
    @SerializedName("LGPLA")
    private String lgpla;
    @SerializedName("MATNR")
    private String matnr;
    @SerializedName("WERKS")
    private String werks;
    @SerializedName("MENGE")
    private String menge;
    @SerializedName("LGTYP")
    private String lgtyp;
    @SerializedName("MAKTX")
    private String maktx;
    @SerializedName("MATERIAL")
    private String material;
    @SerializedName("SCANQTY")
    private String scanqty;


    public String getLgnum() {
        return lgnum;
    }

    public void setLgnum(String lgnum) {
        this.lgnum = lgnum;
    }

    public String getLgpla() {
        return lgpla;
    }

    public void setLgpla(String lgpla) {
        this.lgpla = lgpla;
    }

    public String getLgtyp() {
        return lgtyp;
    }

    public void setLgtyp(String lgtyp) {
        this.lgtyp = lgtyp;
    }

    public String getMaktx() {
        return maktx;
    }

    public void setMaktx(String maktx) {
        this.maktx = maktx;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getMenge() {
        return menge;
    }

    public void setMenge(String menge) {
        this.menge = menge;
    }

    public String getScanqty() {
        return scanqty;
    }

    public void setScanqty(String scanqty) {
        this.scanqty = scanqty;
    }

    public String getWerks() {
        return werks;
    }

    public void setWerks(String werks) {
        this.werks = werks;
    }
}
