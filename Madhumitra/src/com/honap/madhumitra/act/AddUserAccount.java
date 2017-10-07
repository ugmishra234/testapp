package com.honap.madhumitra.act;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.honap.madhumitra.R;
import com.honap.madhumitra.data.MadhumitraDataManager;
import com.honap.madhumitra.data.MadhumitraDataManagerFactory;
import com.honap.madhumitra.entity.Doctor;
import com.honap.madhumitra.entity.UserAccount;
import com.honap.madhumitra.entity.UserWeight;
import com.honap.madhumitra.model.MadhumitraModel;

import java.util.Arrays;
import java.util.Date;

/**
 * Author: Chetan S.
 */
public class AddUserAccount extends Activity {
    UserAccount userAccount = new UserAccount();
    String type = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user_account);
        type = getIntent().getExtras().getString("type");
        Spinner lifestyle = (Spinner)findViewById(R.id.userLifestyleSpinner);
        lifestyle.setPrompt("Select Lifestyle");
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.user_lifestyle));
        lifestyle.setAdapter(stringArrayAdapter);

        if (type.equalsIgnoreCase("edit")) {
            setTitle("Edit User Profile");
            userAccount = MadhumitraModel.getInstance().getCurrentUserAccount();
            EditText displayNameEditText = (EditText) findViewById(R.id.userName);
            displayNameEditText.setText(userAccount.getDisplayName());

            EditText relationEditText = (EditText) findViewById(R.id.userRelation);
            relationEditText.setText(userAccount.getRelationWithPrimaryUser());

            DatePicker dob = (DatePicker) findViewById(R.id.userDob);
            dob.updateDate(userAccount.getDob().getYear(), userAccount.getDob().getMonth(),
                    userAccount.getDob().getDate());

            EditText emailEditText = (EditText) findViewById(R.id.userEmail);
            emailEditText.setText(userAccount.getEmail());

            EditText mobEditText = (EditText) findViewById(R.id.userMobile);
            mobEditText.setText(userAccount.getMobile());

            if (userAccount.getSex().equalsIgnoreCase("male")) {
                RadioButton maleRb = (RadioButton) findViewById(R.id.maleSexRadioBtn);
                maleRb.setChecked(true);
            } else {
                RadioButton femaleRb = (RadioButton) findViewById(R.id.femaleSexRadioBtn);
                femaleRb.setChecked(true);
            }

            UserWeight userWeight = (UserWeight) MadhumitraModel.getInstance().getCurrentUserAccount().getWeightTrail().toArray()
                    [MadhumitraModel.getInstance().getCurrentUserAccount().getWeightTrail().size() - 1];
            EditText wtEditText = (EditText) findViewById(R.id.userWeight);
            wtEditText.setText(new Integer(userWeight.getWeight()).toString());

            Spinner lifestyleSpinner = (Spinner)findViewById(R.id.userLifestyleSpinner);
            int pos = Arrays.asList(getResources().getStringArray(R.array.user_lifestyle)).
                    indexOf(userAccount.getLifestyle());
            lifestyleSpinner.setSelection(pos);

            EditText htEditText = (EditText)findViewById(R.id.userHeight);
            htEditText.setText(new Integer(userAccount.getHeightCm()).toString());
        }

        RadioButton maleRb = (RadioButton) findViewById(R.id.maleSexRadioBtn);
        maleRb.setOnClickListener(new RadioButton.OnClickListener() {
            public void onClick(View view) {
                userAccount.setSex("male");
            }
        });

        RadioButton femaleRb = (RadioButton) findViewById(R.id.femaleSexRadioBtn);
        femaleRb.setOnClickListener(new RadioButton.OnClickListener() {
            public void onClick(View view) {
                userAccount.setSex("female");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.internal_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setEnabled(false);
        switch (item.getItemId()) {
            case R.id.saveMenuBtn: {
                if(type.equalsIgnoreCase("add")) {
                    userAccount.setCreatedOn(new Date());
                }
                EditText displayNameEditText = (EditText) findViewById(R.id.userName);
                if (!(displayNameEditText.getText().length() > 0)) {
                    Toast.makeText(this, "Please enter name", Toast.LENGTH_LONG).show();
                    return true;
                }
                userAccount.setDisplayName(displayNameEditText.getText().toString());

                EditText relationEditText = (EditText) findViewById(R.id.userRelation);
                if (!(relationEditText.getText().length() > 0)) {
                    Toast.makeText(this, "Please enter relation", Toast.LENGTH_LONG).show();
                    return true;
                }
                userAccount.setRelationWithPrimaryUser(relationEditText.getText().toString());

                DatePicker dob = (DatePicker) findViewById(R.id.userDob);
                userAccount.setDob(new Date(dob.getYear(), dob.getMonth(), dob.getDayOfMonth()));

                if(userAccount.getSex() == null) {
                    Toast.makeText(this, "Please select the gender", Toast.LENGTH_LONG).show();
                    return true;
                }

                EditText emailEditText = (EditText) findViewById(R.id.userEmail);
                if (!(emailEditText.getText().length() > 0)) {
                    Toast.makeText(this, "Please enter e-mail address", Toast.LENGTH_LONG).show();
                    return true;
                }
                userAccount.setEmail(emailEditText.getText().toString());

                Spinner lifestyleSpinner = (Spinner)findViewById(R.id.userLifestyleSpinner);
                int pos = lifestyleSpinner.getSelectedItemPosition();
                if(pos < 0) {
                    Toast.makeText(this, "Please select the appropriate lifestyle", Toast.LENGTH_LONG).show();
                    return true;
                }
                String lifestyle = getResources().getStringArray(R.array.user_lifestyle)[pos];
                userAccount.setLifestyle(lifestyle);

                EditText htEditText = (EditText)findViewById(R.id.userHeight);
                if (!(htEditText.getText().length() > 0)) {
                    Toast.makeText(this, "Please enter height in cms", Toast.LENGTH_LONG).show();
                    return true;
                }
                userAccount.setHeightCm(Integer.parseInt(htEditText.getText().toString()));



                EditText mobEditText = (EditText) findViewById(R.id.userMobile);
                if (!(mobEditText.getText().length() > 0)) {
                    Toast.makeText(this, "Please enter mobile number", Toast.LENGTH_LONG).show();
                    return true;
                }
                userAccount.setMobile(mobEditText.getText().toString());




                if (type.equalsIgnoreCase("add")) {
                    userAccount.setWeightTrail(
                            MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager
                                    (this).getEmptyCollection(userAccount, "weight"));
                    userAccount.setDoctors(
                            MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager
                                    (this).getEmptyCollection(userAccount, "doctor"));
                    userAccount.setPrescriptions(
                            MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager
                                    (this).getEmptyCollection(userAccount, "prescription"));
                    userAccount.setHomeInvestigations(
                            MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager
                                    (this).getEmptyCollection(userAccount, "home_investigation"));
                    userAccount.setLabInvestigations(
                            MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager
                                    (this).getEmptyCollection(userAccount, "lab_investigation"));
                    userAccount.setActivityRecords(
                            MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager
                                    (this).getEmptyCollection(userAccount, "activity_record"));
                    userAccount.setSymptomRecords(
                            MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager
                                    (this).getEmptyCollection(userAccount, "symptom_record"));
                    userAccount.setExerciseRecords(
                            MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager
                                    (this).getEmptyCollection(userAccount, "exercise_record"));
                    userAccount.setMealRecords(
                            MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager
                                    (this).getEmptyCollection(userAccount, "meal_record"));
                    userAccount.setMedicationRecords(
                            MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager
                                    (this).getEmptyCollection(userAccount, "medication_record"));
                }

                EditText wtEditText = (EditText) findViewById(R.id.userWeight);
                if (!(wtEditText.getText().length() > 0)) {
                    Toast.makeText(this, "Please enter weight", Toast.LENGTH_LONG).show();
                    return true;
                }

               if(type.equalsIgnoreCase("edit")) {
                   UserWeight currentUserWeight = (UserWeight) MadhumitraModel.getInstance().getCurrentUserAccount().
                           getWeightTrail().toArray()
                           [MadhumitraModel.getInstance().getCurrentUserAccount().getWeightTrail().size() - 1];

                    if(currentUserWeight.getWeight() != Integer.parseInt(wtEditText.getText().toString())) {
                        // create user weight
                        UserWeight userWeight = new UserWeight();
                        userWeight.setWeight(Integer.parseInt(wtEditText.getText().toString()));
                        userWeight.setUpdatedOn(new Date());
                        userWeight.setUserAccount(userAccount);
                        userAccount.getWeightTrail().add(userWeight);
                        MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager(this).createUserWeight(userWeight);
                    }
                } else {
                    UserWeight userWeight = new UserWeight();
                    userWeight.setWeight(Integer.parseInt(wtEditText.getText().toString()));
                    userWeight.setUpdatedOn(new Date());
                    userWeight.setUserAccount(userAccount);
                    userAccount.getWeightTrail().add(userWeight);
                }
                // save user account
                if(type.equalsIgnoreCase("edit")) {
                    MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager(this).updateUserAccount(userAccount);
                } else {
                    MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager(this).createUserAccount(userAccount);
                }
                finish();
                break;
            }

            case R.id.cancelMenuBtn: {
                finish();
                break;
            }
        }
        item.setEnabled(true);
        return true;
    }

}