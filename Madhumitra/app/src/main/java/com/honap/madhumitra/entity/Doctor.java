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

@DatabaseTable(tableName = "doctor")
public class Doctor implements Serializable, IRecord{
    @DatabaseField(generatedId = true,columnName = "doctor_id")
    private Integer id;

    @DatabaseField(columnName = "is_deleted",canBeNull = false)
    private boolean deleted;

    @DatabaseField(columnName = "display_name")
    private String displayName;

    @DatabaseField(columnName = "email_id")
    private String email;

    @DatabaseField(columnName = "primary_contact_number")
    private String primaryContactNo;

    @DatabaseField(columnName = "secondary_contact_number")
    private String secondaryContactNo;

    @DatabaseField(columnName = "inverse_user_account",foreign = true,foreignAutoRefresh = true)
    private UserAccount userAccount;


    public Doctor() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrimaryContactNo() {
        return primaryContactNo;
    }

    public void setPrimaryContactNo(String primaryContactNo) {
        this.primaryContactNo = primaryContactNo;
    }

    public String getSecondaryContactNo() {
        return secondaryContactNo;
    }

    public void setSecondaryContactNo(String secondaryContactNo) {
        this.secondaryContactNo = secondaryContactNo;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public RecordSummary getRecordSummary() {
        RecordSummary recordSummary = new RecordSummary();
        recordSummary.setTitle(this.getDisplayName());
        return recordSummary;
    }

    public Date getRecordDateTime() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
