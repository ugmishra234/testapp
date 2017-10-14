package com.honap.madhumitra.act;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import com.honap.madhumitra.R;
import com.honap.madhumitra.data.DbHelper;
import com.honap.madhumitra.data.MadhumitraDataManagerFactory;
import com.honap.madhumitra.entity.MedicationRecord;
import com.honap.madhumitra.model.MadhumitraModel;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.ForeignCollection;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.prefs.Preferences;

public class LandingActivity extends OrmLiteBaseActivity<DbHelper> {
    private int ADD_NEW_TAB = Menu.FIRST;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        //long d = Date.parse("08:00am");
        //Date date = new Date(d);
//        Time t = new Time();
//        t.parse("08:00am");
//        long utc = t.toMillis(true);
//        Date date = new Date(utc);
        setContentView(R.layout.main);

        TabHost myTabHost = (TabHost) this.findViewById(R.id.th_set_menu_tabhost);
        myTabHost.setup();
        TabHost.TabSpec spec1 = myTabHost.newTabSpec("Activities");
        spec1.setContent(R.id.tab1);
        spec1.setIndicator("Activities");

        TabHost.TabSpec spec2 = myTabHost.newTabSpec("Reports");
        spec2.setIndicator("Reports");
        spec2.setContent(R.id.tab2);

//    TabHost.TabSpec spec3=myTabHost.newTabSpec("Info");
//    spec3.setIndicator("Info");
//    spec3.setContent(R.id.tab3);

        myTabHost.addTab(spec1);
        myTabHost.addTab(spec2);
//    myTabHost.addTab(spec3);

        Button recordActivity = (Button) findViewById(R.id.home_btn_activity);
        recordActivity.setOnClickListener(new View.OnClickListener()

        {
            public void onClick
                    (View
                             v) {

//            Intent actIntent = new Intent(v.getContext(),RecordActivity.class);
//            startActivityForResult(actIntent, 0);

            }
        }

        );

//        Button symptomActivity = (Button) findViewById(R.id.home_btn_symptom);
//        symptomActivity.setOnClickListener(new View.OnClickListener()
//
//        {
//            public void onClick
//                    (View
//                             v) {
//
////            Intent actIntent = new Intent(v.getContext(),RecordSymptom.class);
////            startActivityForResult(actIntent, 0);
//
//            }
//        }
//
//        );

        Button mealActivity = (Button) findViewById(R.id.home_btn_meal);
        mealActivity.setOnClickListener(new View.OnClickListener()

        {
            public void onClick
                    (View
                             v) {

//            Intent actIntent = new Intent(v.getContext(),RecordMeal.class);
//            startActivityForResult(actIntent, 0);

            }
        }

        );

        Button homeInv = (Button) findViewById(R.id.home_btn_home_inv);
        homeInv.setOnClickListener(new View.OnClickListener()

        {
            public void onClick
                    (View
                             v) {

                Intent actIntent = new Intent(v.getContext(), RecordHomeInvestigation.class);
                startActivityForResult(actIntent, 0);
            }
        }

        );


        Button calorieRep = (Button) findViewById(R.id.home_btn_calorie_report);
        calorieRep.setOnClickListener(new View.OnClickListener()

        {
            public void onClick
                    (View
                             v) {
                Intent actIntent = new Intent(v.getContext(), CalorieReport.class);
                startActivityForResult(actIntent, 0);
            }
        }

        );

//        Button symptoms = (Button) findViewById(R.id.home_btn_symptom);
//        symptoms.setOnClickListener(new View.OnClickListener()
//
//        {
//            public void onClick
//                    (View
//                             v) {
//                Intent actIntent = new Intent(v.getContext(), RecordSymptom.class);
//                startActivityForResult(actIntent, 0);
//            }
//        }
//
//        );

        Button activity = (Button) findViewById(R.id.home_btn_activity);
        activity.setOnClickListener(new View.OnClickListener()

        {
            public void onClick
                    (View
                             v) {
                Intent actIntent = new Intent(v.getContext(), RecordActivity.class);
                startActivityForResult(actIntent, 0);
            }
        }

        );

        Button recMeal = (Button) findViewById(R.id.home_btn_meal);
        recMeal.setOnClickListener(new View.OnClickListener()

        {
            public void onClick
                    (View
                             v) {
                Intent actIntent = new Intent(v.getContext(), RecordClassifiedMeal.class);
                startActivityForResult(actIntent, 0);
            }
        }

        );

        Button exercise = (Button) findViewById(R.id.home_btn_record_exercise);
        exercise.setOnClickListener(new View.OnClickListener()

        {
            public void onClick
                    (View
                             v) {
                Intent actIntent = new Intent(v.getContext(), RecordExercise.class);
                startActivityForResult(actIntent, 0);
            }
        }

        );


//        Button labInv = (Button) findViewById(R.id.home_btn_lab_inv);
//        labInv.setOnClickListener(new View.OnClickListener()
//
//        {
//            public void onClick
//                    (View
//                             v) {
//                Intent actIntent = new Intent(v.getContext(), RecordLabInvestigation.class);
//                startActivityForResult(actIntent, 0);
//            }
//        }
//
//        );

//        Button med = (Button) findViewById(R.id.home_btn_medication);
//        med.setOnClickListener(new View.OnClickListener()
//
//        {
//            public void onClick
//                    (View
//                             v) {
//                Intent actIntent = new Intent(v.getContext(), RecordMedication.class);
//                startActivityForResult(actIntent, 0);
//            }
//        }
//
//        );

