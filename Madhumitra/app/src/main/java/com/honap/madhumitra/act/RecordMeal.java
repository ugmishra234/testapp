package com.honap.madhumitra.act;

//import org.android.R;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.*;
import android.view.*;
import android.widget.*;
import com.honap.madhumitra.R;
import android.widget.TableRow.LayoutParams;
import com.honap.madhumitra.data.DbHelper;
import com.honap.madhumitra.data.MadhumitraDataManagerFactory;
import com.honap.madhumitra.entity.MealItemRecord;
import com.honap.madhumitra.entity.MealRecord;
import com.honap.madhumitra.model.MadhumitraModel;
import com.honap.madhumitra.utils.Utils;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class RecordMeal extends OrmLiteBaseActivity<DbHelper> implements Runnable, AdapterView.OnItemClickListener {
    //    static final int TIME_DIALOG_ID = 0;
    static final int DATE_DIALOG_ID = 1;
    private MealRecord mealRecord = new MealRecord();
    private Map<AutoCompleteTextView, EditText> controlMap = new HashMap<AutoCompleteTextView, EditText>();

    String[] itemList = null;
    String[] itemQuantAssistList = null;
    int[] caloriesList = null;


//    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
//        public void onTimeSet(TimePicker timePicker, int i, int i1) {
//            //To change body of implemented methods use File | Settings | File Templates.
//            Button time = (Button) findViewById(R.id.mealTime);
//            Date timeObj = GregorianCalendar.getInstance().getTime();
//            timeObj.setHours(timePicker.getCurrentHour());
//            timeObj.setMinutes(timePicker.getCurrentMinute());
//            String timeString = DateFormat.getTimeInstance().
//                    format(timeObj);
//            //String timeString = timePicker.getCurrentHour().toString() + ":" + timePicker.getCurrentMinute().toString();
//            time.setText(timeString, TextView.BufferType.EDITABLE);
//            mealRecord.getTime().setHours(timePicker.getCurrentHour());
//            mealRecord.getTime().setMinutes(timePicker.getCurrentMinute());
//        }
//    };

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            //To change body of implemented methods use File | Settings | File Templates.
            Button date = (Button) findViewById(R.id.mealDate);
            date.setText(Utils.getBtnDateString(
                    new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth())));
            mealRecord.getTime().setYear(datePicker.getYear() - 1900);
            mealRecord.getTime().setMonth(datePicker.getMonth());
            mealRecord.getTime().setDate(datePicker.getDayOfMonth());
            mealRecord.getTime().setHours(12);
            mealRecord.getTime().setMinutes(0);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_meal);
        mealRecord.setItemRecords(
                MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager
                        (this).getEmptyCollection(mealRecord, "meal_item_record"));
//	    AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.mealItemAutoTxtVw);
        Button addMealItemRecord = (Button) findViewById(R.id.addMealItemQuantBtn);
        addMealItemRecord.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                createMealItemRecordRow();
            }
        });

        Button dateBtn = (Button) findViewById(R.id.mealDate);
        dateBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID);
            }
        });


        dateBtn.setText(Utils.getBtnDateString(GregorianCalendar.getInstance().getTime()));
        mealRecord.setTime(GregorianCalendar.getInstance().getTime());
        mealRecord.getTime().setHours(12);
        mealRecord.getTime().setMinutes(0);

        itemList = getResources().getStringArray(R.array.meal_items);
        itemQuantAssistList = getResources().getStringArray(R.array.meal_item_quant_unit_description);
        caloriesList = getResources().getIntArray(R.array.meal_item_calories);


//        Button timeBtn = (Button) findViewById(R.id.mealTime);
//        timeBtn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                showDialog(TIME_DIALOG_ID);
//            }
//        });
//
//        timeBtn.setText(new SimpleDateFormat("h:mm a").format(GregorianCalendar.getInstance().getTime()));


    }

    private void createMealItemRecordRow() {
        TableLayout tableLayout = (TableLayout) findViewById(R.id.mealItemTableLayout);
        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        // add meal item auto complete text view
        AutoCompleteTextView mealItemTextView = new AutoCompleteTextView(this);
        mealItemTextView.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        mealItemTextView.setHint("Meal Item");
        mealItemTextView.setThreshold(1);

        setupMealItemDataBinding(mealItemTextView);
        // add quanity text box
        EditText quantityEditText = new EditText(this);
        quantityEditText.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        quantityEditText.setHint("Quantity");
        quantityEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        quantityEditText.setEnabled(false);


        // add controls to the row
        tableRow.addView(mealItemTextView);
        tableRow.addView(quantityEditText);
        // add row to the table
        tableLayout.addView(tableRow, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));


//        mealItemTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                //To change body of implemented methods use File | Settings | File Templates.
//                int k = 0;
//            }
//
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                //To change body of implemented methods use File | Settings | File Templates.
//            }
//        });

    }


    private void setupMealItemDataBinding(AutoCompleteTextView autoCompleteTextView) {
        String[] itemList = getResources().getStringArray(R.array.meal_items);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, itemList);
        autoCompleteTextView.setAdapter(adapter3);
        autoCompleteTextView.setOnItemClickListener(this);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            String[] itemList = getResources().getStringArray(R.array.meal_items);
