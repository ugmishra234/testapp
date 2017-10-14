package com.honap.madhumitra.data;

/**
 * Author: Chetan S.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.honap.madhumitra.entity.*;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import junit.framework.Assert;

import java.sql.SQLException;


/**
 * Database helper which creates and upgrades the database and provides the DAOs for the app.
 *
 * @author kevingalligan
 */
public class DbHelper extends OrmLiteSqliteOpenHelper {

	/************************************************
	 * Suggested Copy/Paste code. Everything from here to the done block.
	 ************************************************/

	private static final String DATABASE_NAME = "testm2.data";
	private static final int DATABASE_VERSION = 6;

    private Dao<ActivityRecord,Integer> activityRecordDao = null;
    private Dao<Doctor,Integer> doctorDao = null;
    private Dao<DoctorVisit,Integer> doctorVisitDao = null;
    private Dao<Exercise,Integer> exerciseDao = null;
    private Dao<ExerciseRecord,Integer> exerciseRecordDao = null;
    private Dao<HomeInvestigationParamValue,Integer> homeInvestigationParamValueDao = null;
    private Dao<HomeInvestigationRecord,Integer> homeInvestigationRecordDao = null;
    private Dao<LabInvestigation,Integer> labInvestigationDao = null;
    private Dao<LabInvestigationParamValue,Integer> labInvestigationParamValueDao = null;
    private Dao<LabInvestigationRecord,Integer> labInvestigationRecordDao = null;
    private Dao<MealItemRecord,Integer> mealItemRecordDao = null;
    private Dao<MealRecord,Integer> mealRecordDao = null;
    private Dao<MedicationRecord,Integer> medicationRecordDao = null;
    private Dao<PrescribedDose,Integer> prescribedDoseDao = null;
    private Dao<PrescriptionItem,Integer> prescriptionItemDao = null;
    private Dao<Prescription,Integer> prescriptionDao = null;
    private Dao<SymptomRecord,Integer> symptomRecordDao = null;
    private Dao<UserWeight,Integer> userWeightDao = null;
    private Dao<UserAccount,Integer> accountDao = null;
    private Dao<MedicationNotification,Integer> notificationtDao = null;


	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/************************************************
	 * Suggested Copy/Paste Done
	 ************************************************/



