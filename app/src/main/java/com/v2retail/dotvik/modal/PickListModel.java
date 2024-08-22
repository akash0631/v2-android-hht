package com.v2retail.dotvik.modal;

public class PickListModel {


    private String PLANT;
    private String BIN;
    private String PICNR;
    private String STOR_LOC;
    private String CRATE;
    private String AVL_STOCK;
    private String MATERIAL;
    private String STORAGE_TYPE;

    public PickListModel(String PLANT, String BIN, String STOR_LOC, String CRATE, String AVL_STOCK, String MATERIAL, String STORAGE_TYPE) {
        this.PLANT = PLANT;
        this.BIN = BIN;
        this.STOR_LOC = STOR_LOC;
        this.CRATE = CRATE;
        this.AVL_STOCK = AVL_STOCK;
        this.MATERIAL = MATERIAL;
        this.STORAGE_TYPE = STORAGE_TYPE;
    }

    public String getPLANT() {
        return PLANT;
    }

    public String getBIN() {
        return BIN;
    }

    public String getPICNR() {
        return PICNR;
    }

    public String getSTOR_LOC() {
        return STOR_LOC;
    }

    public String getCRATE() {
        return CRATE;
    }

    public String getAVL_STOCK() {
        return AVL_STOCK;
    }

    public String getMATERIAL() {
        return MATERIAL;
    }

    public String getSTORAGE_TYPE() {
        return STORAGE_TYPE;
    }
}
