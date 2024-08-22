package com.v2retail.dotvik.modal.grt.cratepick;

import com.google.gson.annotations.SerializedName;

public class ETPickITTabc {
    @SerializedName("SRNO")
    private String lgsrno;
    @SerializedName("MATNR")
    private String lgmatnr;
    @SerializedName("MAKTX")
    private String lgmaktx;
    @SerializedName("MAKTL")
    private String lgmaktl;
    @SerializedName("CRATE")
    private String lgcrate;
    @SerializedName("MEINS")
    private String lgmeins;
    @SerializedName("SCAN_QTY")
    private float lgscanqty;
    @SerializedName("VBELN_VL")
    private String lgvbeln_vl;
    @SerializedName("VBELN_VA")
    private float lgvbeln_va;
    @SerializedName("SMMMG")
    private String lgsmmmg;
    @SerializedName("VLPLA")
    private String lgvlpla;
    @SerializedName("POSNR")
    private int lgposnr;
    @SerializedName("GNATURE")
    private String lggnature;

    public String getLgsrno() {
        return lgsrno;
    }

    public void setLgsrno(String lgsrno) {
        this.lgsrno = lgsrno;
    }

    public String getLgmatnr() {
        return lgmatnr;
    }

    public void setLgmatnr(String lgmatnr) {
        this.lgmatnr = lgmatnr;
    }

    public String getLgmaktx() {
        return lgmaktx;
    }

    public void setLgmaktx(String lgmaktx) {
        this.lgmaktx = lgmaktx;
    }

    public String getLgmaktl() {
        return lgmaktl;
    }

    public void setLgmaktl(String lgmaktl) {
        this.lgmaktl = lgmaktl;
    }

    public String getLgcrate() {
        return lgcrate;
    }

    public void setLgcrate(String lgcrate) {
        this.lgcrate = lgcrate;
    }

    public String getLgmeins() {
        return lgmeins;
    }

    public void setLgmeins(String lgmeins) {
        this.lgmeins = lgmeins;
    }

    public float getLgscanqty() {
        return lgscanqty;
    }

    public void setLgscanqty(float lgscanqty) {
        this.lgscanqty = lgscanqty;
    }

    public String getLgvbeln_vl() {
        return lgvbeln_vl;
    }

    public void setLgvbeln_vl(String lgvbeln_vl) {
        this.lgvbeln_vl = lgvbeln_vl;
    }

    public float getLgvbeln_va() {
        return lgvbeln_va;
    }

    public void setLgvbeln_va(float lgvbeln_va) {
        this.lgvbeln_va = lgvbeln_va;
    }

    public String getLgsmmmg() {
        return lgsmmmg;
    }

    public void setLgsmmmg(String lgsmmmg) {
        this.lgsmmmg = lgsmmmg;
    }

    public String getLgvlpla() {
        return lgvlpla;
    }

    public void setLgvlpla(String lgvlpla) {
        this.lgvlpla = lgvlpla;
    }

    public int getLgposnr() {
        return lgposnr;
    }

    public void setLgposnr(int lgposnr) {
        this.lgposnr = lgposnr;
    }

    public String getLggnature() {
        return lggnature;
    }

    public void setLggnature(String lggnature) {
        this.lggnature = lggnature;
    }
}
