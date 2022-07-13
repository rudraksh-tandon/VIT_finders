package com.ieeeias.vit_finders;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public PrefManager(Context context){
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("vit_finders", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setId(String id){
        editor.putString("userId", id);
        editor.apply();
    }

    public String getId(){
        return sharedPreferences.getString("userId", "true");
    }

    public void isLogin(boolean b){
        editor.putBoolean("true", b);
        editor.apply();
    }

    public Boolean getLogin(){
        return sharedPreferences.getBoolean("true", false);
    }
}
