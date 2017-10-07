package com.honap.madhumitra.act;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TableLayout;
import android.widget.TextView;
import com.honap.madhumitra.R;

import java.util.Date;

/**
 * Author: Chetan S.
 */
public class ExpenseReport extends Activity implements DatePicker.OnDateChangedListener{
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set content view
        setContentView(R.layout.expense_report);
        // set event listener
        setEventListeners();
    }

    private void setEventListeners() {
        DatePicker toDatePicker = (DatePicker) findViewById(R.id.toDatePicker);
        DatePicker fromDatePicker = (DatePicker) findViewById(R.id.fromDatePicker);
        Date dummyDate = new Date();
        toDatePicker.init(toDatePicker.getYear(),toDatePicker.getMonth(),toDatePicker.getDayOfMonth(),this);
        fromDatePicker.init(fromDatePicker.getYear(), fromDatePicker.getMonth(), fromDatePicker.getDayOfMonth(), this);

    }

    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
        switch (datePicker.getId()) {
            case R.id.toDatePicker: {
                populateExpenseReport();
                break;
            }

            case R.id.fromDatePicker: {
                populateExpenseReport();
                break;
            }
        }
    }

    private void populateExpenseReport() {
        // get controls
        DatePicker toDatePicker = (DatePicker) findViewById(R.id.toDatePicker);
        DatePicker fromDatePicker = (DatePicker) findViewById(R.id.fromDatePicker);
        TextView labExpenseTxtVw = (TextView) findViewById(R.id.labExpenseTxtVw);
        TextView docExpenseTxtVw = (TextView) findViewById(R.id.docExpenseTxtVw);
        TextView netExpenseTxtVw = (TextView) findViewById(R.id.netExpenseTxtVw);


        labExpenseTxtVw.setText("Rs."+String.valueOf(1800));
        docExpenseTxtVw.setText("Rs." + String.valueOf(1500));
        netExpenseTxtVw.setText("Rs." + String.valueOf(33 * 100));
        netExpenseTxtVw.setTextColor(Color.RED);
        // make report layout visible
        ((TableLayout)findViewById(R.id.reportTabLayout)).setVisibility(View.VISIBLE);
    }




}