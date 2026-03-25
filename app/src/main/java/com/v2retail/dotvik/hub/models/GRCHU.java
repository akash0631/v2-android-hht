package com.v2retail.dotvik.hub.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GRCHU implements Serializable {
    @SerializedName("VBELN")
    private String invoice;
    @SerializedName("EXIDV")
    private String id;
    @SerializedName("SAP_HU")
    private String huno;

    public static GRCHU newInstance(GRCHU source){
        GRCHU target = new GRCHU();
        target.setHuno(source.getHuno());
        target.setId(source.getId());
        target.setInvoice(source.getInvoice());
        return target;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHuno() {
        return huno;
    }

    public void setHuno(String huno) {
        this.huno = huno;
    }
}
