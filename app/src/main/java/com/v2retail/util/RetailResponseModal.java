package com.v2retail.util;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class RetailResponseModal implements Serializable, Parcelable {


	//info*/
	@SerializedName("ET_DATA_SUM")
	private ArrayList<RetailModal> retailModals;
	@SerializedName("ET_DATA")
	private ArrayList<RetailModal> etData;

	public ArrayList<RetailModal> getRetailModals() {
		return retailModals;
	}
	public ArrayList<RetailModal> getRetailEtData() {
		return etData;
	}

	protected RetailResponseModal(Parcel in) {
		retailModals = in.createTypedArrayList(RetailModal.CREATOR);
		etData = in.createTypedArrayList(RetailModal.CREATOR);
	}

	public static final Creator<RetailResponseModal> CREATOR = new Creator<RetailResponseModal>() {
		@Override
		public RetailResponseModal createFromParcel(Parcel in) {
			return new RetailResponseModal(in);
		}

		@Override
		public RetailResponseModal[] newArray(int size) {
			return new RetailResponseModal[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeTypedList(retailModals);
	}
}