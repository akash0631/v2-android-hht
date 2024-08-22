package com.v2retail.dotvik.modal.putaway;

import android.os.Bundle;

import androidx.annotation.Keep;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

/**
 * @author Narayanan
 * @version 11.71
 * {@code Author: Narayanan, Revision: 1, Created: 30th Jul 2024, Modified: 30th Jul 2024}
 */
@Keep
public class ETEanDataStorePutway {
    @SerializedName("EAN11")
    private String ean11;
    @SerializedName("EANNR")
    private String binno;
    @SerializedName("MANDT")
    private String mandt;
    @SerializedName("MATNR")
    private String matnr;
    @SerializedName("UMREN")
    private String umren;
    @SerializedName("UMREZ")
    private String umrez;

    public static ETEanDataStorePutway newInstance(JSONObject objETEanData) {
        return new Gson().fromJson(objETEanData.toString(), ETEanDataStorePutway.class);
    }
    public String getEan11() {
        return ean11;
    }

    public void setEan11(String ean11) {
        this.ean11 = ean11;
    }

    public String getBinno() {
        return binno;
    }

    public void setBinno(String binno) {
        this.binno = binno;
    }

    public String getMandt() {
        return mandt;
    }

    public void setMandt(String mandt) {
        this.mandt = mandt;
    }

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getUmren() {
        return umren;
    }

    public void setUmren(String umren) {
        this.umren = umren;
    }

    public String getUmrez() {
        return umrez;
    }

    public void setUmrez(String umrez) {
        this.umrez = umrez;
    }
}
