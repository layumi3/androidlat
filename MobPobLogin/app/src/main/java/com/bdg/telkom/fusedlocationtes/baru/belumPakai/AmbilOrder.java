package com.bdg.telkom.fusedlocationtes.baru.belumPakai;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bdg.telkom.fusedlocationtes.R;
import com.bdg.telkom.fusedlocationtes.baru.SessionManager;
import com.bdg.telkom.fusedlocationtes.baru.Start;

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
//                session.createOrderSession(id_order,
//                        "Tracking");
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
