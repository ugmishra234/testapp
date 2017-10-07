package com.honap.madhumitra.data;

import android.content.Context;
import android.util.Log;
import com.honap.madhumitra.entity.*;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.android.AndroidDatabaseConnection;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.support.ConnectionSource;

import java.util.Iterator;
import java.util.List;

/**
 * Author: Chetan S.
 */
public class LocalDataManager extends OrmLiteBaseActivity<DbHelper> implements MadhumitraDataManager {
    private static final String TAG = "LocalDataManager";
    private DbHelper helper = null;

    public LocalDataManager(Context context) {
        helper = getHelperInternal(context);
    }


    public LocalDataManager(DbHelper dbHelper) {
        helper = dbHelper;
    }


    public void createUserAccount(UserAccount userAccount) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            this.helper.getUserAccountDao().create(userAccount);
            // not expecting any 'foreign' objects to be there accept weight
            UserWeight uw = userAccount.getWeightTrail().iterator().next();
            // persist weight
            this.createUserWeight(uw);
            this.transactionOver();
        } catch (Exception e) {
            Log.e(TAG, "Error while creating user account", e);
            throw new RuntimeException(e);
        }
    }

    public void updateUserAccount(UserAccount userAccount) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            this.helper.getUserAccountDao().update(userAccount);
            // not expecting any 'foreign' objects to be there accept weight
