package com.honap.madhumitra.act;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.crittercism.app.Crittercism;
import com.honap.madhumitra.R;
import com.honap.madhumitra.data.DbHelper;
import com.honap.madhumitra.data.MadhumitraDataManagerFactory;
import com.honap.madhumitra.entity.Preferences;
import com.honap.madhumitra.entity.UserAccount;
import com.honap.madhumitra.misc.UserAccountAdapter;
import com.honap.madhumitra.model.MadhumitraModel;
import com.honap.madhumitra.utils.Utils;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import java.util.List;

public class SelectUserAccount extends OrmLiteBaseActivity<DbHelper> {

    private List<UserAccount> userAccounts = null;
    private AlarmManager am = null;


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.select_account);
        Crittercism.init(getApplicationContext(), "4ef9de1fb093155343000113", "4ef9de1fb093155343000113xtmtea4k",
                "zihupt6gotdexmv5furn8bczy74x3jhk");
        populatePreferences();
        // retrieve all user accounts
        userAccounts = MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager(this).getAllUserAccounts();
        if (userAccounts.size() == 0) {
            TextView text = findViewById(R.id.noUsersTextView);
            text.setText(R.string.no_users_string);
        }
        UserAccountAdapter userAccountAdapter = new UserAccountAdapter(this, android.R.layout.activity_list_item, userAccounts);
        final GridView listView = (GridView) findViewById(R.id.accountList);
        listView.setAdapter(userAccountAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MadhumitraModel.getInstance().setCurrentUserAccount(userAccounts.get(i));
                Intent actIntent = new Intent(getApplicationContext(), LandingActivity.class);
                startActivityForResult(actIntent, 0);
            }
        });
        am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        setRepeatingAlarm();

        Button addUser = findViewById(R.id.btn_add_user);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actIntent = new Intent(getApplicationContext(), AddUserAccount.class);
                actIntent.putExtra("type", "add");
                startActivityForResult(actIntent, 0);
            }
        });

        //startService(new Intent(this, MedicationNotificationService.class));

    }

    public void setRepeatingAlarm() {
        Intent intent = new Intent(this, MedicationAlarmAnalyser.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
                (60 * 1000), pendingIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
        // retrieve all user accounts
        userAccounts = MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager(this).getAllUserAccounts();
        if (userAccounts.size() == 0) {
            TextView text = findViewById(R.id.noUsersTextView);
            text.setText(R.string.no_users_string);
        }
        UserAccountAdapter userAccountAdapter = new UserAccountAdapter(this, android.R.layout.activity_list_item, userAccounts);
        GridView listView = findViewById(R.id.accountList);
        listView.setAdapter(userAccountAdapter);

    }

    @Override
    protected void onRestart() {
        super.onRestart();    //To change body of overridden methods use File | Settings | File Templates.
        // retrieve all user accounts
        userAccounts = MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager(this).getAllUserAccounts();
        if (userAccounts.size() == 0) {
            TextView text = findViewById(R.id.noUsersTextView);
            text.setText(R.string.no_users_string);
        }
        UserAccountAdapter userAccountAdapter = new UserAccountAdapter(this, android.R.layout.activity_list_item, userAccounts);
        GridView listView = findViewById(R.id.accountList);
        listView.setAdapter(userAccountAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
        //MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager(this).
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setEnabled(false);
        switch (item.getItemId()) {
            case R.id.addUserMenuBtn: {
                // populate activity record
                Intent actIntent = new Intent(getApplicationContext(), AddUserAccount.class);
                actIntent.putExtra("type", "add");
                startActivityForResult(actIntent, 0);
                break;
            }

//            case R.id.medListMenuBtn : {
//                // populate activity record
//                Intent actIntent = new Intent(getApplicationContext(), MedRecList.class);
//                startActivityForResult(actIntent, 0);
//                break;
//            }


//            case R.id.helpMenuBtn : {
//                finish();
//                break;
//            }

        }
        item.setEnabled(true);
        return true;
    }

    private void populatePreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(
                getResources().getString(R.string.preferences_id), MODE_PRIVATE);
        Preferences preferences = MadhumitraModel.getInstance().getPreferences();
        if (preferences == null) preferences = new Preferences();
        String mornTime = sharedPreferences.getString("morningTimePref", "8:00am");
        String noonTime = sharedPreferences.getString("noonTimePref", "12:00pm");
        String evenTime = sharedPreferences.getString("evenTimePref", "04:30pm");
        String nightTime = sharedPreferences.getString("nightTimePref", "08:00pm");
        // morn
        preferences.setMornHour(Utils.getHour(mornTime));
        preferences.setMornMin(Utils.getMin(mornTime));
        // noon
        preferences.setNoonHour(Utils.getHour(noonTime));
        preferences.setNoonMin(Utils.getMin(noonTime));
        // even
        preferences.setEvenHour(Utils.getHour(evenTime));
        preferences.setEvenMin(Utils.getMin(evenTime));
        // night
        preferences.setNightHour(Utils.getHour(nightTime));
        preferences.setNightMin(Utils.getMin(nightTime));

        MadhumitraModel.getInstance().setPreferences(preferences);
    }


}
