package com.honap.madhumitra.entity;

import com.honap.madhumitra.utils.Utils;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

/**
 * Author: Chetan S.
 */

@DatabaseTable (tableName = "prescription")
public class Prescription implements Serializable,IRecord{
    @DatabaseField(generatedId = true,columnName = "prescription_id")
    private Integer id;

    @DatabaseField(columnName = "doctor", foreign = true, foreignAutoRefresh = true)
    private Doctor doctor;

    @DatabaseField(columnName = "visited_on")
    private Date visitedOn;

    @ForeignCollectionField(columnName = "prescription_item")
    private ForeignCollection<PrescriptionItem> prescriptionItems;

    @ForeignCollectionField(columnName = "medication_record")
    private ForeignCollection<MedicationRecord> medicationRecords;

    @DatabaseField(columnName = "inverse_user_account",foreign = true,foreignAutoRefresh = true)
    private UserAccount userAccount;


    public Prescription() {
        visitedOn = GregorianCalendar.getInstance().getTime();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Date getVisitedOn() {
        return visitedOn;
    }

    public void setVisitedOn(Date visitedOn) {
        this.visitedOn = visitedOn;
    }

    public ForeignCollection<PrescriptionItem> getPrescriptionItems() {
        return prescriptionItems;
    }

    public void setPrescriptionItems(ForeignCollection<PrescriptionItem> prescriptionItems) {
        this.prescriptionItems = prescriptionItems;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public ForeignCollection<MedicationRecord> getMedicationRecords() {
        return medicationRecords;
    }

    public void setMedicationRecords(ForeignCollection<MedicationRecord> medicationRecords) {
        this.medicationRecords = medicationRecords;
    }

    public RecordSummary getRecordSummary() {
        RecordSummary recordSummary = new RecordSummary();
        recordSummary.setTitle("Prescription - "+ Utils.getDateTimeString(this.getVisitedOn()));
        String otherDetails = "";
        Iterator<PrescriptionItem> iterator = this.getPrescriptionItems().iterator();
        while(iterator.hasNext()) {
            otherDetails = otherDetails + iterator.next().getDrug();
            if(iterator.hasNext()) {
                otherDetails = otherDetails + ", ";
            }
        }
        recordSummary.setOtherDetails(otherDetails);
        return recordSummary;
    }

    public Date getRecordDateTime() {
        return this.getVisitedOn();
    }


}
