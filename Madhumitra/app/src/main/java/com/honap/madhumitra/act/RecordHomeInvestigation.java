package com.honap.madhumitra.act;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.AndroidCharacter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.honap.madhumitra.R;
import com.honap.madhumitra.data.DbHelper;
import com.honap.madhumitra.data.MadhumitraDataManagerFactory;
import com.honap.madhumitra.entity.HomeInvestigationParamValue;
import com.honap.madhumitra.entity.HomeInvestigationRecord;
import com.honap.madhumitra.model.MadhumitraModel;
import com.honap.madhumitra.utils.Utils;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.ForeignCollection;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Author: Chetan S.
 */
public class RecordHomeInvestigation extends OrmLiteBaseActivity<DbHelper> {

    static final int TIME_DIALOG_ID = 0;
    static final int DATE_DIALOG_ID = 1;
    private boolean firstSpinnerEventFlag = true;

    private HomeInvestigationRecord homeInvestigationRecord = new HomeInvestigationRecord();

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            //To change body of implemented methods use File | Settings | File Templates.
            Button time = (Button) findViewById(R.id.homeInvTime);
            Date timeObj = GregorianCalendar.getInstance().getTime();
            timeObj.setHours(timePicker.getCurrentHour());
            timeObj.setMinutes(timePicker.getCurrentMinute());
            String timeString = DateFormat.getTimeInstance().
                    format(timeObj);
            //String timeString = timePicker.getCurrentHour().toString() + ":" + timePicker.getCurrentMinute().toString();
            time.setText(timeString, TextView.BufferType.EDITABLE);
            homeInvestigationRecord.getInvestigatedOn().setHours(timePicker.getCurrentHour());
            homeInvestigationRecord.getInvestigatedOn().setMinutes(timePicker.getCurrentMinute());
        }
    };

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            //To change body of implemented methods use File | Settings | File Templates.
            Button date = (Button) findViewById(R.id.homeInvDate);
            date.setText(Utils.getBtnDateString(
                    new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth())));
            homeInvestigationRecord.getInvestigatedOn().setYear(datePicker.getYear() - 1900);
            homeInvestigationRecord.getInvestigatedOn().setMonth(datePicker.getMonth());
            homeInvestigationRecord.getInvestigatedOn().setDate(datePicker.getDayOfMonth());
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_home_investigation);
        // setup data binding
        setupDataBinding();
        // setup event listeners
        setupEventListeners();
    }

    private void setupEventListeners() {
        Button dateBtn = (Button) findViewById(R.id.homeInvDate);
        dateBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        dateBtn.setText(Utils.getBtnDateString(GregorianCalendar.getInstance().getTime()));


        Button timeBtn = (Button) findViewById(R.id.homeInvTime);
        timeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showDialog(TIME_DIALOG_ID);
            }
        });
        timeBtn.setText(new SimpleDateFormat("h:mm a").format(GregorianCalendar.getInstance().getTime()));

        homeInvestigationRecord.setInvestigatedOn(GregorianCalendar.getInstance().getTime());

        // setup investigation spinner
