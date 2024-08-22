package com.v2retail.util;

import java.util.ArrayList;

public class Tables {


    public ArrayList<ArrayList<String>> getPOTAble(String tag) {
        ArrayList<ArrayList<String>> dtPO = new ArrayList<ArrayList<String>>();
        ArrayList MATNR = new ArrayList<String>();
        ArrayList VEMNG = new ArrayList<String>();
        ArrayList BDMNG = new ArrayList<String>();
        if (tag.equals("stock")) {
            ArrayList<String> gr_qty = new ArrayList<String>();
            ArrayList<String> scan_qty = new ArrayList<>();
            ArrayList<String> open_qty = new ArrayList<>();
            ArrayList<String> unit = new ArrayList<>();
            dtPO.add(gr_qty);
            dtPO.add(scan_qty);
            dtPO.add(open_qty);
            dtPO.add(unit);
            return dtPO;
        }
        dtPO.add(MATNR);
        dtPO.add(VEMNG);
        dtPO.add(BDMNG);
        if (tag.equals("inout")) {
            ArrayList<String> gr_qty = new ArrayList<String>();
            ArrayList<String> scan_qty = new ArrayList<>();
            ArrayList<String> open_qty = new ArrayList<>();
            ArrayList<String> unit = new ArrayList<>();
            dtPO.add(gr_qty);
            dtPO.add(scan_qty);
            dtPO.add(open_qty);
            dtPO.add(unit);
            return dtPO;
        }
        if (tag.equals("pick")) {
            ArrayList<String> scan_qty = new ArrayList<String>();
            dtPO.add(scan_qty);
            return dtPO;
        }
        if (tag.equals("direct") || tag.equals("msa")) {
            ArrayList<String> scan_qty = new ArrayList<String>();
            ArrayList<String> bin_qty = new ArrayList<>();
            dtPO.add(scan_qty);
            dtPO.add(bin_qty);
            return dtPO;
        }
        return dtPO;
    }

    public ArrayList<ArrayList<String>> getEANTAble(String tag) {

        ArrayList<ArrayList<String>> dtEAN = new ArrayList<ArrayList<String>>();
        ArrayList<String> MANDT = new ArrayList<String>();
        ArrayList<String> MATNR = new ArrayList<String>();
        ArrayList<String> EAN11 = new ArrayList<String>();
        ArrayList<String> UMREZ = new ArrayList<String>();
        ArrayList<String> EANNR = new ArrayList<String>();


        if (tag.equals("del")) {
            dtEAN.add(MANDT);
            dtEAN.add(MATNR);
            dtEAN.add(EAN11);
            dtEAN.add(UMREZ);
            return dtEAN;
        }
        if (tag.equals("floor") || tag.equals("display")) {
            ArrayList<String> LABST = new ArrayList<String>();
            dtEAN.add(LABST);
        }
        dtEAN.add(MANDT);
        dtEAN.add(MATNR);
        dtEAN.add(EAN11);
        dtEAN.add(UMREZ);
        dtEAN.add(EANNR);

        return dtEAN;
    }

