package com.honap.madhumitra.entity;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Author: Chetan S.
 */

@DatabaseTable(tableName = "medication_record")
public class MedicationRecord implements Serializable,Comparable<MedicationRecord>{

    @DatabaseField(generatedId = true,columnName = "medication_record_id")
    private Integer id;

    @DatabaseField(columnName = "drug")
    private String drug;

    @DatabaseField(columnName = "dose")
    private String dose;

    @DatabaseField(columnName = "dose_time")
    private Date time;

    @DatabaseField(columnName = "inverse_user_account",foreign = true,foreignAutoRefresh = true)
    private UserAccount userAccount;

    @DatabaseField(columnName = "dose_tag")
    private String doseTag;

    @DatabaseField(columnName = "dose_status")
    private String doseStatus;

    @DatabaseField(columnName = "prescription",foreign = true,foreignAutoRefresh = true)
    private Prescription prescription;


    public MedicationRecord() {
        this.time = GregorianCalendar.getInstance().getTime();
    }

    public String getDoseStatus() {
        return doseStatus;
    }

    public void setDoseStatus(String doseStatus) {
        this.doseStatus = doseStatus;
    }

    public String getDoseTag() {
        return doseTag;
    }

    public void setDoseTag(String doseTag) {
        this.doseTag = doseTag;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDrug() {
        return drug;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public int compareTo(MedicationRecord medicationRecord) {
        if(medicationRecord.getTime().before(this.getTime())) {
            return 1;
        }

        if(medicationRecord.getTime().after(this.getTime())) {
            return -1;
        }
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String toString() {
        return "MedicationRecord{" +
                "dose='" + dose + '\'' +
                ", drug='" + drug + '\'' +
                ", time=" + time +
                ", doseTag='" + doseTag + '\'' +
                ", doseStatus='" + doseStatus + '\'' +
                '}';
    }
}
