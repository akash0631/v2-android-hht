package com.v2retail.dotvik.modal;

import com.google.gson.annotations.SerializedName;

public class MardModal {
    @SerializedName("MATNR")
    private String matnr;
    @SerializedName("LABST")
    private String labst;

    public static MardModal newInstance(String matnr, String labst){
        MardModal modal = new MardModal();
        modal.setMatnr(matnr);
        modal.setLabst(labst);
        return modal;
    }

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getLabst() {
        return labst;
    }

    public void setLabst(String labst) {
        this.labst = labst;
    }
}
