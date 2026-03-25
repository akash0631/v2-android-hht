package com.v2retail.dotvik.modal.grt.cratepick;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ETPickData implements Serializable {

    @SerializedName("VBELN")
    private String lgvbeln;
    @SerializedName("MATNR")
    private String lgmatnr;
    @SerializedName("VGBEL")
    private String lgvgbel;
    @SerializedName("LFIMG")
    private float lglfimg;
    @SerializedName("MEINS")
    private String lgmeins;
    @SerializedName("SAMMG")
    private String lgsammg;
    @SerializedName("MATERIAL")
    private String lgmaterial;
    @SerializedName("POSNR")
    private String lgposnr;
    @SerializedName("GNATURE")
    private String lggnature;
    @SerializedName("BIN")
    private String lgbin;
    @SerializedName("CRATE")
    private String lgcrate;
    @SerializedName("MC_DESCR")
    private String lgmc_descr;
    @SerializedName("URL")
    private String lgurl;
    @SerializedName("SORT")
    private String sort;

    public String getLgvbeln() {
        return lgvbeln;
    }

    public void setLgvbeln(String lgvbeln) {
        this.lgvbeln = lgvbeln;
    }

    public String getLgmatnr() {
        return lgmatnr;
    }

    public void setLgmatnr(String lgmatnr) {
        this.lgmatnr = lgmatnr;
    }

    public String getLgvgbel() {
        return lgvgbel;
    }

    public void setLgvgbel(String lgvgbel) {
        this.lgvgbel = lgvgbel;
    }

    public String getLgmeins() {
        return lgmeins;
    }

    public void setLgmeins(String lgmeins) {
        this.lgmeins = lgmeins;
    }

    public String getLgsammg() {
        return lgsammg;
    }

    public void setLgsammg(String lgsammg) {
        this.lgsammg = lgsammg;
    }

    public String getLgmaterial() {
        return lgmaterial;
    }

    public void setLgmaterial(String lgmaterial) {
        this.lgmaterial = lgmaterial;
    }

    public String getLgposnr() {
        return lgposnr;
    }

    public void setLgposnr(String lgposnr) {
        this.lgposnr = lgposnr;
    }

    public String getLggnature() {
        return lggnature;
    }

    public void setLggnature(String lggnature) {
        this.lggnature = lggnature;
    }

    public String getLgbin() {
        return lgbin;
    }

    public void setLgbin(String lgbin) {
        this.lgbin = lgbin;
    }

    public String getLgcrate() {
        return lgcrate;
    }

    public void setLgcrate(String lgcrate) {
        this.lgcrate = lgcrate;
    }

    public String getLgmc_descr() {
        return lgmc_descr;
    }

    public void setLgmc_descr(String lgmc_descr) {
        this.lgmc_descr = lgmc_descr;
    }

    public String getLgurl() {
        return lgurl;
    }

    public void setLgurl(String lgurl) {
        this.lgurl = lgurl;
    }

    public float getLglfimg() {
        return lglfimg;
    }

    public void setLglfimg(float lglfimg) {
        this.lglfimg = lglfimg;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
