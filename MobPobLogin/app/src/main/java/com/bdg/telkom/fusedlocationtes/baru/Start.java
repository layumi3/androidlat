package com.bdg.telkom.fusedlocationtes.baru;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bdg.telkom.fusedlocationtes.R;

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
public class Start extends AppCompatActivity{

    SessionManager mSession;
    SharedPreferences shp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intentservice();
        setContentView(R.layout.logout_layout);
        mSession = new SessionManager(getApplicationContext());
        shp = this.getSharedPreferences("UserInfo", MODE_PRIVATE);
    }

    private void intentservice(){
        startService(new Intent(this, LocationServ.class));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void revokeLogin(View view){
        mSession = new SessionManager(getApplicationContext());
        mSession.checkLogin();
        HashMap<String, String> user = mSession.getUserDetails();
        String users = user.get(SessionManager.KEY_USER_NAME);
        String order = user.get(SessionManager.KEY_ORDER);

        Log.i("ss",users+order);
        dataKembali(users,order);

        SharedPreferences.Editor edit = shp.edit();
        edit.putBoolean("isUserLogin", true);
        edit.putString("UserId", "");
        edit.commit();


//        stopService(new Intent(Start.this, LocationServ.class));
        Toast.makeText(getApplicationContext(), "Anda Keluar Aplikasi", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Start.this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void setSelesai(View view){
        mSession = new SessionManager(getApplicationContext());
        mSession.checkLogin();
        HashMap<String, String> user = mSession.getUserDetails();
        String users = user.get(SessionManager.KEY_USER_NAME);
        String order = user.get(SessionManager.KEY_ORDER);

        Log.i("ss",users+order);
        dataSelesai(users,order);
/*

        SharedPreferences.Editor edit = shp.edit();
        edit.putBoolean("isUserLogin", true);
        edit.putString("UserId", "");
        edit.commit();
*/

        stopService(new Intent(Start.this, LocationServ.class));
        Button mSelesai;
        mSelesai= (Button) findViewById(R.id.btnSelesai);
        mSelesai.setEnabled(false);
/*set button dissable*/
       /* Intent intent = new Intent(Start.this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);*/
//        finish();
    }

    private void dataSelesai(final String users, final String order) {
        class selesaiAsync extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                String users = params[0];
                String order = params[1];

                InputStream is= null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("users",users));
                nameValuePairs.add(new BasicNameValuePair("order",order));

                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://cerita-aje.esy.es/selesai.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity= response.getEntity();
                    is= entity.getContent();

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
                Log.i(result,"restult ya" +result);
                return result;
            }

        }
        selesaiAsync la = new selesaiAsync();
        la.execute(users,order);
    }

    private void dataKembali(final String users, final String order) {
        class kembaliAsync extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                String users = params[0];
                String order = params[1];

                InputStream is= null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("users",users));
                nameValuePairs.add(new BasicNameValuePair("order",order));

                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://cerita-aje.esy.es/updatek.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity= response.getEntity();
                    is= entity.getContent();

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
                Log.i(result,"restult ya" +result);
                return result;
            }

        }
        kembaliAsync la = new kembaliAsync();
        la.execute(users,order);
    }

}
