package com.bdg.telkom.fusedlocationtes.baru;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by lacorp on 7/17/2016.
 */
public class SessionManager {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context cont;

    private int PRIVATE_MODE = 0;

    public static final String PREF_NAME = "login";
    public static final String KEY_USER_NAME = "userName";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_ORDER = "orderId";
    public static final String KEY_DRIVER = "driver";

    public static final String KEY_IS_LOGIN = "isLogin";


    // Constructor
    public SessionManager(Context context){
        this.cont = context;
        preferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    //Create login session
    public void createUserLoginSession(String name, String pass){
        // Storing login value as TRUE
        editor.putBoolean(KEY_IS_LOGIN, true);

        // Storing  pref
        editor.putString(KEY_USER_NAME, name);
        editor.putString(KEY_PASSWORD, pass);

        // commit changes
        editor.commit();
    }


    //Create login session
    public void createOrderSession(String orderId, String driver){
        // Storing login value as TRUE
        editor.putBoolean(KEY_IS_LOGIN, true);
        // Storing  pref
        editor.putString(KEY_ORDER, orderId);
        editor.putString(KEY_DRIVER, driver);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     * */
    public boolean checkLogin(){
        // Check login status
        if(!this.isUserLoggedIn()){

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(cont, Login.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            cont.startActivity(i);

            return true;
        }
        return false;
    }



    /**
     * Get stored session data
     * */
    public HashMap getUserDetails(){

        //Use hashmap to store user credentials
        HashMap user = new HashMap();

        // user name
        user.put(KEY_USER_NAME, preferences.getString(KEY_USER_NAME, null));

        // user email id
        user.put(KEY_PASSWORD, preferences.getString(KEY_PASSWORD, null));
        user.put(KEY_ORDER, preferences.getString(KEY_ORDER, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(cont, Login.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        cont.startActivity(i);
    }


    // Check for login
    public boolean isUserLoggedIn(){
        return preferences.getBoolean(KEY_IS_LOGIN, false);
    }
}
