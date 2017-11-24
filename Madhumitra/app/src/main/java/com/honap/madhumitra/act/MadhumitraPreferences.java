package com.honap.madhumitra.act;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.honap.madhumitra.R;
import com.honap.madhumitra.data.MadhumitraDataManagerFactory;
import com.honap.madhumitra.entity.Doctor;
import com.honap.madhumitra.entity.Preferences;
import com.honap.madhumitra.entity.PrescriptionItem;
import com.honap.madhumitra.model.MadhumitraModel;
import com.honap.madhumitra.utils.Utils;

import java.util.Iterator;

/**
 * Author: Chetan S.
 */
public class MadhumitraPreferences extends PreferenceActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.internal_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveMenuBtn: {
                SharedPreferences sharedPreferences = getSharedPreferences(
                        getResources().getString(R.string.preferences_id), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.commit();
                populatePreferences();
                finish();
                break;
            }

            case R.id.cancelMenuBtn: {
                finish();
                break;
            }
        }
        return true;
    }


}