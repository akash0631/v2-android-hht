package com.v2retail.dotvik.dc.reverseputway;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PalletCrate implements Serializable {
    @SerializedName("CRATE")
    private String crate;
    @SerializedName("PALATE")
    private String pallet;
    @SerializedName("QTY")
    private String qty;

    public String getCrate() {
        return crate;
    }

    public void setCrate(String crate) {
        this.crate = crate;
    }

    public String getPallet() {
        return pallet;
    }

    public void setPallet(String pallet) {
        this.pallet = pallet;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
