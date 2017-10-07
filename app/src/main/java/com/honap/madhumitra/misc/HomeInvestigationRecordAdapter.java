package com.honap.madhumitra.misc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.honap.madhumitra.R;
import com.honap.madhumitra.entity.HomeInvestigationParamValue;
import com.honap.madhumitra.entity.HomeInvestigationRecord;
import com.honap.madhumitra.entity.UserAccount;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nandu
 * Date: 8/23/11
 * Time: 10:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class HomeInvestigationRecordAdapter extends ArrayAdapter<HomeInvestigationRecord> {
    private List<HomeInvestigationRecord> items;
//    private Context context;

    public HomeInvestigationRecordAdapter(Context context, int textViewResourceId, List<HomeInvestigationRecord> objects) {
        super(context, textViewResourceId, objects);    //To change body of overridden methods use File | Settings | File Templates.
        this.items = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.cc_report_list_item, null);
        }
        HomeInvestigationRecord o = items.get(position);
        if (o != null) {
            TextView tt = (TextView) v.findViewById(R.id.topreportlisttext);
            TextView rt = (TextView) v.findViewById(R.id.toprightreportlisttext);
            TextView bt = (TextView) v.findViewById(R.id.bottomreportlisttext);

                        if (tt != null) {
                              tt.setText(o.getInvestigation());                            }
                        if(bt != null){
                            HomeInvestigationParamValue temp= o.getInvestigationValues().iterator().next();
                              rt.setText(temp.getValue());
                        }
                        if(bt != null){
                              bt.setText(o.getInvestigatedOn().toLocaleString());
                        }        }
            return v;
    }
}
