package geosat.mysys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            //啟動Service
            Intent it = new Intent(context, SmsService.class);
            intent.putExtra("type","BootCompletedReceiver");
            context.startService(it);       // startService
            //context.stopService(it);        // stopService
            Log.i("rocoo", "BootCompletedReceiver");
        }
    }
}
