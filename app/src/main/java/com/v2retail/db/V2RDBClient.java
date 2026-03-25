package com.v2retail.db;

import android.content.Context;

import androidx.room.Room;

public class V2RDBClient {
    private Context mCtx;
    private static V2RDBClient mInstance;
    private final V2RDB v2RDB;

    private V2RDBClient(Context mCtx) {
        this.mCtx = mCtx;
        v2RDB = Room.databaseBuilder(mCtx, V2RDB.class, "v2rDB").addMigrations(V2RDB.MIGRATION_1_1).allowMainThreadQueries().build();
    }

    public static synchronized V2RDBClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new V2RDBClient(mCtx);
        }
        return mInstance;
    }
    public V2RDB getV2ROfflineDB() {
        return v2RDB;
    }
}

