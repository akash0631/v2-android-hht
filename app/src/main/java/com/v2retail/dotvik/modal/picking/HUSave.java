package com.v2retail.dotvik.modal.picking;

import com.google.gson.annotations.SerializedName;

public class HUSave {
    @SerializedName("HU")
    private String lghu;
    @SerializedName("ITEM_NO")
    private String lgitemno;
    @SerializedName("ARTICLE")
    private String lgarticle;
    @SerializedName("PSTNG_DATE")
    private String lgpostingdate;
    @SerializedName("PLANT")
    private String lgplant;
    @SerializedName("STGE_LOC")
    private String lgstorageloc;
    @SerializedName("SCAN_QTY")
    private String lgscanqty;
    @SerializedName("REM_QTY")
    private String lgremqty;
    @SerializedName("BIN")
    private String lgbin;
    @SerializedName("EAN11")
    private String lgean11;
    @SerializedName("UMREZ")
    private String lgumrez;

    public String getLghu() {
        return lghu;
    }

    public void setLghu(String lghu) {
        this.lghu = lghu;
    }

    public String getLgitemno() {
        return lgitemno;
    }

    public void setLgitemno(String lgitemno) {
        this.lgitemno = lgitemno;
    }

    public String getLgarticle() {
        return lgarticle;
    }

    public void setLgarticle(String lgarticle) {
        this.lgarticle = lgarticle;
    }

    public String getLgpostingdate() {
        return lgpostingdate;
    }

    public void setLgpostingdate(String lgpostingdate) {
        this.lgpostingdate = lgpostingdate;
    }

    public String getLgplant() {
        return lgplant;
    }

    public void setLgplant(String lgplant) {
        this.lgplant = lgplant;
    }

    public String getLgstorageloc() {
        return lgstorageloc;
    }

    public void setLgstorageloc(String lgstorageloc) {
        this.lgstorageloc = lgstorageloc;
    }

    public String getLgscanqty() {
        return lgscanqty;
    }

    public void setLgscanqty(String lgscanqty) {
        this.lgscanqty = lgscanqty;
    }

    public String getLgremqty() {
        return lgremqty;
    }

    public void setLgremqty(String lgremqty) {
        this.lgremqty = lgremqty;
    }

    public String getLgbin() {
        return lgbin;
    }

    public void setLgbin(String lgbin) {
        this.lgbin = lgbin;
    }

    public String getLgean11() {
        return lgean11;
    }

    public void setLgean11(String lgean11) {
        this.lgean11 = lgean11;
    }

    public String getLgumrez() {
        return lgumrez;
    }

    public void setLgumrez(String lgumrez) {
        this.lgumrez = lgumrez;
    }
}
