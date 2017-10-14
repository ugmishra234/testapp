package com.honap.madhumitra.cc;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.honap.madhumitra.entity.PrescriptionItem;
import com.honap.madhumitra.R;
import com.honap.madhumitra.model.MadhumitraModel;

import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * Author: Chetan S.
 */
public class DoseRecordDialog extends Dialog {

    private Context cntx = null;
    private PrescriptionItem prescriptionItemEntity = null;
    private String doseTime = null;

    public DoseRecordDialog(Context context, PrescriptionItem item, String doseTime) {
        super(context);
        this.cntx = context;
        this.prescriptionItemEntity = item;
        this.doseTime = doseTime;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.cc_dose_record_dialog);
        setTitle("Dose Details");
        setupEventListeners();
        TimePicker timePicker = (TimePicker)findViewById(R.id.doseTimePicker);
        if(doseTime.equalsIgnoreCase(cntx.getResources().getString(R.string.med_dose_tag_morn))) {
            timePicker.setCurrentHour(MadhumitraModel.getInstance().getPreferences().getMornHour());
            timePicker.setCurrentMinute(MadhumitraModel.getInstance().getPreferences().getMornMin());
        }
        if(doseTime.equalsIgnoreCase(cntx.getResources().getString(R.string.med_dose_tag_noon))) {
            timePicker.setCurrentHour(MadhumitraModel.getInstance().getPreferences().getNoonHour());
            timePicker.setCurrentMinute(MadhumitraModel.getInstance().getPreferences().getNoonMin());
        }
        if(doseTime.equalsIgnoreCase(cntx.getResources().getString(R.string.med_dose_tag_even))) {
            timePicker.setCurrentHour(MadhumitraModel.getInstance().getPreferences().getEvenHour());
            timePicker.setCurrentMinute(MadhumitraModel.getInstance().getPreferences().getEvenMin());
        }
        if(doseTime.equalsIgnoreCase(cntx.getResources().getString(R.string.med_dose_tag_night))) {
            timePicker.setCurrentHour(MadhumitraModel.getInstance().getPreferences().getNightHour());
            timePicker.setCurrentMinute(MadhumitraModel.getInstance().getPreferences().getNightMin());
        }
    }

    protected void dismissDialog() {
        this.dismiss();
    }


    private void setupEventListeners() {
        Button okBtn = (Button)findViewById(R.id.doseOkBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //To change body of implemented methods use File | Settings | File Templates.
                TimePicker timePicker = (TimePicker)findViewById(R.id.doseTimePicker);
                timePicker.clearFocus();
                if(doseTime.equalsIgnoreCase(cntx.getResources().getString(R.string.med_dose_tag_morn))) {
                    prescriptionItemEntity.setMorningDose(true);
                    prescriptionItemEntity.getMornDoseTime().setHours(timePicker.getCurrentHour());
                    prescriptionItemEntity.getMornDoseTime().setMinutes(timePicker.getCurrentMinute());
                }

                if(doseTime.equalsIgnoreCase(cntx.getResources().getString(R.string.med_dose_tag_noon))) {
                    prescriptionItemEntity.setNoonDose(true);
                    prescriptionItemEntity.getNoonDoseTime().setHours(timePicker.getCurrentHour());
                    prescriptionItemEntity.getNoonDoseTime().setMinutes(timePicker.getCurrentMinute());
                }

                if(doseTime.equalsIgnoreCase(cntx.getResources().getString(R.string.med_dose_tag_even))) {
                    prescriptionItemEntity.setEvenDose(true);
                    prescriptionItemEntity.getEvenDoseTime().setHours(timePicker.getCurrentHour());
                    prescriptionItemEntity.getEvenDoseTime().setMinutes(timePicker.getCurrentMinute());
                }

                if(doseTime.equalsIgnoreCase(cntx.getResources().getString(R.string.med_dose_tag_night))) {
                    prescriptionItemEntity.setNightDose(true);
                    prescriptionItemEntity.getNightDoseTime().setHours(timePicker.getCurrentHour());
                    prescriptionItemEntity.getNightDoseTime().setMinutes(timePicker.getCurrentMinute());
                }
                EditText editText = (EditText)findViewById(R.id.medDoseEditText);
                prescriptionItemEntity.setDose(editText.getText().toString());
                dismissDialog();
            }
        });


        Button cancelBtn = (Button)findViewById(R.id.doseCancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //To change body of implemented methods use File | Settings | File Templates.
                dismissDialog();
            }
        });

    }



}
