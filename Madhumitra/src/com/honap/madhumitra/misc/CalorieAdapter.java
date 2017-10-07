package com.honap.madhumitra.misc;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.honap.madhumitra.R;
import com.honap.madhumitra.entity.ActivityRecord;
import com.honap.madhumitra.entity.ExerciseRecord;
import com.honap.madhumitra.entity.MealItemRecord;

import java.io.Serializable;
import java.nio.Buffer;
import java.text.DateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: Nandu
 * Date: 8/21/11
 * Time: 1:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class CalorieAdapter extends ArrayAdapter<Object> {
    private List<ExerciseRecord> items;
    private List<MealItemRecord> mealItemRecords;
    private List<Object> calorieItems;

    public CalorieAdapter(Context context, int textViewResourceId, List<Object> objects) {
        super(context, textViewResourceId, objects);    //To change body of overridden methods use File | Settings | File Templates.
        this.calorieItems= objects;
    }   int iTotalCalorieConsumed = 0;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.cc_report_list_item, null);
        }
        Object o = calorieItems.get(position);
        TextView tt = (TextView) v.findViewById(R.id.topreportlisttext);
        TextView tr = (TextView) v.findViewById(R.id.toprightreportlisttext);
        TextView bt = (TextView) v.findViewById(R.id.bottomreportlisttext);
        if (o != null) {
            //if(o.getClass().getName() == "MealItemRecord")
            if(o instanceof MealItemRecord)
            {
                MealItemRecord mealItem = (MealItemRecord)o;
                if (tt != null)
                {
                    tt.setText(mealItem.getMealItem());
                }

                if(tr != null)
                {
                    tr.setTextColor(Color.RED);
                    tr.setText( new Integer(mealItem.getCalorieIntake()).toString());
                }
                if(bt != null)
                {
                     //bt.setText(mealItem.getMealRecord().getTime().toString().);
                    char[] buff = null;
                    buff = new char[20];
                    mealItem.getMealRecord().getTime().toString().getChars(0,11, buff,0);
                    bt.setText(buff,0,11);
                    //DateFormat ft = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.SHORT, Locale.US).format(mealItem.getMealRecord().getTime());
                    //DateFormat.getDateTimeInstance(
                }
            }
            //else if(o.getClass().getName() == "ExerciseRecord")
            else if(o instanceof ExerciseRecord)
            {
                ExerciseRecord exerciseRecord = (ExerciseRecord)o;
                if(exerciseRecord.getComment() == "Total Calorie:")
                {
                     if (tt != null)
                    {
                        tt.setText("Total Calorie:");
                    }

                    if(tr != null)
                    {

                        if(exerciseRecord.getCalorieConsumption() < 0)
                        {
                            tr.setTextColor(Color.GREEN);
                            tr.setText(new Integer(exerciseRecord.getCalorieConsumption()).toString());
                        }
                        else
                        {
                            tr.setTextColor(Color.RED);
                            tr.setText("+" + new Integer(exerciseRecord.getCalorieConsumption()).toString());
                        }

                    }
                    if(bt != null)
                    {
                          //bt.setText(exerciseRecord.getStartTime().toString());
                        char[] buff = null;
                        buff = new char[20];
                        exerciseRecord.getStartTime().toString().getChars(0,11, buff,0);
                        bt.setText(buff,0,11);
                    }
                }
                else
                {
                    if (tt != null)
                    {
                        tt.setText(exerciseRecord.getExercise());
                    }

                    if(tr != null)
                    {
                        tr.setTextColor(Color.GREEN);
                        tr.setText(new Integer(exerciseRecord.getCalorieConsumption()).toString());
                    }
                    if(bt != null)
                    {
                        //bt.setText(exerciseRecord.getStartTime().toString());
                        char[] buff = null;
                        buff = new char[20];
                        exerciseRecord.getStartTime().toString().getChars(0,11, buff,0);
                        bt.setText(buff,0,11);
                    }
                }
            }
            else if(o instanceof ActivityRecord)
            {
                ActivityRecord actRecord = (ActivityRecord)o;
                if (tt != null)
                {
                    tt.setText(actRecord.getActivity());
                }

                if(tr != null)
                {

                    tr.setTextColor(Color.GREEN);
                    tr.setText(new Integer(actRecord.getCalorieConsumption()).toString());

                }
                if(bt != null)
                {
                    //bt.setText(actRecord.getStartTime().toString());
                    char[] buff = null;
                    buff = new char[20];
                    actRecord.getStartTime().toString().getChars(0,11, buff,0);
                    bt.setText(buff,0,11);
                }

            }
        }
        return v;
    }
}
