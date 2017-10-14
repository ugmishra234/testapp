package com.honap.madhumitra.cc;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.honap.madhumitra.R;

/**
 * Author: Chetan S.
 */
public class BloodSugar extends LinearLayout{

    public BloodSugar(Context context) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.cc_blood_sugar_inv, this);

    }

    public BloodSugar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.cc_blood_sugar_inv, this);
    }

    public BloodSugarInfo getBloodSugarInfo() {
        // populate info
        BloodSugarInfo bsi = new BloodSugarInfo();
        bsi.bsFasting = Integer.parseInt(((EditText)findViewById(R.id.bsFasting)).
                getText().toString());
        bsi.bsRandom = Integer.parseInt(((EditText)findViewById(R.id.bsRandom)).
                getText().toString());
        bsi.bsPp = Integer.parseInt(((EditText)findViewById(R.id.bsPP)).
                getText().toString());
        return bsi;
    }

    public class BloodSugarInfo {
        public int bsFasting;
        public int bsRandom;
        public int bsPp;

    }

}