//        Spinner homeInvSpinner = (Spinner) findViewById(R.id.homeInvSpinner);
//        homeInvSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                //To change body of implemented methods use File | Settings | File Templates.
//                if(firstSpinnerEventFlag) {
//                    TextView textView = (TextView)view;
//                    String investigation = textView.getText().toString();
//                    EditText editText = (EditText)findViewById(R.id.homeInvReport);
//                    if(investigation.equalsIgnoreCase("bsl")) {
//                        editText.setHint("Blood Sugar");
//                    }
//
//                    if(investigation.equalsIgnoreCase("blood pressure")) {
//                        editText.setHint("Systoloc/Diastolic");
//                    }
//
//                } else {
//                    firstSpinnerEventFlag = true;
//                }
//            }
//
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                //To change body of implemented methods use File | Settings | File Templates.
//            }
//        });

    }

    private void setupDataBinding() {
        // setup investigation spinner
//        Spinner homeInvSpinner = (Spinner) findViewById(R.id.homeInvSpinner);
//        // get strings
//        Resources res = getResources();
//        String[] invs = res.getStringArray(R.array.home_investigations);
//        // bind the array with the spinner
//        homeInvSpinner.setPrompt("Select Home Investigation");
//        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item,invs);
//        homeInvSpinner.setAdapter(stringArrayAdapter);
    }

    private void save() {
//        HomeInvestigationRecord record = new HomeInvestigationRecord();
//        // set investigation
//        String inv = (String)((Spinner)findViewById(R.id.homeInvSpinner)).getSelectedItem();
//        record.setInvestigation(inv);
//        // set comment
//        record.setComment(((EditText)findViewById(R.id.invComment)).getText().toString());
//        // set date and time
//        DatePicker datePicker = ((DatePicker)findViewById(R.id.invDatePicker));
//        TimePicker timePicker = ((TimePicker)findViewById(R.id.invTimePicker));
//        Date dateTime = Utils.getDateTime(datePicker.getDayOfMonth(),datePicker.getMonth(),datePicker.getYear(),
//                timePicker.getCurrentMinute(),timePicker.getCurrentHour());
//        record.setInvestigatedOn(dateTime);
//        // set investigation values
//        ForeignCollection invValues =
//        MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager().getEmptyCollection(
//                HomeInvestigationRecord.class,"investigationValues");
//        HomeInvestigationParamValue pv = new HomeInvestigationParamValue();
//        pv.setRecord(record);
//        pv.setParameter("value");
//        pv.setValue(((EditText)findViewById(R.id.invReport)).getText().toString());
//        // set user account
//        record.setUserAccount(MadhumitraModel.getInstance().getCurrentUserAccount());
//        // save the record
//        MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager().createHomeInvestigationRecord(record);

        finish();
    }

    private void cancel() {
        finish();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID: {
                Date time = new Date();
                return new TimePickerDialog(this, timeSetListener, time.getHours(), time.getMinutes(), false);
            }

            case DATE_DIALOG_ID: {
                return new DatePickerDialog(this, dateSetListener, GregorianCalendar.getInstance().get
                        (GregorianCalendar.YEAR)
                        , GregorianCalendar.getInstance().get(GregorianCalendar.MONTH),
                        GregorianCalendar.getInstance().get(GregorianCalendar.DATE));
            }

            default: {
                return null;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.internal_activity_menu, menu);
        return true;
    }

    protected void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setEnabled(false);
        switch (item.getItemId()) {
            case R.id.saveMenuBtn: {
                String[] homeInvestigations = getResources().getStringArray(R.array.home_investigations);
                // set investigation
//                int position = ((Spinner)findViewById(R.id.homeInvSpinner)).getSelectedItemPosition();
                String inv = "bsl";
                homeInvestigationRecord.setInvestigation(inv);
                ForeignCollection invValues =
                        MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager(this).getEmptyCollection(
                                homeInvestigationRecord, "investigation_value");
                homeInvestigationRecord.setInvestigationValues(invValues);
                // report values
                EditText editText = ((EditText) findViewById(R.id.homeInvReport));
                if (!(editText.getText().length() > 0)) {
                    Toast.makeText(this, "BSL value cannot be blank", Toast.LENGTH_LONG).show();
                    return true;
                } else {

                    String reportValue = editText.getText().toString();
                    if (inv.equalsIgnoreCase("bsl")) {
                        HomeInvestigationParamValue pv = new HomeInvestigationParamValue();
                        pv.setRecord(homeInvestigationRecord);
                        pv.setParameter("value");
                        pv.setValue(reportValue);
                        homeInvestigationRecord.getInvestigationValues().add(pv);
                    }

                    if (inv.equalsIgnoreCase("blood pressure")) {
                        String[] values = reportValue.split(",");
                        HomeInvestigationParamValue bpspv = new HomeInvestigationParamValue();
                        bpspv.setRecord(homeInvestigationRecord);
                        bpspv.setParameter("bpsystolic");
                        bpspv.setValue(values[0]);
                        homeInvestigationRecord.getInvestigationValues().add(bpspv);

                        HomeInvestigationParamValue bpdpv = new HomeInvestigationParamValue();
                        bpdpv.setRecord(homeInvestigationRecord);
                        bpdpv.setParameter("bpdiastolic");
                        bpdpv.setValue(values[1]);
                        homeInvestigationRecord.getInvestigationValues().add(bpdpv);
                    }

                    // set user account
                    homeInvestigationRecord.setUserAccount(MadhumitraModel.getInstance().getCurrentUserAccount());
                    // save the record
                    MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager(this).createHomeInvestigationRecord(
                            homeInvestigationRecord);
                    reload();
                    Toast.makeText(this, "BSL Record Saved", Toast.LENGTH_SHORT);
                    break;
                }
            }

            case R.id.cancelMenuBtn: {
                finish();
                break;
            }
        }
        item.setEnabled(true);
        return true;
    }


}
