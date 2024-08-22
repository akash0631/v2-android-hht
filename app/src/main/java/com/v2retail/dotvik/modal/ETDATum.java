
package com.v2retail.dotvik.modal;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Keep
public class ETDATum implements Serializable {

    @SerializedName("BNAME")
    private String mBNAME;
    @SerializedName("DELI_DATE")
    private String mDELIDATE;
    @SerializedName("DEL_NO")
    private String mDELNO;
    @SerializedName("DEL_QTY")
    private String mDELQTY;
    @SerializedName("ERDAT")
    private String mERDAT;
    @SerializedName("MANDT")
    private String mMANDT;

    public String getBNAME() {
        return mBNAME;
    }

    public void setBNAME(String bNAME) {
        mBNAME = bNAME;
    }

    public String getDELIDATE() {
        return mDELIDATE;
    }

    public void setDELIDATE(String dELIDATE) {
        mDELIDATE = dELIDATE;
    }

    public String getDELNO() {
        return mDELNO;
    }

    public void setDELNO(String dELNO) {
        mDELNO = dELNO;
    }

    public String getDELQTY() {
        return mDELQTY;
    }

    public void setDELQTY(String dELQTY) {
        mDELQTY = dELQTY;
    }

    public String getERDAT() {
        return mERDAT;
    }

    public void setERDAT(String eRDAT) {
        mERDAT = eRDAT;
    }

    public String getMANDT() {
        return mMANDT;
    }

    public void setMANDT(String mANDT) {
        mMANDT = mANDT;
    }

}
