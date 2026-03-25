package com.v2retail.dotvik.dc.ptlnew;

import com.google.gson.annotations.SerializedName;

public class ZoneStation {
    @SerializedName("ZONE_STATION")
    private String zoneStation;

    @SerializedName("ZZONE")
    private String zone;

    public String getZoneStation() {
        return zoneStation;
    }

    public void setZoneStation(String zoneStation) {
        this.zoneStation = zoneStation;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}