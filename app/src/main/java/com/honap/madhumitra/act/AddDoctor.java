package com.honap.madhumitra.act;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import com.honap.madhumitra.R;
import com.honap.madhumitra.data.MadhumitraDataManagerFactory;
import com.honap.madhumitra.entity.Doctor;
import com.honap.madhumitra.entity.UserAccount;
import com.honap.madhumitra.entity.UserWeight;
import com.honap.madhumitra.model.MadhumitraModel;

import java.util.Date;

/**
 * Author: Chetan S.
 */
public class AddDoctor extends Activity {
    Doctor doctor = new Doctor();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_doctor);

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
            case R.id.saveMenuBtn : {
                EditText displayNameEditText = (EditText) findViewById(R.id.doctorName);
                if (!(displayNameEditText.getText().length() > 0)) {
                    Toast.makeText(this, "Please enter doctor's name", Toast.LENGTH_LONG).show();
                    return true;
                }
                doctor.setDisplayName(displayNameEditText.getText().toString());

                EditText emailEditText = (EditText) findViewById(R.id.doctorEmail);
                if (!(emailEditText.getText().length() > 0)) {
                    Toast.makeText(this, "Please enter doctor's e-mail", Toast.LENGTH_LONG).show();
                    return true;
                }
                doctor.setEmail(emailEditText.getText().toString());

                EditText mobEditText = (EditText) findViewById(R.id.doctorMobile);
                if (!(mobEditText.getText().length() > 0)) {
                    Toast.makeText(this, "Please enter doctor's mobile number", Toast.LENGTH_LONG).show();
                    return true;
                }
                doctor.setSecondaryContactNo(mobEditText.getText().toString());

                EditText phoneEditText = (EditText) findViewById(R.id.doctorPhone);
                if (!(phoneEditText.getText().length() > 0)) {
                    Toast.makeText(this, "Please enter doctor's phone number", Toast.LENGTH_LONG).show();
                    return true;
                }
                doctor.setPrimaryContactNo(phoneEditText.getText().toString());
                // save doctor record
                doctor.setUserAccount(MadhumitraModel.getInstance().getCurrentUserAccount());
                MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager(this).createDoctor(doctor);
                finish();
                break;
            }

            case R.id.cancelMenuBtn : {
                finish();
                break;
            }
        }
        item.setEnabled(true);
        return true;
    }
}