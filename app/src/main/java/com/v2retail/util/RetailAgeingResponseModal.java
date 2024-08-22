package com.v2retail.util;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class RetailAgeingResponseModal implements Serializable, Parcelable {


	//info*/
	@SerializedName("ET_DATA")
	private ArrayList<RetailModal.Ageing> ageingArrayList;

	protected RetailAgeingResponseModal(Parcel in) {
		ageingArrayList = in.createTypedArrayList(RetailModal.Ageing.CREATOR);
	}

	public static final Creator<RetailAgeingResponseModal> CREATOR = new Creator<RetailAgeingResponseModal>() {
		@Override
		public RetailAgeingResponseModal createFromParcel(Parcel in) {
			return new RetailAgeingResponseModal(in);
		}

		@Override
		public RetailAgeingResponseModal[] newArray(int size) {
			return new RetailAgeingResponseModal[size];
		}
	};

	public ArrayList<RetailModal.Ageing> getAgeingArrayList() {
		return ageingArrayList;
	}

	public void setAgeingArrayList(ArrayList<RetailModal.Ageing> ageingArrayList) {
		this.ageingArrayList = ageingArrayList;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeTypedList(ageingArrayList);
	}
}