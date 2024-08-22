package com.v2retail.dotvik.modal;

import java.io.Serializable;

public class StockTakeProcessExHeaderModel implements Serializable {

    String LGTYP;
    String AETIM;
    String AENAM;
    String WITH_CRATE;
    String WERKS;
    String AEDAT;
    String GBSTK;
    String STOCK_TAKE;
    String LGPLA_HIGH;
    String MANDT;
    String LGPLA_LOW;
    String ERDAT;
    String LGNUM;
    String UZEIT;
    String ERNAM;

    public StockTakeProcessExHeaderModel(String LGTYP, String AETIM, String AENAM, String WITH_CRATE, String WERKS, String AEDAT, String GBSTK, String STOCK_TAKE, String LGPLA_HIGH, String MANDT, String LGPLA_LOW, String ERDAT, String LGNUM, String UZEIT, String ERNAM) {
        this.LGTYP = LGTYP;
        this.AETIM = AETIM;
        this.AENAM = AENAM;
        this.WITH_CRATE = WITH_CRATE;
        this.WERKS = WERKS;
        this.AEDAT = AEDAT;
        this.GBSTK = GBSTK;
        this.STOCK_TAKE = STOCK_TAKE;
        this.LGPLA_HIGH = LGPLA_HIGH;
        this.MANDT = MANDT;
        this.LGPLA_LOW = LGPLA_LOW;
        this.ERDAT = ERDAT;
        this.LGNUM = LGNUM;
        this.UZEIT = UZEIT;
        this.ERNAM = ERNAM;
    }

    public String getLGTYP() {
        return LGTYP;
    }

    public String getAETIM() {
        return AETIM;
    }

    public String getAENAM() {
        return AENAM;
    }

    public String getWITH_CRATE() {
        return WITH_CRATE;
    }

    public String getWERKS() {
        return WERKS;
    }

    public String getAEDAT() {
        return AEDAT;
    }

    public String getGBSTK() {
        return GBSTK;
    }

    public String getSTOCK_TAKE() {
        return STOCK_TAKE;
    }

    public String getLGPLA_HIGH() {
        return LGPLA_HIGH;
    }

    public String getMANDT() {
        return MANDT;
    }

    public String getLGPLA_LOW() {
        return LGPLA_LOW;
    }

    public String getERDAT() {
        return ERDAT;
    }

    public String getLGNUM() {
        return LGNUM;
    }

    public String getUZEIT() {
        return UZEIT;
    }

    public String getERNAM() {
        return ERNAM;
    }
}
