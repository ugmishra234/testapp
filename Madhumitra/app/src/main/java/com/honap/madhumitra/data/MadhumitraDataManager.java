package com.honap.madhumitra.data;

import com.honap.madhumitra.entity.*;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;

import java.util.List;

/**
 * Author: Chetan S.
 */
public interface MadhumitraDataManager {

    /*
    User Account
     */
    void createUserAccount(UserAccount userAccount);
    void updateUserAccount(UserAccount userAccount);
    void deleteUserAccount(UserAccount userAccount);
    List<UserAccount> getAllUserAccounts();


    /*
    Activity Record
     */
    void createActivityRecord(ActivityRecord activityRecord);
    void deleteActivityRecord(ActivityRecord activityRecord);


    /*
    Doctor
     */
    void createDoctor(Doctor doctor);
    List<Doctor> getAllDoctors();
    void deleteDoctor(Doctor doctor);

    /*
    Doctor Visit
     */
    void createDoctorVisit(DoctorVisit doctorVisit);
    void updateDoctorVisit(DoctorVisit doctorVisit);
    void deleteDoctorVisit(DoctorVisit doctorVisit);

    /*
    Exercise
     */
    void createExercise(Exercise exercise);
    List<Exercise> getAllExercises();


    /*
    Exercise Record
     */
    void createExerciseRecord(ExerciseRecord exerciseRecord);
    void updateExerciseRecord(ExerciseRecord exerciseRecord);
    void deleteExerciseRecord(ExerciseRecord exerciseRecord);


    /*
    Home Investigation Param Value
     */
    void createHomeInvestigationParamValue(HomeInvestigationParamValue homeInvestigationParamValue);
    void updateHomeInvestigationParamValue(HomeInvestigationParamValue homeInvestigationParamValue);
    void deleteHomeInvestigationParamValue(HomeInvestigationParamValue homeInvestigationParamValue);

    /*
    Home Investigation Record
     */
    void createHomeInvestigationRecord(HomeInvestigationRecord homeInvestigationRecord);
    void updateHomeInvestigationRecord(HomeInvestigationRecord homeInvestigationRecord);
    void deleteHomeInvestigationRecord(HomeInvestigationRecord homeInvestigationRecord);


    /*
    Lab Investigation
     */
    void createLabInvestigation(LabInvestigation labInvestigation);
    List<LabInvestigation> getAllLabInvestigations();


    /*
    Lab Investigation Param Value
     */
    void createLabInvestigationParamValue(LabInvestigationParamValue labInvestigationParamValue);
    void updateLabInvestigationParamValue(LabInvestigationParamValue labInvestigationParamValue);
    void deleteLabInvestigationParamValue(LabInvestigationParamValue labInvestigationParamValue);


    /*
    Lab Investigation Record
     */
    void createLabInvestigationRecord(LabInvestigationRecord labInvestigationRecord);
    void updateLabInvestigationRecord(LabInvestigationRecord labInvestigationRecord);
    void deleteLabInvestigationRecord(LabInvestigationRecord labInvestigationRecord);

    /*
    Meal Item Record
     */
    void createMealItemRecord(MealItemRecord mealItemRecord);
    void updateMealItemRecord(MealItemRecord mealItemRecord);
    void deleteMealItemRecord(MealItemRecord mealItemRecord);

    /*                
    Meal Record
     */
    void createMealRecord(MealRecord mealRecord);
    void updateMealRecord(MealRecord mealRecord);
    void deleteMealRecord(MealRecord mealRecord);


    /*                
    Medication Record
     */
    void createMedicationRecord(MedicationRecord medicationRecord);
    void updateMedicationRecord(MedicationRecord medicationRecord);
    void deleteMedicationRecord(MedicationRecord medicationRecord);
    List<MedicationRecord> getAllMedicationRecords();
    MedicationRecord getMedicationRecord(long id);

    /*                
    Prescribed Dose
     */
    void createPrescribedDose(PrescribedDose prescribedDose);
    void updatePrescribedDose(PrescribedDose prescribedDose);
    void deletePrescribedDose(PrescribedDose prescribedDose);


    /*
    Prescription Item
     */
    void createPrescriptionItem(PrescriptionItem prescriptionItem);
    void deletePrescriptionItem(PrescriptionItem prescriptionItem);


    /*                
    Prescription
     */
    void createPrescription(Prescription prescription);
    void updatePrescription(Prescription prescription);
    void deletePrescription(Prescription prescription);

    /*
    Symptom Record
     */
    void createSymptomRecord(SymptomRecord symptomRecord);
    void updateSymptomRecord(SymptomRecord symptomRecord);
    void deleteSymptomRecord(SymptomRecord symptomRecord);

    /*                
    UserWeight
     */
    void createUserWeight(UserWeight userWeight);
    void updateUserWeight(UserWeight userWeight);
    void deleteUserWeight(UserWeight userWeight);

    /*
    Medication Notification
     */
    void createMedicationNotification(MedicationNotification notification);
    void deleteMedicationNotification(MedicationNotification notification);
    List<MedicationNotification> getAllNotifications();

    
    <T> ForeignCollection getEmptyCollection(Object type, String field);

    void close();

    void ensureDbOpen();

    boolean isOpen();

}
