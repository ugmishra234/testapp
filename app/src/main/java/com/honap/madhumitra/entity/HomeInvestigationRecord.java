package com.honap.madhumitra.entity;

import com.honap.madhumitra.utils.Utils;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;

/**
 * Author: Chetan S.
 */
@DatabaseTable(tableName = "home_investigation_record")
public class HomeInvestigationRecord implements Serializable, IRecord{
    @DatabaseField(generatedId = true,columnName = "home_investigation_record_id")
    private Integer id;

    @DatabaseField(columnName = "investigation")
    private String investigation;

    @DatabaseField(columnName = "investigated_on")
    private Date investigatedOn;

    @ForeignCollectionField(columnName = "investigation_value")
    private ForeignCollection<HomeInvestigationParamValue> investigationValues;

    @DatabaseField(columnName = "investigation_comment")
    private String comment;

    @DatabaseField(columnName = "inverse_user_account",foreign = true,foreignAutoRefresh = true)
    private UserAccount userAccount;


    public HomeInvestigationRecord() {
        this.investigatedOn = new Date();
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

    public ForeignCollection<HomeInvestigationParamValue> getInvestigationValues() {
        return investigationValues;
    }

    public void setInvestigationValues(ForeignCollection<HomeInvestigationParamValue> investigationValues) {
        this.investigationValues = investigationValues;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getInvestigation() {
        return investigation;
    }

    public void setInvestigation(String investigation) {
        this.investigation = investigation;
    }

    public RecordSummary getRecordSummary() {
        RecordSummary recordSummary = new RecordSummary();
        recordSummary.setTitle("BSL - "+ Utils.getDateTimeString(this.getInvestigatedOn()));
        recordSummary.setValue(((HomeInvestigationParamValue)this.getInvestigationValues().toArray()[0]).getValue());
        return recordSummary;
    }

    public Date getRecordDateTime() {
        return this.getInvestigatedOn();
    }

}
