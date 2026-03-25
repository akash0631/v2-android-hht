package com.v2retail.dotvik.dc.ptlnew;

import com.google.gson.annotations.SerializedName;

public class ETPickData {
    @SerializedName("TANUM")
    private String tanum;
    @SerializedName("ERDAT")
    private String erDat;
    @SerializedName("ZONE_SEC")
    private String section;
    @SerializedName("PNATURE")
    private String nature;
    @SerializedName("ZONE_CRATE")
    private String zone;
    @SerializedName("FLOOR")
    private String floor;
    @SerializedName("PICKLIST")
    private String picklist;

    public String getTanum() {
        return tanum;
    }

    public void setTanum(String tanum) {
        this.tanum = tanum;
    }

    public String getErDat() {
        return erDat;
    }

    public void setErDat(String erDat) {
        this.erDat = erDat;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getPicklist() {
        return picklist;
    }

    public void setPicklist(String picklist) {
        this.picklist = picklist;
    }
}
