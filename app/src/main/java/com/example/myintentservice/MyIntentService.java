package com.example.myintentservice;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class MyIntentService extends IntentService {


    public MyIntentService() {
        super("MyIntentService");

        // TODO Complete any required constructor tasks.
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // TODO: Actions to perform when service is created.
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        // This handler occurs on a background thread.
        // TODO The time consuming task should be implemented here.
        // Each Intent supplied to this IntentService will be
        // processed consecutively here. When all incoming Intents
        // have been processed the Service will terminate itself.

        // get the message from passed Intent
        String message = intent.getStringExtra("message");
        intent.setAction(MainActivity.FILTER_ACTION_KEY);

        // introduce a delay to show that this is called with sequenced messages
        SystemClock.sleep(3000);

        String echoMessage = "IntentService onHandledIntent processed " + message;
        Log.i("myLog", echoMessage);

        //Broadcast the message for Main Activity
        LocalBroadcastManager. getInstance(getApplicationContext()).
                    sendBroadcast(intent.putExtra("broadcastMessage", echoMessage));
    }
}
