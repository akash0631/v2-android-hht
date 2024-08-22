package com.v2retail.dotvik.modal;

public class EtLquaModel {

    private String LQNUM;
    private String WERKS;
    private String LGPLA;
    private String LGNUM;
    private String MATNR;

    public EtLquaModel(String LQNUM, String WERKS, String LGPLA, String LGNUM, String MATNR) {
        this.LQNUM = LQNUM;
        this.WERKS = WERKS;
        this.LGPLA = LGPLA;
        this.LGNUM = LGNUM;
        this.MATNR = MATNR;
    }

    public String getLQNUM() {
        return LQNUM;
    }

    public void setLQNUM(String LQNUM) {
        this.LQNUM = LQNUM;
    }

    public String getWERKS() {
        return WERKS;
    }

    public void setWERKS(String WERKS) {
        this.WERKS = WERKS;
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

    public String getMATNR() {
        return MATNR;
    }

    public void setMATNR(String MATNR) {
        this.MATNR = MATNR;
    }
}
