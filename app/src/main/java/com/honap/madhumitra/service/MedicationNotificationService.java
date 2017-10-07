package com.honap.madhumitra.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.honap.madhumitra.R;
import com.honap.madhumitra.act.MedicationNotificationAck;
import com.honap.madhumitra.data.DbHelper;
import com.honap.madhumitra.data.MadhumitraDataManagerFactory;
import com.honap.madhumitra.entity.MedicationNotification;
import com.honap.madhumitra.entity.MedicationRecord;
import com.honap.madhumitra.utils.Utils;
import com.j256.ormlite.android.apptools.OrmLiteBaseService;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author: Chetan S.
 */
public class MedicationNotificationService extends OrmLiteBaseService<DbHelper> {
    private long period = 1000 * 60;
    private Timer timer = null;
    private Context cntx = null;
    private final IBinder binder = new MedicationNotificationServiceBinder();
    private final List<MedicationRecord> notificationQueue = new ArrayList<MedicationRecord>();
    private boolean timerRunning = false;

    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();    //To change body of overridden methods use File | Settings | File Templates.
        timer = new Timer();
        cntx = getApplicationContext();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!timerRunning) {
            timer.scheduleAtFixedRate(new MedicationNotificationTask(), 0, period);
            timerRunning = true;
            Log.i("MedicationNotificationService", "Started polling timer");

        }
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        timer.cancel();
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public void onMedicationRecordAddition(MedicationRecord medicationRecord) {
//        synchronized (medicationRecordListSync) {
//            if (Utils.inPriorFiveMin(medicationRecord.getTime())) {
//                medicationRecordList.add(medicationRecord);
//            }
//            if (isRecordAdmissible(medicationRecord)) {
//                medicationRecordList.add(medicationRecord);
//            }
//            Collections.sort(medicationRecordList);
//        }
    }


    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Iterator<MedicationRecord> iterator = notificationQueue.iterator();
            while (iterator.hasNext()) {
                MedicationRecord record = iterator.next();
                CharSequence message = "Medication Alert for " + record.getUserAccount().getDisplayName() +
                        "- Drug: " + record.getDrug() + ", Dose: " + record.getDose() + ", Time: " +
                        new SimpleDateFormat("h:mm a").format(record.getTime());
                NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
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

//                notif.setLatestEventInfo(cntx, from, message, contentIntent);
                notif.defaults |= Notification.DEFAULT_SOUND;
                notif.defaults |= Notification.DEFAULT_VIBRATE;
                notif.defaults |= Notification.DEFAULT_LIGHTS;
                nm.notify(id, notif);
                // removal of the record from queue
                record.setDoseStatus(getResources().getString(R.string.med_dose_notified));
            }
            notificationQueue.clear();
        }
    };


    public class MedicationNotificationServiceBinder extends Binder {
        public MedicationNotificationService getService() {
            return MedicationNotificationService.this;
        }
    }


    class MedicationNotificationTask extends TimerTask {
        MedicationNotificationTask() {
        }

        @Override
        public void run() {
            List<MedicationNotification> notifications =
                    MadhumitraDataManagerFactory.
                            getDefaultMadhumitraDataManager(cntx).getAllNotifications();
            List<MedicationNotification> toRemove = new ArrayList<MedicationNotification>();
            Iterator<MedicationNotification> iterator = notifications.iterator();
            while (iterator.hasNext()) {
                MedicationNotification notification = iterator.next();
                if (Utils.inFiveMin(notification.getTime())) {
                    notificationQueue.add(getMedicationRecordUsingNotification(notification));
                    // mark for removal
                    toRemove.add(notification);
                }
                if (Utils.inPriorElevenMin(notification.getTime())) {
                    notificationQueue.add(getMedicationRecordUsingNotification(notification));
                    // mark for removal
                    toRemove.add(notification);
                }
            }

            Iterator<MedicationNotification> recordIterator = toRemove.iterator();
            while (recordIterator.hasNext()) {
                MadhumitraDataManagerFactory.getDefaultMadhumitraDataManager(cntx).
                        deleteMedicationNotification(recordIterator.next());
            }
            handler.sendEmptyMessage(0);
        }

        private MedicationRecord getMedicationRecordUsingNotification(MedicationNotification notification) {
            return MadhumitraDataManagerFactory.
                    getDefaultMadhumitraDataManager(cntx).getMedicationRecord(notification.getMedRecordId());
        }

        @Override
        public boolean cancel() {

            return super.cancel();    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public long scheduledExecutionTime() {
            return super.scheduledExecutionTime();    //To change body of overridden methods use File | Settings | File Templates.
        }
    }


}
