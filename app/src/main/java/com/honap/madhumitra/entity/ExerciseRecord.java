package com.honap.madhumitra.entity;

import com.honap.madhumitra.utils.Utils;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: Chetan S.
 */
@DatabaseTable(tableName = "exercise_record")
public class ExerciseRecord implements Serializable,IRecord{
    @DatabaseField(generatedId = true,columnName = "exercise_record_id")
    private Integer id;

    @DatabaseField(columnName = "exercise")
    private String exercise;

    @DatabaseField(columnName = "start_time")
    private Date startTime;

    @DatabaseField(columnName = "duration_min")
    private int durationMin;

    @DatabaseField(columnName = "comment")
    private String comment;

    @DatabaseField(columnName = "calorie_consumption")
    private int calorieConsumption;

    @DatabaseField(columnName = "inverse_user_account",foreign = true,foreignAutoRefresh = true)
    private UserAccount userAccount;


    public ExerciseRecord() {
        startTime = new Date();
    }

    public int getCalorieConsumption() {
        return calorieConsumption;
    }

    public void setCalorieConsumption(int calorieConsumption) {
        this.calorieConsumption = calorieConsumption;
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

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public int getDurationMin() {
        return durationMin;
    }

    public void setDurationMin(int durationMin) {
        this.durationMin = durationMin;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public RecordSummary getRecordSummary() {
        RecordSummary recordSummary = new RecordSummary();
        recordSummary.setTitle(this.getExercise());
        recordSummary.setValue(new Integer(this.getDurationMin()).toString() + " Min");
        recordSummary.setOtherDetails(Utils.getDateTimeString(this.getStartTime()));
        return recordSummary;

    }

    public Date getRecordDateTime() {
        return this.getStartTime();
    }
}
