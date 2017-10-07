package com.honap.madhumitra.misc;

import android.content.Context;
import com.honap.madhumitra.R;
import com.honap.madhumitra.act.ExpenseReport;
import com.honap.madhumitra.entity.DoctorVisit;
import com.honap.madhumitra.entity.HomeInvestigationRecord;
import com.honap.madhumitra.entity.LabInvestigationRecord;
import com.honap.madhumitra.model.MadhumitraModel;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Author: Chetan S.
 */
public class MadhumitraReportDBAdapter {

//    private Context context = null;
//
//
//    public MadhumitraReportDBAdapter(Context context) {
//        this.context = context;
//    }
//
//    private List<HomeInvestigationRecord> getHomeInvestigation() {
//        //gets the lists from the DB
//
//
//        return null;
//
//    }
//
//    private List<String> getMedication() {
//        //gets the medication list from the DB
//    }
//
//    public List<String> getBSL(Date from, Date to) {
//        //gets the list from the DB, segregates it for BSL and returns the list for the date
//        Iterator<HomeInvestigationRecord> homeInvRecordIterator = MadhumitraModel.getInstance().getCurrentUserAccount().
//                getHomeInvestigations().iterator();
//        do {
//            if (homeInvRecordIterator.next().getInvestigation().equalsIgnoreCase(context.getResources().getString(R.string.bsl_id))) {
//                if((homeInvRecordIterator.next().getInvestigatedOn().after(from) && (homeInvRecordIterator.next().getInvestigatedOn().before(to))))
//                {
//                    //UI populate from here
//                }
//            }
//        } while (homeInvRecordIterator.hasNext());
//    }
//   //from and to bug is it doesnt consider that day
//    public List<String> getExpenses(Date from, Date to) {
//        //gets the expense list from the DB, returns the list for the date
//        Iterator<LabInvestigationRecord> labInvestigationRecordIterator = MadhumitraModel.getInstance().getCurrentUserAccount().getLabInvestigations().iterator();
//        Iterator<DoctorVisit> doctorVisitIterator = MadhumitraModel.getInstance().getCurrentUserAccount().getDoctorVisits().iterator();
//        do{
//            if((labInvestigationRecordIterator.next().getInvestigatedOn().after(from)) &&  (labInvestigationRecordIterator.next().getInvestigatedOn().before(to)))
//            {
//                labInvestigationRecordIterator.next().getCharges();//keep adding this
//            }
//            if(doctorVisitIterator.next().getVisitedOn().after(from) && doctorVisitIterator.next().getVisitedOn().before(to))
//            {
//                doctorVisitIterator.next().getFees(); //keep adding this
//            }
//        }while (labInvestigationRecordIterator.hasNext() && doctorVisitIterator.hasNext());
//    }
//
//    public List<String> getCalorie(Date from, Date to) {
//        //gets the calorie list from the DB, and returns the list for the date
//    }
}
