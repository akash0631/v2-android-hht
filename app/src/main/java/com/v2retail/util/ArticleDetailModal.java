package com.v2retail.util;

public class ArticleDetailModal {
	private String matNR;
	private String matDesc;
	private String article;
	private String ean;
	private String vo1;
	private String total;
	private String one;
	private String four;
	private String three;
	private String five;
	private String six;
	private String seven;
	private String eight;
	private String msa;
	private String nine;
	private String ten;
	private int thumbnail;


public ArticleDetailModal(){}

	public ArticleDetailModal(String matNR, String matDesc, String article, String ean, String vo1, String total, String one, String four, String three, String five, String six, String seven, String eight, String msa, String nine, String ten, int thumbnail) {
		this.matNR = matNR;
		this.matDesc = matDesc;
		this.article = article;
		this.ean = ean;
		this.vo1 = vo1;
		this.total = total;
		this.one = one;
		this.four = four;
		this.three = three;
		this.five = five;
		this.six = six;
		this.seven = seven;
		this.eight = eight;
		this.msa = msa;
		this.nine = nine;
		this.ten = ten;
		this.thumbnail = thumbnail;
	}

	public ArticleDetailModal(String matNR, String matDesc, String article, String ean, String vo1, String total, String one, String four, String three, String five, String six, String seven, String eight, String msa, String nine, String ten) {
		this.matNR = matNR;
		this.matDesc = matDesc;
		this.article = article;
		this.ean = ean;
		this.vo1 = vo1;
		this.total = total;
		this.one = one;
		this.four = four;
		this.three = three;
		this.five = five;
		this.six = six;
		this.seven = seven;
		this.eight = eight;
		this.msa = msa;
		this.nine = nine;
		this.ten = ten;
	}

	public int getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(int thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getMatNR() {
		return matNR;
	}

	public void setMatNR(String matNR) {
		this.matNR = matNR;
	}

	public String getMatDesc() {
		return matDesc;
	}

	public void setMatDesc(String matDesc) {
		this.matDesc = matDesc;
	}
	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public String getEan() {
		return ean;
	}

	public void setEan(String ean) {
		this.ean = ean;
	}

	public String getVo1() {
		return vo1;
	}

	public void setVo1(String vo1) {
		this.vo1 = vo1;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getOne() {
		return one;
	}

	public void setOne(String one) {
		this.one = one;
	}

	public String getFour() {
		return four;
	}

	public void setFour(String four) {
		this.four = four;
	}

	public String getThree() {
		return three;
	}

	public void setThree(String three) {
		this.three = three;
	}

	public String getFive() {
		return five;
	}

	public void setFive(String five) {
		this.five = five;
	}

	public String getSix() {
		return six;
	}

	public void setSix(String six) {
		this.six = six;
	}

	public String getSeven() {
		return seven;
	}

	public void setSeven(String seven) {
		this.seven = seven;
	}

	public String getEight() {
		return eight;
	}

	public void setEight(String eight) {
		this.eight = eight;
	}

	public String getMsa() {
		return msa;
	}

	public void setMsa(String msa) {
		this.msa = msa;
	}

	public String getNine() {
		return nine;
	}

	public void setNine(String nine) {
		this.nine = nine;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}
}
