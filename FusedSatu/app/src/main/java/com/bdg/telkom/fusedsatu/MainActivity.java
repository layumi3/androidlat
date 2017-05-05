package com.bdg.telkom.fusedsatu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.request_location).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        startService(new Intent(this, FusedLocationService.class));
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Location location = null;
                String time = "";
                if (intent.getExtras() != null)
                {
                    location = (Location) intent.getExtras().get("LOCATION");
                    time = intent.getExtras().getString("TIME");
                }


                if (location == null)
                    ((TextView) findViewById(R.id.location)).setText("null location received");
                else
                    ((TextView) findViewById(R.id.location)).setText("Fused Location Received =lat :" + location.getLatitude() + ",lng :" + location.getLongitude()+" in "+time);

                findViewById(R.id.request_location).setEnabled(true);
            }
        }, new IntentFilter(FusedLocationService.LOCATION_RECEIVED));
        findViewById(R.id.request_location).setEnabled(false);
    }
}
