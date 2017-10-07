package com.honap.madhumitra.cc;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.honap.madhumitra.R;
import com.honap.madhumitra.data.MadhumitraDataManagerFactory;
import com.honap.madhumitra.entity.PrescribedDose;

import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Chetan S.
 */
public class PrescriptionItem extends LinearLayout {

    static final int TIME_DIALOG_ID = 110;
    static final int DATE_DIALOG_ID = 20;
    private Context cntx = null;
    private Button currentTimeBtn = null;
    //private Map<Button,PrescribedDose> btnDoseMap = new HashMap<Button, PrescribedDose>();

    private com.honap.madhumitra.entity.PrescriptionItem prescriptionItemEntity = new com.honap.madhumitra.entity.PrescriptionItem();

//    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
//        public void onTimeSet(TimePicker timePicker, int i, int i1) {
//            //To change body of implemented methods use File | Settings | File Templates.
//            Button time = currentTimeBtn;
//            Date timeObj = GregorianCalendar.getInstance().getTime();
//            timeObj.setHours(timePicker.getCurrentHour());
//            timeObj.setMinutes(timePicker.getCurrentMinute());
//            String timeString = DateFormat.getTimeInstance().
//                    format(timeObj);
//            //String timeString = timePicker.getCurrentHour().toString() + ":" + timePicker.getCurrentMinute().toString();
//            time.setText(timeString,TextView.BufferType.EDITABLE);
//            PrescribedDose dose = btnDoseMap.get(currentTimeBtn);
//            dose.getDoseTime().setHours(timePicker.getCurrentHour());
//            dose.getDoseTime().setMinutes(timePicker.getCurrentMinute());
//        }
//    };

//    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
//        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//            //To change body of implemented methods use File | Settings | File Templates.
//            Button date = (Button) findViewById(R.id.drugStartDate);
//            String dateString = DateFormat.getDateInstance().format
//                    (new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth()));
//            date.setText(dateString, TextView.BufferType.EDITABLE);
//            prescriptionItemEntity.getStartOn().setYear(datePicker.getYear());
//            prescriptionItemEntity.getStartOn().setMonth(datePicker.getMonth());
//            prescriptionItemEntity.getStartOn().setDate(datePicker.getDayOfMonth());
//
//        }
//    };

    public PrescriptionItem(Context context) {
        super(context);
        this.cntx = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.cc_prescription_item, this);
        setupEventListeners();
//        prescriptionItemEntity.setPrescribedDoses(
//                MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager(cntx).
//                        getEmptyCollection(prescriptionItemEntity,"prescribed_dose"));

    }

    public PrescriptionItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.cntx = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.cc_prescription_item, this);
        setupEventListeners();
//        prescriptionItemEntity.setPrescribedDoses(
//                MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager(cntx).
//                        getEmptyCollection(prescriptionItemEntity,"prescribed_dose"));
    }

    private void setupEventListeners() {
        prescriptionItemEntity.setMorningDose(false);
        prescriptionItemEntity.setNoonDose(false);
        prescriptionItemEntity.setEvenDose(false);
        prescriptionItemEntity.setNightDose(false);
        CheckBox mornCheckBox = (CheckBox) findViewById(R.id.mornCheckBox);
        mornCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //To change body of implemented methods use File | Settings | File Templates.
                prescriptionItemEntity.setMorningDose(b);
                if (b) {
                    DoseRecordDialog dialog = new DoseRecordDialog(cntx, prescriptionItemEntity,
                            cntx.getResources().getString(R.string.med_dose_tag_morn));
                    dialog.show();
                }
            }
        });

        CheckBox noonCheckBox = (CheckBox) findViewById(R.id.noonCheckBox);
        noonCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //To change body of implemented methods use File | Settings | File Templates.
                prescriptionItemEntity.setNoonDose(b);
                if (b) {

                    DoseRecordDialog dialog = new DoseRecordDialog(cntx, prescriptionItemEntity,
                            cntx.getResources().getString(R.string.med_dose_tag_noon));
                    dialog.show();
                }
            }
        });


        CheckBox evenCheckBox = (CheckBox) findViewById(R.id.evenCheckBox);
        evenCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //To change body of implemented methods use File | Settings | File Templates.
                prescriptionItemEntity.setEvenDose(b);
                if (b) {

                    DoseRecordDialog dialog = new DoseRecordDialog(cntx, prescriptionItemEntity,
                            cntx.getResources().getString(R.string.med_dose_tag_even));
                    dialog.show();
                }
            }
        });


        CheckBox nightCheckBox = (CheckBox) findViewById(R.id.nightCheckBox);
        nightCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //To change body of implemented methods use File | Settings | File Templates.
                prescriptionItemEntity.setNightDose(b);
                if (b) {

                    DoseRecordDialog dialog = new DoseRecordDialog(cntx, prescriptionItemEntity,
                            cntx.getResources().getString(R.string.med_dose_tag_night));
                    dialog.show();
                }

            }
        });


