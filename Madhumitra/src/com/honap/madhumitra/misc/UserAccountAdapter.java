package com.honap.madhumitra.misc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.honap.madhumitra.R;
import com.honap.madhumitra.entity.UserAccount;

import java.util.List;

/**
 * Author: Chetan S.
 */
public class UserAccountAdapter extends ArrayAdapter<UserAccount> {

    private List<UserAccount> items;
//    private Context context;


    public UserAccountAdapter(Context context, int textViewResourceId, List<UserAccount> objects) {
        super(context, textViewResourceId, objects);    //To change body of overridden methods use File | Settings | File Templates.
        this.items = objects;
//        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.cc_acc_list_item, null);
        }
        UserAccount o = items.get(position);
        if (o != null) {
            TextView tt = (TextView) v.findViewById(R.id.toptext);
                        TextView bt = (TextView) v.findViewById(R.id.bottomtext);
                        if (tt != null) {
                              tt.setText(o.getDisplayName());                            }
                        if(bt != null){
                              bt.setText(o.getRelationWithPrimaryUser() + ", " + o.getAge());
                        }        }
            return v;
    }
}
