package com.honap.madhumitra.entity;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: Chetan S.
 */

@DatabaseTable(tableName = "lab_investigation_record")
public class LabInvestigationRecord implements Serializable{
    @DatabaseField(generatedId = true,columnName = "lab_investigation_record_id")
    private Integer id;

    @DatabaseField(columnName = "investigated_on")
    private Date investigatedOn;

    @DatabaseField(columnName = "investigation")
    private String investigation;

    @DatabaseField(columnName = "doctor_referral",foreign = true,foreignAutoRefresh = true)
    private Doctor doctorReferral;

    @ForeignCollectionField(columnName = "investigation_value")
    private ForeignCollection<LabInvestigationParamValue> investigationValues;

    @DatabaseField(columnName = "investigation_comment")
    private String comment;

    @DatabaseField(columnName = "done_at")
    private String doneAt;

    @DatabaseField(columnName = "charges")
    private int charges;

    @DatabaseField(columnName = "next_investigation_date")
    private int nextInvestigationDate;

    @DatabaseField(columnName = "inverse_user_account",foreign = true,foreignAutoRefresh = true)
    private UserAccount userAccount;


    public LabInvestigationRecord() {

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

    public Date getInvestigatedOn() {
        return investigatedOn;
    }

    public void setInvestigatedOn(Date investigatedOn) {
        this.investigatedOn = investigatedOn;
    }

    public String getInvestigation() {
        return investigation;
    }

    public void setInvestigation(String investigation) {
        this.investigation = investigation;
    }

    public ForeignCollection<LabInvestigationParamValue> getInvestigationValues() {
        return investigationValues;
    }

    public void setInvestigationValues(ForeignCollection<LabInvestigationParamValue> investigationValues) {
        this.investigationValues = investigationValues;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDoneAt() {
        return doneAt;
    }

    public void setDoneAt(String doneAt) {
        this.doneAt = doneAt;
    }

    public int getCharges() {
        return charges;
    }

    public void setCharges(int charges) {
        this.charges = charges;
    }

    public int getNextInvestigationDate() {
        return nextInvestigationDate;
    }

    public void setNextInvestigationDate(int nextInvestigationDate) {
        this.nextInvestigationDate = nextInvestigationDate;
    }

    public Doctor getDoctorReferral() {
        return doctorReferral;
    }

    public void setDoctorReferral(Doctor doctorReferral) {
        this.doctorReferral = doctorReferral;
    }
}
