package com.honap.madhumitra.act;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.honap.madhumitra.R;
import com.honap.madhumitra.data.DbHelper;
import com.honap.madhumitra.entity.HomeInvestigationRecord;
import com.honap.madhumitra.misc.HomeInvestigationRecordAdapter;
import com.honap.madhumitra.misc.UserAccountAdapter;
import com.honap.madhumitra.model.MadhumitraModel;
import com.honap.madhumitra.utils.Utils;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import java.util.*;

/**
 * Author: Chetan S.
 */
public class BslReport extends OrmLiteBaseActivity<DbHelper> {
    List<HomeInvestigationRecord> invRecord = new ArrayList<HomeInvestigationRecord>();
    boolean firstTime = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bsl_report);
        Spinner bslDur = (Spinner) findViewById(R.id.bsldurSpinner);
        Resources res = getResources();
        String[] dur = res.getStringArray(R.array.duration_array);
        // bind the array with the spinner
        bslDur.setPrompt("Select Duration");
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, dur);
        bslDur.setAdapter(stringArrayAdapter);
        bslDur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                invRecord.clear();
                Iterator<HomeInvestigationRecord> homeInvestigationRecordIterator = MadhumitraModel.getInstance().getCurrentUserAccount().getHomeInvestigations().iterator();
                //No BSL record as yet
                //if (!firstTime) {
                    Date date = new Date();
                    /*Calendar c = GregorianCalendar.getInstance();
                    date.setDate(c.get(Calendar.DATE));
                    date.setMonth(c.get(Calendar.MONTH));
                    date.setYear(c.get(Calendar.YEAR));
                    */
                    while (homeInvestigationRecordIterator.hasNext()) {
                        HomeInvestigationRecord temp = homeInvestigationRecordIterator.next();
                        if (i == 0) {
                            if (Utils.compareDates(temp.getInvestigatedOn(), date) && temp.getInvestigation().equalsIgnoreCase(view.getContext().getResources().getString(R.string.bsl_id))) {
                                invRecord.add(temp);
                            }
                        }
                        if (i == 1)//Week
                        {
                            if (Utils.compareWeek(temp.getInvestigatedOn(), date) && temp.getInvestigation().equalsIgnoreCase(view.getContext().getResources().getString(R.string.bsl_id))) {
                                invRecord.add(temp);
                            }
                        }
                        if (i == 2)//Month
                        {
                            if (Utils.compareMonth(temp.getInvestigatedOn(), date) && temp.getInvestigation().equalsIgnoreCase(view.getContext().getResources().getString(R.string.bsl_id))) {
                                invRecord.add(temp);
                            }
                        }
                    }

                    HomeInvestigationRecordAdapter homeInvestigationRecordAdapter = new HomeInvestigationRecordAdapter(view.getContext(), android.R.layout.activity_list_item, invRecord);
                    if (invRecord != null) {
                        final ListView listView = (ListView) findViewById(R.id.bslreportList);
                        listView.setAdapter(homeInvestigationRecordAdapter);
                    }

                //} else
                 //   firstTime = false;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

    }
}
