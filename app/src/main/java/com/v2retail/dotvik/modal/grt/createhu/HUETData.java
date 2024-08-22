package com.v2retail.dotvik.modal.grt.createhu;

import com.google.gson.annotations.SerializedName;

public class HUETData {
    @SerializedName("LGNUM")
    private String lglgnum;
    @SerializedName("LQNUM")
    private int lglqnum;
    @SerializedName("MATNR")
    private String lgmatnr;
    @SerializedName("WERKS")
    private String lgwerks;
    @SerializedName("LGORT")
    private String lglgort;
    @SerializedName("LGTYP")
    private String lglgtyp;
    @SerializedName("LGPLA")
    private String lglgpla;
    @SerializedName("VERME")
    private double lgverme;

    public String getLglgnum() {
        return lglgnum;
    }

    public void setLglgnum(String lglgnum) {
        this.lglgnum = lglgnum;
    }

    public int getLglqnum() {
        return lglqnum;
    }

    public void setLglqnum(int lglqnum) {
        this.lglqnum = lglqnum;
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

    public String getLglgort() {
        return lglgort;
    }

    public void setLglgort(String lglgort) {
        this.lglgort = lglgort;
    }

    public String getLglgtyp() {
        return lglgtyp;
    }

    public void setLglgtyp(String lglgtyp) {
        this.lglgtyp = lglgtyp;
    }

    public String getLglgpla() {
        return lglgpla;
    }

    public void setLglgpla(String lglgpla) {
        this.lglgpla = lglgpla;
    }

    public double getLgverme() {
        return lgverme;
    }

    public void setLgverme(double lgverme) {
        this.lgverme = lgverme;
    }
}
