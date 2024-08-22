package com.v2retail.dotvik.modal.picklist;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.ArrayList;

@Keep
public class GetPickDataResponse implements Serializable {

    public EXRETURN EX_RETURN;
    public ArrayList<ETPICKDATA> ET_PICKDATA;
    public ArrayList<ETEANDATA> ET_EAN_DATA;

    public class EXRETURN {
        public String SYSTEM;
        public String NUMBER;
        public String FIELD;
        public String MESSAGE_V2;
        public String MESSAGE;
        public String MESSAGE_V3;
        public String MESSAGE_V4;
        public String LOG_NO;
        public String MESSAGE_V1;
        public String ID;
        public String ROW;
        public String TYPE;
        public String LOG_MSG_NO;
        public String PARAMETER;
    }

    public class ETPICKDATA {
        public String VBELN;
        public String GNATURE;
        public String POSNR;
        public String VGBEL;
        public String LFIMG;
        public String BIN;
        public String SAMMG;
        public String MEINS;
        public String MATERIAL;
        public String MATNR;
        public String MC_DESCR;
        public String CRATE;
        public String URL;
        public boolean picked;

        ETPICKDATA() {
            picked = false;
        }

    }

    public class ETEANDATA {
        public String HOEHE;
        public String TY2TQ;
        public String MSEHI;
        public String NEST_FTR;
        public String CAPAUSE;
        public String XBEWW;
        public String ATINN;
        public String NUMTP;
        public String MANDT;
        public String GEWEI;
        public String MESUB;
        public String XFHDW;
        public String BFLME_MARM;
        public String MAX_STACK;
        public String EANNR;
        public String MATNR;
        public String MESRT;
        public String BRGEW;
        public String LAENG;
        public String BREIT;
        public String MEABM;
        public String GTIN_VARIANT;
        public String VOLUM;
        public String KZWSO;
        public String UMREN;
        public String MEINH;
        public String VOLEH;
        public String EAN11;
        public String UMREZ;
    }
}
