package com.honap.madhumitra.act;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.honap.madhumitra.R;
import com.honap.madhumitra.data.MadhumitraDataManagerFactory;
import com.honap.madhumitra.entity.SymptomRecord;
import com.honap.madhumitra.model.MadhumitraModel;

import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class RecordSymptom extends Activity {

    static final int TIME_DIALOG_ID = 0;
    static final int DATE_DIALOG_ID = 1;

    private SymptomRecord symptomRecord = new SymptomRecord();

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            //To change body of implemented methods use File | Settings | File Templates.
            Button time = (Button) findViewById(R.id.symptomTime);
            Date timeObj = GregorianCalendar.getInstance().getTime();
            timeObj.setHours(timePicker.getCurrentHour());
            timeObj.setMinutes(timePicker.getCurrentMinute());
            String timeString = DateFormat.getTimeInstance().
                    format(timeObj);
            time.setText(timeString, TextView.BufferType.EDITABLE);
            symptomRecord.getEventTime().setHours(timePicker.getCurrentHour());
            symptomRecord.getEventTime().setMinutes(timePicker.getCurrentMinute());
        }
    };

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            //To change body of implemented methods use File | Settings | File Templates.
            Button date = (Button) findViewById(R.id.symptomDate);
            String dateString = DateFormat.getDateInstance().format
                    (new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth()));
            date.setText(dateString, TextView.BufferType.EDITABLE);
            symptomRecord.getEventTime().setYear(datePicker.getYear());
            symptomRecord.getEventTime().setMonth(datePicker.getMonth());
            symptomRecord.getEventTime().setDate(datePicker.getDayOfMonth());
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_symptom);
        Spinner spinner = (Spinner) findViewById(R.id.symptomSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.symptoms_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button dateBtn = (Button) findViewById(R.id.symptomDate);
        dateBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        Button timeBtn = (Button) findViewById(R.id.symptomTime);
        timeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showDialog(TIME_DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID: {
                Date time = GregorianCalendar.getInstance().getTime();
                return new TimePickerDialog(this,timeSetListener,time.getHours(),time.getMinutes(),false);
            }

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveMenuBtn : {
                String[] symptoms = getResources().getStringArray(R.array.symptoms_list);
                Spinner spinner = (Spinner)findViewById(R.id.symptomSpinner);
                int position = spinner.getSelectedItemPosition();
                symptomRecord.setSymptom(symptoms[position]);
                EditText commentEditText = (EditText)findViewById(R.id.symptomComment);
                symptomRecord.setComment(commentEditText.getText().toString());
                symptomRecord.setUserAccount(MadhumitraModel.getInstance().getCurrentUserAccount());
                MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager(this).createSymptomRecord(symptomRecord);
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
