package com.bdg.telkom.fusedlocationtes.baru;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lacorp on 7/16/2016.
 */
public class LocationServ extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    protected Location mCurrentLocation;
    private SessionManager session;

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
        String lati = String.valueOf(location.getLatitude());
        String longi =String.valueOf(location.getLongitude());

        Log.i("LOCC",lati+longi);
        session = new SessionManager(getApplicationContext());

        session.checkLogin();
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        // name
        String users = user.get(SessionManager.KEY_USER_NAME);
        String order = user.get(SessionManager.KEY_ORDER);

        insert(lati, longi,users,order);
        Toast.makeText(this,"updated and Inserted "+lati+longi,Toast.LENGTH_SHORT).show();
    }

    private void insert(String lati, String longi, String users, String order) {
        class InsertAsync extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... params) {
                String lati = params[0];
                String longi = params[1];
                String users = params[2];
                String order = params[3];

                InputStream is= null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("lati", lati));
                nameValuePairs.add(new BasicNameValuePair("longi", longi));
                nameValuePairs.add(new BasicNameValuePair("user", users));
                nameValuePairs.add(new BasicNameValuePair("order", order));

                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://cerita-aje.esy.es/insertcom.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity= response.getEntity();
                    is= entity.getContent();
                    Log.i("pass 1", "connection success " + httpPost);

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"),8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line= reader.readLine())!=null){
                        sb.append(line+"\n");
                    }
                    result = sb.toString();
                }catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i(result,"result " +result);
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
//                Toast.makeText(getApplicationContext(),"Data Inserted",Toast.LENGTH_SHORT).show();
            }
        }
        InsertAsync ia = new InsertAsync();
        ia.execute(lati, longi, users,order);
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