package com.v2retail.dotvik.modal;

import java.io.Serializable;

public class EtPoDataModel implements Serializable {

    String LGTYP;
    String MAT_DESC;
    String SCAN_QTY;
    String UNIT;
    String OPEN_QTY;
    String CRATE;
    String GR_QTY;
    String LGPLA;
    String MATERIAL;
    String PO_QTY;
    String MATNR;
    String VEMNG;
    String BDMNG;

    public void setVEMNG(String VEMNG) {
        this.VEMNG = VEMNG;
    }

    public void setMATNR(String MATNR) {
        this.MATNR = MATNR;
    }

    public void setBDMNG(String BDMNG) {
        this.BDMNG = BDMNG;
    }

    String MXALOW;

    public EtPoDataModel(String MAT_DESC, String SCAN_QTY, String UNIT, String OPEN_QTY, String GR_QTY, String MATERIAL, String PO_QTY, String CRATE, String MIX_ALLOW) {
        this.MAT_DESC = MAT_DESC;
        this.SCAN_QTY = SCAN_QTY;
        this.UNIT = UNIT;
        this.OPEN_QTY = OPEN_QTY;
        this.GR_QTY = GR_QTY;
        this.MATERIAL = MATERIAL;
        this.PO_QTY = PO_QTY;
        this.CRATE = CRATE;
        this.MXALOW = MIX_ALLOW;
    }

    public EtPoDataModel(String MATNR,String VEMNG,String BDMNG){

        this.BDMNG = BDMNG;
        this.MATNR = MATNR;
        this.VEMNG = VEMNG;

    }

    public String getMATNR() {
        return MATNR;
    }

    public String getVEMNG() {
        return VEMNG;
    }

    public String getBDMNG() {
        return BDMNG;
    }

    public String getLGTYP() {
        return LGTYP;
    }

    public void setLGTYP(String LGTYP) {
        this.LGTYP = LGTYP;
    }

    public String getMAT_DESC() {
        return MAT_DESC;
    }

    public void setMAT_DESC(String MAT_DESC) {
        this.MAT_DESC = MAT_DESC;
    }

    public String getSCAN_QTY() {
        return SCAN_QTY;
    }

    public void setSCAN_QTY(String SCAN_QTY) {
        this.SCAN_QTY = SCAN_QTY;
    }

    public String getUNIT() {
        return UNIT;
    }

    public void setUNIT(String UNIT) {
        this.UNIT = UNIT;
    }

    public String getOPEN_QTY() {
        return OPEN_QTY;
    }

    public void setOPEN_QTY(String OPEN_QTY) {
        this.OPEN_QTY = OPEN_QTY;
    }

    public String getCRATE() {
        return CRATE;
    }

    public void setCRATE(String CRATE) {
        this.CRATE = CRATE;
    }

    public String getGR_QTY() {
        return GR_QTY;
    }

    public void setGR_QTY(String GR_QTY) {
        this.GR_QTY = GR_QTY;
    }

    public String getLGPLA() {
        return LGPLA;
    }

    public void setLGPLA(String LGPLA) {
        this.LGPLA = LGPLA;
    }

    public String getMATERIAL() {
        return MATERIAL;
    }

    public void setMATERIAL(String MATERIAL) {
        this.MATERIAL = MATERIAL;
    }

    public String getPO_QTY() {
        return PO_QTY;
    }

    public void setPO_QTY(String PO_QTY) {
        this.PO_QTY = PO_QTY;
    }

    public String getMXALOW() {
        return MXALOW;
    }

    public void setMXALOW(String MXALOW) {
        this.MXALOW = MXALOW;
    }

    @Override
    public String toString() {
        return "{" +
                "LGTYP='" + LGTYP + '\'' +
                ", MAT_DESC:" + MAT_DESC + '\'' +
                ", SCAN_QTY:" + SCAN_QTY + '\'' +
                ", UNIT:" + UNIT + '\'' +
                ", OPEN_QTY:" + OPEN_QTY + '\'' +
                ", CRATE:" + CRATE + '\'' +
                ", GR_QTY:" + GR_QTY + '\'' +
                ", LGPLA:" + LGPLA + '\'' +
                ", MATERIAL:" + MATERIAL + '\'' +
                ", PO_QTY:" + PO_QTY + '\'' +
                ", MIX_ALLOWED:" + MXALOW + '\'' +
                '}';
    }
}
