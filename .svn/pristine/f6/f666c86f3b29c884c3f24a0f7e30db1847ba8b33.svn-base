package com.niveus.oen.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Adarsh on 17-Jun-17.
 */

public class PreferencesManager {
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;

    public PreferencesManager(Context context){
        this.settings = context.getSharedPreferences(Constants.PREF_NAME, 0);
        this.editor = settings.edit();
    }

    public void setToken(String token){
        editor.putString("token", token);
        editor.commit();
        editor.apply();
    }

    public String getToken(){
        return settings.getString("token", null);
    }

    public void setEmail(String email){
        editor.putString("email", email);
        editor.commit();
        editor.apply();
    }

    public String getEmail(){
        return settings.getString("email", null);
    }

    public void setUserId(int userId){
        editor.putInt("user_id", userId);
        editor.commit();
        editor.apply();
    }

    public int getUserId(){
        return settings.getInt("user_id", 0);
    }

    public void setCustomerId(int customerId){
        editor.putInt("customer_id", customerId);
        editor.commit();
        editor.apply();
    }

    public int getCustomerId(){
        return settings.getInt("customer_id", 0);
    }

    public void setHasLoggedIn(boolean hasLoggedIn){
        editor.putBoolean("has_logged_in", hasLoggedIn);
        editor.commit();
        editor.apply();
    }

    public boolean getHasLoggedIn(){
        return settings.getBoolean("has_logged_in", false);
    }

    public void setHasAddedDevices(boolean hasLoggedIn){
        editor.putBoolean("has_added_devices", hasLoggedIn);
        editor.commit();
        editor.apply();
    }

    public boolean getHasAddedDevices(){
        return settings.getBoolean("has_added_devices", false);
    }

}
