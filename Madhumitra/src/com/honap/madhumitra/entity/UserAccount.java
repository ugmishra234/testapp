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


@DatabaseTable (tableName = "user_account")
public class UserAccount implements Serializable{
    @DatabaseField(generatedId = true,columnName = "account_id")
    private Integer id;

    @DatabaseField(columnName = "created_on", canBeNull = true)
    private Date createdOn;

    @DatabaseField(columnName = "updated_on", canBeNull = true)
    private Date updatedOn;

    @DatabaseField(columnName = "is_deleted", canBeNull = true)
    private boolean deleted;

    @DatabaseField(columnName = "is_primary_acc", canBeNull = true)
    private boolean isPrimary;

    @DatabaseField(columnName = "display_name", canBeNull = true)
    private String displayName;

    @DatabaseField (canBeNull = true, columnName = "relation_with_primary")
    private String relationWithPrimaryUser;

    @DatabaseField(columnName = "date_of_birth", canBeNull = true)
    private Date dob;

    @DatabaseField(columnName = "sex", canBeNull = true)
    private String sex;

    @DatabaseField(columnName = "email_id", canBeNull = true)
    private String email;

    @DatabaseField(columnName = "mobile", canBeNull = true)
    private String mobile;

    @DatabaseField(columnName = "height_cm", canBeNull = true)
    private int heightCm;

    @DatabaseField(columnName = "lifestyle", canBeNull = true)
    private String lifestyle;

    @ForeignCollectionField (columnName = "weight")
    private ForeignCollection<UserWeight> weightTrail;

    @ForeignCollectionField (columnName = "doctor")
    private ForeignCollection<Doctor> doctors;

    @ForeignCollectionField (columnName = "prescription")
    private ForeignCollection<Prescription> prescriptions;

    @ForeignCollectionField (columnName = "home_investigation")
    private ForeignCollection<HomeInvestigationRecord> homeInvestigations;

    @ForeignCollectionField (columnName = "lab_investigation")
    private ForeignCollection<LabInvestigationRecord> labInvestigations;

    @ForeignCollectionField (columnName = "activity_record")
    private ForeignCollection<ActivityRecord> activityRecords;

    @ForeignCollectionField (columnName = "symptom_record")
    private ForeignCollection<SymptomRecord> symptomRecords;

    @ForeignCollectionField (columnName = "exercise_record")
    private ForeignCollection<ExerciseRecord> exerciseRecords;

    @ForeignCollectionField (columnName = "meal_record")
    private ForeignCollection<MealRecord> mealRecords;

    @ForeignCollectionField (columnName = "medication_record")
    private ForeignCollection<MedicationRecord> medicationRecords;




    public UserAccount() {
    }

    public String getLifestyle() {
        return lifestyle;
    }

    public void setLifestyle(String lifestyle) {
        this.lifestyle = lifestyle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    public String getRelationWithPrimaryUser() {
        if(relationWithPrimaryUser == null) {
            return "Self";
        }
        return relationWithPrimaryUser;
    }

    public void setRelationWithPrimaryUser(String relationWithPrimaryUser) {
        this.relationWithPrimaryUser = relationWithPrimaryUser;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getHeightCm() {
        return heightCm;
    }

    public void setHeightCm(int heightCm) {
        this.heightCm = heightCm;
    }

    public ForeignCollection<UserWeight> getWeightTrail() {
        return weightTrail;
    }

    public void setWeightTrail(ForeignCollection<UserWeight> weightTrail) {
        this.weightTrail = weightTrail;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public ForeignCollection<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(ForeignCollection<Doctor> doctors) {
        this.doctors = doctors;
    }

    public ForeignCollection<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(ForeignCollection<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public ForeignCollection<HomeInvestigationRecord> getHomeInvestigations() {
        return homeInvestigations;
    }

    public void setHomeInvestigations(ForeignCollection<HomeInvestigationRecord> homeInvestigations) {
        this.homeInvestigations = homeInvestigations;
    }

    public ForeignCollection<LabInvestigationRecord> getLabInvestigations() {
        return labInvestigations;
    }

    public void setLabInvestigations(ForeignCollection<LabInvestigationRecord> labInvestigations) {
        this.labInvestigations = labInvestigations;
    }

    public ForeignCollection<ActivityRecord> getActivityRecords() {
        return activityRecords;
    }

    public void setActivityRecords(ForeignCollection<ActivityRecord> activityRecords) {
        this.activityRecords = activityRecords;
    }

    public ForeignCollection<SymptomRecord> getSymptomRecords() {
        return symptomRecords;
    }

    public void setSymptomRecords(ForeignCollection<SymptomRecord> symptomRecords) {
        this.symptomRecords = symptomRecords;
    }

    public ForeignCollection<ExerciseRecord> getExerciseRecords() {
        return exerciseRecords;
    }

    public void setExerciseRecords(ForeignCollection<ExerciseRecord> exerciseRecords) {
        this.exerciseRecords = exerciseRecords;
    }

    public ForeignCollection<MealRecord> getMealRecords() {
        return mealRecords;
    }

    public void setMealRecords(ForeignCollection<MealRecord> mealRecords) {
        this.mealRecords = mealRecords;
    }

    public ForeignCollection<MedicationRecord> getMedicationRecords() {
        return medicationRecords;
    }

    public void setMedicationRecords(ForeignCollection<MedicationRecord> medicationRecords) {
        this.medicationRecords = medicationRecords;
    }

    public int getAge() {
        Date today = new Date();
        int todayYr = today.getYear() + 1900;
        int dobYear =  this.dob.getYear();
        return todayYr - dobYear;
    }

}
