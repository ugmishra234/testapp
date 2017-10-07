package com.honap.madhumitra.act;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.InputType;
import android.view.*;
import android.widget.*;
import com.honap.madhumitra.R;
import com.honap.madhumitra.cc.BloodSugar;
import com.honap.madhumitra.cc.LipidProfile;
import com.honap.madhumitra.entity.LabInvestigationRecord;


import java.text.DateFormat;
import java.util.Date;

/**
 * Author: Chetan S.
 */
public class RecordLabInvestigation extends Activity
        implements View.OnClickListener,AdapterView.OnItemSelectedListener{

    static final int TIME_DIALOG_ID = 0;
    static final int DATE_DIALOG_ID = 1;

    static boolean firstSpinnerEventFlag = true;

    private LabInvestigationRecord labInvestigationRecord = new LabInvestigationRecord();


//    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
//        public void onTimeSet(TimePicker timePicker, int i, int i1) {
//            //To change body of implemented methods use File | Settings | File Templates.
//            Button time = (Button) findViewById(R.id.homeInvTime);
//            String timeString = timePicker.getCurrentHour().toString() + ":" + timePicker.getCurrentMinute().toString();
//            time.setText(timeString,TextView.BufferType.EDITABLE);
//        }
//    };

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            //To change body of implemented methods use File | Settings | File Templates.
            Button date = (Button) findViewById(R.id.labInvDate);
            String dateString = DateFormat.getDateInstance().format
                    (new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth()));
            date.setText(dateString, TextView.BufferType.EDITABLE);
            labInvestigationRecord.getInvestigatedOn().setYear(datePicker.getYear());
            labInvestigationRecord.getInvestigatedOn().setMonth(datePicker.getMonth());
            labInvestigationRecord.getInvestigatedOn().setDate(datePicker.getDayOfMonth());
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_lab_investigation);
        // setup data binding
        setupDataBinding();
        // setup event listeners
        setupEventListeners();
    }

    private void setupEventListeners() {
        Button dateBtn = (Button) findViewById(R.id.labInvDate);
        dateBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        Spinner labInvSpinner = (Spinner) findViewById(R.id.labInvSpinner);
        labInvSpinner.setOnItemSelectedListener(this);
    }

    private void setupDataBinding() {
        // setup investigation spinner
        Spinner labInvSpinner = (Spinner) findViewById(R.id.labInvSpinner);
        // get strings
        Resources res = getResources();
        String[] invs = res.getStringArray(R.array.lab_investigations);
        // bind the array with the spinner
        labInvSpinner.setPrompt("Select Lab Investigation");
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,invs);
        labInvSpinner.setAdapter(stringArrayAdapter);
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(firstSpinnerEventFlag) {
            TableLayout tableLayout = (TableLayout)findViewById(R.id.invContainer);
            tableLayout.removeAllViews();
            TextView labInvTxtVw = (TextView)view;
            String labInv = labInvTxtVw.getText().toString();
            if(labInv.equalsIgnoreCase("lipid profile")) {
                // add cc_lipid_profile
                TableRow tableRow = new TableRow(this);
                tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                LipidProfile lipidProfile = new LipidProfile(this);
                lipidProfile.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.FILL_PARENT,1));
                tableRow.addView(lipidProfile);
                tableLayout.addView(tableRow,new TableLayout.LayoutParams(
                                                                TableRow.LayoutParams.FILL_PARENT,
                                                               TableRow.LayoutParams.WRAP_CONTENT));
            }

            if(labInv.equalsIgnoreCase("blood sugar")) {
                TableRow tableRow = new TableRow(this);
                tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                BloodSugar bloodSugar = new BloodSugar(this);
                bloodSugar.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.FILL_PARENT,1));
                tableRow.addView(bloodSugar);
                tableLayout.addView(tableRow,new TableLayout.LayoutParams(
                                                                TableRow.LayoutParams.FILL_PARENT,
                                                               TableRow.LayoutParams.WRAP_CONTENT));
            }

            if(labInv.equalsIgnoreCase("hba1c")) {
                TableRow tableRow = new TableRow(this);
                tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                EditText hba1cEditText= new EditText(this);
                hba1cEditText.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT,1));
                hba1cEditText.setHint("HbA1c");
                hba1cEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                // add controls to the row
                tableRow.addView(hba1cEditText);
                tableLayout.addView(tableRow,new TableLayout.LayoutParams(
                                                                TableRow.LayoutParams.FILL_PARENT,
                                                               TableRow.LayoutParams.WRAP_CONTENT));
            }

            if(labInv.equalsIgnoreCase("blood urea")) {
                TableRow tableRow = new TableRow(this);
                tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                EditText bloodUreaEditText= new EditText(this);
                bloodUreaEditText.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT,1));
                bloodUreaEditText.setHint("Blood Urea");
                bloodUreaEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                // add controls to the row
                tableRow.addView(bloodUreaEditText);
                tableLayout.addView(tableRow,new TableLayout.LayoutParams(
                                                                TableRow.LayoutParams.FILL_PARENT,
                                                               TableRow.LayoutParams.WRAP_CONTENT));

            }

            if(labInv.equalsIgnoreCase("serum creatinine")) {
                TableRow tableRow = new TableRow(this);
                tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                EditText serumCreatinineEditText= new EditText(this);
                serumCreatinineEditText.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT,1));
                serumCreatinineEditText.setHint("Serum Creatinine");
                serumCreatinineEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                // add controls to the row
                tableRow.addView(serumCreatinineEditText);
                tableLayout.addView(tableRow,new TableLayout.LayoutParams(
                                                                TableRow.LayoutParams.FILL_PARENT,
                                                               TableRow.LayoutParams.WRAP_CONTENT));
            }

            if(labInv.equalsIgnoreCase("other")) {
                TableRow invTableRow = new TableRow(this);
                invTableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

                TableRow reportTableRow = new TableRow(this);
                reportTableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));


                EditText otherInvEditText= new EditText(this);
                otherInvEditText.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT,1));
                otherInvEditText.setHint("Investigation Name");
                otherInvEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

                EditText reportEditText= new EditText(this);
                reportEditText.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT,1));
                reportEditText.setHint("Report");
                reportEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                reportEditText.setLines(2);

                // add controls to the row
                invTableRow.addView(otherInvEditText);
                reportTableRow.addView(reportEditText);
                tableLayout.addView(invTableRow,new TableLayout.LayoutParams(
                                                                TableRow.LayoutParams.FILL_PARENT,
                                                               TableRow.LayoutParams.WRAP_CONTENT));
                tableLayout.addView(reportTableRow,new TableLayout.LayoutParams(
                                                                TableRow.LayoutParams.FILL_PARENT,
                                                               TableRow.LayoutParams.WRAP_CONTENT));
            }
        } else {
            firstSpinnerEventFlag = true;
        }

    }

    private void addValuesView(View view) {

    }

    public void onNothingSelected(AdapterView<?> adapterView) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onClick(View view) {
        // check which btn is it - save btn or cancel btn
        switch (view.getId()) {
//            case R.id.saveInvBtn: {
//                save();
//                break;
//            }

//            case R.id.cancelInvBtn: {
//                cancel();
//                break;
//            }
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
//            case TIME_DIALOG_ID: {
//                Date time = new Date();
//                return new TimePickerDialog(this,timeSetListener,time.getHours(),time.getMinutes(),false);
//            }
//
            case DATE_DIALOG_ID: {
                Date date = new Date();
                return new DatePickerDialog(this,dateSetListener,date.getYear()+1900,date.getMonth(),date.getDay());
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveMenuBtn : {
                Toast.makeText(this,"Record Saved",Toast.LENGTH_SHORT);
                finish();
                break;
            }

            case R.id.cancelMenuBtn : {
                finish();
                break;
            }
        }
        return true;
    }


}
