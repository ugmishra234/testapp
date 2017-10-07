package com.honap.madhumitra.act;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.honap.madhumitra.R;
import com.honap.madhumitra.data.DbHelper;
import com.honap.madhumitra.entity.Doctor;
import com.honap.madhumitra.entity.HomeInvestigationParamValue;
import com.honap.madhumitra.entity.HomeInvestigationRecord;
import com.honap.madhumitra.entity.MedicationRecord;
import com.honap.madhumitra.misc.MedRecBslWrapper;
import com.honap.madhumitra.model.MadhumitraModel;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.ForeignCollection;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;
//

/**
 * Author: Chetan S.
 */
public class ActivityReport extends OrmLiteBaseActivity<DbHelper> {
    List<HomeInvestigationRecord> invRecord = new ArrayList<HomeInvestigationRecord>();
    List<MedicationRecord> medRecord = new ArrayList<MedicationRecord>();

    private List<String> sendTo = new ArrayList<String>();
    private List<Doctor> doctors = new ArrayList<Doctor>();

    Date from = new Date();
    Date to = new Date();


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ForeignCollection<Doctor> docs = MadhumitraModel.getInstance().getCurrentUserAccount().getDoctors();
        init();
        Iterator<Doctor> doctorIterator = docs.iterator();
        while (doctorIterator.hasNext()) {
            doctors.add(doctorIterator.next());
        }
        Button generate = (Button) findViewById(R.id.generateActReportBtn);
        generate.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                init();
                //get medication
                getBSLValues(view);
                getMedication(view);
                //writeBSLString();
                writeReport();
                selectDoctors();
            }
        });
    }

    public void init() {
        DatePicker toDatePicker = (DatePicker) findViewById(R.id.actRepToDate);
        DatePicker fromDatePicker = (DatePicker) findViewById(R.id.actRepFromDate);

        to.setDate(toDatePicker.getDayOfMonth());
        to.setMonth(toDatePicker.getMonth());
        to.setYear(toDatePicker.getYear());
        Calendar toCalendar = GregorianCalendar.getInstance();
        toCalendar.set(Calendar.DATE, to.getDate());
        toCalendar.set(Calendar.MONTH, to.getMonth());
        toCalendar.set(Calendar.YEAR, to.getYear());
        toCalendar.set(Calendar.HOUR, 12);
        toCalendar.set(Calendar.MINUTE, 1);

        to = toCalendar.getTime();

        from.setDate(fromDatePicker.getDayOfMonth());
        from.setMonth(fromDatePicker.getMonth());
        from.setYear(fromDatePicker.getYear());

        Calendar fromCalendar = GregorianCalendar.getInstance();
        fromCalendar.set(Calendar.DATE, from.getDate());
        fromCalendar.set(Calendar.MONTH, from.getMonth());
        fromCalendar.set(Calendar.YEAR, from.getYear());
        fromCalendar.set(Calendar.HOUR, 11);
        fromCalendar.set(Calendar.MINUTE, 59);
        fromCalendar.add(Calendar.DATE, -1);

        from = fromCalendar.getTime();

    }

    private void getBSLValues(View view) {
        //get the BSL values for the time period
        Iterator<HomeInvestigationRecord> homeInvestigationRecordIterator = MadhumitraModel.getInstance().getCurrentUserAccount().getHomeInvestigations().iterator();
        //Set the date to one from the spinner
        while (homeInvestigationRecordIterator.hasNext()) {
            HomeInvestigationRecord invRec = homeInvestigationRecordIterator.next();
            boolean pass1 = invRec.getInvestigatedOn().after(from);
            boolean pass2 = invRec.getInvestigatedOn().before(to);
            if (pass1 && pass2 && invRec.getInvestigation().equalsIgnoreCase(view.getContext().getResources().getString(R.string.bsl_id))) {
                invRecord.add(
                        invRec);
            }
        }
    }

    private void getMedication(View view) {
        //get the medication done during this period
        Iterator<MedicationRecord> medicationRecordIterator = MadhumitraModel.getInstance().getCurrentUserAccount().getMedicationRecords().iterator();
        while (medicationRecordIterator.hasNext()) {
            MedicationRecord medicationRecord = medicationRecordIterator.next();
            boolean pass1 = medicationRecord.getTime().after(from);
            boolean pass2 = medicationRecord.getTime().before(to);
            if (pass1 && pass2 && (medicationRecord.getDoseStatus().equalsIgnoreCase(view.getContext().getResources().getString(R.string.med_dose_taken)))) {
                medRecord.add(medicationRecord);
            }
        }
    }

    private void selectDoctors() {
        // prepare list of items
        Iterator<Doctor> iterator = doctors.iterator();
        CharSequence[] docNames = new CharSequence[doctors.size()];
        boolean[] states = new boolean[doctors.size()];
        for (int j = 0; j < states.length; j++) {
            states[j] = false;
        }
        int i = 0;
        while (iterator.hasNext()) {
            docNames[i] = iterator.next().getDisplayName();
            i = i + 1;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Doctor");
        builder.setMultiChoiceItems(docNames, states, new DialogInterface.OnMultiChoiceClickListener() {
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                // get doctor by index
                Doctor d = doctors.get(i);
                if (b) {
                    // add doctor email
                    sendTo.add(d.getEmail());
                } else {
                    sendTo.remove(d.getEmail());
                }

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface dialogInterface) {
                email();
            }
        });
    }

    private void writeBSLString() {
        List<String> bslDates = new ArrayList<String>();
        List<String> bslVal = new ArrayList<String>();

        Iterator<HomeInvestigationRecord> itr = invRecord.iterator();
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(getResources().getString(R.string.act_report_file), false), ',');
            while (itr.hasNext()) {
                HomeInvestigationRecord temp = itr.next();
                HomeInvestigationParamValue tempVal = temp.getInvestigationValues().iterator().next();
                bslDates.add(temp.getInvestigatedOn().toString());
                bslVal.add(tempVal.getValue());
            }

            Iterator<String> bslvalItr = bslVal.iterator();
            String bslvl = new String();
            bslvl += "BSL#";
            while (bslvalItr.hasNext()) {
                bslvl += bslvalItr.next();
                bslvl += "#";
            }
            bslvl += "\n";

            Iterator<String> bslItr = bslDates.iterator();
            //String bslDt = new String();
            while (bslItr.hasNext()) {
                bslvl += bslItr.next();
                bslvl += "#";
            }
            bslvl += "\n";
            String[] finalWrite = bslvl.split("#");
            writer.writeNext(finalWrite);
            writer.close();
        } catch (IOException e)//needed for the CSV Writer
        {

        }
    }

    private void writeReport() {
        //List<String> bslVal = new ArrayList<String>();
        //List<String> medicationVal = new ArrayList<String>();
        List<String> dates = new ArrayList<String>();

        List<MedRecBslWrapper> medRecBslWrapperList = new ArrayList<MedRecBslWrapper>();


        Iterator<HomeInvestigationRecord> itr = invRecord.iterator();
        while (itr.hasNext()) {
            MedRecBslWrapper temp = new MedRecBslWrapper(itr.next());
            medRecBslWrapperList.add(temp);
        }
        Iterator<MedicationRecord> meditr = medRecord.iterator();
        while (meditr.hasNext()) {
            MedRecBslWrapper temp = new MedRecBslWrapper(meditr.next());
            medRecBslWrapperList.add(temp);
        }
        Collections.sort(medRecBslWrapperList);
        String bslString = new String();
        String medicationString = new String();
        String dateString = new String();
        dateString += "Date and Time:";
        dateString += "#";
        Iterator<MedRecBslWrapper> datesItr = medRecBslWrapperList.iterator();
        //String bslDt = new String();
        while (datesItr.hasNext()) {
            dateString += datesItr.next().getDate();
            dateString += "#";
        }
        bslString += "BSL:";
        bslString += "#";
        medicationString += "Medication:";
        medicationString += "#";
        //String finalreport = new String();
        try {
            Iterator<MedRecBslWrapper> medrecBsl = medRecBslWrapperList.iterator();
            CSVWriter writer = new CSVWriter(new FileWriter(getResources().getString(R.string.act_report_file), false), ',');
            while (medrecBsl.hasNext()) {
                MedRecBslWrapper value = medrecBsl.next();
                if (value.getWrappedObj() instanceof HomeInvestigationRecord) {
                    HomeInvestigationRecord homeInvestigationRecord = (HomeInvestigationRecord) value.getWrappedObj();
                    HomeInvestigationParamValue tempVal = homeInvestigationRecord.getInvestigationValues().iterator().next();
                    bslString += tempVal.getValue();
                    bslString += "#";
                    medicationString += "#";
                } else if (value.getWrappedObj() instanceof MedicationRecord) {
                    MedicationRecord medicationRecord = (MedicationRecord) value.getWrappedObj();
                    medicationString += medicationRecord.getDrug();
                    medicationString += "#";
                    bslString += "#";
                }

            }
            String[] dateWrite = dateString.split("#");
            writer.writeNext(dateWrite);
            String[] finalWrite = bslString.split("#");
            writer.writeNext(finalWrite);
            String[] medWrite = medicationString.split("#");
            writer.writeNext(medWrite);
            writer.close();
        } catch (IOException exp) {

        }
    }

    public void email() {
        String[] toList = new String[sendTo.size()];
        for (int i = 0; i < toList.length; i++) {
            toList[i] = sendTo.get(i);
        }
        List<String> filePaths = new ArrayList<String>();

        filePaths.add(getResources().getString(R.string.act_report_file));
        //need to "send multiple" to get more than one attachment
        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND_MULTIPLE);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(Intent.EXTRA_EMAIL,
                toList);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "PFA Activity Report of " +
                MadhumitraModel.getInstance().getCurrentUserAccount().getDisplayName());
        //has to be an ArrayList
        ArrayList<Uri> uris = new ArrayList<Uri>();
        for (String file : filePaths) {
            File fileIn = new File(file);
            Uri u = Uri.fromFile(fileIn);
            uris.add(u);
        }
        emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        this.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.sendTo = new ArrayList<String>();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        this.sendTo = new ArrayList<String>();
    }
}