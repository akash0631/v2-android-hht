package com.v2retail.util;

import android.content.Context;
import android.content.pm.PackageManager;

public class CameraCheck {

	public static boolean isCameraAvailable(Context context) {
		return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
	}
}
