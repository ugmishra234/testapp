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

@DatabaseTable(tableName = "meal_record")
public class MealRecord implements Serializable, IRecord{
    @DatabaseField(generatedId = true,columnName = "meal_record_id")
    private Integer id;

    @DatabaseField(columnName = "time_of_meal")
    private Date time;

    @DatabaseField(columnName = "meal_comment")
    private String comment;

    @ForeignCollectionField(columnName = "meal_item_record")
    private ForeignCollection<MealItemRecord> itemRecords;

    @DatabaseField(columnName = "inverse_user_account",foreign = true,foreignAutoRefresh = true)
    private UserAccount userAccount;


    public MealRecord() {
        this.time = new Date();
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ForeignCollection<MealItemRecord> getItemRecords() {
        return itemRecords;
    }

    public void setItemRecords(ForeignCollection<MealItemRecord> itemRecords) {
        this.itemRecords = itemRecords;
    }

    public RecordSummary getRecordSummary() {
        RecordSummary recordSummary = new RecordSummary();
        recordSummary.setTitle("Meal - "+ Utils.getDateTimeString(this.getTime()));
        recordSummary.setValue("");
        String otherDetails = "";
        Iterator<MealItemRecord> iterator = this.getItemRecords().iterator();
        int i = 1;
        int size = this.getItemRecords().size();
        while(iterator.hasNext()) {
            otherDetails = otherDetails + iterator.next().getMealItem();
            if(i != size) {
                otherDetails = otherDetails + ", ";
            }
            i = i + 1;
        }
        recordSummary.setOtherDetails(otherDetails);
        return recordSummary;

    }

    public Date getRecordDateTime() {
        return this.getTime();
    }


}
