package com.honap.madhumitra.act;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.honap.madhumitra.R;
import com.honap.madhumitra.data.DbHelper;
import com.honap.madhumitra.data.MadhumitraDataManagerFactory;
import com.honap.madhumitra.entity.ExerciseRecord;
import com.honap.madhumitra.model.MadhumitraModel;
import com.honap.madhumitra.utils.Utils;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Author: Chetan S.
 */
public class RecordExercise extends OrmLiteBaseActivity<DbHelper>{
//    static final int TIME_DIALOG_ID = 0;
    static final int DATE_DIALOG_ID = 1;
    private boolean firstSpinnerEntry = true;

    private ExerciseRecord exerciseRecord = new ExerciseRecord();
    private List<String> exercises = null;
//    private int position = 0;

//    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
//        public void onTimeSet(TimePicker timePicker, int i, int i1) {
//            //To change body of implemented methods use File | Settings | File Templates.
//            Button time = (Button) findViewById(R.id.exerciseTime);
//            Date timeObj = GregorianCalendar.getInstance().getTime();
//            timeObj.setHours(timePicker.getCurrentHour());
//            timeObj.setMinutes(timePicker.getCurrentMinute());
//            String timeString = DateFormat.getTimeInstance().
//                    format(timeObj);
//            time.setText(timeString, TextView.BufferType.EDITABLE);
//            exerciseRecord.getStartTime().setHours(timePicker.getCurrentHour());
//            exerciseRecord.getStartTime().setMinutes(timePicker.getCurrentMinute());
//        }
//    };

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            //To change body of implemented methods use File | Settings | File Templates.
            Button date = (Button) findViewById(R.id.exerciseDate);
            date.setText(Utils.getBtnDateString(
                    new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth())));
            exerciseRecord.getStartTime().setYear(datePicker.getYear() - 1900);
            exerciseRecord.getStartTime().setMonth(datePicker.getMonth());
            exerciseRecord.getStartTime().setDate(datePicker.getDayOfMonth());
            exerciseRecord.getStartTime().setHours(12);
            exerciseRecord.getStartTime().setMinutes(0);
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_exercise);
        // setup data binding
        setupDataBinding();
        // setup event listeners
        setupEventListeners();
        this.exercises =  Arrays.asList(getResources().getStringArray(R.array.exercises));
    }

    private void setupEventListeners() {
        // save button
//        Button saveButton = (Button) findViewById(R.id.saveExerciseBtn);
//        saveButton.setOnClickListener(this);
        Button dateBtn = (Button) findViewById(R.id.exerciseDate);
        dateBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        dateBtn.setText(new SimpleDateFormat("dd.MM.yyyy").format(GregorianCalendar.getInstance().getTime()));
        exerciseRecord.setStartTime(GregorianCalendar.getInstance().getTime());
        exerciseRecord.getStartTime().setHours(12);
        exerciseRecord.getStartTime().setMinutes(0);

//        Button timeBtn = (Button) findViewById(R.id.exerciseTime);
//        timeBtn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                showDialog(TIME_DIALOG_ID);
//            }
//        });
//
//        timeBtn.setText(new SimpleDateFormat("h:mm a").format(GregorianCalendar.getInstance().getTime()));
    }

    private void setupDataBinding() {
        // setup investigation spinner
        AutoCompleteTextView exercisesAutoComplete = (AutoCompleteTextView) findViewById(R.id.exercisesAutoComplete);
        // get strings
        Resources res = getResources();
        String[] resExercises = res.getStringArray(R.array.exercises);
        // bind the array with the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.exercises, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_dropdown_item,resExercises);
        exercisesAutoComplete.setAdapter(adapter);

        exercisesAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                position = i;
                populateCalories();
            }
        });

        EditText editText = (EditText) findViewById(R.id.exerciseDuration);
        editText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void afterTextChanged(Editable editable) {
                populateCalories();
            }
        });



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
//            case TIME_DIALOG_ID: {
//                Date time = GregorianCalendar.getInstance().getTime();
//                return new TimePickerDialog(this,timeSetListener,time.getHours(),time.getMinutes(),false);
//            }

            case DATE_DIALOG_ID: {
                int yr = GregorianCalendar.getInstance().get(GregorianCalendar.YEAR);
                int mnth = GregorianCalendar.getInstance().get(GregorianCalendar.MONTH);
                int dat = GregorianCalendar.getInstance().get(GregorianCalendar.DATE);
                return new DatePickerDialog(this,dateSetListener,GregorianCalendar.getInstance().get(GregorianCalendar.YEAR)
                        ,GregorianCalendar.getInstance().get(GregorianCalendar.MONTH),
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

    private void populateCalories() {
        AutoCompleteTextView autoComplete = (AutoCompleteTextView)findViewById(R.id.exercisesAutoComplete);
        int position = this.exercises.indexOf(autoComplete.getText().toString());
        EditText editText = (EditText) findViewById(R.id.exerciseDuration);
        if(editText.getText().length() > 0) {
            EditText durationEditText = (EditText) findViewById(R.id.exerciseDuration);
            // claculate calorie consumption
            int[] exerciseCalories = getResources().getIntArray(R.array.exercise_calories);
            int calorieConsumption = exerciseCalories[position] *
                    Integer.parseInt(durationEditText.getText().toString());
            int cals = Utils.getNormalizedCalories(calorieConsumption);
            //exerciseRecord.setCalorieConsumption(cals);
            TextView textView = (TextView) findViewById(R.id.exerciseCalories);
            textView.setText(new Integer(cals).toString());
            textView.setTextColor(Color.GREEN);
        } else {
            exerciseRecord.setCalorieConsumption(0);
            TextView textView = (TextView) findViewById(R.id.exerciseCalories);
            textView.setText(new Integer(0).toString());
            textView.setTextColor(Color.BLACK);
        }
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
            case R.id.saveMenuBtn : {
                AutoCompleteTextView autoComplete = (AutoCompleteTextView)findViewById(R.id.exercisesAutoComplete);
                int position = this.exercises.indexOf(autoComplete.getText().toString());
                exerciseRecord.setExercise(autoComplete.getText().toString());
                EditText durationEditText = (EditText) findViewById(R.id.exerciseDuration);
                if(!(durationEditText.getText().length() > 0)) {
                    // raise a toast
                    Toast.makeText(this,"Please enter exercise duration",Toast.LENGTH_LONG).show();
                    return true;
                }
                exerciseRecord.setDurationMin(Integer.parseInt(durationEditText.getText().toString()));
                // claculate calorie consumption
                int[] exerciseCalories = getResources().getIntArray(R.array.exercise_calories);
                int calorieConsumption = exerciseCalories[position] * exerciseRecord.getDurationMin();
                int cals = Utils.getNormalizedCalories(calorieConsumption);
                exerciseRecord.setCalorieConsumption(cals);
                exerciseRecord.setUserAccount(MadhumitraModel.getInstance().getCurrentUserAccount());
                MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager(this).createExerciseRecord(exerciseRecord);
                reload();
                Toast.makeText(this,"Exercise Record Saved",Toast.LENGTH_LONG).show();
                break;
            }

            case R.id.cancelMenuBtn : {
                finish();
                break;
            }
        }
        item.setEnabled(true);
        return true;
    }


}