package com.honap.madhumitra.fragment;


import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.honap.madhumitra.R;
import com.honap.madhumitra.entity.Preferences;
import com.honap.madhumitra.model.MadhumitraModel;
import com.honap.madhumitra.utils.Utils;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        View promptsView = inflater.inflate(R.layout.prompt_view, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        // create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();


        final EditText userInput = promptsView
                .findViewById(R.id.editTextDialogUserInput);
        final TextView cancelBtn = promptsView.findViewById(R.id.cancel_action);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
        final TextView okBtn = promptsView.findViewById(R.id.ok_action);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });

        TextView txtMorning = view.findViewById(R.id.morning);
        txtMorning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(alertDialog);
            }
        });
        TextView txtAfternoon = view.findViewById(R.id.afternoon);
        txtAfternoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(alertDialog);
            }
        });
        TextView txtEvening = view.findViewById(R.id.evening);
        txtEvening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(alertDialog);
            }
        });
        TextView txtNight = view.findViewById(R.id.night);
        txtNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(alertDialog);
            }
        });

        Button btnSave = view.findViewById(R.id.save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(
                        getResources().getString(R.string.preferences_id), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.commit();
                populatePreferences();
            }
        });

        return view;
    }

    private void showDialog(AlertDialog alertDialog) {
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, 500);
    }

    private void populatePreferences() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(
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
