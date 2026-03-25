package com.v2retail.dotvik.modal.putaway;

import com.google.gson.annotations.SerializedName;
import com.v2retail.dotvik.modal.DiscountArticle;

public class DiscountArticleScan {
    @SerializedName("MANDT")
    private String mandt;
    @SerializedName("WERKS")
    private String werks;
    @SerializedName("EAN11")
    private String ean11;
    @SerializedName("SDATE")
    private String sdate;
    @SerializedName("STIME")
    private String stime;
    @SerializedName("MATNR")
    private String matnr;
    @SerializedName("SQNTY")
    private String sqnty;
    @SerializedName("ERNAME")
    private String ername;
    @SerializedName("HHT_USER")
    private String hhtuser;
    @SerializedName("STK_TRF")
    private String stktrf;
    @SerializedName("DISPER")
    private String disper;
    @SerializedName("DISPER1")
    private String disper1;

    public DiscountArticleScan newInstance(DiscountArticleScan source){
        if (source == null) {
            return null;
        }

        DiscountArticleScan target = new DiscountArticleScan();

        target.setMandt(source.getMandt());
        target.setWerks(source.getWerks());
        target.setEan11(source.getEan11());
        target.setSdate(source.getSdate());
        target.setStime(source.getStime());
        target.setMatnr(source.getMatnr());
        target.setSqnty(source.getSqnty());
        target.setErname(source.getErname());
        target.setHhtuser(source.getHhtuser());
        target.setStktrf(source.getStktrf());
        target.setDisper(source.getDisper());
        target.setDisper1(source.getDisper1());
        return target;
    }

    public String getDisper1() {
        return disper1;
    }

    public void setDisper1(String disper1) {
        this.disper1 = disper1;
    }

    public String getDisper() {
        return disper;
    }

    public void setDisper(String disper) {
        this.disper = disper;
    }

    public String getMandt() {
        return mandt;
    }

    public void setMandt(String mandt) {
        this.mandt = mandt;
    }

    public String getWerks() {
        return werks;
    }

    public void setWerks(String werks) {
        this.werks = werks;
    }

    public String getEan11() {
        return ean11;
    }

    public void setEan11(String ean11) {
        this.ean11 = ean11;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getSqnty() {
        return sqnty;
    }

    public void setSqnty(String sqnty) {
        this.sqnty = sqnty;
    }

    public String getErname() {
        return ername;
    }

    public void setErname(String ername) {
        this.ername = ername;
    }

    public String getHhtuser() {
        return hhtuser;
    }

    public void setHhtuser(String hhtuser) {
        this.hhtuser = hhtuser;
    }

    public String getStktrf() {
        return stktrf;
    }

    public void setStktrf(String stktrf) {
        this.stktrf = stktrf;
    }
}
