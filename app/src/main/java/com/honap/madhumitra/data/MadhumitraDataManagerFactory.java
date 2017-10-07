package com.honap.madhumitra.data;

import android.content.Context;
import android.content.Intent;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

/**
 * Author: Chetan S.
 */
public class MadhumitraDataManagerFactory {

    private static MadhumitraDataManager defaultMngr = null;

    public static MadhumitraDataManager getDefaultMadhumitraDataManager(Context context) {
        if(defaultMngr == null || defaultMngr.isOpen() != true) {
            OrmLiteBaseActivity<DbHelper> baseActivity = (OrmLiteBaseActivity<DbHelper>)context;
            defaultMngr = new LocalDataManager(baseActivity.getHelper());
        }
        return defaultMngr;
    }

    public static void releaseMadhumitraDataManager() {
        //defaultMngr = null;
    }
}
