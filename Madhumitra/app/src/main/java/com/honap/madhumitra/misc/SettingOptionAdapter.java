package com.honap.madhumitra.misc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.honap.madhumitra.R;
import com.honap.madhumitra.entity.UserAccount;

import java.util.List;

/**
 * Created by umashankar on 22/11/17.
 */

public class SettingOptionAdapter extends ArrayAdapter<String> {
    List<String> objects;

    public SettingOptionAdapter(@NonNull Context context, int resource, List<String> objects) {
        super(context, resource);
        this.objects = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.cc_setting_list, null);
        }
        String o = objects.get(position);
        if (o != null) {
            TextView tt = (TextView) v.findViewById(R.id.textView);
            if (tt != null) {
                tt.setText(o);
            }
        }
        return v;
    }
}
