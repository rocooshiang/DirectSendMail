package geosat.mysys;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

/**
 * 以BroadcastReceiver接收SMS短信
 */
public class BroadCastTest2_SMS extends BroadcastReceiver {
    public static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("rocoo", "onReceive");
        // TODO Auto-generated method stub
        if (ACTION.equals(intent.getAction())) {

            SmsMessage[] msgs = getMessageFromIntent(intent);

            StringBuilder sBuilder = new StringBuilder();
            if (msgs != null && msgs.length > 0) {

                //擷取手機當前的時間
                Calendar today = Calendar.getInstance();
                int Y = today.get(Calendar.YEAR);
                int M = today.get(Calendar.MONTH);
                int D = today.get(Calendar.DAY_OF_MONTH);
                int h = today.get(Calendar.HOUR_OF_DAY);
                int m = today.get(Calendar.MINUTE);
                int s = today.get(Calendar.SECOND);
                String time = Y+"-"+M+"-"+D+" "+h+":"+m+":"+s;

                for (SmsMessage msg : msgs) {
                    sBuilder.append("寄件人：  ");
                    sBuilder.append(msg.getDisplayOriginatingAddress());
                    sBuilder.append("\n\n------訊息内容-------\n\n");
                    sBuilder.append(msg.getDisplayMessageBody());

                    Mydb db = new Mydb(context);
                    ContentValues values = new ContentValues();
                    values.put("Phone",msg.getDisplayOriginatingAddress());
                    values.put("Body",msg.getDisplayMessageBody());
                    values.put("Time",time);
                    db.InsertData(db,"SmsInfo",values);
                }
            }

        }

    }

    public static SmsMessage[] getMessageFromIntent(Intent intent) {
        SmsMessage retmeMessage[] = null;
        Bundle bundle = intent.getExtras();
        Object pdus[] = (Object[]) bundle.get("pdus");
        retmeMessage = new SmsMessage[pdus.length];
        for (int i = 0; i < pdus.length; i++) {
            byte[] bytedata = (byte[]) pdus[i];
            retmeMessage[i] = SmsMessage.createFromPdu(bytedata);
        }
        return retmeMessage;
    }
}
