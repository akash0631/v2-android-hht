package com.v2retail.dotvik.modal;

public class ItBin {


    private String LGTYP;
    private String WERKS;
    private String STOCK_TAKE;
    private String TANUM2;
    private String TANUM1;
    private String LGPLA;
    private String LGNUM;
    private String ERNAM;

    public ItBin(String LGTYP, String WERKS, String STOCK_TAKE, String TANUM2, String TANUM1, String LGPLA, String LGNUM, String ERNAM) {
        this.LGTYP = LGTYP;
        this.WERKS = WERKS;
        this.STOCK_TAKE = STOCK_TAKE;
        this.TANUM2 = TANUM2;
        this.TANUM1 = TANUM1;
        this.LGPLA = LGPLA;
        this.LGNUM = LGNUM;
        this.ERNAM = ERNAM;
    }

    public String getLGTYP() {
        return LGTYP;
    }

    public String getWERKS() {
        return WERKS;
    }

    public String getSTOCK_TAKE() {
        return STOCK_TAKE;
    }

    public String getTANUM2() {
        return TANUM2;
    }

    public String getTANUM1() {
        return TANUM1;
    }

    public String getLGPLA() {
        return LGPLA;
    }

    public String getLGNUM() {
        return LGNUM;
    }

    public String getERNAM() {
        return ERNAM;
    }
}
