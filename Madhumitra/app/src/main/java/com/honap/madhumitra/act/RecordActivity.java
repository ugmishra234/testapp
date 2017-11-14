package com.honap.madhumitra.act;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.*;

import com.honap.madhumitra.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.honap.madhumitra.data.MadhumitraDataManagerFactory;
import com.honap.madhumitra.entity.ActivityRecord;
import com.honap.madhumitra.model.MadhumitraModel;
import com.honap.madhumitra.utils.Utils;


import java.util.*;

public class RecordActivity extends Activity {
    //    static final int TIME_DIALOG_ID = 0;
    static final int DATE_DIALOG_ID = 1;
    private ActivityRecord activityRecord = null;
    private boolean firstSpinnerEntry = true;
    private List<String> activities = null;


//    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
//        public void onTimeSet(TimePicker timePicker, int i, int i1) {
//            //To change body of implemented methods use File | Settings | File Templates.
//            Button time = (Button) findViewById(R.id.actTime);
//            Date timeObj = GregorianCalendar.getInstance().getTime();
//            timeObj.setHours(timePicker.getCurrentHour());
//            timeObj.setMinutes(timePicker.getCurrentMinute());
//            String timeString = DateFormat.getTimeInstance().
//                    format(timeObj);
//            //String timeString = timePicker.getCurrentHour().toString() + ":" + timePicker.getCurrentMinute().toString();
//            time.setText(timeString,TextView.BufferType.EDITABLE);
//            activityRecord.getStartTime().setHours(timePicker.getCurrentHour());
//            activityRecord.getStartTime().setMinutes(timePicker.getCurrentMinute());
//
//        }
//    };

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            //To change body of implemented methods use File | Settings | File Templates.
            Button date = (Button) findViewById(R.id.actDate);
            date.setText(Utils.getBtnDateString(
                    new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth())));
            activityRecord.getStartTime().setYear(datePicker.getYear() - 1900);
            activityRecord.getStartTime().setMonth(datePicker.getMonth());
            activityRecord.getStartTime().setDate(datePicker.getDayOfMonth());
            activityRecord.getStartTime().setHours(12);
            activityRecord.getStartTime().setMinutes(0);

        }

    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_activity);
        activityRecord = new ActivityRecord();
        AutoCompleteTextView autoComplete = (AutoCompleteTextView) findViewById(R.id.activitiesAutoComplete);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.activities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        autoComplete.setAdapter(adapter);
        Button dateBtn = (Button) findViewById(R.id.actDate);
        dateBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        //dateBtn.setText(DateFormat.getInstance().format(GregorianCalendar.getInstance().getTime()));
        dateBtn.setText(Utils.getBtnDateString(GregorianCalendar.getInstance().getTime()));
        activityRecord.setStartTime(GregorianCalendar.getInstance().getTime());
        activityRecord.getStartTime().setHours(12);
        activityRecord.getStartTime().setMinutes(0);

//        Button timeBtn = (Button)findViewById(R.id.actTime);
//        timeBtn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                showDialog(TIME_DIALOG_ID);
//            }
//        });

        this.activities = Arrays.asList(getResources().getStringArray(R.array.activities));


        autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                populateCalories();
            }
        });

        EditText editText = (EditText) findViewById(R.id.activityDuration);
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
                return new DatePickerDialog(this, dateSetListener, GregorianCalendar.getInstance().get(GregorianCalendar.YEAR)
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

    private void populateCalories() {
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView)
                findViewById(R.id.activitiesAutoComplete);
        int position = this.activities.indexOf(autoCompleteTextView.getText().toString());
        EditText duration = (EditText) findViewById(R.id.activityDuration);
        if (duration.getText().length() > 0) {
            activityRecord.setDurationMin(Integer.parseInt(duration.getText().toString()));
            AutoCompleteTextView activity = (AutoCompleteTextView) findViewById(R.id.activitiesAutoComplete);
            // get activities & calorie arrays from resources
            String[] activities = getResources().getStringArray(R.array.activities);
            int[] calories = getResources().getIntArray(R.array.activities_calories);
            activityRecord.setActivity(activities[position]);
            // get calorie consmn per min
            int ccpm = calories[position];
            // calculate calorie consumption
            int cc = activityRecord.getDurationMin() * ccpm;
            // normalize calculated calories
            int cals = Utils.getNormalizedCalories(cc);
            // update calories value
            activityRecord.setCalorieConsumption(cals);
            TextView textView = (TextView) findViewById(R.id.activityCalories);
            textView.setText(new Integer(cals).toString());
            textView.setTextColor(Color.GREEN);
        } else {
            activityRecord.setCalorieConsumption(0);
            TextView textView = (TextView) findViewById(R.id.activityCalories);
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
            case R.id.saveMenuBtn: {
                // populate activity record
//                EditText comments = (EditText)findViewById(R.id.activityComments);
//                activityRecord.setComment(comments.getText().toString());
                AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView)
                        findViewById(R.id.activitiesAutoComplete);
                int position = this.activities.indexOf(autoCompleteTextView.getText().toString());
                EditText duration = (EditText) findViewById(R.id.activityDuration);
                if (!(duration.getText().length() > 0)) {
                    Toast.makeText(this, "Please enter activity duration", Toast.LENGTH_LONG).show();
                }
                if (duration.getText().toString().isEmpty()) {
                    activityRecord.setDurationMin(Integer.parseInt(duration.getText().toString()));
                }

                // get activities & calorie arrays from resources
                String[] activities = getResources().getStringArray(R.array.activities);
                int[] calories = getResources().getIntArray(R.array.activities_calories);
                activityRecord.setActivity(activities[position]);
                // get calorie consmn per min
                int ccpm = calories[position];
                // calculate calorie consumption
                int cc = activityRecord.getDurationMin() * ccpm;
                // assign current user account
                activityRecord.setUserAccount(MadhumitraModel.getInstance().getCurrentUserAccount());
                // save the record
                MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager(this).createActivityRecord(activityRecord);
                reload();
                Toast.makeText(this, "Activity Record Saved", Toast.LENGTH_SHORT).show();
                break;
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



