package com.v2retail.dotvik.store.directpicking;

import com.google.gson.annotations.SerializedName;

class HUArtData {
    @SerializedName("HU")
    private String hu;
    @SerializedName("MATERIAL")
    private String material;
    @SerializedName("FLOOR")
    private String floor;
    @SerializedName("DIVISION")
    private String division;
    @SerializedName("QTY")
    private String qty;
    @SerializedName("BIN")
    private String bin;
    @SerializedName("PLANT_NAME")
    private String plantName;
    @SerializedName("TQTY")
    private String tqty;
    @SerializedName("SQTY")
    private String sqty;
    @SerializedName("PQTY")
    private String pqty;
    @SerializedName("FQTY")
    private String fqty;
    @SerializedName("EAN")
    private String ean;
    private boolean picked;
    private double umrez;

    public static HUArtData newInstance(HUArtData source){
        if (source == null) {
            return null;
        }

        HUArtData target = new HUArtData();
        target.setHu(source.getHu());
        target.setMaterial(source.getMaterial());
        target.setFloor(source.getFloor());
        target.setDivision(source.getDivision());
        target.setQty(source.getQty());
        target.setBin(source.getBin());
        target.setPlantName(source.getPlantName());
        target.setTqty(source.getTqty());
        target.setSqty(source.getSqty());
        target.setPqty(source.getPqty());
        target.setFqty(source.getFqty());
        target.setPicked(source.isPicked());
        target.setEan(source.getEan());
        target.setUmrez(source.getUmrez());

        return target;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public boolean isPicked() {
        return picked;
    }

    public void setPicked(boolean picked) {
        this.picked = picked;
    }

    public String getHu() {
        return hu;
    }

    public void setHu(String hu) {
        this.hu = hu;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getTqty() {
        return tqty;
    }

    public void setTqty(String tqty) {
        this.tqty = tqty;
    }

    public String getSqty() {
        return sqty;
    }

    public void setSqty(String sqty) {
        this.sqty = sqty;
    }

    public String getPqty() {
        return pqty;
    }

    public void setPqty(String pqty) {
        this.pqty = pqty;
    }

    public String getFqty() {
        return fqty;
    }

    public void setFqty(String fqty) {
        this.fqty = fqty;
    }

    public double getUmrez() {
        return umrez;
    }

    public void setUmrez(double umrez) {
        this.umrez = umrez;
    }
}