    public ArrayList<ArrayList<String>> getCrateTable() {

        ArrayList<ArrayList<String>> dtcrate = new ArrayList<ArrayList<String>>();

        ArrayList<String> _1 = new ArrayList<String>();
        ArrayList<String> _2 = new ArrayList<String>();
        ArrayList<String> _3 = new ArrayList<String>();
        ArrayList<String> _4 = new ArrayList<String>();
        ArrayList<String> _5 = new ArrayList<String>();
        ArrayList<String> _6 = new ArrayList<String>();
        ArrayList<String> _7 = new ArrayList<String>();
        ArrayList<String> _8 = new ArrayList<String>();
        ArrayList<String> _9 = new ArrayList<String>();
        ArrayList<String> _10 = new ArrayList<String>();
        ArrayList<String> _11 = new ArrayList<String>();

        dtcrate.add(_1);
        dtcrate.add(_2);
        dtcrate.add(_3);
        dtcrate.add(_4);
        dtcrate.add(_5);
        dtcrate.add(_6);
        dtcrate.add(_7);
        dtcrate.add(_8);
        dtcrate.add(_9);
        dtcrate.add(_10);
        dtcrate.add(_11);

        return dtcrate;
    }
    public ArrayList<ArrayList<String>> getCrateTable(String tag) {

        ArrayList<ArrayList<String>> dtcrate = new ArrayList<ArrayList<String>>();
        ArrayList<String> o = new ArrayList<String>();
        ArrayList<String> t = new ArrayList<String>();
        ArrayList<String> th = new ArrayList<String>();
        ArrayList<String> f = new ArrayList<String>();
        ArrayList<String> fv = new ArrayList<String>();
        ArrayList<String> sx = new ArrayList<String>();
        ArrayList<String> s = new ArrayList<String>();
        ArrayList<String> e = new ArrayList<String>();
        ArrayList<String> n = new ArrayList<String>();
        ArrayList<String> te = new ArrayList<String>();
        ArrayList<String> z = new ArrayList<String>();

        dtcrate.add(o);
        dtcrate.add(t);
        dtcrate.add(th);
        dtcrate.add(f);
        dtcrate.add(fv);
        dtcrate.add(sx);
        dtcrate.add(s);
        dtcrate.add(e);
        dtcrate.add(n);
        dtcrate.add(te);
        dtcrate.add(z);

        return dtcrate;
    }
    public ArrayList<ArrayList<String>> getScanMatTable() {

        ArrayList<ArrayList<String>> dtcrate = new ArrayList<ArrayList<String>>();
        ArrayList<String> crate = new ArrayList<String>();
        ArrayList<String> bin = new ArrayList<String>();
        ArrayList<String> three = new ArrayList<String>();
        ArrayList<String> four = new ArrayList<String>();
        ArrayList<String> five = new ArrayList<String>();
        ArrayList<String> six = new ArrayList<String>();
        ArrayList<String> seven = new ArrayList<String>();

        dtcrate.add(crate);
        dtcrate.add(bin);
        dtcrate.add(three);
        dtcrate.add(four);
        dtcrate.add(five);
        dtcrate.add(seven);
        dtcrate.add(six);

        return dtcrate;
    }

    public ArrayList<ArrayList<String>> getDeliveryTable() {

        ArrayList<ArrayList<String>> dt81 = new ArrayList<ArrayList<String>>();
        ArrayList<String> MANDT = new ArrayList<String>();
        ArrayList<String> MATNR = new ArrayList<String>();
        ArrayList<String> EAN11 = new ArrayList<String>();
        ArrayList<String> UMREZ = new ArrayList<String>();
        ArrayList<String> a = new ArrayList<String>();
        ArrayList<String> b = new ArrayList<String>();
        ArrayList<String> c = new ArrayList<String>();

        dt81.add(MANDT);
        dt81.add(MATNR);
        dt81.add(EAN11);
        dt81.add(UMREZ);
        dt81.add(a);
        dt81.add(b);
        dt81.add(c);

        return dt81;
    }

    public ArrayList<ArrayList<String>> get81TAble() {

        ArrayList<ArrayList<String>> dt81 = new ArrayList<ArrayList<String>>();
        ArrayList<String> MANDT = new ArrayList<String>();
        ArrayList<String> MATNR = new ArrayList<String>();
        ArrayList<String> EAN11 = new ArrayList<String>();
        ArrayList<String> UMREZ = new ArrayList<String>();

        dt81.add(MANDT);
        dt81.add(MATNR);
        dt81.add(EAN11);
        dt81.add(UMREZ);

        return dt81;
    }

    public ArrayList<ArrayList<String>> getPacMatTable(String tag) {

        ArrayList<ArrayList<String>> dtPacMat = new ArrayList<ArrayList<String>>();
        ArrayList<String> MANDT = new ArrayList<String>();
        ArrayList<String> LGNUM = new ArrayList<String>();
        ArrayList<String> MATNR = new ArrayList<String>();
        ArrayList<String> MAKTX = new ArrayList<String>();

        dtPacMat.add(MANDT);
        dtPacMat.add(LGNUM);
        dtPacMat.add(MATNR);
        dtPacMat.add(MAKTX);

        return dtPacMat;
    }

