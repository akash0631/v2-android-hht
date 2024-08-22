package com.v2retail.dotvik.modal;

public class EtCreateModel {

    String CRATE;
    String MANDT;

    public EtCreateModel(String CRATE, String MANDT) {
        this.CRATE = CRATE;
        this.MANDT = MANDT;
    }

    public String getMANDT() {
        return MANDT;
    }

    public void setMANDT(String MANDT) {
        this.MANDT = MANDT;
    }

    public String getCRATE() {
        return CRATE;
    }

    public void setCRATE(String CRATE) {
        this.CRATE = CRATE;
    }


}
