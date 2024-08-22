
package com.v2retail.dotvik.modal.material;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Keep
public class ETPACKMAT implements Serializable {

    @SerializedName("LGNUM")
    private String mLGNUM;
    @SerializedName("MAKTX")
    private String mMAKTX;
    @SerializedName("MANDT")
    private String mMANDT;
    @SerializedName("MATNR")
    private String mMATNR;

    public String getLGNUM() {
        return mLGNUM;
    }

    public void setLGNUM(String lGNUM) {
        mLGNUM = lGNUM;
    }

    public String getMAKTX() {
        return mMAKTX;
    }

    public void setMAKTX(String mAKTX) {
        mMAKTX = mAKTX;
    }

    public String getMANDT() {
        return mMANDT;
    }

    public void setMANDT(String mANDT) {
        mMANDT = mANDT;
    }

    public String getMATNR() {
        return mMATNR;
    }

    public void setMATNR(String mATNR) {
        mMATNR = mATNR;
    }

}
