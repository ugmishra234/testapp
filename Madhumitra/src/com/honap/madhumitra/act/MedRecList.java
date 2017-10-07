package com.honap.madhumitra.act;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.honap.madhumitra.R;
import com.honap.madhumitra.data.DbHelper;
import com.honap.madhumitra.data.MadhumitraDataManagerFactory;
import com.honap.madhumitra.entity.MedicationRecord;
import com.honap.madhumitra.misc.UserAccountAdapter;
import com.honap.madhumitra.model.MadhumitraModel;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.android.apptools.OrmLiteBaseListActivity;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Chetan
 * Date: 11/17/11
 * Time: 7:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class MedRecList extends OrmLiteBaseActivity<DbHelper> {
    private List<MedicationRecord> medRecords = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.med_record);
        medRecords = MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager(this).getAllMedicationRecords();
        ArrayAdapter medAdapter = new ArrayAdapter
                (this, android.R.layout.simple_list_item_1, medRecords);
        final ListView listView = (ListView) findViewById(R.id.medRecList);
        listView.setAdapter(medAdapter);
    }
}