//            UserWeight uw = userAccount.getWeightTrail().iterator().next();
//            // persist weight
//            this.createUserWeight(uw);
            // persist user account
            this.transactionOver();

        } catch (Exception e) {
            Log.e(TAG, "Error while updating user account", e);
            throw new RuntimeException(e);
        }
    }

    public void deleteUserAccount(UserAccount userAccount) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            // activity records
            Iterator<ActivityRecord> activityRecordIterator = userAccount.getActivityRecords().iterator();
            while (activityRecordIterator.hasNext()) {
                ActivityRecord val = activityRecordIterator.next();
                deleteActivityRecord(val);
            }

            // meal record
            Iterator<MealRecord> mealRecordIterator = userAccount.getMealRecords().iterator();
            while (mealRecordIterator.hasNext()) {
                MealRecord val = mealRecordIterator.next();
                deleteMealRecord(val);
            }

            // exercise record
            Iterator<ExerciseRecord> exerciseRecordIterator = userAccount.getExerciseRecords().iterator();
            while (mealRecordIterator.hasNext()) {
                ExerciseRecord val = exerciseRecordIterator.next();
                deleteExerciseRecord(val);
            }

            // home investigation record
            Iterator<HomeInvestigationRecord> homeInvestigationRecordIterator = userAccount.getHomeInvestigations().iterator();
            while (mealRecordIterator.hasNext()) {
                HomeInvestigationRecord val = homeInvestigationRecordIterator.next();
                deleteHomeInvestigationRecord(val);
            }

            // prescription record
            Iterator<Prescription> prescriptionIterator = userAccount.getPrescriptions().iterator();
            while (mealRecordIterator.hasNext()) {
                Prescription val = prescriptionIterator.next();
                deletePrescription(val);
            }

            // user weight trail
            Iterator<UserWeight> userWeightIterator = userAccount.getWeightTrail().iterator();
            while (mealRecordIterator.hasNext()) {
                UserWeight val = userWeightIterator.next();
                deleteUserWeight(val);
            }

            // medication record
            Iterator<MedicationRecord> medicationRecordIterator = userAccount.getMedicationRecords().iterator();
            while (mealRecordIterator.hasNext()) {
                MedicationRecord val = medicationRecordIterator.next();
                deleteMedicationRecord(val);
            }

            // doctors
            Iterator<Doctor> doctorIterator = userAccount.getDoctors().iterator();
            while (mealRecordIterator.hasNext()) {
                Doctor val = doctorIterator.next();
                deleteDoctor(val);
            }

            this.helper.getUserAccountDao().delete(userAccount);
            this.transactionOver();
        } catch (Exception e) {
            Log.e(TAG, "Error while deleting user account", e);
            throw new RuntimeException(e);
        }

    }

    public void createActivityRecord(ActivityRecord activityRecord) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            // assuming user account has already been created
            this.helper.getActivityRecordDao().create(activityRecord);
            this.transactionOver();

        } catch (Exception e) {
            Log.e(TAG, "Error while creating activity record", e);
            throw new RuntimeException(e);
        }
    }

    public void deleteActivityRecord(ActivityRecord activityRecord) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);

            activityRecord.getUserAccount().getActivityRecords().remove(activityRecord);
            this.helper.getActivityRecordDao().delete(activityRecord);
            this.transactionOver();

        } catch (Exception e) {
            Log.e(TAG, "Error while deleting activity record", e);
            throw new RuntimeException(e);
        }
    }

    public List<UserAccount> getAllUserAccounts() {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            List<UserAccount> retList = this.helper.getUserAccountDao().queryForAll();
            this.transactionOver();
            return retList;
        } catch (Exception e) {
            Log.e(TAG, "Error while retrieving all user accounts", e);
            throw new RuntimeException(e);
        }
    }

    public List<MedicationRecord> getAllMedicationRecords() {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            List<MedicationRecord> retList = this.helper.getMedicationRecordDao().queryForAll();
            this.transactionOver();
            return retList;
        } catch (Exception e) {
            Log.e(TAG, "Error while retrieving all medication records", e);
            throw new RuntimeException(e);
        }
    }

    public MedicationRecord getMedicationRecord(long id) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            int i = (int) id;
            MedicationRecord medicationRecord = this.helper.getMedicationRecordDao().queryForId(new Integer(i));
            this.transactionOver();
            return medicationRecord;
        } catch (Exception e) {
            Log.e(TAG, "Error while retrieving all medication records", e);
            throw new RuntimeException(e);
        }
    }


    public void createDoctor(Doctor doctor) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            this.helper.getDoctorDao().create(doctor);
            this.transactionOver();

        } catch (Exception e) {
            Log.e(TAG, "Error while creating doctor", e);
            throw new RuntimeException(e);
        }
    }

    public List<Doctor> getAllDoctors() {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            List<Doctor> retList = this.helper.getDoctorDao().queryForAll();
            this.transactionOver();
            return retList;
        } catch (Exception e) {
            Log.e(TAG, "Error while retrieving all doctors", e);
            throw new RuntimeException(e);
        }
    }

    public void deleteDoctor(Doctor doctor) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            doctor.getUserAccount().getDoctors().remove(doctor);
            this.helper.getDoctorDao().delete(doctor);
            this.transactionOver();
        } catch (Exception e) {
            Log.e(TAG, "Error while deleting doctor", e);
            throw new RuntimeException(e);
        }
    }

    public void createDoctorVisit(DoctorVisit doctorVisit) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            // add doctor visit
            this.helper.getDoctorVisitDao().create(doctorVisit);
            // assuming doctor has already been added
            // add prescription
            this.helper.getPrescriptionDao().create(doctorVisit.getPrescription());
            this.transactionOver();
        } catch (Exception e) {
            Log.e(TAG, "Error while creating doctor visit", e);
            throw new RuntimeException(e);
        }
    }

    public void updateDoctorVisit(DoctorVisit doctorVisit) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deleteDoctorVisit(DoctorVisit doctorVisit) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void createExercise(Exercise exercise) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            this.helper.getExerciseDao().create(exercise);
            this.transactionOver();
        } catch (Exception e) {
            Log.e(TAG, "Error while creating an exercise", e);
            throw new RuntimeException(e);
        }
    }

    public List<Exercise> getAllExercises() {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            List<Exercise> retList = this.helper.getExerciseDao().queryForAll();
            this.transactionOver();
            return retList;
        } catch (Exception e) {
            Log.e(TAG, "Error while retrieving all exercises", e);
            throw new RuntimeException(e);
        }
    }

    public void createExerciseRecord(ExerciseRecord exerciseRecord) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            // assuming user account & exercise have already been created
            // add prescription
            this.helper.getExerciseRecordDao().create(exerciseRecord);
            this.transactionOver();
        } catch (Exception e) {
            Log.e(TAG, "Error while creating exercise record", e);
            throw new RuntimeException(e);
        }
    }

    public void updateExerciseRecord(ExerciseRecord exerciseRecord) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deleteExerciseRecord(ExerciseRecord exerciseRecord) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            exerciseRecord.getUserAccount().getExerciseRecords().remove(exerciseRecord);
            this.helper.getExerciseRecordDao().delete(exerciseRecord);
            this.transactionOver();
        } catch (Exception e) {
            Log.e(TAG, "Error while deleting exercise record", e);
            throw new RuntimeException(e);
        }
    }


    public void createHomeInvestigationParamValue(HomeInvestigationParamValue homeInvestigationParamValue) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            this.helper.getHomeInvestigationParamValueDao().create(homeInvestigationParamValue);
            this.transactionOver();
        } catch (Exception e) {
            Log.e(TAG, "Error while creating home investigation param value", e);
            throw new RuntimeException(e);
        }
    }

    public void updateHomeInvestigationParamValue(HomeInvestigationParamValue homeInvestigationParamValue) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deleteHomeInvestigationParamValue(HomeInvestigationParamValue homeInvestigationParamValue) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            homeInvestigationParamValue.getRecord().getInvestigationValues().remove(homeInvestigationParamValue);
            this.helper.getHomeInvestigationParamValueDao().delete(homeInvestigationParamValue);
            this.transactionOver();
        } catch (Exception e) {
            Log.e(TAG, "Error while deleting home investigation param value", e);
            throw new RuntimeException(e);
        }
    }

    public void createHomeInvestigationRecord(HomeInvestigationRecord homeInvestigationRecord) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            this.helper.getHomeInvestigationRecordDao().create(homeInvestigationRecord);
            // assuming user account and home investigation param value have already been created
            // create home investigation param values
            Iterator<HomeInvestigationParamValue> iterator = homeInvestigationRecord.getInvestigationValues().iterator();
            while (iterator.hasNext()) {
                HomeInvestigationParamValue val = iterator.next();
                this.helper.getHomeInvestigationParamValueDao().create(val);
            }
            this.transactionOver();
        } catch (Exception e) {
            Log.e(TAG, "Error while creating home investigation record", e);
            throw new RuntimeException(e);
        }
    }

    public void updateHomeInvestigationRecord(HomeInvestigationRecord homeInvestigationRecord) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deleteHomeInvestigationRecord(HomeInvestigationRecord homeInvestigationRecord) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            Iterator<HomeInvestigationParamValue> iterator = homeInvestigationRecord.getInvestigationValues().iterator();
            while (iterator.hasNext()) {
                HomeInvestigationParamValue val = iterator.next();
                this.deleteHomeInvestigationParamValue(val);
            }
            homeInvestigationRecord.getUserAccount().getHomeInvestigations().remove(homeInvestigationRecord);
            this.helper.getHomeInvestigationRecordDao().delete(homeInvestigationRecord);
            this.transactionOver();
        } catch (Exception e) {
            Log.e(TAG, "Error while deleting home investigation record", e);
            throw new RuntimeException(e);
        }
    }

    public void createLabInvestigation(LabInvestigation labInvestigation) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<LabInvestigation> getAllLabInvestigations() {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            return this.helper.getLabInvestigationDao().queryForAll();
        } catch (Exception e) {
            Log.e(TAG, "Error while retrieving all lab investigations", e);
            throw new RuntimeException(e);
        }
    }

    public void createLabInvestigationParamValue(LabInvestigationParamValue labInvestigationParamValue) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            this.helper.getLabInvestigationParamValueDao().create(labInvestigationParamValue);
        } catch (Exception e) {
            Log.e(TAG, "Error while creating lab investigation param value", e);
            throw new RuntimeException(e);
        }
    }

    public void updateLabInvestigationParamValue(LabInvestigationParamValue labInvestigationParamValue) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deleteLabInvestigationParamValue(LabInvestigationParamValue labInvestigationParamValue) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void createLabInvestigationRecord(LabInvestigationRecord labInvestigationRecord) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            this.helper.getLabInvestigationRecordDao().create(labInvestigationRecord);
            // assuming user account and home investigation param value have already been created
            // create home investigation param values
            Iterator<LabInvestigationParamValue> iterator = labInvestigationRecord.getInvestigationValues().iterator();
            while (iterator.hasNext()) {
                LabInvestigationParamValue val = iterator.next();
                this.helper.getLabInvestigationParamValueDao().create(val);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error while creating lab investigation record", e);
            throw new RuntimeException(e);
        }
    }

    public void updateLabInvestigationRecord(LabInvestigationRecord labInvestigationRecord) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deleteLabInvestigationRecord(LabInvestigationRecord labInvestigationRecord) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void createMealItemRecord(MealItemRecord mealItemRecord) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            // assuming meal item has been added
            this.helper.getMealItemRecordDao().create(mealItemRecord);
            this.transactionOver();
        } catch (Exception e) {
            Log.e(TAG, "Error while creating meal item record", e);
            throw new RuntimeException(e);
        }
    }

    public void updateMealItemRecord(MealItemRecord mealItemRecord) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deleteMealItemRecord(MealItemRecord mealItemRecord) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            //mealItemRecord.getMealRecord().getItemRecords().remove(mealItemRecord);
            this.helper.getMealItemRecordDao().delete(mealItemRecord);
            this.transactionOver();
        } catch (Exception e) {
            Log.e(TAG, "Error while deleting meal item record", e);
            throw new RuntimeException(e);
        }
    }

    public void createMealRecord(MealRecord mealRecord) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            this.helper.getMealRecordDao().create(mealRecord);
            // create meal record item
            Iterator<MealItemRecord> iterator = mealRecord.getItemRecords().iterator();
            while (iterator.hasNext()) {
                MealItemRecord val = iterator.next();
                this.helper.getMealItemRecordDao().create(val);
            }
            this.transactionOver();
        } catch (Exception e) {
            Log.e(TAG, "Error while creating meal record", e);
            throw new RuntimeException(e);
        }
    }

    public void updateMealRecord(MealRecord mealRecord) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deleteMealRecord(MealRecord mealRecord) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            mealRecord.getUserAccount().getMealRecords().remove(mealRecord);
            Iterator<MealItemRecord> iterator = mealRecord.getItemRecords().iterator();
            while (iterator.hasNext()) {
                MealItemRecord val = iterator.next();
                this.deleteMealItemRecord(val);
            }
            this.helper.getMealRecordDao().delete(mealRecord);
            this.transactionOver();
        } catch (Exception e) {
            Log.e(TAG, "Error while delete meal record", e);
            throw new RuntimeException(e);
        }
    }

    public void createMedicationRecord(MedicationRecord medicationRecord) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            this.helper.getMedicationRecordDao().create(medicationRecord);
            this.transactionOver();
        } catch (Exception e) {
            Log.e(TAG, "Error while creating medication record", e);
            throw new RuntimeException(e);
        }
    }

    public void updateMedicationRecord(MedicationRecord medicationRecord) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            this.helper.getMedicationRecordDao().update(medicationRecord);
            this.transactionOver();
        } catch (Exception e) {
            Log.e(TAG, "Error while updating medication record", e);
            throw new RuntimeException(e);
        }
    }

    public void deleteMedicationRecord(MedicationRecord medicationRecord) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            Iterator<MedicationNotification> iterator = this.getAllNotifications().iterator();
            while (iterator.hasNext()) {
                MedicationNotification notification = iterator.next();
                if(notification.getMedRecordId() == medicationRecord.getId().intValue()) {
                    deleteMedicationNotification(notification);
                }
            }
            this.helper.getMedicationRecordDao().delete(medicationRecord);
            this.transactionOver();
        } catch (Exception e) {
            Log.e(TAG, "Error while deleting medication record", e);
            throw new RuntimeException(e);
        }
    }

    public void createPrescribedDose(PrescribedDose prescribedDose) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            this.helper.getPrescribedDoseDao().create(prescribedDose);
            this.transactionOver();
        } catch (Exception e) {
            Log.e(TAG, "Error while creating prescribed dose", e);
            throw new RuntimeException(e);
        }
    }

    public void updatePrescribedDose(PrescribedDose prescribedDose) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deletePrescribedDose(PrescribedDose prescribedDose) {
    }

    public void createPrescription(Prescription prescription) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            this.helper.getPrescriptionDao().create(prescription);
            // create prescription
            Iterator<PrescriptionItem> iterator = prescription.getPrescriptionItems().iterator();
            while (iterator.hasNext()) {
                PrescriptionItem val = iterator.next();
                createPrescriptionItem(val);
            }
            this.transactionOver();
        } catch (Exception e) {
            Log.e(TAG, "Error while creating prescription", e);
            throw new RuntimeException(e);
        }
    }

    public void createPrescriptionItem(PrescriptionItem prescriptionItem) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            this.helper.getPrescriptionItemDao().create(prescriptionItem);
            this.transactionOver();
            // create prescription item
