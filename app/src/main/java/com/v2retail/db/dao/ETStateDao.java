package com.v2retail.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.v2retail.db.entities.ETState;

import java.util.List;

@Dao
public interface ETStateDao {

    @Query("SELECT * FROM et_state")
    List<ETState> getAllStateData();

    @Query("SELECT * FROM et_state WHERE module = :module")
    ETState getSateByModule(String module);

    @Query("DELETE FROM et_state WHERE module = :module")
    int clearStateByModule(String module);

    @Query("UPDATE et_state SET param18 = :param WHERE module = :module")
    int updateParam18ByModule(String module, String param);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveState(ETState etState);

}
