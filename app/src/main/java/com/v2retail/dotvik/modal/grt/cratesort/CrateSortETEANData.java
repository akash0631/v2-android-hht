package com.v2retail.dotvik.modal.grt.cratesort;

import com.google.gson.annotations.SerializedName;

public class CrateSortETEANData {
    @SerializedName("MANDT")
    private String lgmandt;
    @SerializedName("MATNR")
    private String lgmatnr;
    @SerializedName("MEINH")
    private String lgmeinh;
    @SerializedName("LFNUM")
    private String lglfnum;
    @SerializedName("EAN11")
    private String lgean11;
    @SerializedName("EANTP")
    private String lgeantp;
    @SerializedName("HPEAN")
    private String lghpean;
    @SerializedName("SGT_CATV")
    private String lgsgtcatv;
    @SerializedName("/STTPEC/SER_GTIN")
    private String lgsttpecsergtin;

    public String getLgmandt() {
        return lgmandt;
    }

    public void setLgmandt(String lgmandt) {
        this.lgmandt = lgmandt;
    }

    public String getLgmatnr() {
        return lgmatnr;
    }

    public void setLgmatnr(String lgmatnr) {
        this.lgmatnr = lgmatnr;
    }

    public String getLgmeinh() {
        return lgmeinh;
    }

    public void setLgmeinh(String lgmeinh) {
        this.lgmeinh = lgmeinh;
    }

    public String getLglfnum() {
        return lglfnum;
    }

    public void setLglfnum(String lglfnum) {
        this.lglfnum = lglfnum;
    }

    public String getLgean11() {
        return lgean11;
    }

    public void setLgean11(String lgean11) {
        this.lgean11 = lgean11;
    }

    public String getLgeantp() {
        return lgeantp;
    }

    public void setLgeantp(String lgeantp) {
        this.lgeantp = lgeantp;
    }

    public String getLghpean() {
        return lghpean;
    }

    public void setLghpean(String lghpean) {
        this.lghpean = lghpean;
    }

    public String getLgsgtcatv() {
        return lgsgtcatv;
    }

    public void setLgsgtcatv(String lgsgtcatv) {
        this.lgsgtcatv = lgsgtcatv;
    }

    public String getLgsttpecsergtin() {
        return lgsttpecsergtin;
    }

    public void setLgsttpecsergtin(String lgsttpecsergtin) {
        this.lgsttpecsergtin = lgsttpecsergtin;
    }
}
