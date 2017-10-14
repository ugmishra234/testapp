package com.honap.madhumitra.act;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.honap.madhumitra.R;
import com.honap.madhumitra.data.DbHelper;
import com.honap.madhumitra.entity.*;
import com.honap.madhumitra.misc.CalorieAdapter;
import com.honap.madhumitra.misc.UserAccountAdapter;
import com.honap.madhumitra.model.MadhumitraModel;
//import com.honap.madhumitra.service.MedicationNotificationService;
import com.honap.madhumitra.utils.Utils;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import java.util.*;
import java.util.concurrent.Exchanger;

/**
 * Author: Chetan S.
 */
public class CalorieReport  extends OrmLiteBaseActivity<DbHelper> {

    //private List<MealItemRecord> mealItemRecords = null;
    //this list shall contain the exercise and the mealItem lists
    private List<Object> calorificObjects = new ArrayList<Object>();
    boolean  firsttime = true;
    int iTotalCalorieConsumption;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set content view
        setContentView(R.layout.report_display);
        // set event listener
        setEventListeners();

//        stopService(new Intent(this, MedicationNotificationService.class));
    }

    private void setEventListeners() {
        Spinner durationType = (Spinner) findViewById(R.id.reportDurationSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
            this, R.array.duration_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        durationType.setAdapter(adapter);
        Resources res = getResources();
        String[] dur = res.getStringArray(R.array.duration_array);
        // bind the array with the spinner
        durationType.setPrompt("Select Duration");
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,dur);
        durationType.setAdapter(stringArrayAdapter);

        durationType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                calorificObjects.clear();
                iTotalCalorieConsumption = 0;
                Iterator<MealRecord> mealRecordIterator = MadhumitraModel.getInstance().getCurrentUserAccount().getMealRecords().iterator();
                Iterator<ExerciseRecord> exerciseRecordIterator = MadhumitraModel.getInstance().getCurrentUserAccount().getExerciseRecords().iterator();
                Iterator<ActivityRecord> activityRecordIterator = MadhumitraModel.getInstance().getCurrentUserAccount().getActivityRecords().iterator();
                //if(!firsttime)
                {
                    Date date = new Date();
                    /*Calendar c = Calendar.getInstance();
                    date.setDate(c.get(Calendar.DATE));
                    date.setMonth(c.get(Calendar.MONTH));
                    date.setYear(c.get(Calendar.YEAR));
                    */
                    //To change body of implemented methods use File | Settings | File Templates.
                    while(activityRecordIterator.hasNext())
                    {
                        ActivityRecord actrecord = activityRecordIterator.next();
                        if(i == 0)
                        {
                            if (Utils.compareDates(actrecord.getStartTime(),date))//today
                            {
                                calorificObjects.add(actrecord);
                                iTotalCalorieConsumption -= actrecord.getCalorieConsumption();
                            }
                        }
                        else if (i == 1)
                        {
                            if(Utils.compareWeek(actrecord.getStartTime(),date))
                            {
                                calorificObjects.add(actrecord);
                                iTotalCalorieConsumption -= actrecord.getCalorieConsumption();
                            }
                        }
                        else if(i == 2)
                        {
                            if(Utils.compareMonth(actrecord.getStartTime(),date))
                            {
                                calorificObjects.add(actrecord);
                                iTotalCalorieConsumption -= actrecord.getCalorieConsumption();
                            }
                        }
                    }



                    while (mealRecordIterator.hasNext()) {
                        MealRecord meal = mealRecordIterator.next();
                        Iterator<MealItemRecord> mealItemRecordIterator =  meal.getItemRecords().iterator();
                        while(mealItemRecordIterator.hasNext()) {
                            MealItemRecord mealItem = mealItemRecordIterator.next();
                            if (i == 0)//Today
                            {
                                    if(Utils.compareDates(meal.getTime(),date))//today
                                    {
                                        calorificObjects.add(mealItem);
                                        iTotalCalorieConsumption += mealItem.getCalorieIntake();
                                    }
                            }
                            else if (i == 1)//Week
                            {
                                 if(Utils.compareWeek(meal.getTime(),date))//today
                                 {
                                    calorificObjects.add(mealItem);
                                    iTotalCalorieConsumption += mealItem.getCalorieIntake();


                                 }
                            }
                            else if (i == 2)//Month
                            {
                                if(Utils.compareMonth(meal.getTime(),date))//today
                                {
                                    calorificObjects.add(mealItem);
                                    iTotalCalorieConsumption += mealItem.getCalorieIntake();
                                }

                            }
                        }
                    }

                    while (exerciseRecordIterator.hasNext()) {
                        ExerciseRecord exercise = exerciseRecordIterator.next();
                        if(i == 0)
                        {
                            if (Utils.compareDates(exercise.getStartTime(),date))//today
                            {
                                calorificObjects.add(exercise);
                                iTotalCalorieConsumption -= exercise.getCalorieConsumption();
                            }
                        }
                        else if (i == 1)
                        {
                            if(Utils.compareWeek(exercise.getStartTime(),date))
                            {
                                calorificObjects.add(exercise);
                                iTotalCalorieConsumption -= exercise.getCalorieConsumption();
                            }
                        }
                        else if(i == 2)
                        {
                            if(Utils.compareMonth(exercise.getStartTime(),date))
                            {
                                calorificObjects.add(exercise);
                                iTotalCalorieConsumption -= exercise.getCalorieConsumption();
                            }
                        }
                    }
                    ExerciseRecord total = new ExerciseRecord();
                    total.setComment("Total Calorie:");
                    total.setCalorieConsumption(iTotalCalorieConsumption);
                    Toast.makeText(view.getContext(),new Integer(iTotalCalorieConsumption).toString(),Toast.LENGTH_LONG);
                    calorificObjects.add(total);
                    CalorieAdapter calorieAdapter= new CalorieAdapter(CalorieReport.this,
                            android.R.layout.activity_list_item,calorificObjects);
                    final ListView listView = (ListView) findViewById(R.id.reportList);
                    listView.setAdapter(calorieAdapter);


                }//if(!firsttime)
                //else
                  //  firsttime = false;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }

    private void populateCalorieReport() {
        // get controls

    }
}