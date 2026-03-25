package com.v2retail.dotvik.dc.ptlnew;

import com.google.gson.annotations.SerializedName;

import kotlin.jvm.Transient;

public class ScanData{
    @SerializedName("LGPLA")
    private String bin;
    @SerializedName("CRATE")
    private String crate;
    @SerializedName("MATNR")
    private String matnr;
    @SerializedName("QTY")
    private String qty;
    @SerializedName("WAVE")
    private String wave;
    @SerializedName("TANUM")
    private String picklistno;
    @SerializedName("CRATE1")
    private String crate1;
    @SerializedName("PALATE")
    private String pallet;
    @SerializedName("SCLOSE")
    private String sclose;
    @SerializedName("PNATURE")
    private String pnature;

    public ScanData newInstance(PicklistData etData, String crate, String pallet){
        ScanData data = new ScanData();
        data.setBin(etData.getBin());
        data.setMatnr(etData.getArticle());
        data.setCrate(crate);
        data.setCrate1(etData.getMsaCrate());
        data.setPicklistno(etData.getPicklist());
        data.setWave(etData.getWave());
        data.setQty(etData.getSqty() + "");
        data.setPallet(pallet);
        return data;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getCrate() {
        return crate;
    }

    public void setCrate(String crate) {
        this.crate = crate;
    }

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getWave() {
        return wave;
    }

    public void setWave(String wave) {
        this.wave = wave;
    }

    public String getPicklistno() {
        return picklistno;
    }

    public void setPicklistno(String picklistno) {
        this.picklistno = picklistno;
    }

    public String getCrate1() {
        return crate1;
    }

    public void setCrate1(String crate1) {
        this.crate1 = crate1;
    }

    public String getPallet() {
        return pallet;
    }

    public void setPallet(String pallet) {
        this.pallet = pallet;
    }

    public String getSclose() {
        return sclose;
    }

    public void setSclose(String sclose) {
        this.sclose = sclose;
    }

    public String getPnature() {
        return pnature;
    }

    public void setPnature(String pnature) {
        this.pnature = pnature;
    }
}
