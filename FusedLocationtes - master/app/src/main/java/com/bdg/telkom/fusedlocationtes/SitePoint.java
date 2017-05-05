package com.bdg.telkom.fusedlocationtes;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.Date;

/**
 * Created by lacorp on 6/6/2016.
 */
public class SitePoint extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener{

    private static final String TAG ="SitePoint";
    private TextView mLatitudeTextView;
    private TextView mLongitudeTextView;
    private DateFormat mLastUpdateTime;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.site_point);
        mLatitudeTextView =(TextView) findViewById(R.id.latitude_textview);
        mLongitudeTextView =(TextView) findViewById(R.id.longitude_textview);

        /*end*/
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(3000);
//    mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,this);
//        if (mLocation != null){
//            mLatitudeTextView.setText(String.valueOf(mLocation.getLatitude()));
//            mLongitudeTextView.setText(String.valueOf(mLocation.getLongitude()));
//        }else {
//            Toast.makeText(this, "Location not detected ", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastUpdateTime = new DateFormat();
        mLatitudeTextView.setText(String.valueOf(location.getLatitude()));
        mLongitudeTextView.setText(String.valueOf(location.getLongitude()));
          String mLatitudeTextView = String.valueOf(location.getLatitude());
        String mLongitudeTextView=String.valueOf(location.getLongitude());
        Toast.makeText(this,"updated: "+mLatitudeTextView + mLongitudeTextView,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
Log.i(TAG, "Connection failed error: " + connectionResult.getErrorCode());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect
                ();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
    }
}
