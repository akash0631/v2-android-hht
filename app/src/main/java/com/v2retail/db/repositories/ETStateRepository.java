package com.v2retail.db.repositories;

import android.app.Application;
import com.v2retail.db.V2RDBClient;
import com.v2retail.db.dao.ETStateDao;
import com.v2retail.db.entities.ETState;

public class ETStateRepository {
    private final ETStateDao etStateDao;

    public ETStateRepository(Application application){
        etStateDao = V2RDBClient
                .getInstance(application.getApplicationContext())
                .getV2ROfflineDB()
                .etStateDao();
    }

    public void saveState(ETState etState){
        etStateDao.saveState(etState);
    }

    public ETState getStateByModule(String module){
        return etStateDao.getSateByModule(module);
    }

    public int clearAllETStateByModule(String module){
        return etStateDao.clearStateByModule(module);
    }

    public int updateParam18(String module, String param18){
        return etStateDao.updateParam18ByModule(module, param18);
    }
}
