package com.honap.madhumitra.model;

import android.preference.Preference;
import com.honap.madhumitra.entity.Preferences;
import com.honap.madhumitra.entity.UserAccount;

/**
 * Author: Chetan S.
 */
public class MadhumitraModel {
    private static MadhumitraModel instance = null;
    private UserAccount currentUserAccount;
    private Preferences preferences;

    private MadhumitraModel() {
    }

    public static MadhumitraModel getInstance() {
        if (instance == null) {
            instance = new MadhumitraModel();
        }
        return instance;
    }

    public UserAccount getCurrentUserAccount() {
        return currentUserAccount;
    }

    public void setCurrentUserAccount(UserAccount currentUserAccount) {
        this.currentUserAccount = currentUserAccount;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }
}
