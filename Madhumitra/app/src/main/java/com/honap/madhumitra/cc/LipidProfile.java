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
public class LipidProfile extends LinearLayout {

    public LipidProfile(Context context) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.cc_lipid_profile_inv, this);

    }

    public LipidProfile(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.cc_lipid_profile_inv, this);
    }

    public LipidProfileInfo getLipidProfileInfo() {
        // populate info
        LipidProfileInfo lpi = new LipidProfileInfo();
        lpi.serumCholesterol = Integer.parseInt(((EditText) findViewById(R.id.serumCholesterolEditText)).
                getText().toString());
        lpi.serumTryglecerides = Integer.parseInt(((EditText) findViewById(R.id.serumTriglyceridesEditText)).
                getText().toString());
        lpi.serumHdlCholesterol = Integer.parseInt(((EditText) findViewById(R.id.serumHdlCholesterolEditText)).
                getText().toString());
        lpi.serumVldlCholesterol = Integer.parseInt(((EditText) findViewById(R.id.serumVldlCholesterolEditText)).
                getText().toString());
        lpi.serumHdlRation = Integer.parseInt(((EditText) findViewById(R.id.cholesterolHdlRationEditText)).
                getText().toString());
        lpi.serumLdlCholesterol = Integer.parseInt(((EditText) findViewById(R.id.serumLdlCholesterolEditText)).
                getText().toString());
        return lpi;
    }

    public class LipidProfileInfo {
        public int serumCholesterol;
        public int serumTryglecerides;
        public int serumHdlCholesterol;
        public int serumLdlCholesterol;
        public int serumVldlCholesterol;
        public int serumHdlRation;
    }

}
