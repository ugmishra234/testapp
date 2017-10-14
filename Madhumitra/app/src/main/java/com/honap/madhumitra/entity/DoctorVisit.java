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
@DatabaseTable(tableName = "doctor_visit")
public class DoctorVisit implements Serializable{
    @DatabaseField(generatedId = true,columnName = "doctor_visit_id")
    private Integer id;

    @DatabaseField(columnName = "visited_on")
    private Date visitedOn;

    @DatabaseField(columnName = "doctor", foreign = true, foreignAutoRefresh = true)
    private Doctor doctor;

    @DatabaseField(columnName = "prescription", foreign = true, foreignAutoRefresh = true)
    private Prescription prescription;

    @DatabaseField(columnName = "comment")
    private String comment;

    @DatabaseField(columnName = "next_visit")
    private Date nextVisit;

    @DatabaseField(columnName = "fees")
    private int fees;

    @DatabaseField(columnName = "inverse_user_account",foreign = true,foreignAutoRefresh = true)
    private UserAccount userAccount;


    public DoctorVisit() {
        visitedOn = new Date();
        nextVisit = new Date();
        prescription = new Prescription();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getVisitedOn() {
        return visitedOn;
    }

    public void setVisitedOn(Date visitedOn) {
        this.visitedOn = visitedOn;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getNextVisit() {
        return nextVisit;
    }

    public void setNextVisit(Date nextVisit) {
        this.nextVisit = nextVisit;
    }

    public int getFees() {
        return fees;
    }

    public void setFees(int fees) {
        this.fees = fees;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
}
