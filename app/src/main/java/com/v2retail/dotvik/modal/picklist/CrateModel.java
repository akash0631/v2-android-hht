package com.v2retail.dotvik.modal.picklist;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.ArrayList;

@Keep
public class CrateModel implements Serializable {

    public String ID;
    public String GNATURE;
    public ArrayList<MaterialModel> materialList = new ArrayList<>();
}
