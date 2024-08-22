package com.v2retail.dotvik.modal.picking;

import com.google.gson.annotations.SerializedName;

public class ETFinal {
    @SerializedName("PICNR")
    private String lgpicnr;
    @SerializedName("HU_NO")
    private String lghuno;
    @SerializedName("WERKS")
    private String lgwerks;
    @SerializedName("MATNR")
    private String lgmatnr;
    @SerializedName("HU_QTY")
    private float lghuqty;
    @SerializedName("PICKLIST_QTY")
    private float lgpicklistqty;
    @SerializedName("SCAN_QTY")
    private float lgscanqty;
    @SerializedName("REQ_0008")
    private float lqreq008;
    @SerializedName("BIN_QTY")
    private float lgbinqty;
    @SerializedName("BIN")
    private String lgbin;
    @SerializedName("REM_PICK")
    private float lgrempick;
    @SerializedName("REM_BIN")
    private float lgrembin;

    public String getLgpicnr() {
        return lgpicnr;
    }

    public void setLgpicnr(String lgpicnr) {
        this.lgpicnr = lgpicnr;
    }

    public String getLghuno() {
        return lghuno;
    }

    public void setLghuno(String lghuno) {
        this.lghuno = lghuno;
    }

    public String getLgwerks() {
        return lgwerks;
    }

    public void setLgwerks(String lgwerks) {
        this.lgwerks = lgwerks;
    }

    public String getLgmatnr() {
        return lgmatnr;
    }

    public void setLgmatnr(String lgmatnr) {
        this.lgmatnr = lgmatnr;
    }

    public float getLghuqty() {
        return lghuqty;
    }

    public void setLghuqty(float lghuqty) {
        this.lghuqty = lghuqty;
    }

    public float getLgpicklistqty() {
        return lgpicklistqty;
    }

    public void setLgpicklistqty(float lgpicklistqty) {
        this.lgpicklistqty = lgpicklistqty;
    }

    public float getLgscanqty() {
        return lgscanqty;
    }

    public void setLgscanqty(float lgscanqty) {
        this.lgscanqty = lgscanqty;
    }

    public float getLqreq008() {
        return lqreq008;
    }

    public void setLqreq008(float lqreq008) {
        this.lqreq008 = lqreq008;
    }

    public float getLgbinqty() {
        return lgbinqty;
    }

    public void setLgbinqty(float lgbinqty) {
        this.lgbinqty = lgbinqty;
    }

    public String getLgbin() {
        return lgbin;
    }

    public void setLgbin(String lgbin) {
        this.lgbin = lgbin;
    }

    public float getLgrempick() {
        return lgrempick;
    }

    public void setLgrempick(float lgrempick) {
        this.lgrempick = lgrempick;
    }

    public float getLgrembin() {
        return lgrembin;
    }

    public void setLgrembin(float lgrembin) {
        this.lgrembin = lgrembin;
    }
}
