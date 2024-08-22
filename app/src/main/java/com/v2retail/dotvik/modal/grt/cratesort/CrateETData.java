package com.v2retail.dotvik.modal.grt.cratesort;

import com.google.gson.annotations.SerializedName;

public class CrateETData {
    @SerializedName("CRATE")
    private String lgcrate;
    @SerializedName("TYPE")
    private String lgtype;

    public CrateETData(String lgcrate, String lgtype) {
        this.lgcrate = lgcrate;
        this.lgtype = lgtype;
    }

    public String getLgcrate() {
        return lgcrate;
    }

    public void setLgcrate(String lgcrate) {
        this.lgcrate = lgcrate;
    }

    public String getLgtype() {
        return lgtype;
    }

    public void setLgtype(String lgtype) {
        this.lgtype = lgtype;
    }
}
