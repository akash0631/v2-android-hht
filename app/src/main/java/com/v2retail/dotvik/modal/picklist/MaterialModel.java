package com.v2retail.dotvik.modal.picklist;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.ArrayList;

@Keep
public class MaterialModel implements Serializable {

    public String CRATE;
    public String MEINS;
    public String MATERIAL;
    public String VBELN;
    public String VGBEL;
    public String LFIMG;
    public String SAMMG;
    public String POSNR;
    public String GNATURE;
    public ArrayList<EanModel> scannedEAN = new ArrayList<>();

}
