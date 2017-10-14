package com.honap.madhumitra.misc;

import android.text.format.DateFormat;
import com.honap.madhumitra.act.BslReport;
import com.honap.madhumitra.entity.HomeInvestigationRecord;
import com.honap.madhumitra.entity.MedicationRecord;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Nandu
 * Date: 10/15/11
 * Time: 2:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class MedRecBslWrapper implements Comparable<MedRecBslWrapper> {
    private Object wrappedObj;

    public MedRecBslWrapper(Object wrappedObj) {
        this.wrappedObj = wrappedObj;
    }

    public String getDate()
    {
         if (this.wrappedObj instanceof HomeInvestigationRecord) {
            HomeInvestigationRecord homeInvestigationRecord = (HomeInvestigationRecord) this.wrappedObj;
            //String homeInvestigationRecord.getInvestigatedOn().toString();
            return new SimpleDateFormat("h:mm a, d MMM yyyy").format(homeInvestigationRecord.getInvestigatedOn());

        }

        if (this.wrappedObj instanceof MedicationRecord) {
            MedicationRecord medRecord = (MedicationRecord) this.wrappedObj;
            return new SimpleDateFormat("h:mm a, d MMM yyyy").format(medRecord.getTime());
        }
        return null;
    }
    public Object getWrappedObj() {
        return wrappedObj;
    }

    public void setWrappedObj(Object wrappedObj) {
        this.wrappedObj = wrappedObj;
    }

    public int compareTo(MedRecBslWrapper wrapperRecord)
    {
        Date cmpDate = null;
        Date cmpToDate = null;

        if (wrapperRecord.getWrappedObj() instanceof HomeInvestigationRecord) {
            HomeInvestigationRecord homeInvestigationRecord = (HomeInvestigationRecord) wrapperRecord.getWrappedObj();
            cmpDate = homeInvestigationRecord.getInvestigatedOn();
        }

        if (wrapperRecord.getWrappedObj() instanceof MedicationRecord) {
            MedicationRecord medRecord = (MedicationRecord) wrapperRecord.getWrappedObj();
            cmpDate = medRecord.getTime();
        }

        if (this.wrappedObj instanceof HomeInvestigationRecord) {
            HomeInvestigationRecord homeInvestigationRecord = (HomeInvestigationRecord) this.wrappedObj;
            cmpToDate = homeInvestigationRecord.getInvestigatedOn();
        }

        if (this.wrappedObj instanceof MedicationRecord) {
            MedicationRecord medRecord = (MedicationRecord) this.wrappedObj;
            cmpToDate = medRecord.getTime();
        }


        if (cmpDate.before(cmpToDate)) {
            return 1;
        }

        if (cmpDate.before(cmpToDate)) {
            return -1;
        }
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
