package com.v2retail.dotvik.modal;

public class EtBinModel {

    String LGTYP;
    String MANDT;
    String MJAHR;
    String LOCKED;
    String MBLNR;
    String LGPLA;
    String LGNUM;
    String MATERIAL;
    String AVL_STOCK;

    public EtBinModel(String LGTYP, String MANDT, String MJAHR, String LOCKED, String MBLNR, String LGPLA, String LGNUM) {
        this.LGTYP = LGTYP;
        this.MANDT = MANDT;
        this.MJAHR = MJAHR;
        this.LOCKED = LOCKED;
        this.MBLNR = MBLNR;
        this.LGPLA = LGPLA;
        this.LGNUM = LGNUM;
    }

    public EtBinModel(String LGPLA){
        this.LGPLA = LGPLA;
    }

    public EtBinModel(String LGPLA , String MATERIAL ,String  AVL_STOCK){
        this.LGPLA = LGPLA;
        this.MATERIAL = MATERIAL;
        this.AVL_STOCK = AVL_STOCK;
    }


    public String getMATERIAL() {
        return MATERIAL;
    }

    public String getAVL_STOCK() {
        return AVL_STOCK;
    }

    public String getLGTYP() {
        return LGTYP;
    }

    public void setLGTYP(String LGTYP) {
        this.LGTYP = LGTYP;
    }

    public String getMANDT() {
        return MANDT;
    }

    public void setMANDT(String MANDT) {
        this.MANDT = MANDT;
    }

    public String getMJAHR() {
        return MJAHR;
    }

    public void setMJAHR(String MJAHR) {
        this.MJAHR = MJAHR;
    }

    public String getLOCKED() {
        return LOCKED;
    }

    public void setLOCKED(String LOCKED) {
        this.LOCKED = LOCKED;
    }

    public String getMBLNR() {
        return MBLNR;
    }

    public void setMBLNR(String MBLNR) {
        this.MBLNR = MBLNR;
    }

    public String getLGPLA() {
        return LGPLA;
    }

    public void setLGPLA(String LGPLA) {
        this.LGPLA = LGPLA;
    }

    public String getLGNUM() {
        return LGNUM;
    }

    public void setLGNUM(String LGNUM) {
        this.LGNUM = LGNUM;
    }
}
