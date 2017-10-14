package com.honap.madhumitra.act;

//import org.android.R;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.widget.TableRow.LayoutParams;
import com.honap.madhumitra.R;
import com.honap.madhumitra.data.DbHelper;
import com.honap.madhumitra.data.MadhumitraDataManagerFactory;
import com.honap.madhumitra.entity.Doctor;
import com.honap.madhumitra.entity.MealItemRecord;
import com.honap.madhumitra.entity.MealRecord;
import com.honap.madhumitra.model.MadhumitraModel;
import com.honap.madhumitra.utils.Utils;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;


public class RecordClassifiedMeal extends OrmLiteBaseActivity<DbHelper> implements Runnable, AdapterView.OnItemClickListener {
    //    static final int TIME_DIALOG_ID = 0;
    static final int DATE_DIALOG_ID = 1;
    private MealRecord mealRecord = new MealRecord();
    //private Map<AutoCompleteTextView, EditText> controlMap = new HashMap<AutoCompleteTextView, EditText>();
    private Map<AutoCompleteTextView, String> mealTypeMap = new HashMap<AutoCompleteTextView, String>();
    //private Context cntx = null;
    String[] mealTypes = null;

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
        mealTypes = this.getResources().getStringArray(R.array.meal_types);
        mealRecord.setItemRecords(
                MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager
                        (this).getEmptyCollection(mealRecord, "meal_item_record"));
//	    AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.mealItemAutoTxtVw);
        Button addMealItemRecord = (Button) findViewById(R.id.addMealItemQuantBtn);
        addMealItemRecord.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                CharSequence[] mts = new CharSequence[mealTypes.length];

                for (int index=0;index < mealTypes.length; index++) {
                    mts[index] = mealTypes[index];
                }

