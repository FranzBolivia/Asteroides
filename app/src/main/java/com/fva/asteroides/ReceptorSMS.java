package com.fva.asteroides;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by DTIC-Dir on 24/11/2017.
 */

public class ReceptorSMS extends BroadcastReceiver {
    @Override public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, acercaDeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
