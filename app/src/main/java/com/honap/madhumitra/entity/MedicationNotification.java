package com.honap.madhumitra.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Author: Chetan S.
 */

@DatabaseTable(tableName = "medication_notification")
public class MedicationNotification implements Serializable,Comparable<MedicationNotification>{

    @DatabaseField(generatedId = true,columnName = "medication_notification_id")
    private Integer id;

    @DatabaseField(columnName = "notification_time")
    private Date time;

    @DatabaseField(columnName = "dose_tag")
    private int medRecordId;


    public MedicationNotification() {
        this.time = GregorianCalendar.getInstance().getTime();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getMedRecordId() {
        return medRecordId;
    }

    public void setMedRecordId(int medRecordId) {
        this.medRecordId = medRecordId;
    }

    public int compareTo(MedicationNotification medicationRecord) {
        if(medicationRecord.getTime().before(this.getTime())) {
            return 1;
        }

        if(medicationRecord.getTime().after(this.getTime())) {
            return -1;
        }
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

}
