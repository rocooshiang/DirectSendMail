package geosat.mysys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ShutdownReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SHUTDOWN)) {
                        //啟動Service
            Intent it = new Intent(context, SmsService.class);
            it.putExtra("type","ShutdownReceiver");
            context.startService(it);       // startService
            //context.stopService(it);        // stopService
            Log.i("rocoo","ShutdownReceiver");
        }
    }
}
