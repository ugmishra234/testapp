package com.honap.madhumitra.misc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.honap.madhumitra.R;
import com.honap.madhumitra.entity.ExerciseRecord;
import com.honap.madhumitra.entity.IRecord;
import com.honap.madhumitra.entity.MealItemRecord;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nandu
 * Date: 8/21/11
 * Time: 1:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class RecordAdapter extends ArrayAdapter<Object> {
    private List<Object> items;

//    private Context context;

    public RecordAdapter(Context context, int textViewResourceId, List<Object> objects) {
        super(context, textViewResourceId, objects);
        this.items = objects;
//        this.context = context;
    }

    public RecordAdapter(Context context, List<Object> objects) {
        super(context, 0, objects);
        this.items = objects;
//        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.cc_record_list_item, null);
        }
        Object o = items.get(position);
        if (o != null) {
            TextView tt = (TextView) v.findViewById(R.id.topreportlisttext);
            TextView tr = (TextView) v.findViewById(R.id.toprightreportlisttext);
            TextView bt = (TextView) v.findViewById(R.id.bottomreportlisttext);

            //if(o.getClass().getName() == "MealItemRecord")
            if (o instanceof IRecord) {
                IRecord record = (IRecord) o;
                if (tt != null) {
                    tt.setText(record.getRecordSummary().getTitle());
                }

                if (tr != null) {
                    tr.setText(record.getRecordSummary().getValue());
                }
                if (bt != null) {
                    bt.setText(record.getRecordSummary().getOtherDetails());
                }
            }
        }
        return v;
    }
}
