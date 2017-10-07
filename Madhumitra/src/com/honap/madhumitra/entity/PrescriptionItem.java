package com.honap.madhumitra.entity;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;


import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Author: Chetan S.
 */
@DatabaseTable (tableName = "prescription_item")
public class PrescriptionItem implements Serializable{
    @DatabaseField(generatedId = true,columnName = "prescription_item_id")
    private Integer id;

    @DatabaseField(columnName = "drug")
    private String drug;

    @DatabaseField(columnName = "num_days")
    private int numDays;

    @DatabaseField(columnName = "inverse_prescription",foreign = true,foreignAutoRefresh = true)
    private Prescription prescription;

    @DatabaseField(columnName = "dose")
    private String dose;

    @DatabaseField(columnName = "morning_dose")
    private boolean morningDose;

    private transient Date mornDoseTime;

    @DatabaseField(columnName = "noon_dose")
    private boolean noonDose;

    private transient Date noonDoseTime;

    @DatabaseField(columnName = "evening_dose")
    private boolean evenDose;

    private transient Date evenDoseTime;

    @DatabaseField(columnName = "night_dose")
    private boolean nightDose;

    private transient Date nightDoseTime;

    public PrescriptionItem() {
        this.mornDoseTime = GregorianCalendar.getInstance().getTime();
        this.noonDoseTime = GregorianCalendar.getInstance().getTime();
        this.evenDoseTime = GregorianCalendar.getInstance().getTime();
        this.nightDoseTime = GregorianCalendar.getInstance().getTime();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getMornDoseTime() {
        return mornDoseTime;
    }

    public void setMornDoseTime(Date mornDoseTime) {
        this.mornDoseTime = mornDoseTime;
    }

    public Date getNoonDoseTime() {
        return noonDoseTime;
    }

    public void setNoonDoseTime(Date noonDoseTime) {
        this.noonDoseTime = noonDoseTime;
    }

    public Date getEvenDoseTime() {
        return evenDoseTime;
    }

    public void setEvenDoseTime(Date evenDoseTime) {
        this.evenDoseTime = evenDoseTime;
    }

    public Date getNightDoseTime() {
        return nightDoseTime;
    }

    public void setNightDoseTime(Date nightDoseTime) {
        this.nightDoseTime = nightDoseTime;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getDrug() {
        return drug;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }

    public int getNumDays() {
        return numDays;
    }

    public void setNumDays(int numDays) {
        this.numDays = numDays;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public boolean isMorningDose() {
        return morningDose;
    }

    public void setMorningDose(boolean morningDose) {
        this.morningDose = morningDose;
    }

    public boolean isNoonDose() {
        return noonDose;
    }

    public void setNoonDose(boolean noonDose) {
        this.noonDose = noonDose;
    }

    public boolean isEvenDose() {
        return evenDose;
    }

    public void setEvenDose(boolean evenDose) {
        this.evenDose = evenDose;
    }

    public boolean isNightDose() {
        return nightDose;
    }

    public void setNightDose(boolean nightDose) {
        this.nightDose = nightDose;
    }
}
