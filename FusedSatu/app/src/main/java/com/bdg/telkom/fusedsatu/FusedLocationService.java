package com.bdg.telkom.fusedsatu;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by lacorp on 7/16/2016.
 */

public class FusedLocationService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(5000)
            .setFastestInterval(3000)
            .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

    public static final String LOCATION_RECEIVED = "fused.location.received";
    private Long now;


    private final Object locking = new Object();
    private Runnable onFusedLocationProviderTimeout;
    private Handler handler = new Handler();
    private static final String TAG = "SitePoint";

    private DateFormat mLastUpdateTime;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        now = Long.valueOf(System.currentTimeMillis());
        return START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("FusedLocationService", "Fused Location Provider got connected successfully");
//        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        onFusedLocationProviderTimeout = new Runnable() {
            public void run() {
                Log.d("FusedLocationService", "location Timeout");

//                Location lastbestStaleLocation = getLastBestStaleLocation();
//                sendLocationUsingBroadCast(lastbestStaleLocation);
//
//                if (lastbestStaleLocation != null)
//                    Log.d("FusedLocationService", "Last best location returned [" + lastbestStaleLocation.getLatitude() + "," + lastbestStaleLocation.getLongitude() + "] in " + (Long.valueOf(System.currentTimeMillis()) - now) + " ms");

                if (mGoogleApiClient.isConnected())
                    mGoogleApiClient.disconnect();
            }
        };
        handler.postDelayed(onFusedLocationProviderTimeout, 20000);//20 sec
    }

    private void sendLocationUsingBroadCast(Location location) {
        Intent locationBroadcast = new Intent(FusedLocationService.LOCATION_RECEIVED);
        locationBroadcast.putExtra("LOCATION", location);
        locationBroadcast.putExtra("TIME", Long.valueOf(System.currentTimeMillis() - now) + " ms");
        LocalBroadcastManager.getInstance(this).sendBroadcast(locationBroadcast);
        stopSelf();
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        synchronized (locking) {
            Log.d("FusedLocationService", "Location received successfully [" + location.getLatitude() + "," + location.getLongitude() + "] in " + (Long.valueOf(System.currentTimeMillis() - now)) + " ms");

            handler.removeCallbacks(onFusedLocationProviderTimeout);
            String mLatitudeTextView = String.valueOf(location.getLatitude());
            String mLongitudeTextView=String.valueOf(location.getLongitude());
            Log.i("dd",mLatitudeTextView+mLongitudeTextView);
            Toast.makeText(this,"updated: "+mLatitudeTextView + mLongitudeTextView,Toast.LENGTH_SHORT).show();

            if (mGoogleApiClient.isConnected())
                mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("FusedLocationService", "Error connecting to Fused Location Provider");
    }


}
