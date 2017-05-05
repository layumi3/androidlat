package com.bdg.telkom.fusedlocationtes.baru;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
public class AmbilOrder extends AppCompatActivity{
    private EditText mNoOrder;
    private EditText mNamaDriver;
//    SessionMng mSession;
    SharedPreferences sh_Pref;
    android.content.SharedPreferences.Editor toEdit;
    private SessionManager session;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_layout);
        mNoOrder = (EditText) findViewById(R.id.noOrder);
        mNamaDriver = (EditText) findViewById(R.id.driver);
    }

    /*Exceute once button login action*/
    public void insertLocation(View view){
        String id_order  = mNoOrder.getText().toString();
        String pengemudi = mNamaDriver.getText().toString();
//        session.createOrderSession(id_order,
//                "Tracking");
        Bundle b = new Bundle();
        //Menyisipkan tipe data String ke dalam obyek bundle
        Intent intent = new Intent(AmbilOrder.this, Start.class);
//        intent.putExtra("message", message);
//                b.putString("id_order", id_order);
//                b.putString("driver", pengemudi);
        intent.putExtra("id_order",id_order);
        startActivity(intent);
/*
        sh_Pref = getSharedPreferences("Login Credential",MODE_PRIVATE);
        toEdit = sh_Pref.edit();
        toEdit.putString("Order", id_order);
        toEdit.putString("Pengemudi", pengemudi);
        toEdit.commit();
//        mSession = new SessionMng(getApplicationContext());
        insert(id_order, pengemudi);*/
    }

    private void insert(final String id_order, String pengemudi) {

                Toast.makeText(getApplicationContext(),"Data Inserted",Toast.LENGTH_SHORT).show();
                session.createOrderSession(id_order,
                        "Tracking");
                //Membuat obyek bundle
                Bundle b = new Bundle();

                //Menyisipkan tipe data String ke dalam obyek bundle
                Intent intent = new Intent(AmbilOrder.this, Start.class);
//                b.putString("id_order", id_order);
//                b.putString("driver", pengemudi);
                //Menambahkan bundle intent
                intent.putExtras(b);
                startActivity(intent);

    }
}
