package com.honap.madhumitra.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: Chetan S.
 */
@DatabaseTable (tableName = "prescribed_dose")
public class PrescribedDose implements Serializable{
    @DatabaseField(generatedId = true,columnName = "prescribed_dose_id")
    private Integer id;

    @DatabaseField(columnName = "dose")
    private String dose;

    @DatabaseField(columnName = "dose_time")
    private Date doseTime;

    @DatabaseField(columnName = "inverse_prescription_item",foreign = true,foreignAutoRefresh = true)
    private PrescriptionItem prescriptionItem;

    @DatabaseField(columnName = "dose_tag")
    private String doseTag;


    public PrescribedDose() {
        doseTime = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public Date getDoseTime() {
        return doseTime;
    }

    public void setDoseTime(Date doseTime) {
        this.doseTime = doseTime;
    }

    public PrescriptionItem getPrescriptionItem() {
        return prescriptionItem;
    }

    public void setPrescriptionItem(PrescriptionItem prescriptionItem) {
        this.prescriptionItem = prescriptionItem;
    }

    public String getDoseTag() {
        return doseTag;
    }

    public void setDoseTag(String doseTag) {
        this.doseTag = doseTag;
    }
}