        Button recDocVisit = (Button) findViewById(R.id.home_btn_doctor_record_prescription);
        recDocVisit.setOnClickListener(new View.OnClickListener()

        {
            public void onClick
                    (View
                             v) {
                Intent actIntent = new Intent(v.getContext(), RecordPrescription.class);
                startActivityForResult(actIntent, 0);
            }
        }

        );

        Button bslReport = (Button) findViewById(R.id.home_btn_bsl_report);
        bslReport.setOnClickListener(new View.OnClickListener()

        {
            public void onClick
                    (View
                             v) {
                Intent actIntent = new Intent(v.getContext(), BslReport.class);
                startActivityForResult(actIntent, 0);
            }
        }

        );

        Button actReport = (Button) findViewById(R.id.home_btn_activity_report);
        actReport.setOnClickListener(new View.OnClickListener()

        {
            public void onClick
                    (View
                             v) {
                Intent actIntent = new Intent(v.getContext(), ActivityReport.class);
                startActivityForResult(actIntent, 0);
            }
        }

        );

//        Button expenseReport = (Button)findViewById(R.id.home_btn_expense_report);
//            expenseReport.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    Intent actIntent = new Intent(v.getContext(), ExpenseReport.class);
//                    startActivityForResult(actIntent, 0);
//                }
//            });


    }


//    private void nop() {
//        // save object
//        UserAccount ua = new UserAccount();
//
//        com.honap.madhumitra.entity.Activity activity = new com.honap.madhumitra.entity.Activity();
//        activity.setName("TestActivity");
//        activity.setCaloricConsumptionPerMin(20);
//        Dao<ActivityRecord,Integer> adao = null;
//        ActivityRecord ar = new ActivityRecord();
//        ar.setStartTime(new Date());
//        ar.setDurationMin(50);
//        ar.setComment("First Comment");
//        ar.setActivity(activity);
//        Context context = getApplicationContext();
//        try {
//            getHelper().getUserAccountDao().create(ua);
//            getHelper().getActivityDao().create(activity);
//
//            getHelper().getActivityRecordDao().create(ar);
//
//            List<com.honap.madhumitra.entity.Activity> acts = getHelper().getActivityDao().queryForAll();
//            List<ActivityRecord> ars = getHelper().getActivityRecordDao().queryForAll();
//            CharSequence name = "Completed";
//            Toast.makeText(context,name,25).show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.landing_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.changeUserMenuBtn: {
                Intent selectUserIntent = new Intent(getApplicationContext(), SelectUserAccount.class);
                startActivityForResult(selectUserIntent, 0);
                break;
            }

            case R.id.settingsMenuBtn: {
                Intent selectUserIntent = new Intent(getApplicationContext(), MadhumitraPreferences.class);
                startActivityForResult(selectUserIntent, 0);
                break;
            }

            case R.id.addDocMenuBtn: {
                Intent addDocIntent = new Intent(getApplicationContext(), AddDoctor.class);
                startActivityForResult(addDocIntent, 0);
                break;
            }

            case R.id.editProfileMenuBtn: {
                Intent actIntent = new Intent(getApplicationContext(), AddUserAccount.class);
                actIntent.putExtra("type","edit");
                startActivityForResult(actIntent, 0);
                break;
            }

            case R.id.deleteRecordsMenuBtn: {
                Intent actIntent = new Intent(getApplicationContext(), DeleteRecords.class);
                startActivityForResult(actIntent, 0);
                break;
            }

            case R.id.deleteUserMenuBtn: {
                MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager(this).
                        deleteUserAccount(MadhumitraModel.getInstance().getCurrentUserAccount());
                Intent selectUserIntent = new Intent(getApplicationContext(), SelectUserAccount.class);
                startActivityForResult(selectUserIntent, 0);
                break;
            }


//            case R.id.helpMenuBtn: {
//                List<MedicationRecord> records = MadhumitraDataManagerFactory.
//                        getDefaultMadhumitraDataManager(this).
//                        getAllMedicationRecords();
//                if(records == null) {
//                    break;
//                }
//                if(records.size() == 0) {
//                    break;
//                }
//                MedicationRecord record = records.get(0);
//                CharSequence message = "Medication Alert for " + record.getUserAccount().getDisplayName() +
//                        "- Drug: " + record.getDrug() + ", Dose: " + record.getDose() + ", Time: " +
//                        new SimpleDateFormat("h:mm a").format(record.getTime());
//                NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                CharSequence from = "Madhumitra";
//                Intent intent = new Intent(this, MedicationNotificationAck.class);
//                intent.putExtra("recordId", record.getId().longValue());
//                intent.putExtra("msg",message.toString());
//                PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//                        intent, 0);
//                Notification notif = new Notification(R.drawable.notification,
//                        "Medication Alert", System.currentTimeMillis());
//                notif.setLatestEventInfo(this, from, message, contentIntent);
//                nm.notify(new Random().nextInt(), notif);
//                break;
//            }

        }
        return true;
    }

    private void loadPreferences() {
        SharedPreferences ps = getPreferences(MODE_PRIVATE);
        //ps.edit().

    }


}
