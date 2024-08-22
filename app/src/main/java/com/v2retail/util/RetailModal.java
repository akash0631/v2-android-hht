package com.v2retail.util;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RetailModal implements Serializable, Parcelable {
    @SerializedName("COLOR")
    private String color="";
    @SerializedName("LGNUM")
    private String lgnum="";
    @SerializedName("MATNR")
    private String article="";
    @SerializedName("MAKTX")
    private String matDesc="";
    @SerializedName("SATNR")
    private String satnr="";
    @SerializedName("MATKL")
    private String merchandise="";
    @SerializedName("WERKS")
    private String werks="";
    @SerializedName("EAN11")
    private String ean11="";
    @SerializedName("V01")
    private String vo1="0.0";
    @SerializedName("V02")
    private String vo2="0.0";
    @SerializedName("V04")
    private String vo4="0.0";
    @SerializedName("MSA")
    private String msa="0.0";
    @SerializedName("0001")
    private String one="0.0";
    @SerializedName("0003")
    private String three="0.0";
    @SerializedName("YTD_QTY")
    private String ytd="0.0";
    @SerializedName("YTD_NVAL")
    private String ytdnavl="0.0";
    @SerializedName("MTD_QTY")
    private String mtd="0.0";
    @SerializedName("MTD_NVAL")
    private String mtdnavl="0.0";
    @SerializedName("LMTD_QTY")
    private String lmtd="0.0";
    @SerializedName("LMTD_NVAL")
    private String lmtdnval="0.0";
    @SerializedName("NO_DAYS")
    private String ageing="0.0";
    @SerializedName("INTRANSIT")
    private String intransit="0.0";
    @SerializedName("SALE_AGEING")
    private String saleageing="0.0";
    @SerializedName("STOCK_AGEING")
    private String stockageing="0.0";
    @SerializedName("SIZE1")
    private String size1="";
    @SerializedName("IRODE")
    private String irode="";
    @SerializedName("STR_MTD")
    private String str_mtd="0.0";
    @SerializedName("STR_L7")
    private String str_l7="0.0";
    @SerializedName("STR_L30")
    private String str_l30="0.0";
    @SerializedName("SALE_PSF_MTD")
    private String sale_psf_mtd="0.0";
    @SerializedName("SALE_PSF_L7")
    private String sale_psf_l7="0.0";
    @SerializedName("SALE_PSF_L30")
    private String sale_psf_l30="0.0";
    @SerializedName("GP_PSF_MTD")
    private String gp_psf_mtd="0.0";
    @SerializedName("GP_PSF_L7")
    private String gp_psf_l7="0.0";
    @SerializedName("GP_PSF_L30")
    private String gp_psf_l30="0.0";
    private String total="0.0";
    @SerializedName("TD_QTY")
    private String td="0.0";

    public double getLmtd() {
        if (lmtd.equals("")||  lmtd.equals("-"))
            return 0.00;
        else
            return Double.parseDouble(lmtd);

    }

    public RetailModal() {
    }

    protected RetailModal(Parcel in) {
        matDesc = in.readString();
        article = in.readString();
        merchandise = in.readString();
        intransit = in.readString();
        total = in.readString();
        vo1 = in.readString();
        vo4 = in.readString();
        one = in.readString();
        three = in.readString();
        msa = in.readString();
        td = in.readString();
        ytd = in.readString();
        mtd = in.readString();
        ageing = in.readString();
        str_mtd = in.readString();
        irode = in.readString();
        color = in.readString();
    }

    public static final Creator<RetailModal> CREATOR = new Creator<RetailModal>() {
        @Override
        public RetailModal createFromParcel(Parcel in) {
            return new RetailModal(in);
        }

        @Override
        public RetailModal[] newArray(int size) {
            return new RetailModal[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(matDesc);
        parcel.writeString(article);
        parcel.writeString(merchandise);
        parcel.writeString(intransit);
        parcel.writeString(total);
        parcel.writeString(vo1);
        parcel.writeString(vo4);
        parcel.writeString(one);
        parcel.writeString(three);
        parcel.writeString(msa);
        parcel.writeString(td);
        parcel.writeString(ytd);
        parcel.writeString(mtd);
        parcel.writeString(ageing);
        parcel.writeString(str_mtd);
        parcel.writeString(irode);
        parcel.writeString(color);
    }


    public static class Ageing implements Serializable,Parcelable
    {
        @SerializedName("STOCK_AGEING")
        String stockAgeing="0.0";
        @SerializedName("SALE_AGEING")
        String salesAgeing="0.0";
        @SerializedName("MATNR")
        String article="";
        @SerializedName("MAKTX")
        String desc="";
        public Ageing( ) {

        }


        protected Ageing(Parcel in) {
            stockAgeing = in.readString();
            salesAgeing = in.readString();
            article = in.readString();
            desc = in.readString();
        }

        public double getStockAgeing() {
            if(stockAgeing.equals("") || stockAgeing.equals("-"))
                return 0.00;
            return Double.parseDouble(stockAgeing);
        }

        public double getSalesAgeing() {
            if(salesAgeing.equals("") || salesAgeing.equals("-"))
                return 0.00;
            return Double.parseDouble(salesAgeing);

        }


        public String getDesc() {

            if (desc.equals(""))
                return "-";
            else
                return desc;
        }

        public String getArticle() {
            return article.replaceFirst("^0+(?!$)", "");

        }

        public static Creator<RetailModal.Ageing> getCREATOR() {
            return CREATOR;
        }

        public static final Creator<RetailModal.Ageing> CREATOR = new Creator<RetailModal.Ageing>() {
            @Override
            public RetailModal.Ageing createFromParcel(Parcel in) {
                return new RetailModal.Ageing(in);
            }

            @Override
            public RetailModal.Ageing[] newArray(int size) {
                return new RetailModal.Ageing[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(stockAgeing);
            parcel.writeString(salesAgeing);
            parcel.writeString(article);
            parcel.writeString(desc);
        }
    }

    public String getMatDesc() {

        if (matDesc.equals(""))
            return "-";
        else
            return matDesc;
    }

    public String getArticle() {
        return article.replaceFirst("^0+(?!$)", "");

    }

    public String getMerchandise() {
        return merchandise;

    }

    public double getIntransit() {
        if (intransit.equals(""))
            return 0.00;
        else
            return Double.parseDouble(intransit.trim());
    }

    public double getTotal() {
        if (total.equals(""))
            return 0.00;
        else
            return Double.parseDouble(total);
    }

    public double getVo1() {
        if (vo1.equals(""))
            return 0.00;
        else
            return Double.parseDouble(vo1);
    }

    public double getVo4() {
        if (vo4.equals(""))
            return 0.00;
        else
            return Double.parseDouble(vo4);
    }

    public double getOne() {
        if (one.equals(""))
            return 0.00;
        else
            return Double.parseDouble(one);
    }

    public double getThree() {
        if (three.equals(""))
            return 0.00;
        else
            return Double.parseDouble(three);
    }

    public double getMsa() {
        if (msa.equals(""))
            return 0.00;
        else
            return Double.parseDouble(msa);
    }

    public double getTd() {
        if (td.equals("")||  td.equals("-"))
            return 0.00;
        else
            return Double.parseDouble(td);
    }

    public double getYtd() {
        if (ytd.equals("")||  ytd.equals("-"))
            return 0.00;
        else
            return Double.parseDouble(ytd);
    }

    public double getMtd() {
        if (mtd.equals("")||  mtd.equals("-"))
            return 0.00;
        else
            return Double.parseDouble(mtd);
    }

    public String getAgeing() {
        return ageing;
    }

    public void setMatDesc(String matDesc) {
        this.matDesc = matDesc;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public void setMerchandise(String merchandise) {
        this.merchandise = merchandise;
    }

    public void setIntransit(String intransit) {
        this.intransit = intransit;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setVo1(String vo1) {
        this.vo1 = vo1;
    }

    public void setVo4(String vo4) {
        this.vo4 = vo4;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public void setThree(String three) {
        this.three = three;
    }

    public void setMsa(String msa) {
        this.msa = msa;
    }

    public void setTd(String td) {
        this.td = td;
    }

    public void setYtd(String ytd) {
        this.ytd = ytd;
    }

    public void setMtd(String mtd) {
        this.mtd = mtd;
    }

    public void setLmtd(String lmtd) {
        this.lmtd = lmtd;
    }

    public void setAgeing(String ageing) {
        this.ageing = ageing;
    }

    public void setStr_mtd(String str_mtd) {
        this.str_mtd = str_mtd;
    }

    public static Creator<RetailModal> getCREATOR() {
        return CREATOR;
    }

    public String getIrode() {
        return irode;
    }

    public void setIrode(String irode) {
        this.irode = irode;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLgnum() {
        return lgnum;
    }

    public void setLgnum(String lgnum) {
        this.lgnum = lgnum;
    }

    public String getSatnr() {
        return satnr;
    }

    public void setSatnr(String satnr) {
        this.satnr = satnr;
    }

    public String getWerks() {
        return werks;
    }

    public void setWerks(String werks) {
        this.werks = werks;
    }

    public String getEan11() {
        return ean11;
    }

    public void setEan11(String ean11) {
        this.ean11 = ean11;
    }

    public String getYtdnavl() {
        return ytdnavl;
    }

    public void setYtdnavl(String ytdnavl) {
        this.ytdnavl = ytdnavl;
    }

    public String getMtdnavl() {
        return mtdnavl;
    }

    public void setMtdnavl(String mtdnavl) {
        this.mtdnavl = mtdnavl;
    }

    public String getLmtdnval() {
        return lmtdnval;
    }

    public void setLmtdnval(String lmtdnval) {
        this.lmtdnval = lmtdnval;
    }

    public String getSaleageing() {
        return saleageing;
    }

    public void setSaleageing(String saleageing) {
        this.saleageing = saleageing;
    }

    public String getStockageing() {
        return stockageing;
    }

    public void setStockageing(String stockageing) {
        this.stockageing = stockageing;
    }

    public String getSize1() {
        return size1;
    }

    public void setSize1(String size1) {
        this.size1 = size1;
    }

    public String getStr_mtd() {
        return str_mtd;
    }

    public String getStr_l7() {
        return str_l7;
    }

    public void setStr_l7(String str_l7) {
        this.str_l7 = str_l7;
    }

    public String getStr_l30() {
        return str_l30;
    }

    public void setStr_l30(String str_l30) {
        this.str_l30 = str_l30;
    }

    public String getSale_psf_mtd() {
        return sale_psf_mtd;
    }

    public void setSale_psf_mtd(String sale_psf_mtd) {
        this.sale_psf_mtd = sale_psf_mtd;
    }

    public String getSale_psf_l7() {
        return sale_psf_l7;
    }

    public void setSale_psf_l7(String sale_psf_l7) {
        this.sale_psf_l7 = sale_psf_l7;
    }

    public String getSale_psf_l30() {
        return sale_psf_l30;
    }

    public void setSale_psf_l30(String sale_psf_l30) {
        this.sale_psf_l30 = sale_psf_l30;
    }

    public String getGp_psf_mtd() {
        return gp_psf_mtd;
    }

    public void setGp_psf_mtd(String gp_psf_mtd) {
        this.gp_psf_mtd = gp_psf_mtd;
    }

    public String getGp_psf_l7() {
        return gp_psf_l7;
    }

    public void setGp_psf_l7(String gp_psf_l7) {
        this.gp_psf_l7 = gp_psf_l7;
    }

    public String getGp_psf_l30() {
        return gp_psf_l30;
    }

    public void setGp_psf_l30(String gp_psf_l30) {
        this.gp_psf_l30 = gp_psf_l30;
    }

    public String getVo2() {
        return vo2;
    }

    public void setVo2(String vo2) {
        this.vo2 = vo2;
    }
}
