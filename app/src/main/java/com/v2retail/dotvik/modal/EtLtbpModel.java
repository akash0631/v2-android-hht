package com.v2retail.dotvik.modal;

public class EtLtbpModel {

    String MATNR;
    String ERFMG;
    String MENGE;

    public EtLtbpModel(String MATNR, String ERFMG, String MENGE) {
        this.MATNR = MATNR;
        this.ERFMG = ERFMG;
        this.MENGE = MENGE;
    }

    public String getMATNR() {
        return MATNR;
    }

    public String getERFMG() {
        return ERFMG;
    }

    public String getMENGE() {
        return MENGE;
    }
}
