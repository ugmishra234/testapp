package com.honap.madhumitra.act;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.honap.madhumitra.R;
import com.honap.madhumitra.data.DbHelper;
import com.honap.madhumitra.data.MadhumitraDataManagerFactory;
import com.honap.madhumitra.entity.MedicationRecord;
import com.honap.madhumitra.model.MadhumitraModel;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

/**
 * Author: Chetan S.
 */
public class MedicationNotificationAck extends OrmLiteBaseActivity<DbHelper> {
    private int notificationId = 0;
    private long recordId = 0;
    boolean firstSpinnerEventFlag = true;
    MedicationRecord medicationRecord = null;
    private String message = null;
    //private Context cntx = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medication_notification_dialog);
        Bundle extras = getIntent().getExtras();
        recordId = extras.getLong("recordId");
        notificationId = extras.getInt("notificationId");
        message = extras.getString("msg");
        medicationRecord = MadhumitraDataManagerFactory.
                getDefaultMadhumitraDataManager(this).getMedicationRecord(recordId);
        TextView textView = (TextView) findViewById(R.id.medRecordTextView);
        String msg = extras.getString("msg");
        textView.setText(msg);
        //setup data-binding
        setupDataBinding();
        setupEventListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.medication_notification_dialog);
        Bundle extras = getIntent().getExtras();
        recordId = extras.getLong("recordId");
        notificationId = extras.getInt("notificationId");
        message = extras.getString("msg");
        medicationRecord = MadhumitraDataManagerFactory.
                getDefaultMadhumitraDataManager(this).getMedicationRecord(recordId);
        TextView textView = (TextView) findViewById(R.id.medRecordTextView);
        String msg = extras.getString("msg");
        textView.setText(msg);
        //setup data-binding
        setupDataBinding();
        setupEventListeners();
    }

    private void setupDataBinding() {
        // setup investigation spinner
        ListView medAckActionList = (ListView) findViewById(R.id.medAckActionList);
        // get strings
        Resources res = getResources();
        // bind the array with the spinner
        String[] medAckActions = null;
        if(medicationRecord == null) {
            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            nm.cancel(notificationId);
            finish();
        }

        if (medicationRecord.getUserAccount().getRelationWithPrimaryUser().equalsIgnoreCase("self")) {
            medAckActions = res.getStringArray(R.array.notification_ack_actions_self);
        } else {
            medAckActions = res.getStringArray(R.array.notification_ack_actions);
        }
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, medAckActions);
        medAckActionList.setAdapter(stringArrayAdapter);
    }

    private void setupEventListeners() {
        ListView medAckActionList = (ListView) findViewById(R.id.medAckActionList);
        medAckActionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String opt = ((TextView) view).getText().toString();
                if (opt.equalsIgnoreCase("call")) {
                    medicationRecord.setDoseStatus(getResources().getString(R.string.med_dose_taken));
                    MadhumitraDataManagerFactory.
                            getDefaultMadhumitraDataManager(MedicationNotificationAck.this).
                            updateMedicationRecord(medicationRecord);
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + medicationRecord.getUserAccount().getMobile()));
                    startActivity(callIntent);
                    finish();
                }

                if (opt.equalsIgnoreCase("send sms")) {
                    medicationRecord.setDoseStatus(getResources().getString(R.string.med_dose_taken));
                    MadhumitraDataManagerFactory.
                            getDefaultMadhumitraDataManager(MedicationNotificationAck.this).updateMedicationRecord(medicationRecord);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" +
                            medicationRecord.getUserAccount().getMobile()));
                    intent.putExtra("sms_body", message);
                    startActivity(intent);
                    finish();
                }

                if (opt.equalsIgnoreCase("dose taken")) {
                    medicationRecord.setDoseStatus(getResources().getString(R.string.med_dose_taken));
                    MadhumitraDataManagerFactory.
                            getDefaultMadhumitraDataManager(MedicationNotificationAck.this).updateMedicationRecord(medicationRecord);
                    NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    nm.cancel(notificationId);
                    finish();
                }

                if (opt.equalsIgnoreCase("dose missed")) {
                    medicationRecord.setDoseStatus(getResources().getString(R.string.med_dose_missed));
                    MadhumitraDataManagerFactory.
                            getDefaultMadhumitraDataManager(MedicationNotificationAck.this).updateMedicationRecord(medicationRecord);
                    NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    nm.cancel(notificationId);
                    finish();
                }

            }

        });

    }


}