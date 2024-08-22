package com.v2retail.util;

public class ScanDataItem {
    private String id;
    private String userId;
    private String nextScanNO;
    private String boxNO1;
    private String boxNO2;
    private String boxNO3;

    public ScanDataItem(String id, String userId, String nextScanNO, String boxNO1, String boxNO2, String boxNO3) {
        this.id = id;
        this.userId = userId;
        this.nextScanNO = nextScanNO;
        this.boxNO1 = boxNO1;
        this.boxNO2 = boxNO2;
        this.boxNO3 = boxNO3;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNextScanNO() {
        return nextScanNO;
    }

    public void setNextScanNO(String nextScanNO) {
        this.nextScanNO = nextScanNO;
    }

    public String getBoxNO1() {
        return boxNO1;
    }

    public void setBoxNO1(String boxNO1) {
        this.boxNO1 = boxNO1;
    }

    public String getBoxNO2() {
        return boxNO2;
    }

    public void setBoxNO2(String boxNO2) {
        this.boxNO2 = boxNO2;
    }

    public String getBoxNO3() {
        return boxNO3;
    }

    public void setBoxNO3(String boxNO3) {
        this.boxNO3 = boxNO3;
    }
}