	@Override
	public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, ActivityRecord.class);
            TableUtils.createTable(connectionSource, Doctor.class);
            TableUtils.createTable(connectionSource, DoctorVisit.class);
            TableUtils.createTable(connectionSource, Exercise.class);
            TableUtils.createTable(connectionSource, ExerciseRecord.class);
            TableUtils.createTable(connectionSource, HomeInvestigationRecord.class);
            TableUtils.createTable(connectionSource, LabInvestigation.class);
            TableUtils.createTable(connectionSource, LabInvestigationRecord.class);
            TableUtils.createTable(connectionSource, MealItemRecord.class);
            TableUtils.createTable(connectionSource, MealRecord.class);
            TableUtils.createTable(connectionSource, MedicationRecord.class);
            TableUtils.createTable(connectionSource, PrescribedDose.class);
            TableUtils.createTable(connectionSource, PrescriptionItem.class);
            TableUtils.createTable(connectionSource, Prescription.class);
            TableUtils.createTable(connectionSource, SymptomRecord.class);
            TableUtils.createTable(connectionSource, UserAccount.class);
            TableUtils.createTable(connectionSource, UserWeight.class);
            TableUtils.createTable(connectionSource, HomeInvestigationParamValue.class);
            TableUtils.createTable(connectionSource, LabInvestigationParamValue.class);
            TableUtils.createTable(connectionSource, MedicationNotification.class);
		} catch (SQLException e) {
			Log.e(DbHelper.class.getName(), "Unable to create databases", e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
		try {
            TableUtils.dropTable(connectionSource, ActivityRecord.class,true);
            TableUtils.dropTable(connectionSource, Doctor.class,true);
            TableUtils.dropTable(connectionSource, DoctorVisit.class,true);
            TableUtils.dropTable(connectionSource, Exercise.class,true);
            TableUtils.dropTable(connectionSource, ExerciseRecord.class,true);
            TableUtils.dropTable(connectionSource, HomeInvestigationRecord.class,true);
            TableUtils.dropTable(connectionSource, LabInvestigation.class,true);
            TableUtils.dropTable(connectionSource, LabInvestigationRecord.class,true);
            TableUtils.dropTable(connectionSource, MealItemRecord.class,true);
            TableUtils.dropTable(connectionSource, MealRecord.class,true);
            TableUtils.dropTable(connectionSource, MedicationRecord.class,true);
            TableUtils.dropTable(connectionSource, PrescribedDose.class,true);
            TableUtils.dropTable(connectionSource, PrescriptionItem.class,true);
            TableUtils.dropTable(connectionSource, Prescription.class,true);
            TableUtils.dropTable(connectionSource, SymptomRecord.class,true);
            TableUtils.dropTable(connectionSource, UserAccount.class,true);
            TableUtils.dropTable(connectionSource, UserWeight.class,true);
            TableUtils.dropTable(connectionSource, HomeInvestigationParamValue.class,true);
            TableUtils.dropTable(connectionSource, LabInvestigationParamValue.class,true);
            TableUtils.dropTable(connectionSource, MedicationNotification.class,true);
			onCreate(sqliteDatabase, connectionSource);
		} catch (SQLException e) {
			Log.e(DbHelper.class.getName(), "Unable to upgrade database from version " + oldVer + " to new "
					+ newVer, e);
		}
	}

    public Dao<ActivityRecord,Integer> getActivityRecordDao() throws Exception {
        if(this.activityRecordDao == null) {
            activityRecordDao = getDao(ActivityRecord.class);
        }
        return activityRecordDao;
    }

    public Dao<Doctor,Integer> getDoctorDao() throws Exception {
        if(this.doctorDao == null) {
            doctorDao = getDao(Doctor.class);
        }
        return doctorDao;
    }

    public Dao<DoctorVisit,Integer> getDoctorVisitDao() throws Exception {
        if(this.doctorVisitDao == null) {
            doctorVisitDao = getDao(DoctorVisit.class);
        }
        return doctorVisitDao;
    }

    public Dao<Exercise,Integer> getExerciseDao() throws Exception {
        if(this.exerciseDao == null) {
            exerciseDao = getDao(Exercise.class);
        }
        return exerciseDao;
    }
    
    public Dao<ExerciseRecord,Integer> getExerciseRecordDao() throws Exception {
        if(this.exerciseRecordDao == null) {
            exerciseRecordDao = getDao(ExerciseRecord.class);
        }
        return exerciseRecordDao;
    }

    public Dao<HomeInvestigationParamValue,Integer> getHomeInvestigationParamValueDao() throws Exception {
        if(this.homeInvestigationParamValueDao == null) {
            homeInvestigationParamValueDao = getDao(HomeInvestigationParamValue.class);
        }
        return homeInvestigationParamValueDao;
    }
    
    public Dao<HomeInvestigationRecord,Integer> getHomeInvestigationRecordDao() throws Exception {
        if(this.homeInvestigationRecordDao == null) {
            homeInvestigationRecordDao = getDao(HomeInvestigationRecord.class);
        }
        return homeInvestigationRecordDao;
    }
    

    public Dao<LabInvestigation,Integer> getLabInvestigationDao() throws Exception {
        if(this.labInvestigationDao == null) {
            labInvestigationDao = getDao(LabInvestigation.class);
        }
        return labInvestigationDao;
    }

    public Dao<LabInvestigationParamValue,Integer> getLabInvestigationParamValueDao() throws Exception {
        if(this.labInvestigationParamValueDao == null) {
            labInvestigationParamValueDao = getDao(LabInvestigationParamValue.class);
        }
        return labInvestigationParamValueDao;
    }
    
    public Dao<LabInvestigationRecord,Integer> getLabInvestigationRecordDao() throws Exception {
        if(this.labInvestigationRecordDao == null) {
            labInvestigationRecordDao = getDao(LabInvestigationRecord.class);
        }
        return labInvestigationRecordDao;
    }
    

    public Dao<MealItemRecord,Integer> getMealItemRecordDao() throws Exception {
        if(this.mealItemRecordDao == null) {
            mealItemRecordDao = getDao(MealItemRecord.class);
        }
        return mealItemRecordDao;
    }

    public Dao<MealRecord,Integer> getMealRecordDao() throws Exception {
        if(this.mealRecordDao == null) {
            mealRecordDao = getDao(MealRecord.class);
        }
        return mealRecordDao;
    }

    public Dao<MedicationRecord,Integer> getMedicationRecordDao() throws Exception {
        if(this.medicationRecordDao == null) {
            medicationRecordDao = getDao(MedicationRecord.class);
        }
        return medicationRecordDao;
    }

    public Dao<PrescribedDose,Integer> getPrescribedDoseDao() throws Exception {
        if(this.prescribedDoseDao == null) {
            prescribedDoseDao = getDao(PrescribedDose.class);
        }
        return prescribedDoseDao;
    }

    public Dao<PrescriptionItem,Integer> getPrescriptionItemDao() throws Exception {
        if(this.prescriptionItemDao == null) {
            prescriptionItemDao = getDao(PrescriptionItem.class);
        }
        return prescriptionItemDao;
    }


    public Dao<Prescription,Integer> getPrescriptionDao() throws Exception {
        if(this.prescriptionDao == null) {
            prescriptionDao = getDao(Prescription.class);
        }
        return prescriptionDao;
    }

    public Dao<SymptomRecord,Integer> getSymptomRecordDao() throws Exception {
        if(this.symptomRecordDao == null) {
            symptomRecordDao = getDao(SymptomRecord.class);
        }
        return symptomRecordDao;
    }

    public Dao<UserWeight,Integer> getUserWeightDao() throws Exception {
        if(this.userWeightDao == null) {
            userWeightDao = getDao(UserWeight.class);
        }
        return userWeightDao;
    }

    public Dao<UserAccount,Integer> getUserAccountDao() throws Exception {
        if(this.accountDao == null) {
            accountDao = getDao(UserAccount.class);
        }
        return accountDao;
    }

    public Dao<MedicationNotification, Integer> getNotificationtDao() throws Exception {
        if(this.notificationtDao == null) {
            notificationtDao = getDao(MedicationNotification.class);
        }
        return notificationtDao;
    }

    public <T> ForeignCollection getEmptyCollection(Object type, String field) throws Exception {
        Dao<T,Integer> genericDao = (Dao<T,Integer>)getDao(type.getClass());
        ForeignCollection fc = genericDao.getEmptyForeignCollection(field);
        return fc;
    }

    @Override
    public void close() {
        super.close();    //To change body of overridden methods use File | Settings | File Templates.
        activityRecordDao = null;
        doctorDao = null;
        doctorVisitDao = null;
        exerciseDao = null;
        exerciseRecordDao = null;
        homeInvestigationParamValueDao = null;
        homeInvestigationRecordDao = null;
        labInvestigationDao = null;
        labInvestigationParamValueDao = null;
        labInvestigationRecordDao = null;
        mealItemRecordDao = null;
        mealRecordDao = null;
        medicationRecordDao = null;
        prescribedDoseDao = null;
        prescriptionItemDao = null;
        prescriptionDao = null;
        symptomRecordDao = null;
        userWeightDao = null;
        accountDao = null;
        notificationtDao = null;


    }
}