       public ArrayList<ArrayList<String>> getMATTAble() {
        ArrayList<ArrayList<String>> dtMaterial = new ArrayList<ArrayList<String>>();

        ArrayList<String> Material = new ArrayList<String>();
        ArrayList<String> Qty = new ArrayList<String>();
        ArrayList<String> Bin = new ArrayList<String>();

        dtMaterial.add(Material);
        dtMaterial.add(Qty);
        dtMaterial.add(Bin);

        return dtMaterial;
    }
    public ArrayList<ArrayList<String>> getVAlMATTAble() {
        ArrayList<ArrayList<String>> dtMaterial = new ArrayList<ArrayList<String>>();

        ArrayList<String> Material = new ArrayList<String>();
        ArrayList<String> Qty = new ArrayList<String>();
        ArrayList<String> Bin = new ArrayList<String>();

        ArrayList<String> crate = new ArrayList<String>();
        dtMaterial.add(Material);
        dtMaterial.add(Qty);
        dtMaterial.add(Bin);
        dtMaterial.add(crate);

        return dtMaterial;
    }
    public ArrayList<ArrayList<String>> getItemTable()
    {

        ArrayList<ArrayList<String>> dtcrate = new ArrayList<ArrayList<String>>();
        ArrayList<String> crate = new ArrayList<String>();
        ArrayList<String> bin = new ArrayList<String>();
        ArrayList<String> three = new ArrayList<String>();
        ArrayList<String> four = new ArrayList<String>();
        ArrayList<String> five = new ArrayList<String>();
        ArrayList<String> six = new ArrayList<String>();
        ArrayList<String> seven = new ArrayList<String>();
        ArrayList<String> e = new ArrayList<String>();
        ArrayList<String> n = new ArrayList<String>();
        ArrayList<String> t = new ArrayList<String>();
        ArrayList<String> el = new ArrayList<String>();
        ArrayList<String> tw = new ArrayList<String>();
        ArrayList<String> th = new ArrayList<String>();
        ArrayList<String> f = new ArrayList<String>();
        ArrayList<String> fv = new ArrayList<String>();
        ArrayList<String> sx = new ArrayList<String>();
        ArrayList<String> svt = new ArrayList<String>();
        ArrayList<String> et = new ArrayList<String>();
        ArrayList<String> nt = new ArrayList<String>();
        ArrayList<String> twt = new ArrayList<String>();
        ArrayList<String> thr = new ArrayList<String>();

        dtcrate.add(crate);
        dtcrate.add(bin);
        dtcrate.add(three);
        dtcrate.add(four);
        dtcrate.add(five);
        dtcrate.add(seven);
        dtcrate.add(six);
        dtcrate.add(e);
        dtcrate.add(n);
        dtcrate.add(t);
        dtcrate.add(el);
        dtcrate.add(tw);
        dtcrate.add(th);
        dtcrate.add(f);
        dtcrate.add(fv);
        dtcrate.add(sx);
        dtcrate.add(svt);
        dtcrate.add(et);
        dtcrate.add(nt);
        dtcrate.add(twt);
        dtcrate.add(thr);
        return dtcrate;
    }
    public ArrayList<ArrayList<String>> getStockTakeTable() {
        ArrayList<ArrayList<String>> dtMaterial = new ArrayList<ArrayList<String>>();

        ArrayList<String> s = new ArrayList<String>();
        ArrayList<String> se = new ArrayList<String>();
        ArrayList<String> e = new ArrayList<String>();
        ArrayList<String> o = new ArrayList<String>();
        ArrayList<String> t = new ArrayList<String>();
        ArrayList<String> th = new ArrayList<String>();
        ArrayList<String> f = new ArrayList<String>();
        ArrayList<String> fi = new ArrayList<String>();
        ArrayList<String> n = new ArrayList<String>();
        ArrayList<String> te = new ArrayList<String>();


        dtMaterial.add(o);
        dtMaterial.add(t);
        dtMaterial.add(th);
        dtMaterial.add(f);
        dtMaterial.add(fi);
        dtMaterial.add(s);
        dtMaterial.add(se);
        dtMaterial.add(e);
        dtMaterial.add(n);
        dtMaterial.add(te);

        return dtMaterial;
    }
    public ArrayList<String> getBINTAble() {
        ArrayList<String> dtBin = new ArrayList<>();

        return dtBin;
    }

