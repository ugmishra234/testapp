package com.honap.madhumitra.act;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.honap.madhumitra.R;
import com.honap.madhumitra.data.DbHelper;
import com.honap.madhumitra.data.MadhumitraDataManagerFactory;
import com.honap.madhumitra.entity.*;
import com.honap.madhumitra.misc.CalorieAdapter;
import com.honap.madhumitra.misc.RecordAdapter;
import com.honap.madhumitra.model.MadhumitraModel;
import com.honap.madhumitra.utils.Utils;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author: Chetan S.
 */
public class DeleteRecords extends OrmLiteBaseActivity<DbHelper> {
    static final int FROM_DATE_DIALOG_ID = 0;
    static final int TO_DATE_DIALOG_ID = 1;
    private boolean firstSpinnerEventFlag = true;
    private String recordType = "activity records";
    private Date from = GregorianCalendar.getInstance().getTime();
    private Date to = GregorianCalendar.getInstance().getTime();
    List<Object> records = new ArrayList<Object>();

    private DatePickerDialog.OnDateSetListener fromDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            //To change body of implemented methods use File | Settings | File Templates.
            Button fromDate = (Button) findViewById(R.id.recFromDate);
            fromDate.setText("From - " + Utils.getBtnDateString(
                    new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth())));
            from.setYear(datePicker.getYear());
            from.setMonth(datePicker.getMonth());
            from.setDate(datePicker.getDayOfMonth());
            Calendar fromCalendar = GregorianCalendar.getInstance();
            fromCalendar.set(Calendar.DATE,from.getDate());
            fromCalendar.set(Calendar.MONTH,from.getMonth());
            fromCalendar.set(Calendar.YEAR,from.getYear());
            fromCalendar.add(Calendar.DATE,-1);
            fromCalendar.set(Calendar.HOUR,11);
            fromCalendar.set(Calendar.MINUTE,59);

            from = fromCalendar.getTime();
            updateRecordList();
        }
    };

    private DatePickerDialog.OnDateSetListener toDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            //To change body of implemented methods use File | Settings | File Templates.
            Button toDate = (Button) findViewById(R.id.recToDate);
            toDate.setText("To - " + Utils.getBtnDateString(
                    new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth())));
            to.setYear(datePicker.getYear());
            to.setMonth(datePicker.getMonth());
            to.setDate(datePicker.getDayOfMonth());
            Calendar toCalendar = GregorianCalendar.getInstance();
            toCalendar.set(Calendar.DATE,to.getDate());
            toCalendar.set(Calendar.MONTH,to.getMonth());
            toCalendar.set(Calendar.YEAR,to.getYear());
            toCalendar.set(Calendar.HOUR,12);
            toCalendar.set(Calendar.MINUTE,01);
            to = toCalendar.getTime();
            updateRecordList();
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_records);
        setupEventListeners();
    }

    private void setupEventListeners() {
        Button fromDateBtn = (Button) findViewById(R.id.recFromDate);
        fromDateBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showDialog(FROM_DATE_DIALOG_ID);
            }
        });
        fromDateBtn.setText("From - " + Utils.getBtnDateString(GregorianCalendar.getInstance().getTime()));

        Button toDateBtn = (Button) findViewById(R.id.recToDate);
        toDateBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showDialog(TO_DATE_DIALOG_ID);
            }
        });
        toDateBtn.setText("To - " + Utils.getBtnDateString(GregorianCalendar.getInstance().getTime()));
        Spinner spinner = (Spinner) findViewById(R.id.recTypeSpinner);
        Resources res = getResources();
        //String[] recTypes = res.getStringArray(R.array.record_type);
        // bind the array with the spinner
        spinner.setPrompt("Select Record Type");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.record_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_dropdown_item,resExercises);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (firstSpinnerEventFlag) {
                    firstSpinnerEventFlag = false;
                } else {
                    recordType = ((TextView) view).getText().toString();
                    updateRecordList();
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }

    private void updateRecordList() {
        records.clear();

        if (recordType.equalsIgnoreCase("activity records")) {
            Iterator<ActivityRecord> recordIterator = MadhumitraModel.getInstance().getCurrentUserAccount().
                    getActivityRecords().iterator();
            while (recordIterator.hasNext()) {
                ActivityRecord activityRecord = recordIterator.next();
                if (activityRecord.getRecordDateTime().after(from) && activityRecord.getRecordDateTime().before(to)) {
                    records.add(activityRecord);
                }
            }
        }

        if (recordType.equalsIgnoreCase("exercise records")) {
            Iterator<ExerciseRecord> recordIterator = MadhumitraModel.getInstance().getCurrentUserAccount().
                    getExerciseRecords().iterator();
            while (recordIterator.hasNext()) {
                ExerciseRecord record = recordIterator.next();
                if (record.getRecordDateTime().after(from) && record.getRecordDateTime().before(to)) {
                    records.add(record);
                }
            }
        }

        if (recordType.equalsIgnoreCase("meal records")) {
            Iterator<MealRecord> recordIterator = MadhumitraModel.getInstance().getCurrentUserAccount().
                    getMealRecords().iterator();
            while (recordIterator.hasNext()) {
                MealRecord record = recordIterator.next();
                if (record.getRecordDateTime().after(from) && record.getRecordDateTime().before(to)) {
                    records.add(record);
                }
            }
        }

        if (recordType.equalsIgnoreCase("bsl records")) {
            Iterator<HomeInvestigationRecord> recordIterator = MadhumitraModel.getInstance().getCurrentUserAccount().
                    getHomeInvestigations().iterator();
            while (recordIterator.hasNext()) {
                HomeInvestigationRecord record = recordIterator.next();
                if (record.getRecordDateTime().after(from) && record.getRecordDateTime().before(to)) {
                    records.add(record);
                }
            }
        }

        if (recordType.equalsIgnoreCase("prescriptions")) {
            Iterator<Prescription> recordIterator = MadhumitraModel.getInstance().getCurrentUserAccount().
                    getPrescriptions().iterator();
            while (recordIterator.hasNext()) {
                Prescription record = recordIterator.next();
                if (record.getRecordDateTime().after(from) && record.getRecordDateTime().before(to)) {
                    records.add(record);
                }
            }
        }

        if (recordType.equalsIgnoreCase("doctors")) {
            Iterator<Doctor> recordIterator = MadhumitraModel.getInstance().getCurrentUserAccount().
                    getDoctors().iterator();
            while (recordIterator.hasNext()) {
                Doctor record = recordIterator.next();
                records.add(record);
            }
        }


        RecordAdapter recordAdapter = new RecordAdapter(this, records);
        final ListView listView = (ListView) findViewById(R.id.recList);
        listView.setAdapter(recordAdapter);
        listView.setItemsCanFocus(false);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case FROM_DATE_DIALOG_ID: {
                return new DatePickerDialog(this, fromDateSetListener, GregorianCalendar.getInstance().get
                        (GregorianCalendar.YEAR)
                        , GregorianCalendar.getInstance().get(GregorianCalendar.MONTH),
                        GregorianCalendar.getInstance().get(GregorianCalendar.DATE));
            }

            case TO_DATE_DIALOG_ID: {
                return new DatePickerDialog(this, toDateSetListener, GregorianCalendar.getInstance().get
                        (GregorianCalendar.YEAR)
                        , GregorianCalendar.getInstance().get(GregorianCalendar.MONTH),
                        GregorianCalendar.getInstance().get(GregorianCalendar.DATE));
            }

            default: {
                return null;
            }
        }
    }

    private List<Object> getCheckedRecords() {
        List<Object> retList = new ArrayList<Object>();
        ListView listView = (ListView) findViewById(R.id.recList);
        long[] checkedItems = listView.getCheckedItemIds();
        SparseBooleanArray sparseBooleanArray = listView.getCheckedItemPositions();

        if (sparseBooleanArray == null) {
            Toast.makeText(this, "No selection info available",
                    Toast.LENGTH_LONG).show();
            return retList;
        }

        // For each element in the status array
        // --
        boolean isFirstSelected = true;
        final int checkedItemsCount = sparseBooleanArray.size();
        for (int i = 0; i < checkedItemsCount; ++i) {
            // This tells us the item position we are looking at
            // --
            final int position = sparseBooleanArray.keyAt(i);

            // This tells us the item status at the above position
            // --
            final boolean isChecked = sparseBooleanArray.valueAt(i);

            if (isChecked) {
                isFirstSelected = false;
                retList.add(records.get(position));
            }
        }
        return retList;
    }

    private void deleteRecords() {
        Iterator<Object> recordIterator = getCheckedRecords().iterator();
        while (recordIterator.hasNext()) {
            Object object = recordIterator.next();
            if (object instanceof ActivityRecord) {
                MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager(this).
                        deleteActivityRecord((ActivityRecord) object);
            }

            if (object instanceof MealRecord) {
                MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager(this).
                        deleteMealRecord((MealRecord) object);
            }

            if (object instanceof ExerciseRecord) {
                MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager(this).
                        deleteExerciseRecord((ExerciseRecord) object);
            }

            if (object instanceof HomeInvestigationRecord) {
                MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager(this).
                        deleteHomeInvestigationRecord((HomeInvestigationRecord) object);
            }

            if (object instanceof Prescription) {
                // delete medication records
                Iterator<MedicationRecord> iterator =
                        ((Prescription)object).getMedicationRecords().iterator();
                while (iterator.hasNext()) {
                    MedicationRecord medicationRecord = iterator.next();
                            MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager(this).
                                    deleteMedicationRecord(medicationRecord);
                }
                MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager(this).
                        deletePrescription((Prescription) object);
            }

            if (object instanceof Doctor) {
                MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager(this).
                        deleteDoctor((Doctor) object);
            }
        }
        updateRecordList();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.delete_records_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setEnabled(false);
        switch (item.getItemId()) {

            case R.id.deleteRecMenuBtn: {
                deleteRecords();
                break;
            }

            case R.id.cancelDelMenuBtn: {
                finish();
                break;
            }
        }
        item.setEnabled(true);
        return true;
    }


}