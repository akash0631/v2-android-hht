package com.v2retail.util;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RetailModalOld implements Serializable, Parcelable {


//info*/
	@SerializedName("MAKTX")
	private String matDesc="";
	@SerializedName("MATNR")
	private String article="";
	@SerializedName("MATKL")
	private String merchandise="";
	@SerializedName("IRODE")
	private String irode="";
	@SerializedName("COLOR")
	private String color="";
	@SerializedName("INTRANSIT")
	private String intransit="0.0";

//stock - 11 args (1 is not getting displayed)
	private String total="0.0";
	@SerializedName("V01")
	private String vo1="0.0";
	@SerializedName("V04")
	private String vo4="0.0";
	@SerializedName("0001")
	private String one="0.0";
	@SerializedName("0003")
	private String three="0.0";
	@SerializedName("0004")
	private String four="0.0";
	@SerializedName("0005")
	private String five="0.0";

	private String six="0.0";
	@SerializedName("0007")
	private String seven="0.0";
	@SerializedName("0008")
	private String eight="0.0";
	@SerializedName("MSA")
	private String msa="0.0";
	@SerializedName("0009")
	private String nine="0.0";
	@SerializedName("0010")
	private String ten="0.0";
//sales
	@SerializedName("TD_QTY")
	private String td="0.0";
	@SerializedName("YTD_QTY")
	private String ytd="0.0";
	@SerializedName("MTD_QTY")
	private String mtd="0.0";
	@SerializedName("LMTD_QTY")
	private String lmtd="0.0";
	@SerializedName("NO_DAYS")
	private String ageing="0.0";

	private String str_td="0.0";
	private String STR_TDf="0.0";
	private String str_mtd="0.0";
	private String str_mtdf="0.0";
	private String str_ytd="0.0";

	public double getLmtd() {
		if (lmtd.equals("")||  lmtd.equals("-"))
			return 0.00;
		else
			return Double.parseDouble(lmtd);

	}

/*
	@SerializedName("ET_DATA")
	private ArrayList<RetailModal.Ageing> ageingList;

	public ArrayList<Ageing> getAgeingList() {
		return ageingList;
	}

	public void setAgeingList(ArrayList<Ageing> ageingList) {
		this.ageingList = ageingList;
	}*/

	public RetailModalOld() {
	}

	protected RetailModalOld(Parcel in) {
		matDesc = in.readString();
		article = in.readString();
		merchandise = in.readString();
		intransit = in.readString();
		total = in.readString();
		vo1 = in.readString();
		vo4 = in.readString();
		one = in.readString();
		three = in.readString();
		four = in.readString();
		five = in.readString();
		six = in.readString();
		seven = in.readString();
		eight = in.readString();
		msa = in.readString();
		nine = in.readString();
		ten = in.readString();
		td = in.readString();
		ytd = in.readString();
		mtd = in.readString();
		ageing = in.readString();
		str_td = in.readString();
		STR_TDf = in.readString();
		str_mtd = in.readString();
		str_mtdf = in.readString();
		str_ytd = in.readString();
		irode = in.readString();
		//ageingList = in.createTypedArrayList(Ageing.CREATOR);
	}

	public static final Creator<RetailModalOld> CREATOR = new Creator<RetailModalOld>() {
		@Override
		public RetailModalOld createFromParcel(Parcel in) {
			return new RetailModalOld(in);
		}

		@Override
		public RetailModalOld[] newArray(int size) {
			return new RetailModalOld[size];
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
		parcel.writeString(four);
		parcel.writeString(five);
		parcel.writeString(six);
		parcel.writeString(seven);
		parcel.writeString(eight);
		parcel.writeString(msa);
		parcel.writeString(nine);
		parcel.writeString(ten);
		parcel.writeString(td);
		parcel.writeString(ytd);
		parcel.writeString(mtd);
		parcel.writeString(ageing);
		parcel.writeString(str_td);
		parcel.writeString(STR_TDf);
		parcel.writeString(str_mtd);
		parcel.writeString(str_mtdf);
		parcel.writeString(str_ytd);
		parcel.writeString(irode);
		//parcel.writeTypedList(ageingList);
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

		public static Creator<Ageing> getCREATOR() {
			return CREATOR;
		}

		public static final Creator<Ageing> CREATOR = new Creator<Ageing>() {
			@Override
			public Ageing createFromParcel(Parcel in) {
				return new Ageing(in);
			}

			@Override
			public Ageing[] newArray(int size) {
				return new Ageing[size];
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

	public double getFour() {
	 if (four.equals(""))
			return 0.00;
		else
			return Double.parseDouble(four);
	}

	public double getFive() {
		 if (five.equals(""))
			return 0.00;
		else
			return Double.parseDouble(five);
	}

	public double getSix() {
		 	if (six.equals(""))
			return 0.00;
		else
			return Double.parseDouble(six);
	}

	public double getSeven() {
	 if (seven.equals(""))
			return 0.00;
		else
			return Double.parseDouble(seven);
	}

	public double getEight() {
		 	if (eight.equals(""))
			return 0.00;
		else
			return Double.parseDouble(eight);
	}

	public double getMsa() {
	 	if (msa.equals(""))
			return 0.00;
		else
			return Double.parseDouble(msa);
	}

	public double getNine() {
		 if (nine.equals(""))
			return 0.00;
		else
			return Double.parseDouble(nine);
	}

	public double getTen() {
	 	if (ten.equals(""))
			return 0.00;
		else
			return Double.parseDouble(ten);
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

	public double getStr_td() {
		if (str_td.equals("") ||  str_td.equals("-"))
			return 0.00;
		else
			return Double.parseDouble(str_td);
	}

	public double getSTR_TDf() {
		 if (STR_TDf.equals("")||  STR_TDf.equals("-"))
			return 0.00;
		else
			return Double.parseDouble(STR_TDf);
	}

	public double getStr_mtd() {
	 if (str_mtd.equals("")||  str_mtd.equals("-"))
			return 0.00;
		else
			return Double.parseDouble(str_mtd);
	}

	public double getStr_mtdf() {
	 if (str_mtdf.equals("")||  str_mtdf.equals("-"))
			return 0.00;
		else
			return Double.parseDouble(str_mtdf);
	}

	public double getStr_ytd() {
		 if (str_ytd.equals("")||  str_ytd.equals("-"))
			return 0.00;
		else
			return Double.parseDouble(str_ytd);
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

	public void setFour(String four) {
		this.four = four;
	}

	public void setFive(String five) {
		this.five = five;
	}

	public void setSix(String six) {
		this.six = six;
	}

	public void setSeven(String seven) {
		this.seven = seven;
	}

	public void setEight(String eight) {
		this.eight = eight;
	}

	public void setMsa(String msa) {
		this.msa = msa;
	}

	public void setNine(String nine) {
		this.nine = nine;
	}

	public void setTen(String ten) {
		this.ten = ten;
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

	public void setStr_td(String str_td) {
		this.str_td = str_td;
	}

	public void setSTR_TDf(String STR_TDf) {
		this.STR_TDf = STR_TDf;
	}

	public void setStr_mtd(String str_mtd) {
		this.str_mtd = str_mtd;
	}

	public void setStr_mtdf(String str_mtdf) {
		this.str_mtdf = str_mtdf;
	}

	public void setStr_ytd(String str_ytd) {
		this.str_ytd = str_ytd;
	}

	public static Creator<RetailModalOld> getCREATOR() {
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
}
