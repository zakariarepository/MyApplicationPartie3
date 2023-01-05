package com.example.myapplicationpartie3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textViewBattery;
    TextView textViewReceiver;
    MyBroadcastBatteryLow myBroadcastBatteryLow = new MyBroadcastBatteryLow();
    MyBroadcastCallReceiver myBroadcastCallReceiver = new MyBroadcastCallReceiver();
    IntentFilter f1 = new IntentFilter();



    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void received(View view){

        Intent intent = new Intent();
        intent.setAction("FAKE_EVENT_INFO");

        sendBroadcast (intent);
    }//


    public class MyBroadcastBatteryLow extends MyReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            textViewBattery.setText("Evenement Batterie faible reçu");
        }
    }

    private class MyBroadcastCallReceiver extends MyReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_PHONE_STATE)) {
            if (intent.getAction().equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
                String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
                if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                    String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                    textViewReceiver.setText("Incoming call from " + incomingNumber);
                    Toast.makeText(context, "Incoming call from " + incomingNumber, Toast.LENGTH_SHORT).show();
                }
            }
//            } else {
//                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
//            }

        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myBroadcastBatteryLow, f1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myBroadcastBatteryLow);
        unregisterReceiver(myBroadcastCallReceiver);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[]
                                                   grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
                return;
            }
        }
    }

}