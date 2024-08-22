//package com.v2retail.dotvik.scanner;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.ArrayAdapter;
//
//import androidx.fragment.app.Fragment;
//
//import com.symbol.emdk.EMDKManager;
//import com.symbol.emdk.EMDKResults;
//import com.symbol.emdk.EMDKManager.EMDKListener;
//import com.symbol.emdk.EMDKManager.FEATURE_TYPE;
//import com.symbol.emdk.barcode.BarcodeManager;
//import com.symbol.emdk.barcode.BarcodeManager.ConnectionState;
//import com.symbol.emdk.barcode.BarcodeManager.ScannerConnectionListener;
//import com.symbol.emdk.barcode.ScanDataCollection;
//import com.symbol.emdk.barcode.Scanner;
//import com.symbol.emdk.barcode.ScannerConfig;
//import com.symbol.emdk.barcode.ScannerException;
//import com.symbol.emdk.barcode.ScannerInfo;
//import com.symbol.emdk.barcode.ScannerResults;
//import com.symbol.emdk.barcode.ScanDataCollection.ScanData;
//import com.symbol.emdk.barcode.Scanner.DataListener;
//import com.symbol.emdk.barcode.Scanner.StatusListener;
//import com.symbol.emdk.barcode.Scanner.TriggerType;
//import com.symbol.emdk.barcode.StatusData.ScannerStates;
//import com.symbol.emdk.barcode.StatusData;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//public class BaseScanner extends Fragment implements EMDKManager.EMDKListener, Scanner.DataListener, StatusListener, ScannerConnectionListener {
//    String TAG = "BaseScanner";
//
//    // Zebra SDK
//    private EMDKManager emdkManager = null;
//    private BarcodeManager barcodeManager = null;
//    private Scanner scanner = null;
//    private int scannerIndex = 0;
//    private int defaultIndex = 0;
//    private int triggerIndex = 0;
//
//    List<ScannerInfo> deviceList = null;
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        deviceList = new ArrayList<ScannerInfo>();
//
//        EMDKResults results = EMDKManager.getEMDKManager(getActivity().getApplicationContext(), this);
//        if (results.statusCode != EMDKResults.STATUS_CODE.SUCCESS) {
//            Log.d(TAG, "Status: " + "EMDKManager object request failed!");
//            return;
//        }
//    }
//
//
//    @Override
//    public void onOpened(EMDKManager emdkManager) {
//        Log.d(TAG, "Status: " + "EMDK open success!");
//        this.emdkManager = emdkManager;
//        // Acquire the barcode manager resources
//        barcodeManager = (BarcodeManager) emdkManager.getInstance(FEATURE_TYPE.BARCODE);
//
//        // Add connection listener
//        if (barcodeManager != null) {
//            barcodeManager.addConnectionListener(this);
//        }
//        // Enumerate scanner devices
//        enumerateScannerDevices();
//
//        // Set default scanner
//        // spinnerScannerDevices.setSelection(defaultIndex);
//
//        // Initialize scanner
//        initScanner();
//        setTrigger();
//        setDecoders();
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
//            // Release all the resources
//            emdkManager.release();
//            emdkManager = null;
//        }
//    }
//
//    @Override
//    public void onData(ScanDataCollection scanDataCollection) {
//
//        if ((scanDataCollection != null) && (scanDataCollection.getResult() == ScannerResults.SUCCESS)) {
//            ArrayList <ScanData> scanData = scanDataCollection.getScanData();
//            for(ScanData data : scanData) {
//                final String dataString =  data.getData();
//                Log.d(TAG, "Scanned Data = " +  dataString);
//                if(mScanningFieldListener!=null) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            mScanningFieldListener.onScannedResult(dataString);
//                        }
//                    });
//                }
//            }
//        }
//    }
//
//
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//
//        // De-initialize scanner
//        deInitScanner();
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
//    public void onPause() {
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
//            emdkManager.release(FEATURE_TYPE.BARCODE);
//        }
//    }
//
//    @Override
//    public void onResume() {
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
//    @Override
//    public void onConnectionChange(ScannerInfo scannerInfo, ConnectionState connectionState) {
//
//    }
//
//    @Override
//    public void onStatus(StatusData statusData) {
//
//        ScannerStates state = statusData.getState();
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
//                    scanner.triggerType = TriggerType.HARD;
//                    break;
//                case 1: // Selected "SOFT"
//                    scanner.triggerType = TriggerType.SOFT_ALWAYS;
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
//    protected void startScan(ScanningFieldResultListener fieldResultListener) {
//
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
//    protected void stopScan(ScanningFieldResultListener fieldResultListener) {
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
//    ScanningFieldResultListener mScanningFieldListener;
//    public interface ScanningFieldResultListener {
//        void onScannedResult(String scannedValue);
//    }
//
//}
