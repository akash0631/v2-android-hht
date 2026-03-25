package com.v2retail.dotvik.dc.ptlnew.withpallate;

import com.google.gson.annotations.SerializedName;

public class ZoneData {


    @SerializedName("CRATE")
    private String crate;

    @SerializedName("ARTICLE")
    private String article;

    @SerializedName("ZONE_CRATE")
    private String zoneCrate;

    @SerializedName("QTY")
    private String qty;

    @SerializedName("SCRATE")
    private String sCrate;

    @SerializedName("DCRATE")
    private String dCrate;

    @SerializedName("ZONE_COUNT")
    private int zoneCount;

    @SerializedName("SHORT_CLOSE")
    private String shortClose;

    @SerializedName("SCAN_QTY")
    private String scanQty;

    @SerializedName("PLT_REC_HUBZONE")
    private String pltRecHubZone;

    // Getters and Setters
    public String getCrate() {
        return crate;
    }

    public void setCrate(String crate) {
        this.crate = crate;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getZoneCrate() {
        return zoneCrate;
    }

    public void setZoneCrate(String zoneCrate) {
        this.zoneCrate = zoneCrate;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getsCrate() {
        return sCrate;
    }

    public void setsCrate(String sCrate) {
        this.sCrate = sCrate;
    }

    public String getdCrate() {
        return dCrate;
    }

    public void setdCrate(String dCrate) {
        this.dCrate = dCrate;
    }

    public int getZoneCount() {
        return zoneCount;
    }

    public void setZoneCount(int zoneCount) {
        this.zoneCount = zoneCount;
    }

    public String isShortClose() {
        return shortClose;
    }

    public void setShortClose(String shortClose) {
        this.shortClose = shortClose;
    }

    public String getScanQty() {
        return scanQty;
    }

    public void setScanQty(String scanQty) {
        this.scanQty = scanQty;
    }

    public String getPltRecHubZone() {
        return pltRecHubZone;
    }

    public void setPltRecHubZone(String pltRecHubZone) {
        this.pltRecHubZone = pltRecHubZone;
    }
}

