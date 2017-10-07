package com.honap.madhumitra.act;

//import org.android.R;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.widget.TableRow.LayoutParams;
import com.honap.madhumitra.R;

import java.util.Date;

public class RecordMedication extends Activity {
    static final int TIME_DIALOG_ID = 0;
    static final int DATE_DIALOG_ID = 1;


    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            //To change body of implemented methods use File | Settings | File Templates.
            Button time = (Button) findViewById(R.id.medTime);
            String timeString = timePicker.getCurrentHour().toString() + ":" + timePicker.getCurrentMinute().toString();
            time.setText(timeString,TextView.BufferType.EDITABLE);
        }
    };

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            //To change body of implemented methods use File | Settings | File Templates.
            Button date = (Button) findViewById(R.id.medDate);
            String dateString = Integer.toString(datePicker.getDayOfMonth() + 1) + "-" +
            Integer.toString(datePicker.getMonth()) + "-" + Integer.toString(datePicker.getYear());
            date.setText(dateString, TextView.BufferType.EDITABLE);
        }
    };

	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record_medication);

        Button addMealItemRecord = (Button)findViewById(R.id.addDoseBtn);
        addMealItemRecord.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view) {
                createMedicationDoseRow();
            }
        });


//	    Button save = (Button)findViewById(R.id.saveMedBtn);
//	    save.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//            	//CS TODO add this to DB
//            	finish();
//            }
//	    });

        Button dateBtn = (Button)findViewById(R.id.medDate);
        dateBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        Button timeBtn = (Button)findViewById(R.id.medTime);
        timeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showDialog(TIME_DIALOG_ID);
            }
        });

	}

    private void createMedicationDoseRow() {
        TableLayout tableLayout = (TableLayout) findViewById(R.id.mealItemTableLayout);
        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        // add meal item auto complete text view
        EditText mealItemTextView = new EditText(this);
        mealItemTextView.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT,1));
        mealItemTextView.setHint("Drug");

        // add quanity text box
        EditText quantityEditText = new EditText(this);
        quantityEditText.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT,1));
        quantityEditText.setHint("Dose");
        quantityEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        // add controls to the row
        tableRow.addView(mealItemTextView);
        tableRow.addView(quantityEditText);
        // add row to the table
        tableLayout.addView(tableRow,new TableLayout.LayoutParams(
                                                        LayoutParams.FILL_PARENT,
                                                       LayoutParams.WRAP_CONTENT));
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID: {
                Date time = new Date();
                return new TimePickerDialog(this,timeSetListener,time.getHours(),time.getMinutes(),false);
            }

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
