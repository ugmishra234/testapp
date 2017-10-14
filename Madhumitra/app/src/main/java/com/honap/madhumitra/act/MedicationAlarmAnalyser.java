package com.honap.madhumitra.act;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.honap.madhumitra.R;
import com.honap.madhumitra.data.DbHelper;
import com.honap.madhumitra.data.MadhumitraDataManagerFactory;
import com.honap.madhumitra.entity.MedicationNotification;
import com.honap.madhumitra.entity.MedicationRecord;
import com.honap.madhumitra.utils.Utils;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Chetan
 * Date: 12/29/11
 * Time: 12:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class MedicationAlarmAnalyser extends BroadcastReceiver {
    private final List<MedicationRecord> notificationQueue = new ArrayList<MedicationRecord>();
    private DbHelper dbHelper = null;
    private static final String TAG = "MedicationAlarmAnalyser";



    private void shootNotifications(Context cntx) {
        Iterator<MedicationRecord> iterator = notificationQueue.iterator();
        while (iterator.hasNext()) {
            MedicationRecord record = iterator.next();
            CharSequence message = "Medication Alert for " + record.getUserAccount().getDisplayName() +
                    "- Drug: " + record.getDrug() + ", Dose: " + record.getDose() + ", Time: " +
                    new SimpleDateFormat("h:mm a").format(record.getTime());
            NotificationManager nm = (NotificationManager) cntx.getSystemService(Context.NOTIFICATION_SERVICE);
            CharSequence from = "Madhumitra";
            Intent intent = new Intent(cntx, MedicationNotificationAck.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("recordId", record.getId().longValue());
            intent.putExtra("msg", message.toString());
            int id = new Random().nextInt();
            intent.putExtra("notificationId", id);
            PendingIntent contentIntent = PendingIntent.getActivity(cntx, 0,
                    intent, PendingIntent.FLAG_CANCEL_CURRENT);

            Notification notif = new Notification(R.drawable.notification,
                    "Medication Alert", System.currentTimeMillis());

//            notif.setLatestEventInfo(cntx, from, message, contentIntent);
            notif.defaults |= Notification.DEFAULT_SOUND;
            notif.defaults |= Notification.DEFAULT_VIBRATE;
            notif.defaults |= Notification.DEFAULT_LIGHTS;
            nm.notify(id, notif);
            // removal of the record from queue
            record.setDoseStatus(cntx.getResources().getString(R.string.med_dose_notified));
        }
        notificationQueue.clear();


    }

    public DbHelper getDbHelper(Context context) {
        if (dbHelper == null) {
            dbHelper = (DbHelper)OpenHelperManager.getHelper(context, DbHelper.class);
        }
        return dbHelper;
    }


    public void onReceive(Context context, Intent intent) {
//        DbHelper dbHelper = new DbHelper(cntx);
        List<MedicationNotification> notifications = getNotifications(context);
        List<MedicationNotification> toRemove = new ArrayList<MedicationNotification>();
        Iterator<MedicationNotification> iterator = notifications.iterator();
        while (iterator.hasNext()) {
            MedicationNotification notification = iterator.next();
            if (Utils.inFiveMin(notification.getTime())) {
                notificationQueue.add(getMedicationRecordUsingNotification(notification,context));
                // mark for removal
                toRemove.add(notification);
            }
            if (Utils.inPriorElevenMin(notification.getTime())) {
                notificationQueue.add(getMedicationRecordUsingNotification(notification,context));
                // mark for removal
                toRemove.add(notification);
            }
        }

        Iterator<MedicationNotification> recordIterator = toRemove.iterator();
        while (recordIterator.hasNext()) {
            deleteMedicationNotification(recordIterator.next(),context);
        }
        shootNotifications(context);
    }



    private List<MedicationNotification> getNotifications(Context context) {
        try {
            List<MedicationNotification> retList = this.getDbHelper(context).getNotificationtDao().queryForAll();
            return retList;
        } catch (Exception e) {
            Log.e(TAG, "Error while retrieving all notification records", e);
            throw new RuntimeException(e);
        }

    }

    private void deleteMedicationNotification(MedicationNotification notification, Context context) {
        try {
            this.getDbHelper(context).getNotificationtDao().delete(notification);
        } catch (Exception e) {
            Log.e(TAG, "Error while delete notification record", e);
            throw new RuntimeException(e);
        }
    }




    private MedicationRecord getMedicationRecordUsingNotification(MedicationNotification notification,
                                                                  Context cntx) {
        return MadhumitraDataManagerFactory.
                getDefaultMadhumitraDataManager(cntx).getMedicationRecord(notification.getMedRecordId());
    }


}
