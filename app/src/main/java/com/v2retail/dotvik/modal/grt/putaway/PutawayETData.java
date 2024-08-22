package com.v2retail.dotvik.modal.grt.putaway;

import com.google.gson.annotations.SerializedName;

public class PutawayETData {

    @SerializedName("LGNUM")
    private String lglgnum;
    @SerializedName("LGPLA")
    private int lgpla;
    @SerializedName("MATNR")
    private String lgmatnr;
    @SerializedName("WERKS")
    private String lgwerks;
    @SerializedName("MENGE")
    private String lgmenge;
    @SerializedName("LGTYP")
    private String lglgtyp;
    @SerializedName("MAKTX")
    private String lgmaktx;
    @SerializedName("MATERIAL")
    private double lgmaterial;
    @SerializedName("SCANQTY")
    private double lgscanqty;

    public String getLglgnum() {
        return lglgnum;
    }

    public void setLglgnum(String lglgnum) {
        this.lglgnum = lglgnum;
    }

    public int getLgpla() {
        return lgpla;
    }

    public void setLgpla(int lgpla) {
        this.lgpla = lgpla;
    }

    public String getLgmatnr() {
        return lgmatnr;
    }

    public void setLgmatnr(String lgmatnr) {
        this.lgmatnr = lgmatnr;
    }

    public String getLgwerks() {
        return lgwerks;
    }

    public void setLgwerks(String lgwerks) {
        this.lgwerks = lgwerks;
    }

    public String getLgmenge() {
        return lgmenge;
    }

    public void setLgmenge(String lgmenge) {
        this.lgmenge = lgmenge;
    }

    public String getLglgtyp() {
        return lglgtyp;
    }

    public void setLglgtyp(String lglgtyp) {
        this.lglgtyp = lglgtyp;
    }

    public String getLgmaktx() {
        return lgmaktx;
    }

    public void setLgmaktx(String lgmaktx) {
        this.lgmaktx = lgmaktx;
    }

    public double getLgmaterial() {
        return lgmaterial;
    }

    public void setLgmaterial(double lgmaterial) {
        this.lgmaterial = lgmaterial;
    }

    public double getLgscanqty() {
        return lgscanqty;
    }

    public void setLgscanqty(double lgscanqty) {
        this.lgscanqty = lgscanqty;
    }
}
