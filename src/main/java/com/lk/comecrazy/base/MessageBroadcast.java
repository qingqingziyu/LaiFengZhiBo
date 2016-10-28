package com.lk.comecrazy.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2016/10/21.
 *
 */
public class MessageBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if("msg".equals(action)){

        }
    }
}
