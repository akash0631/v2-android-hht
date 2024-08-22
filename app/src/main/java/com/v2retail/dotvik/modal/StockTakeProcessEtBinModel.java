package com.v2retail.dotvik.modal;

import java.io.Serializable;

public class StockTakeProcessEtBinModel  implements Serializable {

    String LGTYP;
    String MANDT;
    String WERKS;
    String LVORM;
    String ERDAT;
    String LGPLA;
    String LGNUM;
    String UZEIT;
    String STOCK_TAKE;
    String ERNAM;


    public StockTakeProcessEtBinModel(String LGTYP, String MANDT, String WERKS, String LVORM, String ERDAT, String LGPLA, String LGNUM, String UZEIT, String STOCK_TAKE, String ERNAM) {
        this.LGTYP = LGTYP;
        this.MANDT = MANDT;
        this.WERKS = WERKS;
        this.LVORM = LVORM;
        this.ERDAT = ERDAT;
        this.LGPLA = LGPLA;
        this.LGNUM = LGNUM;
        this.UZEIT = UZEIT;
        this.STOCK_TAKE = STOCK_TAKE;
        this.ERNAM = ERNAM;
    }


    public String getLGTYP() {
        return LGTYP;
    }

    public String getMANDT() {
        return MANDT;
    }

    public String getWERKS() {
        return WERKS;
    }

    public String getLVORM() {
        return LVORM;
    }

    public String getERDAT() {
        return ERDAT;
    }

    public String getLGPLA() {
        return LGPLA;
    }

    public String getLGNUM() {
        return LGNUM;
    }

    public String getUZEIT() {
        return UZEIT;
    }

    public String getSTOCK_TAKE() {
        return STOCK_TAKE;
    }

    public String getERNAM() {
        return ERNAM;
    }
}
