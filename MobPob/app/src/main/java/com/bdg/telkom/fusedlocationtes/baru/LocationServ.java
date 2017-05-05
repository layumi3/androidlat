package com.bdg.telkom.fusedlocationtes.baru;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
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
public class LocationServ extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    protected Location mCurrentLocation;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //Set the desired interval for active location updates, in milliseconds.
        mLocationRequest.setInterval(5 * 1000);
        //Explicitly set the fastest interval for location updates, in milliseconds.
        mLocationRequest.setFastestInterval(3 * 1000);
        //Set the minimum displacement between location updates in meters
        mLocationRequest.setSmallestDisplacement(1); // float
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onConnected(Bundle bundle) {
        if (mCurrentLocation == null) {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(3000);
//    mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,this);
        //Requests location updates from the FusedLocationApi.
        startLocationUpdates();
    }

    @Override
    public void onLocationChanged(Location location) {

        String mLatitudeTextView = String.valueOf(location.getLatitude());
        String mLongitudeTextView=String.valueOf(location.getLongitude());
        Log.i("LOCC",mLatitudeTextView+mLongitudeTextView);
        Toast.makeText(this,"updated: "+mLatitudeTextView + mLongitudeTextView,Toast.LENGTH_SHORT).show();

        mCurrentLocation = location;
        //Toast.makeText(this, ""+mCurrentLocation.getLatitude()+","+mCurrentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
        System.out.println("ffff" + mCurrentLocation.getLatitude() + "," + mCurrentLocation.getLongitude());
    }

    @Override
    public void onConnectionSuspended(int i) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        mGoogleApiClient.connect();
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), "Location services connection failed with code " + connectionResult.getErrorCode(), Toast.LENGTH_LONG).show();

    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onDestroy() {
//        super.onDestroy();
        mGoogleApiClient.disconnect();
        Toast.makeText(this, "MyService Stopped", Toast.LENGTH_LONG).show();
    }

}