//        ((RecordPrescription)cntx).setDrugDoseTimeListener(timeSetListener);
//        ((RecordPrescription)cntx).setDrugStartDateListener(dateSetListener);
//
//        Button addDoseButton = (Button)findViewById(R.id.addPrescriptionItemDoseBtn);
//            addDoseButton.setOnClickListener(new OnClickListener() {
//                public void onClick(View view) {
//                    createDoseRow();
//                }
//            });
//
//        Button date = (Button) findViewById(R.id.drugStartDate);
//        date.setOnClickListener(new OnClickListener() {
//            public void onClick(View view) {
//                ((RecordPrescription)cntx).showDialog(DATE_DIALOG_ID);
//            }
//        });
    }

    private void createDoseRow() {
//        TableLayout tableLayout = (TableLayout) findViewById(R.id.prescriptionItemDosesTableLayout);
//        TableRow tableRow = new TableRow(cntx);
//        tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
//
//        // add dose item button
//        Button doseItemTime = new Button(cntx);
//        doseItemTime.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
//        doseItemTime.setText("Dose Time");
//
//        // add dose item label
//        EditText doseItemLabel = new EditText(cntx);
//        doseItemLabel.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
//        doseItemLabel.setHint("Dose");
//
//        PrescribedDose dose = new PrescribedDose();
//        dose.setPrescriptionItem(prescriptionItemEntity);
//        prescriptionItemEntity.getPrescribedDoses().add(dose);
//        btnDoseMap.put(doseItemTime,dose);
//
//        doseItemTime.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                ((RecordPrescription)cntx).showDialog(TIME_DIALOG_ID);
//                currentTimeBtn = (Button)view;
//            }
//        });
//
//        tableRow.addView(doseItemTime);
//        tableRow.addView(doseItemLabel);
//        // add row to the table
//        tableLayout.addView(tableRow,new TableLayout.LayoutParams(
//                                                        TableRow.LayoutParams.FILL_PARENT,
//                                                       TableRow.LayoutParams.WRAP_CONTENT));
    }

    public com.honap.madhumitra.entity.PrescriptionItem getPrescriptionItem() {
        // set drug name
        EditText drugNameEditText = (EditText) findViewById(R.id.drugName);

        if (!(drugNameEditText.getText().length() > 0)) {
            Toast.makeText(this.cntx, "Please enter drug name", Toast.LENGTH_LONG).show();
            return null;
        } else {
            prescriptionItemEntity.setDrug(drugNameEditText.getText().toString());
        }


        // set drug duration
        EditText drugDurationEditText = (EditText) findViewById(R.id.drugDuration);
        if (!(drugDurationEditText.getText().length() > 0)) {
            Toast.makeText(this.cntx, "Please enter drug duration", Toast.LENGTH_LONG).show();
            return null;
        } else {
            prescriptionItemEntity.setNumDays(Integer.parseInt(drugDurationEditText.getText().toString()));
        }


        if(!(prescriptionItemEntity.isMorningDose() || prescriptionItemEntity.isNoonDose() ||
                prescriptionItemEntity.isEvenDose() || prescriptionItemEntity.isNightDose())) {
            Toast.makeText(this.cntx, "Please enter at least one drug dose", Toast.LENGTH_LONG).show();
            return null;
        }


        // iterate and set drug doses
//        TableLayout tableLayout = (TableLayout) findViewById(R.id.prescriptionItemDosesTableLayout);
//        for(int index = 0; index < tableLayout.getChildCount(); index++) {
//            TableRow tableRow = (TableRow)tableLayout.getChildAt(index);
//            Button timeBtn = (Button)tableRow.getChildAt(0);
//            EditText doseEditText = (EditText)tableRow.getChildAt(1);
//            // get prescribed dose by button
//            PrescribedDose prescribedDose = btnDoseMap.get(timeBtn);
//            prescribedDose.setDose(doseEditText.getText().toString());
//        }

        return prescriptionItemEntity;
    }
}