                boolean[] states = new boolean[mealTypes.length];
                for (int j = 0; j < states.length; j++) {
                    states[j] = false;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(RecordClassifiedMeal.this);
                builder.setTitle("Select Meal Type");


                builder.setSingleChoiceItems(mts, 0, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String mealType = mealTypes[i];
                        createMealItemRecordRow(mealType);
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
//                alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                    public void onDismiss(DialogInterface dialogInterface) {
//                    }
//                });

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
    }

    private void createMealItemRecordRow(String mealType) {
        TableLayout tableLayout = (TableLayout) findViewById(R.id.mealItemTableLayout);
        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        // add meal item auto complete text view
        AutoCompleteTextView mealItemTextView = new AutoCompleteTextView(this);
        mealItemTextView.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        mealItemTextView.setHint(mealType);
        mealItemTextView.setThreshold(1);
        setupMealItemDataBinding(mealItemTextView,mealType);
        this.mealTypeMap.put(mealItemTextView,mealType);
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
    }

    private String[] getItemList(String mealType) {
        String[] itemList = null;
        if(mealType.equals("Hard and Soft Drinks")) {
            itemList = getResources().getStringArray(R.array.meal_beverages_items);
        }
        if(mealType.equals("Veg. Main Course")) {
            itemList = getResources().getStringArray(R.array.meal_main_course_items);
        }
        if(mealType.equals("Milk Products")) {
            itemList = getResources().getStringArray(R.array.meal_milk_prods_items);
        }
        if(mealType.equals("Non Veg. Main Course")) {
            itemList = getResources().getStringArray(R.array.meal_poultry_mutton_items);
        }
        if(mealType.equals("Fruits and Nuts")) {
            itemList = getResources().getStringArray(R.array.meal_nuts_items);
        }
        if(mealType.equals("Snacks")) {
            itemList = getResources().getStringArray(R.array.meal_snacks_items);
        }
        if(mealType.equals("Sweets")) {
            itemList = getResources().getStringArray(R.array.meal_sweets_items);
        }
        if(mealType.equals("Rice, Rotis and S.Indian")) {
            itemList = getResources().getStringArray(R.array.meal_rice_rotis_south_items);
        }

        return itemList;
    }

    private String[] getItemQuantLabelList(String mealType) {
        String[] itemList = null;
        if(mealType.equals("Hard and Soft Drinks")) {
            itemList = getResources().getStringArray(R.array.meal_beverages_item_quant_unit_description);
        }
        if(mealType.equals("Veg. Main Course")) {
            itemList = getResources().getStringArray(R.array.meal_main_course_item_quant_unit_description);
        }
        if(mealType.equals("Milk Products")) {
            itemList = getResources().getStringArray(R.array.meal_milk_prods_item_quant_unit_description);
        }
        if(mealType.equals("Non Veg. Main Course")) {
            itemList = getResources().getStringArray(R.array.meal_poultry_mutton_item_quant_unit_description);
        }
        if(mealType.equals("Fruits and Nuts")) {
            itemList = getResources().getStringArray(R.array.meal_nuts_item_quant_unit_description);
        }
        if(mealType.equals("Snacks")) {
            itemList = getResources().getStringArray(R.array.meal_snacks_item_quant_unit_description);
        }
        if(mealType.equals("Sweets")) {
            itemList = getResources().getStringArray(R.array.meal_sweets_item_quant_unit_description);
        }
        if(mealType.equals("Rice, Rotis and S.Indian")) {
            itemList = getResources().getStringArray(R.array.meal_rice_rotis_south_item_quant_unit_description);
        }


        return itemList;
    }

    private int[] getItemCalorieList(String mealType) {
        int[] itemList = null;
        if(mealType.equals("Hard and Soft Drinks")) {
            itemList = getResources().getIntArray(R.array.meal_beverages_item_calories);
        }
        if(mealType.equals("Veg. Main Course")) {
            itemList = getResources().getIntArray(R.array.meal_main_course_item_calories);
        }
        if(mealType.equals("Milk Products")) {
            itemList = getResources().getIntArray(R.array.meal_milk_prods_item_calories);
        }

        if(mealType.equals("Non Veg. Main Course")) {
            itemList = getResources().getIntArray(R.array.meal_poultry_mutton_item_calories);
        }
        if(mealType.equals("Fruits and Nuts")) {
            itemList = getResources().getIntArray(R.array.meal_nuts_item_calories);
        }
        if(mealType.equals("Snacks")) {
            itemList = getResources().getIntArray(R.array.meal_snacks_item_calories);
        }
        if(mealType.equals("Sweets")) {
            itemList = getResources().getIntArray(R.array.meal_sweets_item_calories);
        }
        if(mealType.equals("Rice, Rotis and S.Indian")) {
            itemList = getResources().getIntArray(R.array.meal_rice_rotis_south_item_calories);
        }

        return itemList;
    }


    private void setupMealItemDataBinding(AutoCompleteTextView autoCompleteTextView,String mealType) {
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,
                getItemList(mealType));
        autoCompleteTextView.setAdapter(adapter3);
        autoCompleteTextView.setOnItemClickListener(this);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String[] itemList = null;
            String[] itemQuantAssistList = null;
//            int[] caloriesList = getResources().getIntArray(R.array.meal_item_calories);
            // iterate through the table layout, table rows and get to edit text, set its hint
            TableLayout tableLayout = (TableLayout) findViewById(R.id.mealItemTableLayout);
            int maxItems = tableLayout.getChildCount();
            for (int index = 0; index < maxItems; index++) {
                TableRow tableRow = (TableRow) tableLayout.getChildAt(index);
                String mealItem = null;
                String mealType = null;
                int pos = -1;
                for (int indexOne = 0; indexOne < 2; indexOne++) {
                    View v = tableRow.getChildAt(indexOne);
                    if (v instanceof AutoCompleteTextView) {
                        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) v;
                        // get meal item type associated
                        mealType = mealTypeMap.get(autoCompleteTextView);
                        itemList = getItemList(mealType);
                        mealItem = autoCompleteTextView.getText().toString();
                        for (int indexTwo = 0; indexTwo < itemList.length; indexTwo++) {
                            if (itemList[indexTwo].equalsIgnoreCase(mealItem)) {
                                pos = indexTwo;
                            }
                        }
                    }

                    if (!(v instanceof AutoCompleteTextView)) {
                        if (pos != -1) {
                            itemQuantAssistList = getItemQuantLabelList(mealType);
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
        String[] itemList = null;
//        String[] itemQuantAssistList = getResources().getStringArray(R.array.meal_item_quant_unit_description);
        int[] caloriesList = null;
        // iterate through the table layout, table rows and get to edit text, set its hint
        TableLayout tableLayout = (TableLayout) findViewById(R.id.mealItemTableLayout);
        int cals = 0;
        int maxItems = tableLayout.getChildCount();
        for (int index = 0; index < maxItems; index++) {
            TableRow tableRow = (TableRow) tableLayout.getChildAt(index);
            String mealType= null;
            MealItemRecord mealItemRecord = new MealItemRecord();
            for (int indexOne = 0; indexOne < 2; indexOne++) {
                View v = tableRow.getChildAt(indexOne);
                mealItemRecord.setMealRecord(mealRecord);
                if (v instanceof AutoCompleteTextView) {
                    AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) v;
                        // get meal item type associated
                    mealType = mealTypeMap.get(autoCompleteTextView);
                    itemList = getItemList(mealType);
                    String mealItem = autoCompleteTextView.getText().toString();
                    mealItemRecord.setMealItem(mealItem);
                } else {
                    EditText editText = (EditText) v;
                    if (editText.getText().length() > 0) {
                        mealItemRecord.setNumDishes(Integer.parseInt(editText.getText().toString()));
                        caloriesList = getItemCalorieList(mealType);
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
        String[] itemList = null;
//        String[] itemQuantAssistList = getResources().getStringArray(R.array.meal_item_quant_unit_description);
        int[] caloriesList = null;
        // iterate through the table layout, table rows and get to edit text, set its hint
        TableLayout tableLayout = (TableLayout) findViewById(R.id.mealItemTableLayout);
        int maxItems = tableLayout.getChildCount();
        for (int index = 0; index < maxItems; index++) {
            TableRow tableRow = (TableRow) tableLayout.getChildAt(index);
            MealItemRecord mealItemRecord = new MealItemRecord();
            String mealType = null;
            for (int indexOne = 0; indexOne < 2; indexOne++) {
                View v = tableRow.getChildAt(indexOne);
                mealItemRecord.setMealRecord(mealRecord);
                if (v instanceof AutoCompleteTextView) {
                    AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) v;
                    mealType = mealTypeMap.get(autoCompleteTextView);
                    itemList = getItemList(mealType);
                    if (!(autoCompleteTextView.getText().length() > 0)) {
                        Toast.makeText(this, "Please select meal item", Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        String mealItem = autoCompleteTextView.getText().toString();
                        mealItemRecord.setMealItem(mealItem);
                    }

                } else {
                    EditText editText = (EditText) v;
                    caloriesList = getItemCalorieList(mealType);
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
