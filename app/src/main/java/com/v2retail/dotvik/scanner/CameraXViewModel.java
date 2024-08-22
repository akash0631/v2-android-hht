package com.v2retail.dotvik.scanner;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.ExecutionException;

public final class CameraXViewModel extends AndroidViewModel {
    private MutableLiveData cameraProviderLiveData;

    public final LiveData getProcessCameraProvider() {
        if (this.cameraProviderLiveData == null) {
            this.cameraProviderLiveData = new MutableLiveData();
            ListenableFuture var10000 = ProcessCameraProvider.getInstance((Context)this.getApplication());
            final ListenableFuture cameraProviderFuture = var10000;
            cameraProviderFuture.addListener((Runnable)(new Runnable() {
                public final void run() {
                    try {
                        MutableLiveData var10000 = CameraXViewModel.this.cameraProviderLiveData;
                        var10000.setValue(cameraProviderFuture.get());
                    } catch (ExecutionException var2) {
                        Log.e("V2 CameraX", "Unhandled exception", (Throwable)var2);
                    } catch (InterruptedException var3) {
                        Log.e("V2 CameraX", "Unhandled exception", (Throwable)var3);
                    }

                }
            }), ContextCompat.getMainExecutor((Context)this.getApplication()));
        }

        MutableLiveData var2 = this.cameraProviderLiveData;
        return (LiveData)var2;
    }

    public CameraXViewModel(Application application) {
        super(application);
    }

    // $FF: synthetic method
    public static final void access$setCameraProviderLiveData$p(CameraXViewModel $this, MutableLiveData var1) {
        $this.cameraProviderLiveData = var1;
    }
}
