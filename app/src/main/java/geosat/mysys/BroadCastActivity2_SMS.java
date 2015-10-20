package geosat.mysys;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.SmsManager;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.util.Calendar;

public class BroadCastActivity2_SMS extends Activity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode
                .setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                        .detectDiskReads()
                        .detectDiskWrites()
                        .detectNetwork()   // or .detectAll() for all detectable problems
                        .penaltyLog()
                        .build());

        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bc2_sms);

        Intent it = new Intent(this, SmsService.class);
        it.putExtra("type", "Activity");
        startService(it);       // startService
        //context.stopService(it);        // stopService



        WebViewClient mWebViewClient = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        };
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(mWebViewClient);
        webView.loadUrl("http://tw.yahoo.com");

        Intent intent = getIntent();
        if (intent != null) {
            String address = intent.getStringExtra("sms_address");

            if (address != null) {

                //textView.append("\n\n發件人：\n" + address);
                String bodyString = intent.getStringExtra("sms_body");
                if (bodyString != null) {
                    //textView.append("\n短信内容：\n" + bodyString);
                }
            }
        }

    }


    public void deleteSMS(Context context, String message, String number) {
        try {
            Log.d("rocoo", "delete sms");
            Uri uriSms = Uri.parse("content://sms/inbox");
            Cursor c = context.getContentResolver().query(uriSms,
                    new String[]{"_id", "thread_id", "address",
                            "person", "date", "body"}, null, null, null);

            if (c != null && c.moveToFirst()) {
                Log.d("rocoo", "delete sms2");
                do {
                    long id = c.getLong(0);
                    long threadId = c.getLong(1);
                    String address = c.getString(2);
                    String body = c.getString(5);

                    if (message.equals(body) && address.equals(number)) {
                        Log.d("rocoo", "delete sms3");
                        context.getContentResolver().delete(
                                Uri.parse("content://sms/" + id), null, null);
                    }
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Log.d("rocoo", "delete sms exception");
        }
    }
}
