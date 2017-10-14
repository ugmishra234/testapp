package com.honap.madhumitra.act;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.honap.madhumitra.R;
import com.honap.madhumitra.cc.PrescriptionItem;
import com.honap.madhumitra.data.MadhumitraDataManagerFactory;
import com.honap.madhumitra.entity.*;
import com.honap.madhumitra.model.MadhumitraModel;
//import com.honap.madhumitra.service.MedicationNotificationService;
import com.honap.madhumitra.utils.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class RecordPrescription extends Activity {
    static final int VISIT_TIME_DIALOG_ID = 0;
    static final int VISIT_DATE_DIALOG_ID = 1;
    static final int NEXT_VISIT_TIME_DIALOG_ID = 3;
    static final int NEXT_VISIT_DATE_DIALOG_ID = 4;
    static final int DRUG_START_DATE_DIALOG_ID = 20;
    static final int DRUG_DOSE_TIME_DIALOG_ID = 110;

    private DatePickerDialog.OnDateSetListener drugStartDateListener = null;
    private TimePickerDialog.OnTimeSetListener drugDoseTimeListener = null;

    private Prescription prescription = new Prescription();
    private List<Doctor> doctors = new ArrayList<Doctor>();
    private List<MedicationRecord> serviceNotificationQueue = new ArrayList<MedicationRecord>();

    private boolean isServiceBound = false;
//    private MedicationNotificationService service = null;
    private final Object syncObject = new Object();

//    private TimePickerDialog.OnTimeSetListener visitTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
//        public void onTimeSet(TimePicker timePicker, int i, int i1) {
//            //To change body of implemented methods use File | Settings | File Templates.
//            Button time = (Button) findViewById(R.id.visitTime);
//            Date timeObj = GregorianCalendar.getInstance().getTime();
//            timeObj.setHours(timePicker.getCurrentHour());
//            timeObj.setMinutes(timePicker.getCurrentMinute());
//            String timeString = DateFormat.getTimeInstance().
//                    format(timeObj);
//            //String timeString = timePicker.getCurrentHour().toString() + ":" + timePicker.getCurrentMinute().toString();
//            time.setText(timeString,TextView.BufferType.EDITABLE);
//            doctorVisit.getVisitedOn().setHours(timePicker.getCurrentHour());
//            doctorVisit.getVisitedOn().setMinutes(timePicker.getCurrentMinute());
//        }
//    };

    private DatePickerDialog.OnDateSetListener visitDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            //To change body of implemented methods use File | Settings | File Templates.
            Button date = (Button) findViewById(R.id.visitDate);
            date.setText(Utils.getBtnDateString(
                    new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth())));
            prescription.getVisitedOn().setYear(datePicker.getYear() - 1900);
            prescription.getVisitedOn().setMonth(datePicker.getMonth());
            prescription.getVisitedOn().setDate(datePicker.getDayOfMonth());
        }
    };

//    private TimePickerDialog.OnTimeSetListener nxtVisitTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
//        public void onTimeSet(TimePicker timePicker, int i, int i1) {
//            //To change body of implemented methods use File | Settings | File Templates.
//            Button time = (Button) findViewById(R.id.nxtVisitTime);
//            Date timeObj = GregorianCalendar.getInstance().getTime();
//            timeObj.setHours(timePicker.getCurrentHour());
//            timeObj.setMinutes(timePicker.getCurrentMinute());
//            String timeString = DateFormat.getTimeInstance().
//                    format(timeObj);
//            //String timeString = timePicker.getCurrentHour().toString() + ":" + timePicker.getCurrentMinute().toString();
//            time.setText(timeString,TextView.BufferType.EDITABLE);
//            doctorVisit.getNextVisit().setHours(timePicker.getCurrentHour());
//            doctorVisit.getNextVisit().setMinutes(timePicker.getCurrentMinute());
//        }
//    };

//    private DatePickerDialog.OnDateSetListener nxtVisitDateSetListener = new DatePickerDialog.OnDateSetListener() {
//        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//            //To change body of implemented methods use File | Settings | File Templates.
//            Button date = (Button) findViewById(R.id.nxtVisitDate);
//            String dateString = DateFormat.getDateInstance().format
//                    (new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth()));
//            date.setText(dateString, TextView.BufferType.EDITABLE);
//            doctorVisit.getNextVisit().setYear(datePicker.getYear());
//            doctorVisit.getNextVisit().setMonth(datePicker.getMonth());
//            doctorVisit.getNextVisit().setDate(datePicker.getDayOfMonth());
//        }
//    };

    public void setDrugDoseTimeListener(TimePickerDialog.OnTimeSetListener drugDoseTimeListener) {
        this.drugDoseTimeListener = drugDoseTimeListener;
    }

    public void setDrugStartDateListener(DatePickerDialog.OnDateSetListener drugStartDateListener) {
        this.drugStartDateListener = drugStartDateListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_prescription);
        setupEventListeners();
        setupDatabinding();
    }

    private void setupEventListeners() {
        Button addMealItemRecord = (Button) findViewById(R.id.addPrescriptionItemBtn);
        addMealItemRecord.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                createPrescriptionItemRow();
            }
        });


        Button dateBtn = (Button) findViewById(R.id.visitDate);
        dateBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showDialog(VISIT_DATE_DIALOG_ID);
            }
        });

        dateBtn.setText(Utils.getBtnDateString(GregorianCalendar.getInstance().getTime()));


