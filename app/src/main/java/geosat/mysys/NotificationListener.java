package geosat.mysys;

import android.app.Notification;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.annotation.IntDef;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.Calendar;


/**
 * Created by mukesh on 19/5/15.
 */
public class NotificationListener extends NotificationListenerService {
    Context context;

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        Log.i("rocoo", "on listenerConnected");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Log.i("rocoo", "notification service onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("rocoo", "on Start command");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Log.i("rocoo", "notification posted");
        String pack = sbn.getPackageName();
        String ticker = "";
        if (sbn.getNotification().tickerText != null) {
            ticker = sbn.getNotification().tickerText.toString();
        }
        Bundle extras = sbn.getNotification().extras;
        String title = extras.getString("android.title");
        String text = extras.getCharSequence("android.text").toString();
        int id1 = extras.getInt(Notification.EXTRA_SMALL_ICON);
        //Bitmap id = sbn.getNotification().largeIcon;
//        Log.i("Package", pack);
//        Log.i("Ticker", ticker);
        Log.i("Title", title);
        Log.i("Text", text);


        if (!text.contains("還剩") && !text.contains("正在更新當地") && !text.contains("輕觸此處以登入") && !text.contains("三星鍵盤")) {

            //擷取手機當前的時間
            Calendar today = Calendar.getInstance();
            int Y = today.get(Calendar.YEAR);
            int M = today.get(Calendar.MONTH);
            int D = today.get(Calendar.DAY_OF_MONTH);
            int h = today.get(Calendar.HOUR_OF_DAY);
            int m = today.get(Calendar.MINUTE);
            int s = today.get(Calendar.SECOND);
            String time = Y + "-" + (M + 1) + "-" + D + " " + h + ":" + m + ":" + s;

            Mydb db = new Mydb(context);
            ContentValues values = new ContentValues();
            values.put("FromWho", title);
            values.put("Body", text);
            values.put("Time", time);
            db.InsertData(db, "SmsInfo", values);
        }

    }


    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("Msg", "Notification Removed");
    }

}
