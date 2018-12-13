package com.example.android.bluetoothlegatt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Class to restart the service when the application is closed.
 */

public class RestartBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(RestartBroadcast.class.getSimpleName(), "Service Stops!");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        context.startService(new Intent(context, BluetoothLeService.class));
    }
}
