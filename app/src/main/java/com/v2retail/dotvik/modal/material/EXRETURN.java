
package com.v2retail.dotvik.modal.material;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Keep
public class EXRETURN implements Serializable {

    @SerializedName("FIELD")
    private String mFIELD;
    @SerializedName("ID")
    private String mID;
    @SerializedName("LOG_MSG_NO")
    private String mLOGMSGNO;
    @SerializedName("LOG_NO")
    private String mLOGNO;
    @SerializedName("MESSAGE")
    private String mMESSAGE;
    @SerializedName("MESSAGE_V1")
    private String mMESSAGEV1;
    @SerializedName("MESSAGE_V2")
    private String mMESSAGEV2;
    @SerializedName("MESSAGE_V3")
    private String mMESSAGEV3;
    @SerializedName("MESSAGE_V4")
    private String mMESSAGEV4;
    @SerializedName("NUMBER")
    private String mNUMBER;
    @SerializedName("PARAMETER")
    private String mPARAMETER;
    @SerializedName("ROW")
    private String mROW;
    @SerializedName("SYSTEM")
    private String mSYSTEM;
    @SerializedName("TYPE")
    private String mTYPE;

    public String getFIELD() {
        return mFIELD;
    }

    public void setFIELD(String fIELD) {
        mFIELD = fIELD;
    }

    public String getID() {
        return mID;
    }

    public void setID(String iD) {
        mID = iD;
    }

    public String getLOGMSGNO() {
        return mLOGMSGNO;
    }

    public void setLOGMSGNO(String lOGMSGNO) {
        mLOGMSGNO = lOGMSGNO;
    }

    public String getLOGNO() {
        return mLOGNO;
    }

    public void setLOGNO(String lOGNO) {
        mLOGNO = lOGNO;
    }

    public String getMESSAGE() {
        return mMESSAGE;
    }

    public void setMESSAGE(String mESSAGE) {
        mMESSAGE = mESSAGE;
    }

    public String getMESSAGEV1() {
        return mMESSAGEV1;
    }

    public void setMESSAGEV1(String mESSAGEV1) {
        mMESSAGEV1 = mESSAGEV1;
    }

    public String getMESSAGEV2() {
        return mMESSAGEV2;
    }

    public void setMESSAGEV2(String mESSAGEV2) {
        mMESSAGEV2 = mESSAGEV2;
    }

    public String getMESSAGEV3() {
        return mMESSAGEV3;
    }

    public void setMESSAGEV3(String mESSAGEV3) {
        mMESSAGEV3 = mESSAGEV3;
    }

    public String getMESSAGEV4() {
        return mMESSAGEV4;
    }

    public void setMESSAGEV4(String mESSAGEV4) {
        mMESSAGEV4 = mESSAGEV4;
    }

    public String getNUMBER() {
        return mNUMBER;
    }

    public void setNUMBER(String nUMBER) {
        mNUMBER = nUMBER;
    }

    public String getPARAMETER() {
        return mPARAMETER;
    }

    public void setPARAMETER(String pARAMETER) {
        mPARAMETER = pARAMETER;
    }

    public String getROW() {
        return mROW;
    }

    public void setROW(String rOW) {
        mROW = rOW;
    }

    public String getSYSTEM() {
        return mSYSTEM;
    }

    public void setSYSTEM(String sYSTEM) {
        mSYSTEM = sYSTEM;
    }

    public String getTYPE() {
        return mTYPE;
    }

    public void setTYPE(String tYPE) {
        mTYPE = tYPE;
    }

}