    public ArrayList<String> getPoNoTAble() {
        ArrayList<String> pono = new ArrayList<>();

        return pono;
    }
    public ArrayList<ArrayList<String>> getScannedStockTable() {
        ArrayList<ArrayList<String>> dtMaterial = new ArrayList<ArrayList<String>>();

        ArrayList<String> s = new ArrayList<String>();
        ArrayList<String> se = new ArrayList<String>();
        ArrayList<String> e = new ArrayList<String>();
        ArrayList<String> o = new ArrayList<String>();
        ArrayList<String> t = new ArrayList<String>();
        ArrayList<String> th = new ArrayList<String>();
        ArrayList<String> f = new ArrayList<String>();
        ArrayList<String> fi = new ArrayList<String>();


        dtMaterial.add(o);
        dtMaterial.add(t);
        dtMaterial.add(th);
        dtMaterial.add(f);
        dtMaterial.add(fi);
        dtMaterial.add(s);
        dtMaterial.add(se);
        dtMaterial.add(e);


        return dtMaterial;
    }
    public ArrayList<ArrayList<String>> getStockDetailsTable() {
        ArrayList<ArrayList<String>> dtMaterial = new ArrayList<ArrayList<String>>();

        ArrayList<String> s = new ArrayList<String>();
        ArrayList<String> se = new ArrayList<String>();
        ArrayList<String> e = new ArrayList<String>();
        ArrayList<String> o = new ArrayList<String>();
        ArrayList<String> t = new ArrayList<String>();
        ArrayList<String> th = new ArrayList<String>();
        ArrayList<String> f = new ArrayList<String>();
        ArrayList<String> fi = new ArrayList<String>();
        ArrayList<String> n = new ArrayList<String>();
        ArrayList<String> te = new ArrayList<String>();
        ArrayList<String> el = new ArrayList<String>();
        ArrayList<String> tw = new ArrayList<String>();
        ArrayList<String> thr = new ArrayList<String>();
        ArrayList<String> z = new ArrayList<String>();
        ArrayList<String> fv = new ArrayList<String>();


        dtMaterial.add(o);
        dtMaterial.add(t);
        dtMaterial.add(th);
        dtMaterial.add(f);
        dtMaterial.add(fi);
        dtMaterial.add(s);
        dtMaterial.add(se);
        dtMaterial.add(e);

        dtMaterial.add(n);
        dtMaterial.add(te);
        dtMaterial.add(el);
        dtMaterial.add(tw);
        dtMaterial.add(thr);
        dtMaterial.add(z);
        dtMaterial.add(fv);

        return dtMaterial;
    }

	public ArrayList<ArrayList<String>> getStockBinPOTable() {
        ArrayList<ArrayList<String>> dtMaterial = new ArrayList<ArrayList<String>>();


        ArrayList<String> te = new ArrayList<String>();
        ArrayList<String> el = new ArrayList<String>();
        ArrayList<String> tw = new ArrayList<String>();
        ArrayList<String> thr = new ArrayList<String>();
        ArrayList<String> z = new ArrayList<String>();
        ArrayList<String> fv = new ArrayList<String>();
        dtMaterial.add(te);
        dtMaterial.add(el);
        dtMaterial.add(tw);
        dtMaterial.add(thr);
        dtMaterial.add(z);
        dtMaterial.add(fv);
        return dtMaterial;
    }
}
