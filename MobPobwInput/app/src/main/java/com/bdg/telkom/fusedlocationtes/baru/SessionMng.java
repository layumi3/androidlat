package com.bdg.telkom.fusedlocationtes.baru;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by lacorp on 7/17/2016.
 */
public class SessionMng {

    /*pls check diff*/
    public void setPreferences(Context context, String key, String value){
        SharedPreferences.Editor editor = context.getSharedPreferences("TrackingCar", Context.MODE_PRIVATE).edit();
        editor.putString(key,value);
        editor.commit();

    }

    public String getPreferences(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences("TrackingCar", Context.MODE_PRIVATE);
        String position = prefs.getString(key, "");
        return position;
    }


}
