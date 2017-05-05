package com.bdg.telkom.fusedlocationtes.baru;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bdg.telkom.fusedlocationtes.R;

/**
 * Created by lacorp on 7/16/2016.
 */
public class Start extends AppCompatActivity{
//    SessionMng mSession;
    SessionManager mSession;
    SharedPreferences shp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intentservice();

        setContentView(R.layout.logout_layout);

        TextView mOrde =(TextView) findViewById(R.id.id_order);
        Intent intent=getIntent();
        mOrde.setText(intent.getStringExtra("message"));
       /* Bundle b = getIntent().getExtras();
        //membuat obyek dari widget textview
        TextView id_order = (TextView) findViewById(R.id.id_order);
        TextView driver = (TextView) findViewById(R.id.driver);
        //menset nilai dari widget textview
        id_order.setText(b.getCharSequence("id_order"));
        driver.setText(b.getCharSequence("driver"));*/

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

//        mSession.setPreferences(Start.this,"status","0");

//        String status = mSession.getPreferences(Start.this, "status");
//        Log.d("status", status);

        SharedPreferences.Editor edit = shp.edit();
        edit.putBoolean("isUserLogin", true);
        edit.putString("UserId", "");
        edit.commit();
        stopService(new Intent(Start.this, LocationServ.class));
        Intent intent = new Intent(Start.this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}
