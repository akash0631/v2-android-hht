package com.v2retail.dotvik.dc;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.rscja.deviceapi.RFIDWithUHFUART;
import com.rscja.deviceapi.entity.UHFTAGInfo;
import com.v2retail.dotvik.R;
import com.v2retail.util.FileImport;

import java.util.ArrayList;
import java.util.HashMap;

public class HU_RFID_Scan_Fragment extends Fragment {

    private boolean loopFlag = false;
    Handler handler;
    private ArrayList<HashMap<String, String>> tagList;
    SimpleAdapter adapter;
    Button BtClear;
    TextView tv_count;
    Button BtInventory;
    ListView LvTags;
    private HashMap<String, String> map;
    public RFIDWithUHFUART mReader;
    int statusCodeButton=0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("Debug", "HU_RFID_Scan_Fragment.onCreateView");
        return inflater.inflate(R.layout.hu_rfid_scan_poc, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i("Debug", "HU_RFID_Scan_Fragment.onActivityCreated");
        try {
            initUHF();
            initSound();
        }catch (Exception e){
            e.printStackTrace();
        }
        super.onActivityCreated(savedInstanceState);
        tagList = new ArrayList<HashMap<String, String>>();
        BtClear = (Button) getView().findViewById(R.id.BtClear);
        tv_count = (TextView) getView().findViewById(R.id.tv_count);
        BtInventory = (Button) getView().findViewById(R.id.BtInventory);
        LvTags = (ListView) getView().findViewById(R.id.LvTags);

        adapter = new SimpleAdapter(getActivity(), tagList, R.layout.listtag_items,
                new String[]{"tagUii", "tagLen", "tagCount", "tagRssi"},
                new int[]{R.id.TvTagUii, R.id.TvTagLen, R.id.TvTagCount,
                        R.id.TvTagRssi});

        BtClear.setOnClickListener(new BtClearClickListener());
        BtInventory.setOnClickListener(new BtInventoryClickListener());

        LvTags.setAdapter(adapter);
        clearData();
        Log.i("Debug", "HU_RFID_Scan_Fragment.EtCountOfTags=" + tv_count.getText());
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String result = msg.obj + "";
                String[] strs = result.split("@");
                addEPCToList(strs[0], strs[1]);
                playSound(1);
            }
        };
    }

    public void initUHF() {
        try {
            mReader = RFIDWithUHFUART.getInstance();
        } catch (Exception ex) {
            Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        if (mReader != null) {
                new InitTask().execute();
        }
    }

    @Override
    public void onPause() {
        Log.i("Debug", "HU_RFID_Scan_Fragment.onPause");
        super.onPause();
        stopInventory();
    }

    private void addEPCToList(String epc, String rssi) {
        if (!TextUtils.isEmpty(epc)) {
            int index = checkIsExist(epc);

            map = new HashMap<String, String>();

            map.put("tagUii", epc);
            map.put("tagCount", String.valueOf(1));
            map.put("tagRssi", rssi);


            if (index == -1) {
                tagList.add(map);
                LvTags.setAdapter(adapter);
                tv_count.setText("" + adapter.getCount());
            } else {
                int tagcount = Integer.parseInt(tagList.get(index).get("tagCount"), 10) + 1;
                map.put("tagCount", String.valueOf(tagcount));
                tagList.set(index, map);
            }

            adapter.notifyDataSetChanged();
        }
    }

    public class BtClearClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            clearData();
        }
    }

    public class BtImportClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            if (BtInventory.getText().equals("Start")) {
                BtInventory.setTextColor(Color.GREEN);
                if(tagList.size()==0) {
                    Toast.makeText(getActivity(), "No Tags Found!", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean re = FileImport.daochu("", tagList);
                if (re) {
                    Toast.makeText(getActivity(), "Export successfully", Toast.LENGTH_SHORT).show();
                    tv_count.setText("0");
                    tagList.clear();
                    Log.i("Debug", "tagList.size " + tagList.size());
                    adapter.notifyDataSetChanged();
                }
            } else {
                BtInventory.setTextColor(Color.RED);
                Toast.makeText(getActivity(), "Please stop scanning before exporting", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void clearData() {
        tv_count.setText("0");
        tagList.clear();
        Log.i("Debug", "tagList.size " + tagList.size());
        adapter.notifyDataSetChanged();
    }

    public class BtInventoryClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            readTag();
        }
    }

    private void readTag() {
        if (BtInventory.getText().equals("Start")) {
            if (mReader.startInventoryTag()) {
                BtInventory.setText("Stop");
                loopFlag = true;
                setViewEnabled(false);
                new TagThread().start();
            } else {
                mReader.stopInventory();
                Toast.makeText(getActivity(), "Inventory Failure", Toast.LENGTH_SHORT).show();
            }

        } else {
            stopInventory();
        }
    }

    private void setViewEnabled(boolean enabled) {
        BtClear.setEnabled(enabled);
    }

    private void stopInventory() {
        if (loopFlag) {
            loopFlag = false;
            setViewEnabled(true);
            if (mReader.stopInventory()) {
                BtInventory.setText("Start");
            } else {
                Toast.makeText(getActivity(), "Inventory stop failure", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public int checkIsExist(String strEPC) {
        int existFlag = -1;
        if (strEPC.isEmpty()) {
            return existFlag;
        }
        String tempStr = "";
        for (int i = 0; i < tagList.size(); i++) {
            HashMap<String, String> temp = new HashMap<String, String>();
            temp = tagList.get(i);
            tempStr = temp.get("tagUii");
            if (strEPC.equals(tempStr)) {
                existFlag = i;
                break;
            }
        }
        return existFlag;
    }

    class TagThread extends Thread {
        public void run() {
            String strTid;
            String strResult;
            UHFTAGInfo res = null;
            while (loopFlag) {
                res = mReader.readTagFromBuffer();
                if (res != null) {
                    strTid = res.getTid();
                    if (strTid.length() != 0 && !strTid.equals("0000000" +
                            "000000000") && !strTid.equals("000000000000000000000000")) {
                        strResult = "TID:" + strTid + "\n";
                    } else {
                        strResult = "";
                    }
                    Log.i("data","EPC:"+res.getEPC()+"|"+strResult);
                    Message msg = handler.obtainMessage();
                    msg.obj = strResult + "EPC:" + res.getEPC() + "@" + res.getRssi();

                    handler.sendMessage(msg);
                }
            }
        }
    }

    HashMap<Integer, Integer> soundMap = new HashMap<Integer, Integer>();
    private SoundPool soundPool;
    private float volumnRatio;
    private AudioManager am;
    private void initSound(){
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);
        soundMap.put(1, soundPool.load(getActivity(), R.raw.barcodebeep, 1));
        soundMap.put(2, soundPool.load(getActivity(), R.raw.serror, 1));
        am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
    }

    public void playSound(int id) {

        float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        volumnRatio = audioCurrentVolumn / audioMaxVolumn;
        try {
            soundPool.play(soundMap.get(id), volumnRatio,
                    volumnRatio,
                    1,
                    0,
                    1
            );
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public class InitTask extends AsyncTask<String, Integer, Boolean> {
        ProgressDialog mypDialog;

        @Override
        protected Boolean doInBackground(String... params) {
            return mReader.init();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            mypDialog.cancel();

            if (!result) {
                Toast.makeText(getActivity(), "RFID scanner fail", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mypDialog = new ProgressDialog(getActivity());
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("init...");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.show();
        }
    }

}
