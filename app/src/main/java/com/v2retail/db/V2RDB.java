package com.v2retail.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.v2retail.db.dao.ETStateDao;
import com.v2retail.db.entities.ETState;


@Database(entities = {ETState.class}, version = 1, exportSchema = false)
@TypeConverters({com.v2retail.db.Converters.class})
public abstract class V2RDB extends RoomDatabase {

    public abstract ETStateDao etStateDao();

    static final Migration MIGRATION_1_1 = new Migration(1, 1) {

        @Override
        public void migrate(SupportSQLiteDatabase database) {

        }

    };
}
