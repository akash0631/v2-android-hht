package com.v2retail.dotvik.modal;

import com.v2retail.dotvik.store.StoreBinConsolidationFragment;

public class EtEanDataModel {

    String HOEHE;
    String TY2TQ;
    String MSEHI;
    String PCBUT;
    String NEST_FTR;
    String CAPAUSE;
    String XBEWW;
    String ATINN;
    String NUMTP;
    String MANDT;
    String DUMMY_UOM_INCL_EEW_PS;
    String GEWEI;
    String MESUB;
    String XFHDW;
    String BFLME_MARM;
    String MAX_STACK;
    String EANNR;
    String MATNR;
    String UMREN;
    String VOLEH;
    String EAN11;
    String MESRT;
    String UMREZ;
    String MEINH;


    public EtEanDataModel(String MATNR, String UMREZ, String MANDT, String EAN11) {
        this.MATNR = MATNR;
        this.UMREZ = UMREZ;
        this.MANDT = MANDT;
        this.EAN11 = EAN11;
    }

    public EtEanDataModel(String MATNR, String UMREZ, String MANDT, String EAN11,String EANNR){
        this.MATNR = MATNR;
        this.UMREZ = UMREZ;
        this.MANDT = MANDT;
        this.EAN11 = EAN11;
        this.EANNR = EANNR;
    }


    public EtEanDataModel(String MANDT, String EANNR, String UMREZ, String EAN11,String MATNR,String MESRT) {
        this.MANDT = MANDT;
        this.EANNR = EANNR;
        this.UMREZ = UMREZ;
        this.EAN11 = EAN11;
        this.MATNR = MATNR;
        this.MESRT = MESRT;
    }
    public EtEanDataModel(String MANDT, String MATNR, String UMREZ, String UMREN,String EANNR, String MEINH, String EAN11) {
        this.MANDT = MANDT;
        this.UMREZ = UMREZ;
        this.EAN11 = EAN11;
        this.MATNR = MATNR;
        this.UMREN =UMREN;
        this.MEINH = MEINH;
        this.EANNR = EANNR;

    }


    public String getMEINH() {
        return MEINH;
    }

    public String getMESRT() {
        return MESRT;
    }

    public String getHOEHE() {
        return HOEHE;
    }

    public void setHOEHE(String HOEHE) {
        this.HOEHE = HOEHE;
    }

    public String getTY2TQ() {
        return TY2TQ;
    }

    public void setTY2TQ(String TY2TQ) {
        this.TY2TQ = TY2TQ;
    }

    public String getMSEHI() {
        return MSEHI;
    }

    public void setMSEHI(String MSEHI) {
        this.MSEHI = MSEHI;
    }

    public String getPCBUT() {
        return PCBUT;
    }

    public void setPCBUT(String PCBUT) {
        this.PCBUT = PCBUT;
    }

    public String getNEST_FTR() {
        return NEST_FTR;
    }

    public void setNEST_FTR(String NEST_FTR) {
        this.NEST_FTR = NEST_FTR;
    }

    public String getCAPAUSE() {
        return CAPAUSE;
    }

    public void setCAPAUSE(String CAPAUSE) {
        this.CAPAUSE = CAPAUSE;
    }

    public String getXBEWW() {
        return XBEWW;
    }

    public void setXBEWW(String XBEWW) {
        this.XBEWW = XBEWW;
    }

    public String getATINN() {
        return ATINN;
    }

    public void setATINN(String ATINN) {
        this.ATINN = ATINN;
    }

    public String getNUMTP() {
        return NUMTP;
    }

    public void setNUMTP(String NUMTP) {
        this.NUMTP = NUMTP;
    }

    public String getMANDT() {
        return MANDT;
    }

    public void setMANDT(String MANDT) {
        this.MANDT = MANDT;
    }

    public String getDUMMY_UOM_INCL_EEW_PS() {
        return DUMMY_UOM_INCL_EEW_PS;
    }

    public void setDUMMY_UOM_INCL_EEW_PS(String DUMMY_UOM_INCL_EEW_PS) {
        this.DUMMY_UOM_INCL_EEW_PS = DUMMY_UOM_INCL_EEW_PS;
    }

    public String getGEWEI() {
        return GEWEI;
    }

    public void setGEWEI(String GEWEI) {
        this.GEWEI = GEWEI;
    }

    public String getMESUB() {
        return MESUB;
    }

    public void setMESUB(String MESUB) {
        this.MESUB = MESUB;
    }

    public String getXFHDW() {
        return XFHDW;
    }

    public void setXFHDW(String XFHDW) {
        this.XFHDW = XFHDW;
    }

    public String getBFLME_MARM() {
        return BFLME_MARM;
    }

    public void setBFLME_MARM(String BFLME_MARM) {
        this.BFLME_MARM = BFLME_MARM;
    }

    public String getMAX_STACK() {
        return MAX_STACK;
    }

    public void setMAX_STACK(String MAX_STACK) {
        this.MAX_STACK = MAX_STACK;
    }

    public String getEANNR() {
        return EANNR;
    }

    public void setEANNR(String EANNR) {
        this.EANNR = EANNR;
    }

    public String getMATNR() {
        return MATNR;
    }

    public void setMATNR(String MATNR) {
        this.MATNR = MATNR;
    }

    public String getUMREN() {
        return UMREN;
    }

    public void setUMREN(String UMREN) {
        this.UMREN = UMREN;
    }

    public String getVOLEH() {
        return VOLEH;
    }

    public void setVOLEH(String VOLEH) {
        this.VOLEH = VOLEH;
    }

    public String getEAN11() {
        return EAN11;
    }

    public void setEAN11(String EAN11) {
        this.EAN11 = EAN11;
    }

    public String getUMREZ() {
        return UMREZ;
    }
}