//        Button timeBtn = (Button)findViewById(R.id.visitTime);
//        timeBtn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                showDialog(VISIT_TIME_DIALOG_ID);
//            }
//        });
//
//        Button nxtVisitDateBtn = (Button)findViewById(R.id.nxtVisitDate);
//        nxtVisitDateBtn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                showDialog(NEXT_VISIT_DATE_DIALOG_ID);
//            }
//        });
//
//        Button nxtVisitTimeBtn = (Button)findViewById(R.id.nxtVisitTime);
//        nxtVisitTimeBtn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                showDialog(NEXT_VISIT_TIME_DIALOG_ID);
//            }
//        });

    }

    private void setupDatabinding() {
        Spinner docSpinner = (Spinner) findViewById(R.id.docSpinner);
        // for the time being fake doctor, till add doctor is implemented
        Iterator<Doctor> iterator = MadhumitraModel.getInstance().getCurrentUserAccount().getDoctors().iterator();
        while (iterator.hasNext()) {
            doctors.add(iterator.next());
        }
        docSpinner.setPrompt("Select Doctor");
        ArrayAdapter<Doctor> stringArrayAdapter = new ArrayAdapter<Doctor>(this,
                android.R.layout.simple_spinner_item, doctors);
        docSpinner.setAdapter(stringArrayAdapter);
    }

    private void createPrescriptionItemRow() {
        TableLayout tableLayout = (TableLayout) findViewById(R.id.prescriptionTableLayout);
        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        PrescriptionItem prescriptionItem = new PrescriptionItem(this);
        prescriptionItem.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.FILL_PARENT, 1));
        tableRow.addView(prescriptionItem);
        tableLayout.addView(tableRow, new TableLayout.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
//            case VISIT_TIME_DIALOG_ID: {
//                Date time = GregorianCalendar.getInstance().getTime();
//                return new TimePickerDialog(this,visitTimeSetListener,time.getHours(),time.getMinutes(),false);
//            }

            case VISIT_DATE_DIALOG_ID: {
                int yr = GregorianCalendar.getInstance().get(GregorianCalendar.YEAR);
                int mnth = GregorianCalendar.getInstance().get(GregorianCalendar.MONTH);
                int dat = GregorianCalendar.getInstance().get(GregorianCalendar.DATE);
                return new DatePickerDialog(this, visitDateSetListener, GregorianCalendar.
                        getInstance().get(GregorianCalendar.YEAR)
                        , GregorianCalendar.getInstance().get(GregorianCalendar.MONTH),
                        GregorianCalendar.getInstance().get(GregorianCalendar.DATE));
            }

//            case NEXT_VISIT_TIME_DIALOG_ID: {
//                Date time = GregorianCalendar.getInstance().getTime();
//                return new TimePickerDialog(this,nxtVisitTimeSetListener,time.getHours(),time.getMinutes(),false);
//            }

//            case NEXT_VISIT_DATE_DIALOG_ID: {
//                int yr = GregorianCalendar.getInstance().get(GregorianCalendar.YEAR);
//                int mnth = GregorianCalendar.getInstance().get(GregorianCalendar.MONTH);
//                int dat = GregorianCalendar.getInstance().get(GregorianCalendar.DATE);
//                return new DatePickerDialog(this,nxtVisitDateSetListener,GregorianCalendar.
//                        getInstance().get(GregorianCalendar.YEAR)
//                        ,GregorianCalendar.getInstance().get(GregorianCalendar.MONTH),
//                        GregorianCalendar.getInstance().get(GregorianCalendar.DATE));
//            }

//            case DRUG_DOSE_TIME_DIALOG_ID: {
//                Date time = new Date();
//                return new TimePickerDialog(this, drugDoseTimeListener,time.getHours(),time.getMinutes(),false);
//            }
//
//            case DRUG_START_DATE_DIALOG_ID: {
//                int yr = GregorianCalendar.getInstance().get(GregorianCalendar.YEAR);
//                int mnth = GregorianCalendar.getInstance().get(GregorianCalendar.MONTH);
//                int dat = GregorianCalendar.getInstance().get(GregorianCalendar.DATE);
//                return new DatePickerDialog(this,drugStartDateListener,GregorianCalendar.
//                        getInstance().get(GregorianCalendar.YEAR)
//                        ,GregorianCalendar.getInstance().get(GregorianCalendar.MONTH),
//                        GregorianCalendar.getInstance().get(GregorianCalendar.DATE));
//            }

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

    public void populateMedicationRecords() {
//        bindService();
        Iterator<com.honap.madhumitra.entity.PrescriptionItem> iterator = prescription.getPrescriptionItems().iterator();
        Date start = prescription.getVisitedOn();
        while (iterator.hasNext()) {
            com.honap.madhumitra.entity.PrescriptionItem item = iterator.next();
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(start);
            for (int i = 0; i < item.getNumDays(); i++) {
                if (i != 0) {
                    calendar.add(Calendar.DATE, 1);
                }
                if (item.isMorningDose()) {
                    MedicationRecord medicationRecord = new MedicationRecord();
                    medicationRecord.setPrescription(prescription);
//                    prescription.getMedicationRecords().add(medicationRecord);
                    medicationRecord.getTime().setYear(calendar.getTime().getYear());
                    medicationRecord.getTime().setMonth(calendar.getTime().getMonth());
                    medicationRecord.getTime().setDate(calendar.getTime().getDate());
                    medicationRecord.setDose(item.getDose());
                    medicationRecord.setDoseStatus(getResources().getString(R.string.med_dose_not_notified));
                    medicationRecord.setDrug(item.getDrug());
                    medicationRecord.setUserAccount(MadhumitraModel.getInstance().getCurrentUserAccount());
                    medicationRecord.setDoseTag(getResources().getString(R.string.med_dose_tag_morn));
                    medicationRecord.getTime().setHours(item.getMornDoseTime().getHours());
                    medicationRecord.getTime().setMinutes(item.getMornDoseTime().getMinutes());
                    MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager(this).
                            createMedicationRecord(medicationRecord);
//                    synchronized (syncObject) {
//                        serviceNotificationQueue.add(medicationRecord);
//                    }
                    // create notification record
                    createMedicationNotificationRecord(medicationRecord);
                }
                if (item.isNoonDose()) {
                    MedicationRecord medicationRecord = new MedicationRecord();
                    medicationRecord.setPrescription(prescription);
//                    prescription.getMedicationRecords().add(medicationRecord);
                    medicationRecord.getTime().setYear(calendar.getTime().getYear());
                    medicationRecord.getTime().setMonth(calendar.getTime().getMonth());
                    medicationRecord.getTime().setDate(calendar.getTime().getDate());
                    medicationRecord.setDose(item.getDose());
                    medicationRecord.setDoseStatus(getResources().getString(R.string.med_dose_not_notified));
                    medicationRecord.setDrug(item.getDrug());
                    medicationRecord.setUserAccount(MadhumitraModel.getInstance().getCurrentUserAccount());
                    medicationRecord.setDoseTag(getResources().getString(R.string.med_dose_tag_noon));
                    medicationRecord.getTime().setHours(item.getNoonDoseTime().getHours());
                    medicationRecord.getTime().setMinutes(item.getNoonDoseTime().getMinutes());
                    MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager(this).
                            createMedicationRecord(medicationRecord);
//                    synchronized (syncObject) {
//                        serviceNotificationQueue.add(medicationRecord);
//                    }
                    createMedicationNotificationRecord(medicationRecord);
                }
                if (item.isEvenDose()) {
                    MedicationRecord medicationRecord = new MedicationRecord();
                    medicationRecord.setPrescription(prescription);
//                    prescription.getMedicationRecords().add(medicationRecord);
                    medicationRecord.getTime().setYear(calendar.getTime().getYear());
                    medicationRecord.getTime().setMonth(calendar.getTime().getMonth());
                    medicationRecord.getTime().setDate(calendar.getTime().getDate());
                    medicationRecord.setDose(item.getDose());
                    medicationRecord.setDoseStatus(getResources().getString(R.string.med_dose_not_notified));
                    medicationRecord.setDrug(item.getDrug());
                    medicationRecord.setUserAccount(MadhumitraModel.getInstance().getCurrentUserAccount());
                    medicationRecord.setDoseTag(getResources().getString(R.string.med_dose_tag_even));
                    medicationRecord.getTime().setHours(item.getEvenDoseTime().getHours());
                    medicationRecord.getTime().setMinutes(item.getEvenDoseTime().getMinutes());
                    MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager(this).
                            createMedicationRecord(medicationRecord);
//                    synchronized (syncObject) {
//                        serviceNotificationQueue.add(medicationRecord);
//                    }
                    createMedicationNotificationRecord(medicationRecord);
                }
                if (item.isNightDose()) {
                    MedicationRecord medicationRecord = new MedicationRecord();
                    medicationRecord.setPrescription(prescription);
//                    prescription.getMedicationRecords().add(medicationRecord);
                    medicationRecord.getTime().setYear(calendar.getTime().getYear());
                    medicationRecord.getTime().setMonth(calendar.getTime().getMonth());
                    medicationRecord.getTime().setDate(calendar.getTime().getDate());
                    medicationRecord.setDose(item.getDose());
                    medicationRecord.setDoseStatus(getResources().getString(R.string.med_dose_not_notified));
                    medicationRecord.setDrug(item.getDrug());
                    medicationRecord.setUserAccount(MadhumitraModel.getInstance().getCurrentUserAccount());
                    medicationRecord.setDoseTag(getResources().getString(R.string.med_dose_tag_night));
                    medicationRecord.getTime().setHours(item.getNightDoseTime().getHours());
                    medicationRecord.getTime().setMinutes(item.getNightDoseTime().getMinutes());
                    MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager(this).
                            createMedicationRecord(medicationRecord);
//                    synchronized (syncObject) {
//                        serviceNotificationQueue.add(medicationRecord);
//                    }
                    createMedicationNotificationRecord(medicationRecord);
                }
            }
        }
        //unbindService();
    }

    private void createMedicationNotificationRecord(MedicationRecord medicationRecord) {
        MedicationNotification notification = new MedicationNotification();
        notification.setMedRecordId(medicationRecord.getId());
        notification.setTime(medicationRecord.getTime());
        MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager(this).
                createMedicationNotification(notification);
    }

//    private ServiceConnection connection = new ServiceConnection() {
//
//        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//            service = ((MedicationNotificationService.MedicationNotificationServiceBinder) iBinder).getService();
//            synchronized (syncObject) {
//                Iterator<MedicationRecord> iterator = serviceNotificationQueue.iterator();
//                while (iterator.hasNext()) {
//                    MedicationRecord record = iterator.next();
//                    notifyServiceOfNewMedicationRecord(record);
//                }
//                serviceNotificationQueue.clear();
//            }
//            unbindService();
//
//        }
//
//        public void onServiceDisconnected(ComponentName componentName) {
//            service = null;
//        }
//    };
//
//
//    private void bindService() {
//        if (!isServiceBound) {
//            bindService(new Intent(RecordPrescription.this, MedicationNotificationService.class), connection,
//                    Context.BIND_AUTO_CREATE);
//            isServiceBound = true;
//        }
//    }
//
//    private void unbindService() {
//        if (isServiceBound) {
//            unbindService(connection);
//            isServiceBound = false;
//        }
//    }
//
//    private void notifyServiceOfNewMedicationRecord(MedicationRecord record) {
//        if (service != null) service.onMedicationRecordAddition(record);
//    }


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
                item.setEnabled(false);
                prescription.setPrescriptionItems(MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager(this).
                        getEmptyCollection(prescription, "prescription_item"));
                prescription.setMedicationRecords(MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager(this).
                        getEmptyCollection(prescription, "medication_record"));

                // get doctor spinner position and through it doctor
                Spinner docSpinner = (Spinner) findViewById(R.id.docSpinner);
                int docSpinnerPosition = docSpinner.getSelectedItemPosition();
                Doctor doctor = doctors.get(docSpinnerPosition);
                prescription.setDoctor(doctor);
                // get prescription items
                TableLayout prescriptionTableLayout = (TableLayout) findViewById(R.id.prescriptionTableLayout);
                for (int index = 0; index < prescriptionTableLayout.getChildCount(); index++) {
                    PrescriptionItem prescriptionItem = (PrescriptionItem) ((TableRow) prescriptionTableLayout.
                            getChildAt(index)).
                            getChildAt(0);
                    if (prescriptionItem != null) {
                        com.honap.madhumitra.entity.PrescriptionItem prescriptionItemEntity =
                                prescriptionItem.getPrescriptionItem();
                        if (prescriptionItemEntity == null) return true;
                        prescriptionItemEntity.setPrescription(prescription);
                        prescription.getPrescriptionItems().add(prescriptionItemEntity);
                    }
                }
                // set visit fees
//                EditText visitFeesEditText = (EditText)findViewById(R.id.visitFees);
//                doctorVisit.setFees(Integer.parseInt(visitFeesEditText.getText().toString()));
                prescription.setUserAccount(MadhumitraModel.getInstance().getCurrentUserAccount());
                MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager(this).createPrescription(prescription);
                populateMedicationRecords();
                reload();
                Toast.makeText(this, "Prescription Saved", Toast.LENGTH_LONG).show();
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
