package com.v2retail.dotvik.dc.putwayinbin;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PalletHU  implements Serializable {
    @SerializedName("HU")
    private String hu;
    @SerializedName("PALATE")
    private String pallet;
    @SerializedName("MATERIAL")
    private String material;
    @SerializedName("QTY")
    private String qty;

    public String getHu() {
        return hu;
    }

    public void setHu(String hu) {
        this.hu = hu;
    }

    public String getPallet() {
        return pallet;
    }

    public void setPallet(String pallet) {
        this.pallet = pallet;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
