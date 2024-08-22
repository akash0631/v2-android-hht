//package com.v2retail.dotvik;
//
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.InputFilter;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.View;
//import android.widget.EditText;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.symbol.emdk.EMDKManager;
//import com.symbol.emdk.EMDKResults;
//import com.symbol.emdk.barcode.BarcodeManager;
//import com.symbol.emdk.barcode.ScanDataCollection;
//import com.symbol.emdk.barcode.Scanner;
//import com.symbol.emdk.barcode.ScannerConfig;
//import com.symbol.emdk.barcode.ScannerException;
//import com.symbol.emdk.barcode.ScannerInfo;
//import com.symbol.emdk.barcode.ScannerResults;
//import com.symbol.emdk.barcode.StatusData;
//import com.v2retail.dotvik.scanner.BaseScanner;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//
//public class ScannerActivity extends AppCompatActivity  implements EMDKManager.EMDKListener,
//        BarcodeManager.ScannerConnectionListener, Scanner.DataListener, Scanner.StatusListener {
//
//    String TAG = "ScannerActivity";
//
//    private EMDKManager emdkManager = null;
//    private BarcodeManager barcodeManager = null;
//    private Scanner scanner = null;
//    private List<ScannerInfo> deviceList = null;
//
//    private int scannerIndex = 0; // Keep the selected scanner
//    private int defaultIndex = 0; // Keep the default scanner
//    private int triggerIndex = 0;
//    private int dataLength = 0;
//    private String statusString = "";
//
//    private String [] triggerStrings = {"HARD", "SOFT"};
//
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        EMDKResults results = EMDKManager.getEMDKManager(getApplicationContext(), this);
//        if (results.statusCode != EMDKResults.STATUS_CODE.SUCCESS) {
//            Log.d(TAG, "Status: " + "EMDKManager object request failed!");
//            return;
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//        // De-initialize scanner
//        deInitScanner();
//
//        // Remove connection listener
//        if (barcodeManager != null) {
//            barcodeManager.removeConnectionListener(this);
//            barcodeManager = null;
//        }
//
//        // Release all the resources
//        if (emdkManager != null) {
//            emdkManager.release();
//            emdkManager = null;
//        }
//
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        // The application is in background
//
//        // De-initialize scanner
//        deInitScanner();
//
//        // Remove connection listener
//        if (barcodeManager != null) {
//            barcodeManager.removeConnectionListener(this);
//            barcodeManager = null;
//            deviceList = null;
//        }
//
//        // Release the barcode manager resources
//        if (emdkManager != null) {
//            emdkManager.release(EMDKManager.FEATURE_TYPE.BARCODE);
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        // The application is in foreground
//
//        // Acquire the barcode manager resources
//        if (emdkManager != null) {
//            barcodeManager = (BarcodeManager) emdkManager.getInstance(EMDKManager.FEATURE_TYPE.BARCODE);
//
//            // Add connection listener
//            if (barcodeManager != null) {
//                barcodeManager.addConnectionListener(this);
//            }
//
//            // Enumerate scanner devices
//            enumerateScannerDevices();
//
//            // Set selected scanner
//            // spinnerScannerDevices.setSelection(scannerIndex);
//
//            // Initialize scanner
//            initScanner();
//            setTrigger();
//            setDecoders();
//        }
//    }
//
//    @Override
//    public void onOpened(EMDKManager emdkManager) {
//        Log.d(TAG, "Status: " + "EMDK open success!");
//        this.emdkManager = emdkManager;
//        // Acquire the barcode manager resources
//        barcodeManager = (BarcodeManager) emdkManager.getInstance(EMDKManager.FEATURE_TYPE.BARCODE);
//
//        // Add connection listener
//        if (barcodeManager != null) {
//            barcodeManager.addConnectionListener(this);
//        }
//
//        // Enumerate scanner devices
//        enumerateScannerDevices();
//    }
//
//    @Override
//    public void onClosed() {
//        if (emdkManager != null) {
//            // Remove connection listener
//            if (barcodeManager != null){
//                barcodeManager.removeConnectionListener(this);
//                barcodeManager = null;
//            }
//
//            // Release all the resources
//            emdkManager.release();
//            emdkManager = null;
//        }
//        Log.d(TAG, "Status: EMDK closed unexpectedly! Please close and restart the application.");
//    }
//
//
//    private void initScanner() {
//        if (scanner == null) {
//            if ((deviceList != null) && (deviceList.size() != 0)) {
//                scanner = barcodeManager.getDevice(deviceList.get(scannerIndex));
//            }
//            else {
//                Log.d(TAG, "Status: " + "Failed to get the specified scanner device! Please close and restart the application.");
//                return;
//            }
//
//            if (scanner != null) {
//                scanner.addDataListener(this);
//                scanner.addStatusListener(this);
//
//                try {
//                    scanner.enable();
//                } catch (ScannerException e) {
//                    Log.d(TAG, "Status: " + e.getMessage());
//                }
//            }else{
//                Log.d(TAG,"Status: " + "Failed to initialize the scanner device.");
//            }
//        }
//    }
//
//    private void deInitScanner() {
//        if (scanner != null) {
//            try {
//                scanner.cancelRead();
//                scanner.disable();
//            } catch (Exception e) {
//                Log.d(TAG, "Status: " + e.getMessage());
//            }
//
//            try {
//                scanner.removeDataListener(this);
//                scanner.removeStatusListener(this);
//            } catch (Exception e) {
//                Log.d(TAG, "Status: " + e.getMessage());
//            }
//
//            try{
//                scanner.release();
//            } catch (Exception e) {
//                Log.d(TAG, "Status: " + e.getMessage());
//            }
//
//            scanner = null;
//        }
//    }
//
//    private void enumerateScannerDevices() {
//        if (barcodeManager != null) {
//            List<String> friendlyNameList = new ArrayList<String>();
//            int spinnerIndex = 0;
//
//            deviceList = barcodeManager.getSupportedDevicesInfo();
//
//            if ((deviceList != null) && (deviceList.size() != 0)) {
//
//                Iterator<ScannerInfo> it = deviceList.iterator();
//                while(it.hasNext()) {
//                    ScannerInfo scnInfo = it.next();
//                    friendlyNameList.add(scnInfo.getFriendlyName());
//                    if(scnInfo.isDefaultScanner()) {
//                        defaultIndex = spinnerIndex;
//                        // setting scannerIndex as default (Auto)
//                        scannerIndex = defaultIndex;
//                    }
//                    ++spinnerIndex;
//                }
//            }
//            else {
//                Log.d(TAG, "Status: " + "Failed to get the list of supported scanner devices! Please close and restart the application.");
//            }
//
//            /*
//            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, friendlyNameList);
//            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spinnerScannerDevices.setAdapter(spinnerAdapter);
//             */
//        }
//    }
//
//
//
//    @Override
//    public void onConnectionChange(ScannerInfo scannerInfo, BarcodeManager.ConnectionState connectionState) {
//
//    }
//
//    @Override
//    public void onStatus(StatusData statusData) {
//
//        StatusData.ScannerStates state = statusData.getState();
//        String statusString = "";
//        switch(state) {
//            case IDLE:
//                statusString = statusData.getFriendlyName()+" is enabled and idle...";
//
//
//                try {
//                    // An attempt to use the scanner continuously and rapidly (with a delay < 100 ms between scans)
//                    // may cause the scanner to pause momentarily before resuming the scanning.
//                    // Hence add some delay (>= 100ms) before submitting the next read.
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    scanner.read();
//                } catch (ScannerException e) {
//                    statusString = e.getMessage();
//                }
//
//
//                break;
//            case WAITING:
//                statusString = "Scanner is waiting for trigger press...";
//
//                break;
//            case SCANNING:
//                statusString = "Scanning...";
//
//                break;
//            case DISABLED:
//                statusString = statusData.getFriendlyName()+" is disabled.";
//
//                break;
//            case ERROR:
//                statusString = "An error has occurred.";
//                break;
//            default:
//                break;
//        }
//
//        Log.d(TAG, "statusString = " + statusString);
//    }
//
//
//    private void setTrigger() {
//
//        if (scanner == null) {
//            initScanner();
//        }
//
//        if (scanner != null) {
//            switch (triggerIndex) {
//                case 0: // Selected "HARD", default
//                    scanner.triggerType = com.symbol.emdk.barcode.Scanner.TriggerType.HARD;
//                    break;
//                case 1: // Selected "SOFT"
//                    scanner.triggerType = com.symbol.emdk.barcode.Scanner.TriggerType.SOFT_ALWAYS;
//                    break;
//            }
//        }
//    }
//
//    private void setDecoders() {
//
//        if (scanner == null) {
//            initScanner();
//        }
//
//        if ((scanner != null) && (scanner.isEnabled())) {
//            try {
//
//                ScannerConfig config = scanner.getConfig();
//
//                config.decoderParams.ean13.enabled = true;
//                config.decoderParams.ean8.enabled = true;
//                config.decoderParams.code39.enabled = true;
//                config.decoderParams.code128.enabled = true;
//
//                /*
//                // Set EAN8
//                if(checkBoxEAN8.isChecked())
//                    config.decoderParams.ean8.enabled = true;
//                else
//                    config.decoderParams.ean8.enabled = false;
//
//                // Set EAN13
//                if(checkBoxEAN13.isChecked())
//                    config.decoderParams.ean13.enabled = true;
//                else
//                    config.decoderParams.ean13.enabled = false;
//
//                // Set Code39
//                if(checkBoxCode39.isChecked())
//                    config.decoderParams.code39.enabled = true;
//                else
//                    config.decoderParams.code39.enabled = false;
//
//                //Set Code128
//                if(checkBoxCode128.isChecked())
//                    config.decoderParams.code128.enabled = true;
//                else
//                    config.decoderParams.code128.enabled = false;
//                 */
//
//                scanner.setConfig(config);
//            } catch (ScannerException e) {
//
//                Log.d(TAG, "Status: " + e.getMessage());
//            }
//        }
//    }
//
//    @Override
//    public void onData(ScanDataCollection scanDataCollection) {
//
//        if ((scanDataCollection != null) && (scanDataCollection.getResult() == ScannerResults.SUCCESS)) {
//            ArrayList <ScanDataCollection.ScanData> scanData = scanDataCollection.getScanData();
//            for(ScanDataCollection.ScanData data : scanData) {
//                final String dataString =  data.getData();
//                Log.d(TAG, "Scanned Data = " +  dataString);
//                if(mScanningFieldListener!=null) {
//                    ScannerActivity.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            mScanningFieldListener.onScannedResult( ScannerActivity.this.getCurrentFocus(), dataString);
//                        }
//                    });
//                } else {
//
//                    ScannerActivity.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            View view = ScannerActivity.this.getCurrentFocus();
//                            if(view instanceof EditText ) {
//                                EditText editText = (EditText) view;
//
//                                if (editText.getKeyListener() != null) {
//                                    if (!editText.getKeyListener().onKeyUp(editText, null, KeyEvent.KEYCODE_BUTTON_R1, new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BUTTON_R1))) {
//                                        editText.setText(dataString);
//                                    }
//                                } else {
//                                    editText.setText(dataString);
//                                }
//                            }
//                        }
//                    });
//                }
//            }
//        }
//    }
//
//    ScanningFieldResultListener mScanningFieldListener;
//    public interface ScanningFieldResultListener {
//        // It should return on the current focussable view
//        void onScannedResult(View view, String scannedValue);
//    }
//
//
//    public void startScan(ScanningFieldResultListener fieldResultListener) {
//
//        Log.d(TAG, "startScan()");
//        mScanningFieldListener = fieldResultListener;
//
//        if(scanner == null) {
//            initScanner();
//        }
//
//        if (scanner != null) {
//
//            try {
//                if(scanner.isEnabled())
//                {
//                    // Submit a new read.
//                    scanner.read();
//                    /*
//                    if (checkBoxContinuous.isChecked())
//                        bContinuousMode = true;
//                    else
//                        bContinuousMode = false;
//
//                    new AsyncUiControlUpdate().execute(false);
//                     */
//                }
//                else
//                {
//                    Log.d(TAG, "Status: Scanner is not enabled");
//                }
//            } catch (ScannerException e) {
//                Log.d(TAG, "Status: " + e.getMessage());
//            }
//        }
//
//    }
//
//    public void stopScan(ScanningFieldResultListener fieldResultListener) {
//
//        Log.d(TAG, "stopScan()");
//
//        mScanningFieldListener = null;
//
//        if (scanner != null) {
//            try {
//                // Reset continuous flag
//                // bContinuousMode = false;
//                // Cancel the pending read.
//                scanner.cancelRead();
//                // new AsyncUiControlUpdate().execute(true);
//            } catch (ScannerException e) {
//                Log.d(TAG, "Status: " + e.getMessage());
//            }
//        }
//    }
//
//}
