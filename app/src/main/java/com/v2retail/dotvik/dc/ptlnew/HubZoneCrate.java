package com.v2retail.dotvik.dc.ptlnew;

import com.google.gson.annotations.SerializedName;

public class HubZoneCrate {
    @SerializedName("MANDT")
    private String mandt;

    @SerializedName("PLANT")
    private String plant;

    @SerializedName("HUB_STN")
    private String hubStation;

    @SerializedName("ZONE_CRATE")
    private String zoneCrate;

    @SerializedName("CRATE")
    private String crate;

    public String getMandt() {
        return mandt;
    }

    public void setMandt(String mandt) {
        this.mandt = mandt;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getHubStation() {
        return hubStation;
    }

    public void setHubStation(String hubStation) {
        this.hubStation = hubStation;
    }

    public String getZoneCrate() {
        return zoneCrate;
    }

    public void setZoneCrate(String zoneCrate) {
        this.zoneCrate = zoneCrate;
    }

    public String getCrate() {
        return crate;
    }

    public void setCrate(String crate) {
        this.crate = crate;
    }
}