//            Iterator<PrescribedDose> iterator = prescriptionItem.getPrescribedDoses().iterator();
//            while (iterator.hasNext()) {
//                PrescribedDose val = iterator.next();
//                createPrescribedDose(val);
//            }
        } catch (Exception e) {
            Log.e(TAG, "Error while creating prescription item", e);
            throw new RuntimeException(e);
        }
    }

    public void deletePrescriptionItem(PrescriptionItem prescriptionItem) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            prescriptionItem.getPrescription().getPrescriptionItems().remove(prescriptionItem);
            this.helper.getPrescriptionItemDao().delete(prescriptionItem);
            this.transactionOver();
        } catch (Exception e) {
            Log.e(TAG, "Error while deleting prescription item", e);
            throw new RuntimeException(e);
        }
    }

    public void updatePrescription(Prescription prescription) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deletePrescription(Prescription prescription) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            Iterator<PrescriptionItem> iterator = prescription.getPrescriptionItems().iterator();
            while (iterator.hasNext()) {
                PrescriptionItem val = iterator.next();
                deletePrescriptionItem(val);
            }
            prescription.getUserAccount().getPrescriptions().remove(prescription);
            this.helper.getPrescriptionDao().delete(prescription);
            this.transactionOver();
        } catch (Exception e) {
            Log.e(TAG, "Error while deleting prescription", e);
            throw new RuntimeException(e);
        }
    }

    public void createSymptomRecord(SymptomRecord symptomRecord) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            this.helper.getSymptomRecordDao().create(symptomRecord);
        } catch (Exception e) {
            Log.e(TAG, "Error while creating symptom record", e);
            throw new RuntimeException(e);
        }
    }

    public void updateSymptomRecord(SymptomRecord symptomRecord) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deleteSymptomRecord(SymptomRecord symptomRecord) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void createUserWeight(UserWeight userWeight) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            this.helper.getUserWeightDao().create(userWeight);
            this.transactionOver();
        } catch (Exception e) {
            Log.e(TAG, "Error while creating weight", e);
            throw new RuntimeException(e);
        }
    }

    public void updateUserWeight(UserWeight userWeight) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deleteUserWeight(UserWeight userWeight) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            userWeight.getUserAccount().getWeightTrail().remove(userWeight);
            this.helper.getUserWeightDao().delete(userWeight);
            this.transactionOver();
        } catch (Exception e) {
            Log.e(TAG, "Error while delete weight", e);
            throw new RuntimeException(e);
        }
    }

    public void createMedicationNotification(MedicationNotification notification) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            this.helper.getNotificationtDao().create(notification);
            this.transactionOver();
        } catch (Exception e) {
            Log.e(TAG, "Error while creating notification record", e);
            throw new RuntimeException(e);
        }
    }

    public void deleteMedicationNotification(MedicationNotification notification) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            this.helper.getNotificationtDao().delete(notification);
            this.transactionOver();
        } catch (Exception e) {
            Log.e(TAG, "Error while delete notification record", e);
            throw new RuntimeException(e);
        }
    }

    public List<MedicationNotification> getAllNotifications() {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            List<MedicationNotification> retList = this.helper.getNotificationtDao().queryForAll();
            this.transactionOver();
            return retList;
        } catch (Exception e) {
            Log.e(TAG, "Error while retrieving all notification records", e);
            throw new RuntimeException(e);
        }

    }

    public <T> ForeignCollection getEmptyCollection(Object type, String field) {
        try {
            //ConnectionSource connectionSource = new AndroidConnectionSource(this.helper);
            ForeignCollection coll = helper.getEmptyCollection(type, field);
            transactionOver();
            return coll;
        } catch (Exception e) {
            Log.e(TAG, "Error while getting empty collection", e);
            throw new RuntimeException(e);
        }
    }

    public void close() {
        if(this.helper.isOpen()) {
            this.helper.close();
        }
    }

    public void ensureDbOpen() {
//        if(!helper.isOpen()) {
//        }
    }

    private void transactionOver() {
        //this.helper.close();
//        OpenHelperManager.releaseHelper();
//        MadhumitraDataManagerFactory.releaseMadhumitraDataManager();
//        this.helper = null;
    }

    public boolean isOpen() {
        return helper.isOpen();
    }
}