//            String[] itemQuantAssistList = getResources().getStringArray(R.array.meal_item_quant_unit_description);
//            int[] caloriesList = getResources().getIntArray(R.array.meal_item_calories);
            // iterate through the table layout, table rows and get to edit text, set its hint
            TableLayout tableLayout = (TableLayout) findViewById(R.id.mealItemTableLayout);
            int maxItems = tableLayout.getChildCount();
            for (int index = 0; index < maxItems; index++) {
                TableRow tableRow = (TableRow) tableLayout.getChildAt(index);
                String mealItem = null;
                int pos = -1;
                for (int indexOne = 0; indexOne < 2; indexOne++) {
                    View v = tableRow.getChildAt(indexOne);
                    if (v instanceof AutoCompleteTextView) {
                        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) v;
                        mealItem = autoCompleteTextView.getText().toString();
                        for (int indexTwo = 0; indexTwo < itemList.length; indexTwo++) {
                            if (itemList[indexTwo].equalsIgnoreCase(mealItem)) {
                                pos = indexTwo;
                            }
                        }
                    }

                    if (!(v instanceof AutoCompleteTextView)) {
                        if (pos != -1) {
                            EditText editText = (EditText) v;
                            editText.setEnabled(true);
                            editText.setHint(itemQuantAssistList[pos]);
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
                    }
                }
            }
        }
    };

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Thread thread = new Thread(this);
        thread.start();
    }

    public void run() {
        handler.sendEmptyMessage(0);
    }

    private void populateCalories() {
//        String[] itemList = getResources().getStringArray(R.array.meal_items);
//        String[] itemQuantAssistList = getResources().getStringArray(R.array.meal_item_quant_unit_description);
//        int[] caloriesList = getResources().getIntArray(R.array.meal_item_calories);
        // iterate through the table layout, table rows and get to edit text, set its hint
        TableLayout tableLayout = (TableLayout) findViewById(R.id.mealItemTableLayout);
        int cals = 0;
        int maxItems = tableLayout.getChildCount();
        for (int index = 0; index < maxItems; index++) {
            TableRow tableRow = (TableRow) tableLayout.getChildAt(index);
            MealItemRecord mealItemRecord = new MealItemRecord();
            for (int indexOne = 0; indexOne < 2; indexOne++) {
                View v = tableRow.getChildAt(indexOne);
                mealItemRecord.setMealRecord(mealRecord);
                if (v instanceof AutoCompleteTextView) {
                    AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) v;
                    String mealItem = autoCompleteTextView.getText().toString();
                    mealItemRecord.setMealItem(mealItem);
                } else {
                    EditText editText = (EditText) v;
                    if (editText.getText().length() > 0) {
                        mealItemRecord.setNumDishes(Integer.parseInt(editText.getText().toString()));
                        // calculate and set the calorie intake
                        // find item index
                        int pos = -1;
                        for (int indexTwo = 0; indexTwo < itemList.length; indexTwo++) {
                            if (itemList[indexTwo].equalsIgnoreCase(mealItemRecord.getMealItem())) {
                                pos = indexTwo;
                            }
                        }
                        int calorieIntake = mealItemRecord.getNumDishes() * caloriesList[pos];
                        cals = cals + calorieIntake;
                    }
                }
            }
        }
        TextView textView = (TextView) findViewById(R.id.mealCalories);
        textView.setText(new Integer(cals).toString());
        textView.setTextColor(Color.RED);
    }

    private void populateMealItemRecords() {
//        String[] itemList = getResources().getStringArray(R.array.meal_items);
//        String[] itemQuantAssistList = getResources().getStringArray(R.array.meal_item_quant_unit_description);
//        int[] caloriesList = getResources().getIntArray(R.array.meal_item_calories);
        // iterate through the table layout, table rows and get to edit text, set its hint
        TableLayout tableLayout = (TableLayout) findViewById(R.id.mealItemTableLayout);
        int maxItems = tableLayout.getChildCount();
        for (int index = 0; index < maxItems; index++) {
            TableRow tableRow = (TableRow) tableLayout.getChildAt(index);
            MealItemRecord mealItemRecord = new MealItemRecord();
            for (int indexOne = 0; indexOne < 2; indexOne++) {
                View v = tableRow.getChildAt(indexOne);
                mealItemRecord.setMealRecord(mealRecord);
                if (v instanceof AutoCompleteTextView) {
                    AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) v;
                    if (!(autoCompleteTextView.getText().length() > 0)) {
                        Toast.makeText(this, "Please select meal item", Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        String mealItem = autoCompleteTextView.getText().toString();
                        mealItemRecord.setMealItem(mealItem);
                    }

                } else {
                    EditText editText = (EditText) v;
                    if (!(editText.getText().length() > 0)) {
                        Toast.makeText(this, "Please enter quantity of meal item", Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        mealItemRecord.setNumDishes(Integer.parseInt(editText.getText().toString()));
                        // calculate and set the calorie intake
                        // find item index
                        int pos = -1;
                        for (int indexTwo = 0; indexTwo < itemList.length; indexTwo++) {
                            if (itemList[indexTwo].equalsIgnoreCase(mealItemRecord.getMealItem())) {
                                pos = indexTwo;
                            }
                        }
                        int calorieIntake = mealItemRecord.getNumDishes() * caloriesList[pos];
                        mealItemRecord.setCalorieIntake(calorieIntake);
                        mealRecord.getItemRecords().add(mealItemRecord);
                    }
                }
            }
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
    protected Dialog onCreateDialog(int id) {
        switch (id) {
//            case TIME_DIALOG_ID: {
//                Date time = GregorianCalendar.getInstance().getTime();
//                return new TimePickerDialog(this, timeSetListener, time.getHours(), time.getMinutes(), false);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setEnabled(false);
        switch (item.getItemId()) {
            case R.id.saveMenuBtn: {
                mealRecord.setUserAccount(MadhumitraModel.getInstance().getCurrentUserAccount());
                populateMealItemRecords();
                MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager(this).createMealRecord(mealRecord);
                reload();
                Toast.makeText(this, "Meal record saved", Toast.LENGTH_LONG).show();
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
