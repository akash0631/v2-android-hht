package com.v2retail.dotvik.modal;

public class EtFinalModel {

    private String SCAN_QTY;
    private String REQ_0008;
    private String HU_QTY;
    private String WERKS;
    private String PICNR;
    private String PICKLIST_QTY;
    private String BIN_QTY;
    private String HU_NO;
    private String MATNR;

    public EtFinalModel(String SCAN_QTY, String REQ_0008, String HU_QTY, String WERKS, String PICNR, String PICKLIST_QTY, String BIN_QTY, String HU_NO, String MATNR) {
        this.SCAN_QTY = SCAN_QTY;
        this.REQ_0008 = REQ_0008;
        this.HU_QTY = HU_QTY;
        this.WERKS = WERKS;
        this.PICNR = PICNR;
        this.PICKLIST_QTY = PICKLIST_QTY;
        this.BIN_QTY = BIN_QTY;
        this.HU_NO = HU_NO;
        this.MATNR = MATNR;
    }

    public String getSCAN_QTY() {
        return SCAN_QTY;
    }

    public void setSCAN_QTY(String SCAN_QTY) {
        this.SCAN_QTY = SCAN_QTY;
    }

    public String getREQ_0008() {
        return REQ_0008;
    }

    public void setREQ_0008(String REQ_0008) {
        this.REQ_0008 = REQ_0008;
    }

    public String getHU_QTY() {
        return HU_QTY;
    }

    public void setHU_QTY(String HU_QTY) {
        this.HU_QTY = HU_QTY;
    }

    public String getWERKS() {
        return WERKS;
    }

    public void setWERKS(String WERKS) {
        this.WERKS = WERKS;
    }

    public String getPICNR() {
        return PICNR;
    }

    public void setPICNR(String PICNR) {
        this.PICNR = PICNR;
    }

    public String getPICKLIST_QTY() {
        return PICKLIST_QTY;
    }

    public void setPICKLIST_QTY(String PICKLIST_QTY) {
        this.PICKLIST_QTY = PICKLIST_QTY;
    }

    public String getBIN_QTY() {
        return BIN_QTY;
    }

    public void setBIN_QTY(String BIN_QTY) {
        this.BIN_QTY = BIN_QTY;
    }

    public String getHU_NO() {
        return HU_NO;
    }

    public void setHU_NO(String HU_NO) {
        this.HU_NO = HU_NO;
    }

    public String getMATNR() {
        return MATNR;
    }

    public void setMATNR(String MATNR) {
        this.MATNR = MATNR;
    }
}
