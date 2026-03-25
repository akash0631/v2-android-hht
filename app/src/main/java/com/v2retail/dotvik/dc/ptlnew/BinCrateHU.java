package com.v2retail.dotvik.dc.ptlnew;

import com.google.gson.annotations.SerializedName;

public class BinCrateHU extends BinCrateData {
    @SerializedName("HU_NUM")
    private String hu;

    @SerializedName("QTY")
    private String qty;

    public static BinCrateHU newInstance(BinCrateHU source){
        BinCrateHU target = new BinCrateHU();
        target.setHu(source.getHu());
        target.setQty(source.getQty());
        target.setBin(source.getBin());
        target.setCrate(source.getCrate());
        target.setPicklist(source.getPicklist());
        target.setQty(source.getQty());
        return target;
    }

    public String getHu() {
        return hu;
    }

    public void setHu(String hu) {
        this.hu = hu;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
