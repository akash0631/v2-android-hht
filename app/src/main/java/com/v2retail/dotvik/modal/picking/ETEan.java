package com.v2retail.dotvik.modal.picking;

import com.google.gson.annotations.SerializedName;

public class ETEan {
    @SerializedName("UMREN")
    private int lgumren;
    @SerializedName("MEINH")
    private String lgmeinh;
    @SerializedName("EAN11")
    private String lgean11;
    @SerializedName("UMREZ")
    private int lgumrez;
    @SerializedName("MATNR")
    private String lgmatnr;

    public int getLgumren() {
        return lgumren;
    }

    public void setLgumren(int lgumren) {
        this.lgumren = lgumren;
    }

    public String getLgmeinh() {
        return lgmeinh;
    }

    public void setLgmeinh(String lgmeinh) {
        this.lgmeinh = lgmeinh;
    }

    public String getLgean11() {
        return lgean11;
    }

    public void setLgean11(String lgean11) {
        this.lgean11 = lgean11;
    }

    public int getLgumrez() {
        return lgumrez;
    }

    public void setLgumrez(int lgumrez) {
        this.lgumrez = lgumrez;
    }

    public String getLgmatnr() {
        return lgmatnr;
    }

    public void setLgmatnr(String lgmatnr) {
        this.lgmatnr = lgmatnr;
    }
}
