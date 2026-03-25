package com.v2retail.dotvik.modal;

import com.google.gson.annotations.SerializedName;

public class DiscountArticle {
    @SerializedName("MANDT")
    private String mandt;
    @SerializedName("WERKS")
    private String werks;
    @SerializedName("MATNR")
    private String matnr;
    @SerializedName("EAN11")
    private String ean11;
    @SerializedName("UDATE")
    private String udate;
    @SerializedName("ZFLAG")
    private String zflag;
    @SerializedName("ERNAME")
    private String ername;
    @SerializedName("ERDAT")
    private String erdat;
    @SerializedName("DISPER")
    private String disper;
    @SerializedName("DISPER1")
    private String disper1;

    public String getDisper1() {
        return disper1;
    }

    public void setDisper1(String disper1) {
        this.disper1 = disper1;
    }

    public static DiscountArticle newInstance(DiscountArticle source) {
        if (source == null) {
            return null;
        }

        DiscountArticle target = new DiscountArticle();

        target.setMandt(source.getMandt());
        target.setWerks(source.getWerks());
        target.setMatnr(source.getMatnr());
        target.setEan11(source.getEan11());
        target.setUdate(source.getUdate());
        target.setZflag(source.getZflag());
        target.setErname(source.getErname());
        target.setErdat(source.getErdat());
        target.setDisper(source.getDisper());
        target.setDisper1(source.getDisper1());

        return target;
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

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getEan11() {
        return ean11;
    }

    public void setEan11(String ean11) {
        this.ean11 = ean11;
    }

    public String getUdate() {
        return udate;
    }

    public void setUdate(String udate) {
        this.udate = udate;
    }

    public String getZflag() {
        return zflag;
    }

    public void setZflag(String zflag) {
        this.zflag = zflag;
    }

    public String getErname() {
        return ername;
    }

    public void setErname(String ername) {
        this.ername = ername;
    }

    public String getErdat() {
        return erdat;
    }

    public void setErdat(String erdat) {
        this.erdat = erdat;
    }

    public String getDisper() {
        return disper;
    }

    public void setDisper(String disper) {
        this.disper = disper;
    }
}
