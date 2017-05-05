package com.bdg.telkom.fusedlocationtes.baru;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import java.util.List;

/**
 * Created by lacorp on 7/17/2016.
 */
public class Login extends AppCompatActivity{
    private EditText mUsernameView;
    private EditText mPasswordView;
    SessionMng mSession;
    String username;
    String password;
    SharedPreferences sh_Pref;
    android.content.SharedPreferences.Editor toEdit;
    private SessionManager session;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        mUsernameView = (EditText) findViewById(R.id.txtUsername);
        mPasswordView = (EditText) findViewById(R.id.txtPassword);
        session = new SessionManager(getApplicationContext());

    }
/*Exceute once button login action*/
    public void invokeLogin(View view){

        username = mUsernameView.getText().toString();
        password = mPasswordView.getText().toString();
//        mSession = new SessionMng(getApplicationContext());

        Log.i("Input",username+" "+password);
        if (mUsernameView.getText().toString().isEmpty() || mPasswordView.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter value in all field", Toast.LENGTH_SHORT).show();
        }
            sh_Pref = getSharedPreferences("Login Credential",MODE_PRIVATE);
            toEdit = sh_Pref.edit();
            toEdit.putString("Username", username);
            toEdit.putString("Password", password);
            toEdit.commit();
//            mSession.setPreferences(this,"status","1");
//            String status = mSession.getPreferences(this, "status");
//            Log.d("status", status);


        login(username,password);
    }

    private void login(final String username, final String password) {
        class LoginAsync extends AsyncTask<String, Void, String> {
            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(Login.this, "Please wait ..", "Loading");
            }

            @Override
            protected String doInBackground(String... params) {
                String username = params[0];
                String password = params[1];

                InputStream is= null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("username",username));
                nameValuePairs.add(new BasicNameValuePair("password",password));

                if (username.isEmpty() || password.isEmpty()){
                    Toast.makeText(Login.this, "Enter value in all field", Toast.LENGTH_SHORT).show();
                }
//                mSession.setPreferences(Login.this,"status","1");

//                String status = mSession.getPreferences(Login.this, "status");
//                Log.d("status", status);

                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://cerita-aje.esy.es/login.php");
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

            @Override
            protected void onPostExecute(String result) {
                String s = result.trim();
                loadingDialog.dismiss();
                if(s.equalsIgnoreCase("success")){
                    session.createUserLoginSession(username,
                            "Tracking");
                    Intent intent = new Intent(Login.this, AmbilOrder.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    // Add new Flag to start new Activity
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra("USERNAME", username);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"Invalid username or password",Toast.LENGTH_SHORT).show();
                }
                Log.i(username,"ini user" + username + password);

            }
        }
        LoginAsync la = new LoginAsync();
        la.execute(username, password);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Login.super.onBackPressed();
                    }
                }).create().show();
    }
}
