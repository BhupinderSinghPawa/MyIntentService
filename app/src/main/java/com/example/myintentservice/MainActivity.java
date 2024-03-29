package com.example.myintentservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button button;
    EditText editText;
    MyReceiver myReceiver;

    public static final String FILTER_ACTION_KEY = "any_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        editText = findViewById(R.id.inputText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Explicit start of Intent Service, with message passed
                String message = editText.getText().toString();
                Intent intent = new Intent(MainActivity.this, MyIntentService.class);
                intent.putExtra("message", message);
                startService(intent);

                Log.i("myLog", "Intent Service started with message " + message);
            }
        });
    }

    // extend Broadcast Receiver and override onReceive
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("broadcastMessage");
            // keep appending to the text
            textView.setText(textView.getText() + "\n" + message);
        }
    }

    // register the Receiver against the Intent of message broadcasted from Intent Service
    private void setReceiver() {
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(FILTER_ACTION_KEY);

        // use Local Broadcast Manager to register
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver, intentFilter);
        Log.i("myLog", "setReceiver() " + myReceiver + " " + intentFilter);
    }

    @Override
    protected void onStart() {
        // register the receiver
        setReceiver();
        super.onStart();
    }

    @Override
    protected void onStop() {
        // unregister the receiver
        unregisterReceiver(myReceiver); // receiver not registered?
        super.onStop();
    }

}
